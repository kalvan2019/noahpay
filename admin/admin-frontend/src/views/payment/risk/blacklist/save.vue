<template>
  <a-modal
    :title="`黑名单` + title"
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
        <a-form-item label="黒名单类型">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['blackType', {initialValue: model.blackType,rules:[{ required: true, message: '黒名单类型不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.black_type" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="客户号">
          <a-input v-decorator="['custNo', {initialValue: model.custNo,rules: [{ required: true, message: '客户号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="客户名称">
          <a-input v-decorator="['custName', {initialValue: model.custName,rules: []}]"/>
        </a-form-item>
        <a-form-item label="证件号码">
          <a-input v-decorator="['certificateNo', {initialValue: model.certificateNo,rules: []}]"/>
        </a-form-item>
        <a-form-item label="银行帐号">
          <a-input v-decorator="['bankAccountNo', {initialValue: model.bankAccountNo,rules: []}]"/>
        </a-form-item>
        <a-form-item label="客户邮箱">
          <a-input v-decorator="['email', {initialValue: model.email,rules: []}]"/>
        </a-form-item>
        <a-form-item label="客户手机">
          <a-input v-decorator="['mobile', {initialValue: model.mobile,rules: []}]"/>
        </a-form-item>
        <a-form-item label="营业执照注册号">
          <a-input v-decorator="['businessLicenseNo', {initialValue: model.businessLicenseNo,rules: []}]"/>
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
  import { getInfo, addInfo, editInfo } from '@/api/payment/risk/blacklist'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        black_type: {},
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('black_type').then(res => {
        this.black_type = res.data
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
