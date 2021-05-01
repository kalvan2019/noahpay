<template>
  <a-modal
    :title="`mock服务配置` + title"
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
        <a-form-item label="自增id">
          <a-input v-decorator="['id', {initialValue: model.id,rules: [{ required: true, message: '自增id不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="系统">
          <a-input v-decorator="['serviceId', {initialValue: model.serviceId,rules: [{ required: true, message: '系统不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="服务地址">
          <a-input v-decorator="['service', {initialValue: model.service,rules: [{ required: true, message: '服务地址不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="mock地址">
          <a-input v-decorator="['mockUrl', {initialValue: model.mockUrl,rules: [{ required: true, message: 'mock地址不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="状态">
          <a-select
            show-search
            :placeholder="$t('global.placeholder.select')"
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
  import { getInfo, addInfo, editInfo } from '@/api/platform/develop/mockservice'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        switch_state: {},
        genTemp: null
      }
    },
    created () {
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
