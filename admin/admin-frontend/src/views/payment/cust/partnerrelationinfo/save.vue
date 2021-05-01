<template>
  <a-modal
    :title="`合作方关系维护表` + title"
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
        <a-form-item label="合作方编号">
          <a-input v-decorator="['partnerNo', {initialValue: model.partnerNo,rules: [{ required: true, message: '合作方编号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="商户编号">
          <a-input v-decorator="['merchantNo', {initialValue: model.merchantNo,rules: [{ required: true, message: '商户编号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="交易成本费率">
          <a-input v-decorator="['feeRate', {initialValue: model.feeRate,rules: [{ required: true, message: '交易成本费率不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="提现成本手续费">
          <a-input v-decorator="['withdrawFee', {initialValue: this.amountFix(model.withdrawFee, 100, 2),rules: [{ required: true, message: '提现成本手续费不能为空！' }]}]"/>
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
  import { getInfo, addInfo, editInfo } from '@/api/payment/cust/partnerrelationinfo'
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
        values.withdrawFee = this.amountFix(parseFloat(values.withdrawFee), 1 / 100, 0)
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
