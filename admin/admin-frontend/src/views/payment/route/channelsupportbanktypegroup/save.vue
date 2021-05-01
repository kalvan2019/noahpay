<template>
  <a-modal
    :title="`渠道支持银行卡类型组` + title"
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
        <a-form-item label="行别组">
          <a-input v-decorator="['bankTypeGroup', {initialValue: model.bankTypeGroup,rules: [{ required: true, message: '行别组不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="行别">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['bankType', {initialValue: model.bankType,rules:[{ required: true, message: '行别不能为空！' }]}]">
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
  import { getInfo, addInfo, editInfo } from '@/api/payment/route/channelsupportbanktypegroup'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        bank_type: {},
        switch_state: {},
        genTemp: null
      }
    },
    created () {
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
