<template>
  <a-modal
    :title="`渠道分段计费配置` + title"
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
        <a-form-item label="分段计费规则">
          <a-input v-decorator="['feeSegmentRule', {initialValue: model.feeSegmentRule,rules: [{ required: true, message: '分段计费规则不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="开始金额">
          <a-input v-decorator="['beginAmount', {initialValue: model.beginAmount,rules: [{ required: true, message: '开始金额不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="结束金额">
          <a-input v-decorator="['endAmount', {initialValue: model.endAmount,rules: [{ required: true, message: '结束金额不能为空！' }]}]"/>
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
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getInfo, addInfo, editInfo } from '@/api/payment/channel/channelsegmentfee'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        fee_type: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('fee_type').then(res => {
        this.fee_type = res.data
      })
    },
    methods: {
      filterForm (values) {
        values.fixFee = this.amountFix(parseFloat(values.fixFee), 1 / 100, 0)
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
