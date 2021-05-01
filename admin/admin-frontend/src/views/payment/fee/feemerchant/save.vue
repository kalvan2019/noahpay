<template>
  <a-modal
    :title="`商户计费配置` + title"
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
        <a-form-item label="计费方式">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['feeMode', {initialValue: model.feeMode,rules:[{ required: true, message: '计费方式不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.fee_mode" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="计费对象">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['feeObject', {initialValue: model.feeObject,rules:[{ required: true, message: '计费对象不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.fee_object" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="计费规则">
          <a-input v-decorator="['feeRule', {initialValue: model.feeRule,rules: [{ required: true, message: '计费规则不能为空！' }]}]"/>
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
  import { getInfo, addInfo, editInfo } from '@/api/payment/fee/feemerchant'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        trans_type: {},
        fee_mode: {},
        fee_object: {},
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('trans_type').then(res => {
        this.trans_type = res.data
      })
      this.getDictValue('fee_mode').then(res => {
        this.fee_mode = res.data
      })
      this.getDictValue('fee_object').then(res => {
        this.fee_object = res.data
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
