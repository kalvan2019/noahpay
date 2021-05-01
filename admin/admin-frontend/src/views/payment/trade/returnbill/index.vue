<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--查询区-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="交易流水号">
                <a-input v-decorator="['transId',{rules: []}]" placeholder="请输入交易流水号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易日期">
                <a-input v-decorator="['transDate',{rules: []}]" placeholder="请输入交易日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="商户号">
                <a-input v-decorator="['merchantNo',{rules: []}]" placeholder="请输入商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易会计日期">
                <a-input v-decorator="['transAccountDate',{rules: []}]" placeholder="请输入交易会计日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="退票日期">
                <a-input v-decorator="['returnTicketDate',{rules: []}]" placeholder="请输入退票日期"/>
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
        <a-button type="primary" icon="plus" @click="handleAdd" v-if="hasPerm('payment:trade:returnbill:add')">新建</a-button>
        <a-button type="primary" icon="download" @click="handleDownloadTemplate" v-if="hasPerm('payment:trade:returnbill:upload')">批量导入模板</a-button>
        <a-upload
          name="file"
          :customRequest="handleUpload"
        >
          <a-button type="primary" icon="upload" v-if="hasPerm('payment:trade:returnbill:upload')">批量导入</a-button>
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
        :scroll="{ x: 1500}"
        @change="tableChange"
        size="small"
        bordered
      >
        <!--字段扩展显示customRender-->
        <!--行操作-->
        <template v-slot:action="text, record">
          <span>
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
  import { getList, downloadTemplateFile, uploadFile } from '@/api/payment/trade/returnbill'
  import save from './save'
  export default {
    mixins: [curdMixins],
    components: {
      save
    },
    data () {
      this.columns = [
        {
          title: '交易流水号',
          dataIndex: 'transId'
        },
        {
          title: '交易日期',
          dataIndex: 'transDate'
        },
        {
          title: '订单号',
          dataIndex: 'orderId'
        },
        {
          title: '订单金额',
          dataIndex: 'orderAmount'
        },
        {
          title: '商户号',
          dataIndex: 'merchantNo'
        },
        {
          title: '商户手续费',
          dataIndex: 'merchantFee'
        },
        {
          title: '子商户号',
          dataIndex: 'subMerchantNo'
        },
        {
          title: '子商户手续费',
          dataIndex: 'subMerchantFee'
        },
        {
          title: '交易会计日期',
          dataIndex: 'transAccountDate'
        },
        {
          title: '退票日期',
          dataIndex: 'returnTicketDate'
        },
        {
          title: '退票备注',
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
        initUpdateTime: [this.moment().add(-1, 'd').format('YYYY-MM-DD'), this.moment().add(1, 'd').format('YYYY-MM-DD')],
        genTemp: null
      }
    },
    created () {
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
      // 导入模板下载,mixins调用
      downloadTemplate () {
        return downloadTemplateFile()
      },
      // 导入,mixins调用
      upload (file) {
        return uploadFile(file)
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
