<template>
  <a-modal
    :title="`交易订单支付明细` + title"
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
        <a-descriptions-item label="自增id">{{ model.id }}</a-descriptions-item>
        <a-descriptions-item label="交易流水号">{{ model.transId }}</a-descriptions-item>
        <a-descriptions-item label="订单日期">{{ model.orderDate }}</a-descriptions-item>
        <a-descriptions-item label="支付日期">{{ model.payDate }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="银行帐号">{{ model.bankAccountNo }}</a-descriptions-item>
        <a-descriptions-item label="银行户名">{{ model.bankAccountName }}</a-descriptions-item>
        <a-descriptions-item label="银行帐户类型">{{ this.dictFilter(model.bankAccountType,'bank_account_type') }}</a-descriptions-item>
        <a-descriptions-item label="银行卡有效期">{{ model.bankAccountExpiry }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="行别">{{ this.dictFilter(model.bankType,'bank_type') }}</a-descriptions-item>
        <a-descriptions-item label="行名">{{ model.bankName }}</a-descriptions-item>
        <a-descriptions-item label="银行协议号">{{ model.bankProtocol }}</a-descriptions-item>
        <a-descriptions-item label="客户手机">{{ model.mobile }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="证件类型">{{ this.dictFilter(model.certificateType,'certificate_type') }}</a-descriptions-item>
        <a-descriptions-item label="证件号码">{{ model.certificateNo }}</a-descriptions-item>
        <a-descriptions-item label="支付类型">{{ this.dictFilter(model.payType,'pay_type') }}</a-descriptions-item>
        <a-descriptions-item label="返回码">{{ model.payResultCode }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="返回描述">{{ model.payResultNote }}</a-descriptions-item>
        <a-descriptions-item label="渠道编号">{{ this.dictFilter(model.channelNo,'channel_no') }}</a-descriptions-item>
        <a-descriptions-item label="送往渠道流水号">{{ model.channelSendSn }}</a-descriptions-item>
        <a-descriptions-item label="送往渠道时间">{{ model.channelSendTime }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="送往渠道扩展数据">{{ model.channelSendExt }}</a-descriptions-item>
        <a-descriptions-item label="渠道商户号">{{ model.channelMerchantNo }}</a-descriptions-item>
        <a-descriptions-item label="渠道商户名">{{ model.channelMerchantName }}</a-descriptions-item>
        <a-descriptions-item label="渠道子商户号">{{ model.channelSubMerchantNo }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="送往渠道金额">{{ this.amountFix(model.channelAmount, 100, 2) }}</a-descriptions-item>
        <a-descriptions-item label="渠道类目">{{ model.channelMcc }}</a-descriptions-item>
        <a-descriptions-item label="渠道城市">{{ model.channelCity }}</a-descriptions-item>
        <a-descriptions-item label="渠道账户类型">{{ model.channelBankAccountType }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="渠道行别">{{ model.channelBankType }}</a-descriptions-item>
        <a-descriptions-item label="渠道证件类型">{{ model.channelCertificateType }}</a-descriptions-item>
        <a-descriptions-item label="渠道会计日期">{{ model.channelAccountDate }}</a-descriptions-item>
        <a-descriptions-item label="渠道返回流水号">{{ model.channelRecvSn }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="渠道返回时间">{{ model.channelRecvTime }}</a-descriptions-item>
        <a-descriptions-item label="渠道返回扩展数据">{{ model.channelRecvExt }}</a-descriptions-item>
        <a-descriptions-item label="渠道返回链接">{{ model.channelRecvUrl }}</a-descriptions-item>
        <a-descriptions-item label="渠道返回码">{{ model.channelResultCode }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="渠道返回描述">{{ model.channelResultNote }}</a-descriptions-item>
        <a-descriptions-item label="交易状态">{{ this.dictFilter(model.state,'trans_state') }}</a-descriptions-item>
        <a-descriptions-item label="对账状态">{{ this.dictFilter(model.checkState,'check_state') }}</a-descriptions-item>
        <a-descriptions-item label="创建时间">{{ model.createTime }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="更新时间">{{ model.updateTime }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
    </a-card>
  </a-modal>
</template>
<script>
  import { baseMixin } from '@/store/app-mixin'
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getInfo } from '@/api/payment/trade/transpaybill'
  export default {
    mixins: [baseMixin, detailMixins],
    data () {
      return {
        bank_account_type: {},
        bank_type: {},
        certificate_type: {},
        pay_type: {},
        channel_no: {},
        trans_state: {},
        check_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('bank_account_type').then(res => {
        this.bank_account_type = res.data
      })
      this.getDictValue('bank_type').then(res => {
        this.bank_type = res.data
      })
      this.getDictValue('certificate_type').then(res => {
        this.certificate_type = res.data
      })
      this.getDictValue('pay_type').then(res => {
        this.pay_type = res.data
      })
      this.getDictValue('channel_no').then(res => {
        this.channel_no = res.data
      })
      this.getDictValue('trans_state').then(res => {
        this.trans_state = res.data
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
