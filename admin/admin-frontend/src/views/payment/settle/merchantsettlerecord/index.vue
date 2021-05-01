<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--查询区-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="订单批次号">
                <a-input v-decorator="['batchId',{rules: []}]" placeholder="请输入订单批次号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="提现订单号">
                <a-input v-decorator="['orderId',{rules: []}]" placeholder="请输入提现订单号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易流水号">
                <a-input v-decorator="['transId',{rules: []}]" placeholder="请输入交易流水号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="商户号">
                <a-input v-decorator="['merchantNo',{rules: []}]" placeholder="请输入商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="提现日期">
                <a-input v-decorator="['settleDate',{rules: []}]" placeholder="请输入提现日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="提现审核状态：0：通过；1.待审；2.不通过">
                <a-input v-decorator="['state',{rules: []}]" placeholder="请输入提现审核状态：0：通过；1.待审；2.不通过"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易状态">
                <a-input v-decorator="['transState',{rules: []}]" placeholder="请输入交易状态"/>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="修改时间">
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:settle:merchantsettlerecord:download')">下载</a-button>
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
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, downloadFile } from '@/api/payment/settle/merchantsettlerecord'
  export default {
    mixins: [curdMixins],
    components: {
    },
    data () {
      this.columns = [
        {
          title: '订单批次号',
          dataIndex: 'batchId'
        },
        {
          title: '提现订单号',
          dataIndex: 'orderId'
        },
        {
          title: '交易流水号',
          dataIndex: 'transId'
        },
        {
          title: '商户号',
          dataIndex: 'merchantNo'
        },
        {
          title: '银行账户人',
          dataIndex: 'bankAccountName'
        },
        {
          title: '银行帐号',
          dataIndex: 'bankAccountNo'
        },
        {
          title: '证件号',
          dataIndex: 'certificateNo'
        },
        {
          title: '银行帐户类型',
          dataIndex: 'bankAccountType'
        },
        {
          title: '行名',
          dataIndex: 'bankName'
        },
        {
          title: '银行类别',
          dataIndex: 'bankType'
        },
        {
          title: '手机号',
          dataIndex: 'mobile'
        },
        {
          title: '提现金额',
          dataIndex: 'settleAmount'
        },
        {
          title: '提现手续费',
          dataIndex: 'settleMerchantFee'
        },
        {
          title: '提现日期',
          dataIndex: 'settleDate'
        },
        {
          title: '提现审核状态：0：通过；1.待审；2.不通过',
          dataIndex: 'state'
        },
        {
          title: '交易状态',
          dataIndex: 'transState'
        },
        {
          title: '交易返回备注',
          dataIndex: 'resultNote'
        },
        {
          title: '创建时间',
          dataIndex: 'createTime'
        },
        {
          title: '修改时间',
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
      // 下载list,mixins调用
      download (param) {
        return downloadFile(param)
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
