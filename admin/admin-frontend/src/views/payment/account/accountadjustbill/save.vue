<template>
  <a-modal
    :title="`账户调账流水` + title"
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
        <a-form-item label="调账流水id">
          <a-input v-decorator="['adjustTransId', {initialValue: model.adjustTransId,rules: [{ required: true, message: '调账流水id不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="交易类型">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['transType', {initialValue: model.transType,rules:[{ required: true, message: '交易类型不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.trans_type" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="凭证类型">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['voucherType', {initialValue: model.voucherType,rules:[{ required: true, message: '凭证类型不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.voucher_type" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="记账流水">
          <a-input v-decorator="['accountTransId', {initialValue: model.accountTransId,rules: [{ required: true, message: '记账流水不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="会计日期">
          <a-input v-decorator="['accountDate', {initialValue: model.accountDate,rules: [{ required: true, message: '会计日期不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="账户号">
          <a-input v-decorator="['accountNo', {initialValue: model.accountNo,rules: [{ required: true, message: '账户号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="客户号">
          <a-input v-decorator="['custNo', {initialValue: model.custNo,rules: [{ required: true, message: '客户号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="入账金额">
          <a-input v-decorator="['incomeAmount', {initialValue: this.amountFix(model.incomeAmount, 100, 2),rules: [{ required: true, message: '入账金额不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="出账金额">
          <a-input v-decorator="['outgoAmount', {initialValue: this.amountFix(model.outgoAmount, 100, 2),rules: [{ required: true, message: '出账金额不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="调账原因">
          <a-input v-decorator="['adjustReason', {initialValue: model.adjustReason,rules: [{ required: true, message: '调账原因不能为空！' }]}]"/>
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
  import { getInfo, addInfo } from '@/api/payment/account/accountadjustbill'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        trans_type: {},
        voucher_type: {},
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('trans_type').then(res => {
        this.trans_type = res.data
      })
      this.getDictValue('voucher_type').then(res => {
        this.voucher_type = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
    },
    methods: {
      filterForm (values) {
        values.incomeAmount = this.amountFix(parseFloat(values.incomeAmount), 1 / 100, 0)
        values.outgoAmount = this.amountFix(parseFloat(values.outgoAmount), 1 / 100, 0)
        return values
      },
      info (record) {
        return getInfo(record, 'id')
      },
      add (values) {
        return addInfo(values)
      }
    }
  }
</script>
<style scoped>
</style>
