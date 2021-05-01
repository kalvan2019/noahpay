<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--查询区-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道编号">
                <a-input v-decorator="['channelNo',{rules: []}]" placeholder="请输入渠道编号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道商户号">
                <a-input v-decorator="['channelMerchantNo',{rules: []}]" placeholder="请输入渠道商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="大商户轮询">
                <a-select show-search placeholder="请选择" v-decorator="['merchantPoolConvert',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="转换多商户">
                <a-select show-search placeholder="请选择" v-decorator="['multiMerchantConvert',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="转换发往银行流水号">
                <a-select show-search placeholder="请选择" v-decorator="['sendSnConvert',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="转换行别">
                <a-select show-search placeholder="请选择" v-decorator="['bankTypeConvert',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="转换银行账户类型">
                <a-select show-search placeholder="请选择" v-decorator="['bankAccountTypeConvert',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="转换证件类型">
                <a-select show-search placeholder="请选择" v-decorator="['certificateTypeConvert',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:channel:channelinfo:download')">下载</a-button>
        <a-button type="primary" icon="plus" @click="handleAdd" v-if="hasPerm('payment:channel:channelinfo:add')">新建</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="delete" @click="handleMenuClick" v-if="hasPerm('payment:channel:channelinfo:delete')">
              <a-icon type="delete"/>
              删除
            </a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            批量操作
            <a-icon type="down"/>
          </a-button>
        </a-dropdown>
        <a-button type="primary" icon="download" @click="handleDownloadTemplate" v-if="hasPerm('payment:channel:channelinfo:upload')">批量导入模板</a-button>
        <a-upload
          name="file"
          :customRequest="handleUpload"
        >
          <a-button type="primary" icon="upload" v-if="hasPerm('payment:channel:channelinfo:upload')">批量导入</a-button>
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
        :scroll="{ x: 2000}"
        @change="tableChange"
        size="small"
        bordered
      >
        <!--字段扩展显示customRender-->
        <template v-slot:merchantPoolConvert="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:multiMerchantConvert="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:sendSnConvert="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:bankTypeConvert="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:bankAccountTypeConvert="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:certificateTypeConvert="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:cityConvert="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:mccConvert="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:custConvert="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:state="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <!--行操作-->
        <template v-slot:action="text, record">
          <span>
            <a href="javascript:" @click="handleInfo(record)" v-if="hasPerm('payment:channel:channelinfo:info')">详情</a>
            <a-divider type="vertical"/>
            <a href="javascript:" @click="handleEdit(record)" v-if="hasPerm('payment:channel:channelinfo:edit')">编辑</a>
            <a-divider type="vertical"/>
            <a-popconfirm title="确定删除吗？" @confirm="handleDelete(record)" v-if="hasPerm('payment:channel:channelinfo:delete')">
              <a-icon slot="icon" type="question-circle-o" style="color: red"/>
              <a href="javascript:">删除</a>
            </a-popconfirm>
            <a-divider type="vertical"/>
          </span>
        </template>
      </a-table>
      <!--自定义组件-->
      <save ref="save" @refresh="refresh" />
      <info ref="info" @refresh="refresh" />
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, downloadFile, downloadTemplateFile, uploadFile, deleteByIds } from '@/api/payment/channel/channelinfo'
  import save from './save'
  import info from './info'
  export default {
    mixins: [curdMixins],
    components: {
      save,
      info
    },
    data () {
      this.columns = [
        {
          title: '渠道编号',
          dataIndex: 'channelNo'
        },
        {
          title: '渠道名称',
          dataIndex: 'channelName'
        },
        {
          title: '实现类名',
          dataIndex: 'channelClass'
        },
        {
          title: '渠道商户号',
          dataIndex: 'channelMerchantNo'
        },
        {
          title: '大商户轮询',
          dataIndex: 'merchantPoolConvert',
          scopedSlots: { customRender: 'merchantPoolConvert' }
        },
        {
          title: '转换多商户',
          dataIndex: 'multiMerchantConvert',
          scopedSlots: { customRender: 'multiMerchantConvert' }
        },
        {
          title: '转换发往银行流水号',
          dataIndex: 'sendSnConvert',
          scopedSlots: { customRender: 'sendSnConvert' }
        },
        {
          title: '转换行别',
          dataIndex: 'bankTypeConvert',
          scopedSlots: { customRender: 'bankTypeConvert' }
        },
        {
          title: '转换银行账户类型',
          dataIndex: 'bankAccountTypeConvert',
          scopedSlots: { customRender: 'bankAccountTypeConvert' }
        },
        {
          title: '转换证件类型',
          dataIndex: 'certificateTypeConvert',
          scopedSlots: { customRender: 'certificateTypeConvert' }
        },
        {
          title: '转换城市代码',
          dataIndex: 'cityConvert',
          scopedSlots: { customRender: 'cityConvert' }
        },
        {
          title: '行业类目转换',
          dataIndex: 'mccConvert',
          scopedSlots: { customRender: 'mccConvert' }
        },
        {
          title: '转换客户号类型',
          dataIndex: 'custConvert',
          scopedSlots: { customRender: 'custConvert' }
        },
        {
          title: '支持对账 0-支持对账;1-不支持',
          dataIndex: 'checkSupport'
        },
        {
          title: '对账时间',
          dataIndex: 'checkTime'
        },
        {
          title: '对账凭证',
          dataIndex: 'checkField'
        },
        {
          title: '状态',
          dataIndex: 'state',
          scopedSlots: { customRender: 'state' }
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
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
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
      }
    }
  }
</script>
<style scoped>
</style>
