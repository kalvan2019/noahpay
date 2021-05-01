<template>
  <a-modal
    :title="`渠道商户信息` + title"
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
        <a-form-item label="渠道商户号">
          <a-input v-decorator="['channelMerchantNo', {initialValue: model.channelMerchantNo,rules: [{ required: true, message: '渠道商户号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="渠道子级商户号">
          <a-input v-decorator="['channelSubMerchantNo', {initialValue: model.channelSubMerchantNo,rules: [{ required: true, message: '渠道子级商户号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="子商户名称">
          <a-input v-decorator="['channelSubMerchantName', {initialValue: model.channelSubMerchantName,rules: [{ required: true, message: '子商户名称不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="商户号">
          <a-input v-decorator="['merchantNo', {initialValue: model.merchantNo,rules: [{ required: true, message: '商户号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="城市代码">
          <a-input v-decorator="['city', {initialValue: model.city,rules: []}]"/>
        </a-form-item>
        <a-form-item label="行业类别">
          <a-input v-decorator="['mcc', {initialValue: model.mcc,rules: []}]"/>
        </a-form-item>
        <a-form-item label="开始时间">
          <a-input v-decorator="['beginTime', {initialValue: model.beginTime,rules: [{ required: true, message: '开始时间不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="结束时间">
          <a-input v-decorator="['endTime', {initialValue: model.endTime,rules: [{ required: true, message: '结束时间不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="单笔金额下限">
          <a-input v-decorator="['limitMinAmount', {initialValue: this.amountFix(model.limitMinAmount, 100, 2),rules: [{ required: true, message: '单笔金额下限不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="单笔金额上限">
          <a-input v-decorator="['limitMaxAmount', {initialValue: this.amountFix(model.limitMaxAmount, 100, 2),rules: [{ required: true, message: '单笔金额上限不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="日使用金额">
          <a-input v-decorator="['dayUseAmount', {initialValue: this.amountFix(model.dayUseAmount, 100, 2),rules: [{ required: true, message: '日使用金额不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="日限额">
          <a-input v-decorator="['dayLimitAmount', {initialValue: this.amountFix(model.dayLimitAmount, 100, 2),rules: [{ required: true, message: '日限额不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="月使用金额">
          <a-input v-decorator="['monthUseAmount', {initialValue: this.amountFix(model.monthUseAmount, 100, 2),rules: [{ required: true, message: '月使用金额不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="月金额限额">
          <a-input v-decorator="['monthLimitAmount', {initialValue: this.amountFix(model.monthLimitAmount, 100, 2),rules: [{ required: true, message: '月金额限额不能为空！' }]}]"/>
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
        <a-form-item label="备注">
          <a-input v-decorator="['remark', {initialValue: model.remark,rules: [{ required: true, message: '备注不能为空！' }]}]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getInfo, addInfo, editInfo } from '@/api/payment/route/channelmerchantpool'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        channel_no: {},
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('channel_no').then(res => {
        this.channel_no = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
    },
    methods: {
      filterForm (values) {
        values.limitMinAmount = this.amountFix(parseFloat(values.limitMinAmount), 1 / 100, 0)
        values.limitMaxAmount = this.amountFix(parseFloat(values.limitMaxAmount), 1 / 100, 0)
        values.dayUseAmount = this.amountFix(parseFloat(values.dayUseAmount), 1 / 100, 0)
        values.dayLimitAmount = this.amountFix(parseFloat(values.dayLimitAmount), 1 / 100, 0)
        values.monthUseAmount = this.amountFix(parseFloat(values.monthUseAmount), 1 / 100, 0)
        values.monthLimitAmount = this.amountFix(parseFloat(values.monthLimitAmount), 1 / 100, 0)
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
