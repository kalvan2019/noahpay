<template>
  <a-modal
    :title="`渠道列表` + title"
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
        <a-descriptions-item label="渠道编号">{{ model.channelNo }}</a-descriptions-item>
        <a-descriptions-item label="渠道名称">{{ model.channelName }}</a-descriptions-item>
        <a-descriptions-item label="实现类名">{{ model.channelClass }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="渠道商户号">{{ model.channelMerchantNo }}</a-descriptions-item>
        <a-descriptions-item label="大商户轮询">{{ this.dictFilter(model.merchantPoolConvert,'switch_state') }}</a-descriptions-item>
        <a-descriptions-item label="转换多商户">{{ this.dictFilter(model.multiMerchantConvert,'switch_state') }}</a-descriptions-item>
        <a-descriptions-item label="转换发往银行流水号">{{ this.dictFilter(model.sendSnConvert,'switch_state') }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="转换行别">{{ this.dictFilter(model.bankTypeConvert,'switch_state') }}</a-descriptions-item>
        <a-descriptions-item label="转换银行账户类型">{{ this.dictFilter(model.bankAccountTypeConvert,'switch_state') }}</a-descriptions-item>
        <a-descriptions-item label="转换证件类型">{{ this.dictFilter(model.certificateTypeConvert,'switch_state') }}</a-descriptions-item>
        <a-descriptions-item label="转换城市代码">{{ this.dictFilter(model.cityConvert,'switch_state') }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="行业类目转换">{{ this.dictFilter(model.mccConvert,'switch_state') }}</a-descriptions-item>
        <a-descriptions-item label="转换客户号类型">{{ this.dictFilter(model.custConvert,'switch_state') }}</a-descriptions-item>
        <a-descriptions-item label="连接超时时间">{{ model.connectionTimeout }}</a-descriptions-item>
        <a-descriptions-item label="渠道最大并发数">{{ model.connectionMaxNum }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="读超时时间">{{ model.readTimeout }}</a-descriptions-item>
        <a-descriptions-item label="渠道发送短信">{{ this.dictFilter(model.smsSupport,'switch_state') }}</a-descriptions-item>
        <a-descriptions-item label="支持对账 0-支持对账;1-不支持">{{ model.checkSupport }}</a-descriptions-item>
        <a-descriptions-item label="对账时间">{{ model.checkTime }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="对账凭证">{{ model.checkField }}</a-descriptions-item>
        <a-descriptions-item label="结算周期">{{ model.settlementTime }}</a-descriptions-item>
        <a-descriptions-item label="结算账号">{{ model.settlementBankAccountNo }}</a-descriptions-item>
        <a-descriptions-item label="结算户名">{{ model.settlementBankAccountName }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="结算银行">{{ this.dictFilter(model.settlementBankType,'bank_type') }}</a-descriptions-item>
        <a-descriptions-item label="状态">{{ this.dictFilter(model.state,'switch_state') }}</a-descriptions-item>
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
  import { getInfo } from '@/api/payment/channel/channelinfo'
  export default {
    mixins: [baseMixin, detailMixins],
    data () {
      return {
        switch_state: {},
        bank_type: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('bank_type').then(res => {
        this.bank_type = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
    },
    methods: {
      // 详情查看,mixins调用
      info (record) {
        return getInfo(record, 'id')
      }
    }
  }
</script>
<style scoped>
</style>
