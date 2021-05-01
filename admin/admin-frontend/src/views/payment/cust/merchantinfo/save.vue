<template>
  <a-modal
    :title="`商户信息` + title"
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
        <a-form-item label="商户号">
          <a-input v-decorator="['merchantNo', {initialValue: model.merchantNo,rules: [{ required: true, message: '商户号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="商户名">
          <a-input v-decorator="['merchantName', {initialValue: model.merchantName,rules: [{ required: true, message: '商户名不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="商户客户号">
          <a-input v-decorator="['custNo', {initialValue: model.custNo,rules: [{ required: true, message: '商户客户号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="营业执照注册号">
          <a-input v-decorator="['businessLicenseNo', {initialValue: model.businessLicenseNo,rules: [{ required: true, message: '营业执照注册号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="结算银行帐号">
          <a-input v-decorator="['bankAccountNo', {initialValue: model.bankAccountNo,rules: [{ required: true, message: '结算银行帐号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="银行户名">
          <a-input v-decorator="['bankAccountName', {initialValue: model.bankAccountName,rules: [{ required: true, message: '银行户名不能为空！' }]}]"/>
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
        <a-form-item label="行名">
          <a-input v-decorator="['bankName', {initialValue: model.bankName,rules: [{ required: true, message: '行名不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="商户状态">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['state', {initialValue: model.state,rules:[{ required: true, message: '商户状态不能为空！' }]}]">
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
  import { getInfo, addInfo, editInfo } from '@/api/payment/cust/merchantinfo'
  export default {
    mixins: [detailMixins],
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
      filterForm (values) {
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
