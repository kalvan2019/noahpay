<template>
  <a-modal
    :title="`交易订单` + title"
    :width="800"
    destroyOnClose
    centered
    :visible="visible"
    :confirmLoading="loading"
    @ok="visible=!visible"
    @cancel="visible=!visible"
  >
    <a-card :bordered="true">
      <a-descriptions title="">
        <a-descriptions-item label="自增id">{{ model.transId }}</a-descriptions-item>
        <a-descriptions-item label="交易流水号">{{ model.transId }}</a-descriptions-item>
        <a-descriptions-item label="交易日期">{{ model.transDate }}</a-descriptions-item>
        <a-descriptions-item label="交易类型">{{ this.dictFilter(model.transType,'trans_type') }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="订单号">{{ model.orderId }}</a-descriptions-item>
        <a-descriptions-item label="订单日期">{{ model.orderDate }}</a-descriptions-item>
        <a-descriptions-item label="订单说明">{{ model.orderNote }}</a-descriptions-item>
        <a-descriptions-item label="订单金额">{{ this.amountFix(model.orderAmount, 100, 2) }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="域名">{{ model.orderDomain }}</a-descriptions-item>
        <a-descriptions-item label="商户端IP">{{ model.orderIp }}</a-descriptions-item>
        <a-descriptions-item label="设备信息">{{ model.orderDeviceInfo }}</a-descriptions-item>
        <a-descriptions-item label="行业类别">{{ model.orderMcc }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="城市代码">{{ model.orderCity }}</a-descriptions-item>
        <a-descriptions-item label="失效时间">{{ model.orderExpiryTime }}</a-descriptions-item>
        <a-descriptions-item label="页面通知地址">{{ model.notifyPgUrl }}</a-descriptions-item>
        <a-descriptions-item label="后台通知地址">{{ model.notifyBgUrl }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="商户号">{{ model.merchantNo }}</a-descriptions-item>
        <a-descriptions-item label="商户名">{{ model.merchantName }}</a-descriptions-item>
        <a-descriptions-item label="商户客户号">{{ model.merchantCustNo }}</a-descriptions-item>
        <a-descriptions-item label="商户账户号">{{ model.merchantAccountNo }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="商户手续费">{{ this.amountFix(model.merchantFee, 100, 2) }}</a-descriptions-item>
        <a-descriptions-item label="子商户号">{{ model.subMerchantNo }}</a-descriptions-item>
        <a-descriptions-item label="子商户客户号">{{ model.subMerchantCustNo }}</a-descriptions-item>
        <a-descriptions-item label="子商户账户号">{{ model.subMerchantAccountNo }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="子商户手续费">{{ this.amountFix(model.subMerchantFee, 100, 2) }}</a-descriptions-item>
        <a-descriptions-item label="客户手续费">{{ this.amountFix(model.consumerFee, 100, 2) }}</a-descriptions-item>
        <a-descriptions-item label="结算金额">{{ this.amountFix(model.settlementAmount, 100, 2) }}</a-descriptions-item>
        <a-descriptions-item label="记账流水号">{{ model.accountTransId }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="账务记账时间">{{ model.accountTransTime }}</a-descriptions-item>
        <a-descriptions-item label="会计日期">{{ model.accountDate }}</a-descriptions-item>
        <a-descriptions-item label="冲正记账流水号">{{ model.undoAccountTransId }}</a-descriptions-item>
        <a-descriptions-item label="冲正记账时间">{{ model.undoAccountTransTime }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="冲正会计日期">{{ model.undoAccountDate }}</a-descriptions-item>
        <a-descriptions-item label="支付模式">{{ this.dictFilter(model.payModel,'pay_model') }}</a-descriptions-item>
        <a-descriptions-item label="支付类型">{{ this.dictFilter(model.payType,'pay_type') }}</a-descriptions-item>
        <a-descriptions-item label="支付流水号">{{ model.payId }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="路由扩展数据">{{ model.payRouteInfo }}</a-descriptions-item>
        <a-descriptions-item label="支付网关返回码">{{ model.payResultCode }}</a-descriptions-item>
        <a-descriptions-item label="支付网关备注">{{ model.payResultNote }}</a-descriptions-item>
        <a-descriptions-item label="交易状态">{{ this.dictFilter(model.state,'trans_state') }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="通知状态">{{ this.dictFilter(model.notifyState,'notify_state') }}</a-descriptions-item>
        <a-descriptions-item label="对账状态">{{ this.dictFilter(model.checkState,'check_state') }}</a-descriptions-item>
        <a-descriptions-item label="创建时间">{{ model.createTime }}</a-descriptions-item>
        <a-descriptions-item label="更新时间">{{ model.updateTime }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
    </a-card>
  </a-modal>
</template>
<script>
  import { baseMixin } from '@/store/app-mixin'
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getInfo } from '@/api/payment/trade/transbill'
  export default {
    mixins: [baseMixin, detailMixins],
    data () {
      return {
        trans_type: {},
        pay_model: {},
        pay_type: {},
        trans_state: {},
        notify_state: {},
        check_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('trans_type').then(res => {
        this.trans_type = res.data
      })
      this.getDictValue('pay_model').then(res => {
        this.pay_model = res.data
      })
      this.getDictValue('pay_type').then(res => {
        this.pay_type = res.data
      })
      this.getDictValue('trans_state').then(res => {
        this.trans_state = res.data
      })
      this.getDictValue('notify_state').then(res => {
        this.notify_state = res.data
      })
      this.getDictValue('check_state').then(res => {
        this.check_state = res.data
      })
    },
    methods: {
      // 详情查看,mixins调用
      info (record) {
        return getInfo(record, 'transId')
      }
    }
  }
</script>
<style scoped>
</style>
