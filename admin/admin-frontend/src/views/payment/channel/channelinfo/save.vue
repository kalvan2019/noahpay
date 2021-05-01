<template>
  <a-modal
    :title="`渠道列表` + title"
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
          <a-input v-decorator="['channelNo', {initialValue: model.channelNo,rules: [{ required: true, message: '渠道编号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="渠道名称">
          <a-input v-decorator="['channelName', {initialValue: model.channelName,rules: [{ required: true, message: '渠道名称不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="实现类名">
          <a-input v-decorator="['channelClass', {initialValue: model.channelClass,rules: [{ required: true, message: '实现类名不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="渠道商户号">
          <a-input v-decorator="['channelMerchantNo', {initialValue: model.channelMerchantNo,rules: [{ required: true, message: '渠道商户号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="大商户轮询">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['merchantPoolConvert', {initialValue: model.merchantPoolConvert,rules:[{ required: true, message: '大商户轮询不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="转换多商户">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['multiMerchantConvert', {initialValue: model.multiMerchantConvert,rules:[{ required: true, message: '转换多商户不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="转换发往银行流水号">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['sendSnConvert', {initialValue: model.sendSnConvert,rules:[{ required: true, message: '转换发往银行流水号不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="转换行别">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['bankTypeConvert', {initialValue: model.bankTypeConvert,rules:[{ required: true, message: '转换行别不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="转换银行账户类型">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['bankAccountTypeConvert', {initialValue: model.bankAccountTypeConvert,rules:[{ required: true, message: '转换银行账户类型不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="转换证件类型">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['certificateTypeConvert', {initialValue: model.certificateTypeConvert,rules:[{ required: true, message: '转换证件类型不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="转换城市代码">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['cityConvert', {initialValue: model.cityConvert,rules:[{ required: true, message: '转换城市代码不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="行业类目转换">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['mccConvert', {initialValue: model.mccConvert,rules:[{ required: true, message: '行业类目转换不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="转换客户号类型">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['custConvert', {initialValue: model.custConvert,rules:[{ required: true, message: '转换客户号类型不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="连接超时时间">
          <a-input v-decorator="['connectionTimeout', {initialValue: model.connectionTimeout,rules: [{ required: true, message: '连接超时时间不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="渠道最大并发数">
          <a-input v-decorator="['connectionMaxNum', {initialValue: model.connectionMaxNum,rules: [{ required: true, message: '渠道最大并发数不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="读超时时间">
          <a-input v-decorator="['readTimeout', {initialValue: model.readTimeout,rules: [{ required: true, message: '读超时时间不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="渠道发送短信">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['smsSupport', {initialValue: model.smsSupport,rules:[{ required: true, message: '渠道发送短信不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="支持对账 0-支持对账;1-不支持">
          <a-input v-decorator="['checkSupport', {initialValue: model.checkSupport,rules: [{ required: true, message: '支持对账 0-支持对账;1-不支持不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="对账时间">
          <a-input v-decorator="['checkTime', {initialValue: model.checkTime,rules: [{ required: true, message: '对账时间不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="对账凭证">
          <a-input v-decorator="['checkField', {initialValue: model.checkField,rules: [{ required: true, message: '对账凭证不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="结算周期">
          <a-input v-decorator="['settlementTime', {initialValue: model.settlementTime,rules: [{ required: true, message: '结算周期不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="结算账号">
          <a-input v-decorator="['settlementBankAccountNo', {initialValue: model.settlementBankAccountNo,rules: [{ required: true, message: '结算账号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="结算户名">
          <a-input v-decorator="['settlementBankAccountName', {initialValue: model.settlementBankAccountName,rules: [{ required: true, message: '结算户名不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="结算银行">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['settlementBankType', {initialValue: model.settlementBankType,rules:[{ required: true, message: '结算银行不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.bank_type" :key="key">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
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
  import { getInfo, addInfo, editInfo } from '@/api/payment/channel/channelinfo'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        switch_state: {},
        bank_type: {},
        genTemp: null
      }
    },
    created () {
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
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
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
