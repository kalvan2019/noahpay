<template>
  <a-modal
    :title="`计费规则配置` + title"
    :width="640"
    destroyOnClose
    centered
    :visible="visible"
    :confirmLoading="loading"
    @ok="handleSubmit('id')"
    @cancel="visible=!visible"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <a-form-item label="主键ID" v-show="model.id" >
          <a-input v-decorator="['id', { initialValue: model.id }]" disabled />
        </a-form-item>
        <a-form-item label="计费规则">
          <a-input v-decorator="['feeRule', {initialValue: model.feeRule,rules: [{ required: true, message: '计费规则不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="渠道编号">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['channelNo', {initialValue: model.channelNo,rules:[{ required: true, message: '渠道编号不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.channel_no" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="支付类型">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['payType', {initialValue: model.payType,rules:[{ required: true, message: '支付类型不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.pay_type" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="行别">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['bankType', {initialValue: model.bankType,rules:[{ required: true, message: '行别不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.bank_type" :key="key">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="银行帐户类型">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['bankAccountType', {initialValue: model.bankAccountType,rules:[{ required: true, message: '银行帐户类型不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.bank_account_type" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="计费方法">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['feeType', {initialValue: model.feeType,rules:[{ required: true, message: '计费方法不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.fee_type" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="费率">
          <a-input v-decorator="['feeRate', {initialValue: model.feeRate,rules: [{ required: true, message: '费率不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="固定收费">
          <a-input v-decorator="['fixFee', {initialValue: this.amountFix(model.fixFee, 100, 2),rules: []}]"/>
        </a-form-item>
        <a-form-item label="最低手续费">
          <a-input v-decorator="['minFee', {initialValue: this.amountFix(model.minFee, 100, 2),rules: [{ required: true, message: '最低手续费不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="分段计费规则">
          <a-input v-decorator="['feeSegmentRule', {initialValue: model.feeSegmentRule,rules: [{ required: true, message: '分段计费规则不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="状态">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['state', {initialValue: model.state,rules:[{ required: true, message: '状态不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getInfo, addInfo, editInfo } from '@/api/payment/fee/feerule'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        channel_no: {},
        pay_type: {},
        bank_type: {},
        bank_account_type: {},
        fee_type: {},
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('channel_no').then(res => {
        this.channel_no = res.data
      })
      this.getDictValue('pay_type').then(res => {
        this.pay_type = res.data
      })
      this.getDictValue('bank_type').then(res => {
        this.bank_type = res.data
      })
      this.getDictValue('bank_account_type').then(res => {
        this.bank_account_type = res.data
      })
      this.getDictValue('fee_type').then(res => {
        this.fee_type = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
    },
    methods: {
      filterForm (values) {
        values.fixFee = this.amountFix(parseFloat(values.fixFee), 1 / 100, 0)
        values.minFee = this.amountFix(parseFloat(values.minFee), 1 / 100, 0)
        return values
      },
      info (record) {
        return getInfo(record, 'id')
      },
      add (values) {
        return addInfo(values)
      },
      edit (values) {
        return editInfo(values)
      }
    }
  }
</script>
<style scoped>
</style>
