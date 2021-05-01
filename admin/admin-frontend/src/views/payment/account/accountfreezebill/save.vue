<template>
  <a-modal
    :title="`账户冻结解冻明细` + title"
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
        <a-form-item label="冻结id">
          <a-input v-decorator="['accountFreezeId', {initialValue: model.accountFreezeId,rules: [{ required: true, message: '冻结id不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="关联交易流水">
          <a-input v-decorator="['refTransId', {initialValue: model.refTransId,rules: [{ required: true, message: '关联交易流水不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="账户号">
          <a-input v-decorator="['accountNo', {initialValue: model.accountNo,rules: [{ required: true, message: '账户号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="客户号">
          <a-input v-decorator="['custNo', {initialValue: model.custNo,rules: [{ required: true, message: '客户号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="冻结金额">
          <a-input v-decorator="['freezeAmount', {initialValue: this.amountFix(model.freezeAmount, 100, 2),rules: [{ required: true, message: '冻结金额不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="冻结原因">
          <a-input v-decorator="['freezeReason', {initialValue: model.freezeReason,rules: [{ required: true, message: '冻结原因不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="冻结日期">
          <a-input v-decorator="['freezeAccountDate', {initialValue: model.freezeAccountDate,rules: [{ required: true, message: '冻结日期不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="解冻金额">
          <a-input v-decorator="['unfreezeAmount', {initialValue: this.amountFix(model.unfreezeAmount, 100, 2),rules: []}]"/>
        </a-form-item>
        <a-form-item label="解冻原因">
          <a-input v-decorator="['unfreezeReason', {initialValue: model.unfreezeReason,rules: [{ required: true, message: '解冻原因不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="解冻日期">
          <a-input v-decorator="['unfreezeAccountDate', {initialValue: model.unfreezeAccountDate,rules: [{ required: true, message: '解冻日期不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="冻结状态">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['state', {initialValue: model.state,rules:[{ required: true, message: '冻结状态不能为空！' }]}]">
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
  import { getInfo, addInfo } from '@/api/payment/account/accountfreezebill'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
    },
    methods: {
      filterForm (values) {
        values.freezeAmount = this.amountFix(parseFloat(values.freezeAmount), 1 / 100, 0)
        values.unfreezeAmount = this.amountFix(parseFloat(values.unfreezeAmount), 1 / 100, 0)
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
