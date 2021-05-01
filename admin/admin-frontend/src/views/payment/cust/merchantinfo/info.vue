<template>
  <a-modal
    :title="`商户信息` + title"
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
        <a-descriptions-item label="商户号">{{ model.merchantNo }}</a-descriptions-item>
        <a-descriptions-item label="商户名">{{ model.merchantName }}</a-descriptions-item>
        <a-descriptions-item label="商户客户号">{{ model.custNo }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="营业执照注册号">{{ model.businessLicenseNo }}</a-descriptions-item>
        <a-descriptions-item label="结算银行帐号">{{ model.bankAccountNo }}</a-descriptions-item>
        <a-descriptions-item label="银行户名">{{ model.bankAccountName }}</a-descriptions-item>
        <a-descriptions-item label="银行帐户类型">{{ this.dictFilter(model.bankAccountType,'bank_account_type') }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="行别">{{ this.dictFilter(model.bankType,'bank_type') }}</a-descriptions-item>
        <a-descriptions-item label="行名">{{ model.bankName }}</a-descriptions-item>
        <a-descriptions-item label="登录密码">{{ model.loginPassword }}</a-descriptions-item>
        <a-descriptions-item label="支付密码">{{ model.payPassword }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="密码盐">{{ model.salt }}</a-descriptions-item>
        <a-descriptions-item label="商户状态">{{ this.dictFilter(model.state,'switch_state') }}</a-descriptions-item>
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
  import { getInfo } from '@/api/payment/cust/merchantinfo'
  export default {
    mixins: [baseMixin, detailMixins],
    data () {
      return {
        bank_account_type: {},
        bank_type: {},
        switch_state: {},
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
