<template>
  <a-modal
    :title="`数据字典配置` + title"
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
        <a-form-item label="应用代码">
          <a-input v-decorator="['dictKey', {initialValue: model.dictKey,rules: [{ required: true, message: '应用代码不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="应用名称">
          <a-input v-decorator="['dictValue', {initialValue: model.dictValue,rules: [{ required: true, message: '应用名称不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="排序">
          <a-input v-decorator="['priority', {initialValue: model.priority,rules: [{ required: true, message: '排序不能为空！' }]}]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getInfo, addInfo, editInfo } from '@/api/platform/permission/app'
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
