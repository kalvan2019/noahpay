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
              <a-form-item label="支付类型">
                <a-select show-search placeholder="请选择" v-decorator="['payType',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.pay_type" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道返回码">
                <a-input v-decorator="['channelReturnCode',{rules: []}]" placeholder="请输入渠道返回码"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="转换返回码">
                <a-input v-decorator="['returnCode',{rules: []}]" placeholder="请输入转换返回码"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易状态">
                <a-select show-search placeholder="请选择" v-decorator="['transState',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.trans_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="更新时间">
                <a-input type="hidden" v-decorator="['updateTime',{initialValue:initUpdateTime+'',rules: []}]"/>
                <a-range-picker
                  allowClear
                  :defaultValue="initUpdateTime"
                  format="YYYY-MM-DD HH:mm:ss"
                  show-time
                  @change="onUpdateTimeChange"
                />
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:channel:channelreturncode:download')">下载</a-button>
        <a-button type="primary" icon="plus" @click="handleAdd" v-if="hasPerm('payment:channel:channelreturncode:add')">新建</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="delete" @click="handleMenuClick" v-if="hasPerm('payment:channel:channelreturncode:delete')">
              <a-icon type="delete"/>
              删除
            </a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            批量操作
            <a-icon type="down"/>
          </a-button>
        </a-dropdown>
        <a-button type="primary" icon="download" @click="handleDownloadTemplate" v-if="hasPerm('payment:channel:channelreturncode:upload')">批量导入模板</a-button>
        <a-upload
          name="file"
          :customRequest="handleUpload"
        >
          <a-button type="primary" icon="upload" v-if="hasPerm('payment:channel:channelreturncode:upload')">批量导入</a-button>
        </a-upload>
      </div>
      <!--汇总-->
      <!--数据表格-->
      <a-table
        :rowKey=" (record => record.id)"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="pagination"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        :loading="loading"
        @change="tableChange"
        size="small"
        bordered
      >
        <!--字段扩展显示customRender-->
        <template v-slot:channelNo="text">
          {{ dictFilter(text,'channel_no') }}
        </template>
        <template v-slot:payType="text">
          {{ dictFilter(text,'pay_type') }}
        </template>
        <template v-slot:transState="text">
          {{ dictFilter(text,'trans_state') }}
        </template>
        <!--行操作-->
        <template v-slot:action="text, record">
          <span>
            <a href="javascript:" @click="handleEdit(record)" v-if="hasPerm('payment:channel:channelreturncode:edit')">编辑</a>
            <a-divider type="vertical"/>
            <a-popconfirm title="确定删除吗？" @confirm="handleDelete(record)" v-if="hasPerm('payment:channel:channelreturncode:delete')">
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
  import { getList, downloadFile, downloadTemplateFile, uploadFile, deleteByIds } from '@/api/payment/channel/channelreturncode'
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
          title: '支付类型',
          dataIndex: 'payType',
          scopedSlots: { customRender: 'payType' }
        },
        {
          title: '渠道返回码',
          dataIndex: 'channelReturnCode'
        },
        {
          title: '渠道返回备注',
          dataIndex: 'channelResultNote'
        },
        {
          title: '转换返回码',
          dataIndex: 'returnCode'
        },
        {
          title: '转换返回说明',
          dataIndex: 'returnNote'
        },
        {
          title: '交易状态',
          dataIndex: 'transState',
          scopedSlots: { customRender: 'transState' }
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
        pay_type: {},
        trans_state: {},
        initUpdateTime: [this.moment().add(-1, 'd').format('YYYY-MM-DD'), this.moment().add(1, 'd').format('YYYY-MM-DD')],
        genTemp: null
      }
    },
    created () {
      // 获取字典数据
      this.getDictValue('channel_no').then(res => {
        this.channel_no = res.data
      })
      // 获取字典数据
      this.getDictValue('pay_type').then(res => {
        this.pay_type = res.data
      })
      // 获取字典数据
      this.getDictValue('trans_state').then(res => {
        this.trans_state = res.data
      })
    },
    methods: {
      // 表单参数过滤,mixins调用
      filterForm (values) {
        return values
      },
      // 列表查询,mixins调用
      list (param) {
        return getList(param)
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
      },
       // 日期格式转换
      onUpdateTimeChange (date, dateString) {
         this.form.setFieldsValue({ 'updateTime': dateString + '' })
      }
    }
  }
</script>
<style scoped>
</style>
