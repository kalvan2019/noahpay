<template>
  <a-modal
    :title="`渠道扩展参数` + title"
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
        <a-form-item label="渠道商户号">
          <a-input v-decorator="['channelMerchantNo', {initialValue: model.channelMerchantNo,rules: [{ required: true, message: '渠道商户号不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="渠道属性">
          <a-input v-decorator="['paramKey', {initialValue: model.paramKey,rules: [{ required: true, message: '渠道属性不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="渠道属性值">
          <a-input v-decorator="['paramValue', {initialValue: model.paramValue,rules: [{ required: true, message: '渠道属性值不能为空！' }]}]"/>
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
  import { getInfo, addInfo, editInfo } from '@/api/payment/channel/channelextparam'
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
      },
      edit (values) {
        return editInfo(values)
      }
    }
  }
</script>
<style scoped>
</style>
