<template>
  <a-modal
    :title="`渠道支持支付方式` + title"
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
        <a-form-item label="日使用金额">
          <a-input v-decorator="['dayUseAmount', {initialValue: this.amountFix(model.dayUseAmount, 100, 2),rules: [{ required: true, message: '日使用金额不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="日限额">
          <a-input v-decorator="['dayLimitAmount', {initialValue: this.amountFix(model.dayLimitAmount, 100, 2),rules: [{ required: true, message: '日限额不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="日使用笔数">
          <a-input v-decorator="['dayUseNumber', {initialValue: model.dayUseNumber,rules: [{ required: true, message: '日使用笔数不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="日成功交易笔数限制">
          <a-input v-decorator="['dayLimitNumber', {initialValue: model.dayLimitNumber,rules: [{ required: true, message: '日成功交易笔数限制不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="月使用金额">
          <a-input v-decorator="['monthUseAmount', {initialValue: this.amountFix(model.monthUseAmount, 100, 2),rules: [{ required: true, message: '月使用金额不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="月金额限额">
          <a-input v-decorator="['monthLimitAmount', {initialValue: this.amountFix(model.monthLimitAmount, 100, 2),rules: [{ required: true, message: '月金额限额不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="最后统计日期">
          <a-input v-decorator="['lastDate', {initialValue: model.lastDate,rules: [{ required: true, message: '最后统计日期不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="支持非工作日">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['holidaySupport', {initialValue: model.holidaySupport,rules:[{ required: true, message: '支持非工作日不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="银行路由">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['bankRouteEnabled', {initialValue: model.bankRouteEnabled,rules:[{ required: true, message: '银行路由不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="状态">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['state', {initialValue: model.state,rules:[{ required: true, message: '启用状态不能为空！' }]}]">
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
  import { getInfo, addInfo, editInfo } from '@/api/payment/route/channelsupportpaytype'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        channel_no: {},
        pay_type: {},
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
