<template>
  <a-modal
    :title="`渠道支持地区组` + title"
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
        <a-form-item label="地区组">
          <a-input v-decorator="['bankCityGroup', {initialValue: model.bankCityGroup,rules: [{ required: true, message: '地区组不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="开户行所在地">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['bankCity', {initialValue: model.bankCity,rules:[{ required: true, message: '开户行所在地不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.bank_city" :key="parseInt(key)">
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
  import { getInfo, addInfo, editInfo } from '@/api/payment/route/channelsupportbankcitygroup'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        bank_city: {},
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('bank_city').then(res => {
        this.bank_city = res.data
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
