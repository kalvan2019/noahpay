<template>
  <a-modal
    :title="`灰度服务配置` + title"
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
        <a-form-item label="服务实例">
          <a-input v-decorator="['serviceId', {initialValue: model.serviceId,rules: [{ required: true, message: '服务实例不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="ip">
          <a-input v-decorator="['host', {initialValue: model.host,rules: [{ required: true, message: 'ip不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="端口">
          <a-input v-decorator="['port', {initialValue: model.port,rules: [{ required: true, message: '端口不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="规则名">
          <a-input v-decorator="['ruleName', {initialValue: model.ruleName,rules: [{ required: true, message: '规则名不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="灰度标签">
          <a-input v-decorator="['grayTag', {initialValue: model.grayTag,rules: [{ required: true, message: '灰度标签不能为空！' }]}]"/>
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
  import { getInfo, addInfo, editInfo } from '@/api/platform/gateway/grayservice'
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
