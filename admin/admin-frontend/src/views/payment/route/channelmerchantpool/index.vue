<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--查询区-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道编号">
                <a-select show-search placeholder="请选择" v-decorator="['channelNo',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.channel_no" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道商户号">
                <a-input v-decorator="['channelMerchantNo',{rules: []}]" placeholder="请输入渠道商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道子级商户号">
                <a-input v-decorator="['channelSubMerchantNo',{rules: []}]" placeholder="请输入渠道子级商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="子商户名称">
                <a-input v-decorator="['channelSubMerchantName',{rules: []}]" placeholder="请输入子商户名称"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="商户号">
                <a-input v-decorator="['merchantNo',{rules: []}]" placeholder="请输入商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="开始时间">
                <a-input v-decorator="['beginTime',{rules: []}]" placeholder="请输入开始时间"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="结束时间">
                <a-input v-decorator="['endTime',{rules: []}]" placeholder="请输入结束时间"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="单笔金额下限">
                <a-input v-decorator="['limitMinAmount',{rules: []}]" placeholder="请输入单笔金额下限"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="单笔金额上限">
                <a-input v-decorator="['limitMaxAmount',{rules: []}]" placeholder="请输入单笔金额上限"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="更新时间">
                <a-input v-decorator="['updateTime',{rules: []}]" placeholder="请输入更新时间"/>
              </a-form-item>
            </a-col>
            <!--隐藏查询条件-->
            <template v-if="advanced">
            </template>
            <a-col :md="!advanced && 8 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="handleSearch">查询</a-button>
                <a-button style="margin-left: 8px" @click="handleReset">重置</a-button>
                <a @click="() => this.advanced = !this.advanced" style="margin-left: 8px">
                  {{ advanced ? '收起' : '展开' }}
                  <a-icon :type="advanced ? 'up' : 'down'"/>
                </a>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <!--全局操作-->
      <div class="table-operator">
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:route:channelmerchantpool:download')">下载</a-button>
        <a-button type="primary" icon="plus" @click="handleAdd" v-if="hasPerm('payment:route:channelmerchantpool:add')">新建</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="delete" @click="handleMenuClick" v-if="hasPerm('payment:route:channelmerchantpool:delete')">
              <a-icon type="delete"/>
              删除
            </a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            批量操作
            <a-icon type="down"/>
          </a-button>
        </a-dropdown>
        <a-button type="primary" icon="download" @click="handleDownloadTemplate" v-if="hasPerm('payment:route:channelmerchantpool:upload')">批量导入模板</a-button>
        <a-upload
          name="file"
          :customRequest="handleUpload"
        >
          <a-button type="primary" icon="upload" v-if="hasPerm('payment:route:channelmerchantpool:upload')">批量导入</a-button>
        </a-upload>
      </div>
      <!--汇总-->
      <a-alert showIcon>
        <template slot="message">
          <span style="margin-right: 12px">已选择: <a style="font-weight: 600">{{ this.selectedRowKeys.length }}</a>记录</span>
          <template v-if="hasPerm('payment:route:channelmerchantpool:sum')">
            <span style="margin-right: 12px">总单笔金额下限: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.limitMinAmount, 100, 2) }}</a></span>
            <span style="margin-right: 12px">总单笔金额上限: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.limitMaxAmount, 100, 2) }}</a></span>
            <span style="margin-right: 12px">总日使用金额: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.dayUseAmount, 100, 2) }}</a></span>
            <span style="margin-right: 12px">总日限额: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.dayLimitAmount, 100, 2) }}</a></span>
            <span style="margin-right: 12px">总月使用金额: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.monthUseAmount, 100, 2) }}</a></span>
            <span style="margin-right: 12px">总月金额限额: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.monthLimitAmount, 100, 2) }}</a></span>
          </template>
        </template>
      </a-alert>
      <!--数据表格-->
      <a-table
        :rowKey=" (record => record.id)"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="pagination"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        :loading="loading"
        :scroll="{ x: 2000}"
        @change="tableChange"
        size="small"
        bordered
      >
        <!--字段扩展显示customRender-->
        <template v-slot:channelNo="text">
          {{ dictFilter(text,'channel_no') }}
        </template>
        <template v-slot:state="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <!--行操作-->
        <template v-slot:action="text, record">
          <span>
            <a href="javascript:" @click="handleEdit(record)" v-if="hasPerm('payment:route:channelmerchantpool:edit')">编辑</a>
            <a-divider type="vertical"/>
            <a-popconfirm title="确定删除吗？" @confirm="handleDelete(record)" v-if="hasPerm('payment:route:channelmerchantpool:delete')">
              <a-icon slot="icon" type="question-circle-o" style="color: red"/>
              <a href="javascript:">删除</a>
            </a-popconfirm>
            <a-divider type="vertical"/>
          </span>
        </template>
      </a-table>
      <!--自定义组件-->
      <save ref="save" @refresh="refresh" />
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, getSum, downloadFile, downloadTemplateFile, uploadFile, deleteByIds } from '@/api/payment/route/channelmerchantpool'
  import save from './save'
  export default {
    mixins: [curdMixins],
    components: {
      save
    },
    data () {
      this.columns = [
        {
          title: '渠道编号',
          dataIndex: 'channelNo',
          scopedSlots: { customRender: 'channelNo' }
        },
        {
          title: '渠道商户号',
          dataIndex: 'channelMerchantNo'
        },
        {
          title: '渠道子级商户号',
          dataIndex: 'channelSubMerchantNo'
        },
        {
          title: '子商户名称',
          dataIndex: 'channelSubMerchantName'
        },
        {
          title: '商户号',
          dataIndex: 'merchantNo'
        },
        {
          title: '城市代码',
          dataIndex: 'city'
        },
        {
          title: '行业类别',
          dataIndex: 'mcc'
        },
        {
          title: '开始时间',
          dataIndex: 'beginTime'
        },
        {
          title: '结束时间',
          dataIndex: 'endTime'
        },
        {
          title: '单笔金额下限',
          dataIndex: 'limitMinAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '单笔金额上限',
          dataIndex: 'limitMaxAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '日使用金额',
          dataIndex: 'dayUseAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '日限额',
          dataIndex: 'dayLimitAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '月使用金额',
          dataIndex: 'monthUseAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '月金额限额',
          dataIndex: 'monthLimitAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '商户状态',
          dataIndex: 'state',
          scopedSlots: { customRender: 'state' }
        },
        {
          title: '备注',
          dataIndex: 'remark'
        },
        {
          title: '创建时间',
          dataIndex: 'createTime'
        },
        {
          title: '更新时间',
          dataIndex: 'updateTime'
        },
        {
          title: '操作',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ]
      return {
        // 多选操作
        multiOperator: [
          {
            key: 'delete',
            method: deleteByIds
          }
        ],
        channel_no: {},
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      // 获取字典数据
      this.getDictValue('channel_no').then(res => {
        this.channel_no = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
    },
    methods: {
      // 表单参数过滤,mixins调用
      filterForm (values) {
      values.limitMinAmount = values.limitMinAmount * 100
      values.limitMaxAmount = values.limitMaxAmount * 100
        return values
      },
      // 列表查询,mixins调用
      list (param) {
        return getList(param)
      },
      // 汇总查询,mixins调用
      sum (param) {
          return getSum(param)
      },
      // 下载list,mixins调用
      download (param) {
        return downloadFile(param)
      },
      // 导入模板下载,mixins调用
      downloadTemplate () {
        return downloadTemplateFile()
      },
      // 导入,mixins调用
      upload (file) {
        return uploadFile(file)
      },
      // 删除,mixins调用
      delete (record) {
        return deleteByIds({ ids: [record.id] })
      }
    }
  }
</script>
<style scoped>
</style>
