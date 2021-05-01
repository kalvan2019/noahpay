<template>
  <a-modal
    :title="`退票流水` + title"
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
        <a-form-item label="交易流水号">
          <a-input v-decorator="['transId', {initialValue: model.transId,rules: [{ required: true, message: '交易流水号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="交易日期">
          <a-input v-decorator="['transDate', {initialValue: model.transDate,rules: [{ required: true, message: '交易日期不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="订单号">
          <a-input v-decorator="['orderId', {initialValue: model.orderId,rules: []}]"/>
        </a-form-item>
        <a-form-item label="订单金额">
          <a-input v-decorator="['orderAmount', {initialValue: model.orderAmount,rules: []}]"/>
        </a-form-item>
        <a-form-item label="商户号">
          <a-input v-decorator="['merchantNo', {initialValue: model.merchantNo,rules: [{ required: true, message: '商户号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="商户手续费">
          <a-input v-decorator="['merchantFee', {initialValue: model.merchantFee,rules: [{ required: true, message: '商户手续费不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="子商户号">
          <a-input v-decorator="['subMerchantNo', {initialValue: model.subMerchantNo,rules: []}]"/>
        </a-form-item>
        <a-form-item label="子商户手续费">
          <a-input v-decorator="['subMerchantFee', {initialValue: model.subMerchantFee,rules: []}]"/>
        </a-form-item>
        <a-form-item label="交易会计日期">
          <a-input v-decorator="['transAccountDate', {initialValue: model.transAccountDate,rules: [{ required: true, message: '交易会计日期不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="退票日期">
          <a-input v-decorator="['returnTicketDate', {initialValue: model.returnTicketDate,rules: [{ required: true, message: '退票日期不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="退票备注">
          <a-input v-decorator="['remark', {initialValue: model.remark,rules: []}]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getInfo, addInfo } from '@/api/payment/trade/returnbill'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        genTemp: null
      }
    },
    created () {
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
      }
    }
  }
</script>
<style scoped>
</style>
