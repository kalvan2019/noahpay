<template>
  <a-modal
    :title="`渠道返回码` + title"
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
        <a-form-item label="渠道返回码">
          <a-input v-decorator="['channelReturnCode', {initialValue: model.channelReturnCode,rules: [{ required: true, message: '渠道返回码不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="渠道返回备注">
          <a-input v-decorator="['channelResultNote', {initialValue: model.channelResultNote,rules: []}]"/>
        </a-form-item>
        <a-form-item label="转换返回码">
          <a-input v-decorator="['returnCode', {initialValue: model.returnCode,rules: [{ required: true, message: '转换返回码不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="转换返回说明">
          <a-input v-decorator="['returnNote', {initialValue: model.returnNote,rules: []}]"/>
        </a-form-item>
        <a-form-item label="交易状态">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['transState', {initialValue: model.transState,rules:[{ required: true, message: '交易状态不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.trans_state" :key="parseInt(key)">
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
  import { getInfo, addInfo, editInfo } from '@/api/payment/channel/channelreturncode'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        channel_no: {},
        pay_type: {},
        trans_state: {},
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
      this.getDictValue('trans_state').then(res => {
        this.trans_state = res.data
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
