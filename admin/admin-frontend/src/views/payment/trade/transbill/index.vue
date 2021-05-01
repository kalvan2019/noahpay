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
              <a-form-item label="交易类型">
                <a-select show-search placeholder="请选择" v-decorator="['transType',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.trans_type" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="订单号">
                <a-input v-decorator="['orderId',{rules: []}]" placeholder="请输入订单号"/>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="订单日期">
                <a-input type="hidden" v-decorator="['orderDate',{initialValue:initOrderDate,rules: [{ required: true, message: '订单日期不能为空！' }]}]"/>
                <a-range-picker
                  allowClear
                  :defaultValue="initOrderDate"
                  format="YYYYMMDD"
                  show-time
                  @change="onOrderDateChange"
                />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="订单金额">
                <span :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-input v-decorator="['orderAmountBegin',{rules: []}]" placeholder="请输入订单金额"/>
                </span>
                -
                <span :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-input v-decorator="['orderAmountEnd',{rules: []}]" placeholder="请输入订单金额"/>
                </span>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="商户号">
                <a-input v-decorator="['merchantNo',{rules: []}]" placeholder="请输入商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="记账流水号">
                <a-input v-decorator="['accountTransId',{rules: []}]" placeholder="请输入记账流水号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="会计日期">
                <a-input v-decorator="['accountDate',{rules: []}]" placeholder="请输入会计日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="冲正记账流水号">
                <a-input v-decorator="['undoAccountTransId',{rules: []}]" placeholder="请输入冲正记账流水号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="冲正会计日期">
                <a-input v-decorator="['undoAccountDate',{rules: []}]" placeholder="请输入冲正会计日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="支付模式">
                <a-select show-search placeholder="请选择" v-decorator="['payModel',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.pay_model" :key="key">
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
              <a-form-item label="支付网关返回码">
                <a-input v-decorator="['payResultCode',{rules: []}]" placeholder="请输入支付网关返回码"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易状态">
                <a-select show-search placeholder="请选择" v-decorator="['state',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.trans_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="通知状态">
                <a-select show-search placeholder="请选择" v-decorator="['notifyState',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.notify_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="对账状态">
                <a-select show-search placeholder="请选择" v-decorator="['checkState',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.check_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="创建时间">
                <a-input type="hidden" v-decorator="['createTime',{rules: []}]"/>
                <a-range-picker
                  allowClear
                  format="YYYY-MM-DD HH:mm:ss"
                  show-time
                  @change="onCreateTimeChange"
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:trade:transbill:download')">下载</a-button>
        <a-button type="primary" @click="handleNotifyMerchant" v-if="hasPerm('payment:trade:transbill:notifyMerchant')">补单</a-button>
        <a-button type="primary" @click="handleSyncQuery" v-if="hasPerm('payment:trade:transbill:syncQuery')">同步状态</a-button>
      </div>
      <!--汇总-->
      <a-alert showIcon>
        <template slot="message">
          <span style="margin-right: 12px">已选择: <a style="font-weight: 600">{{ this.selectedRowKeys.length }}</a>记录</span>
          <template v-if="hasPerm('payment:trade:transbill:sum')">
            <span style="margin-right: 12px">总订单金额: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.orderAmount, 100, 2) }}</a></span>
            <span style="margin-right: 12px">总商户手续费: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.merchantFee, 100, 2) }}</a></span>
            <span style="margin-right: 12px">总客户手续费: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.consumerFee, 100, 2) }}</a></span>
            <span style="margin-right: 12px">总结算金额: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.settlementAmount, 100, 2) }}</a></span>
          </template>
        </template>
      </a-alert>
      <!--数据表格-->
      <a-table
        :rowKey=" (record => record.transId)"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="pagination"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        :loading="loading"
        :scroll="{ x: 2300}"
        @change="tableChange"
        size="small"
        bordered
      >
        <!--字段扩展显示customRender-->
        <template v-slot:transType="text">
          {{ dictFilter(text,'trans_type') }}
        </template>
        <template v-slot:payModel="text">
          {{ dictFilter(text,'pay_model') }}
        </template>
        <template v-slot:payType="text">
          {{ dictFilter(text,'pay_type') }}
        </template>
        <template v-slot:state="text">
          <a-badge :status="text | dictIconFilter(that,'trans_state')" :text="text | dictFilter(that,'trans_state')" />
        </template>
        <template v-slot:notifyState="text">
          {{ dictFilter(text,'notify_state') }}
        </template>
        <template v-slot:checkState="text">
          {{ dictFilter(text,'check_state') }}
        </template>
        <!--行操作-->
        <template v-slot:action="text, record">
          <span>
            <a href="javascript:" @click="handleInfo(record)" v-if="hasPerm('payment:trade:transbill:info')">详情</a>
            <a-divider type="vertical"/>
            <a href="javascript:" @click="handleNotifyMerchant(record)" v-if="hasPerm('payment:trade:transbill:notifyMerchant')">补单</a>
            <a-divider type="vertical"/>
            <a href="javascript:" @click="handleSyncQuery(record)" v-if="hasPerm('payment:trade:transbill:syncQuery')">同步状态</a>
            <a-divider type="vertical"/>
          </span>
        </template>
      </a-table>
      <!--自定义组件-->
      <info ref="info" @refresh="refresh" />
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, getSum, downloadFile, notifyMerchant, syncQuery } from '@/api/payment/trade/transbill'
  import info from './info'
  export default {
    mixins: [curdMixins],
    components: {
      info
    },
    data () {
      this.columns = [
        {
          title: '交易流水号',
          dataIndex: 'transId',
          fixed: 'left'
        },
        {
          title: '交易状态',
          dataIndex: 'state',
          scopedSlots: { customRender: 'state' },
          fixed: 'left'
        },
        {
          title: '交易类型',
          dataIndex: 'transType',
          scopedSlots: { customRender: 'transType' }
        },
        {
          title: '订单号',
          dataIndex: 'orderId'
        },
        {
          title: '订单日期',
          dataIndex: 'orderDate'
        },
        {
          title: '订单金额',
          dataIndex: 'orderAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '商户号',
          dataIndex: 'merchantNo'
        },
        {
          title: '商户手续费',
          dataIndex: 'merchantFee',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '客户手续费',
          dataIndex: 'consumerFee',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '结算金额',
          dataIndex: 'settlementAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '支付模式',
          dataIndex: 'payModel',
          scopedSlots: { customRender: 'payModel' }
        },
        {
          title: '支付类型',
          dataIndex: 'payType',
          scopedSlots: { customRender: 'payType' }
        },
        {
          title: '支付流水号',
          dataIndex: 'payId'
        },
        {
          title: '返回码',
          dataIndex: 'payResultCode'
        },
        {
          title: '返回描述',
          dataIndex: 'payResultNote'
        },
        {
          title: '记账流水号',
          dataIndex: 'accountTransId'
        },
        {
          title: '会计日期',
          dataIndex: 'accountDate'
        },
        {
          title: '通知状态',
          dataIndex: 'notifyState',
          scopedSlots: { customRender: 'notifyState' }
        },
        {
          title: '对账状态',
          dataIndex: 'checkState',
          scopedSlots: { customRender: 'checkState' }
        },
        {
          title: '失效时间',
          dataIndex: 'orderExpiryTime'
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
        trans_type: {},
        initOrderDate: [this.moment().add(-1, 'd').format('YYYYMMDD'), this.moment().add(1, 'd').format('YYYYMMDD')],
        pay_model: {},
        pay_type: {},
        trans_state: {},
        notify_state: {},
        check_state: {},
        genTemp: null
      }
    },
    created () {
      // 获取字典数据
      this.getDictValue('trans_type').then(res => {
        this.trans_type = res.data
      })
      // 获取字典数据
      this.getDictValue('pay_model').then(res => {
        this.pay_model = res.data
      })
      // 获取字典数据
      this.getDictValue('pay_type').then(res => {
        this.pay_type = res.data
      })
      // 获取字典数据
      this.getDictValue('trans_state').then(res => {
        this.trans_state = res.data
      })
      // 获取字典数据
      this.getDictValue('notify_state').then(res => {
        this.notify_state = res.data
      })
      // 获取字典数据
      this.getDictValue('check_state').then(res => {
        this.check_state = res.data
      })
    },
    methods: {
      // 表单参数过滤,mixins调用
      filterForm (values) {
       values.orderAmountBegin = values.orderAmountBegin * 100
       values.orderAmountEnd = values.orderAmountEnd * 100
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
      // 自定义方法
      handleNotifyMerchant (param) {
        notifyMerchant(param).then(res => {
          if (res.state === 0) {
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      // 自定义方法
      handleSyncQuery (param) {
        syncQuery(param).then(res => {
          if (res.state === 0) {
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      },
       // 日期格式转换
      onOrderDateChange (date, dateString) {
         this.form.setFieldsValue({ 'orderDate': dateString + '' })
      },
       // 日期格式转换
      onCreateTimeChange (date, dateString) {
         this.form.setFieldsValue({ 'createTime': dateString + '' })
      }
    }
  }
</script>
<style scoped>
</style>
