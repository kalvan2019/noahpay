<template>
  <a-modal
    :title="`商户交易业务入网` + title"
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
        <a-form-item label="单笔金额上限">
          <a-input v-decorator="['limitMaxAmount', {initialValue: this.amountFix(model.limitMaxAmount, 100, 2),rules: [{ required: true, message: '单笔金额上限不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="日限额">
          <a-input v-decorator="['dayLimitAmount', {initialValue: this.amountFix(model.dayLimitAmount, 100, 2),rules: [{ required: true, message: '日限额不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="日限笔数">
          <a-input v-decorator="['dayLimitNumber', {initialValue: model.dayLimitNumber,rules: [{ required: true, message: '日限笔数不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="月金额限额">
          <a-input v-decorator="['monthLimitAmount', {initialValue: this.amountFix(model.monthLimitAmount, 100, 2),rules: [{ required: true, message: '月金额限额不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="月笔数限额">
          <a-input v-decorator="['monthLimitNumber', {initialValue: model.monthLimitNumber,rules: [{ required: true, message: '月笔数限额不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="生效日期">
          <a-input v-decorator="['effectiveDate', {initialValue: model.effectiveDate,rules: [{ required: true, message: '生效日期不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="失效日期">
          <a-input v-decorator="['expiryDate', {initialValue: model.expiryDate,rules: [{ required: true, message: '失效日期不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="客户协议审核">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['signAuditType', {initialValue: model.signAuditType,rules:[{ required: true, message: '客户协议审核不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="签约验证手机">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['signCheckSms', {initialValue: model.signCheckSms,rules:[{ required: true, message: '签约验证手机不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="签约成功发短信">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['signSendSms', {initialValue: model.signSendSms,rules:[{ required: true, message: '签约成功发短信不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="处理方式">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['transDealType', {initialValue: model.transDealType,rules:[{ required: true, message: '处理方式不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="交易审核">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['transAuditType', {initialValue: model.transAuditType,rules:[{ required: true, message: '交易审核不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="交易检查客户协议">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['transCheckProtocol', {initialValue: model.transCheckProtocol,rules:[{ required: true, message: '交易检查客户协议不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="交易验证手机">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['transCheckSms', {initialValue: model.transCheckSms,rules:[{ required: true, message: '交易验证手机不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="交易成功发短信">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['transSendSms', {initialValue: model.transSendSms,rules:[{ required: true, message: '交易成功发短信不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="入网状态">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['state', {initialValue: model.state,rules:[{ required: true, message: '入网状态不能为空！' }]}]">
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
  import { getInfo, addInfo, editInfo } from '@/api/payment/cust/merchantproducttrans'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        trans_type: {},
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('trans_type').then(res => {
        this.trans_type = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
    },
    methods: {
      filterForm (values) {
        values.limitMaxAmount = this.amountFix(parseFloat(values.limitMaxAmount), 1 / 100, 0)
        values.dayLimitAmount = this.amountFix(parseFloat(values.dayLimitAmount), 1 / 100, 0)
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
