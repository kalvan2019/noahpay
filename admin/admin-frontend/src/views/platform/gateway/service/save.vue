<template>
  <a-modal
    :title="`服务发布` + title"
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
        <a-form-item label="服务">
          <a-input v-decorator="['service', {initialValue: model.service,rules: [{ required: true, message: '服务不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="服务名">
          <a-input v-decorator="['serviceName', {initialValue: model.serviceName,rules: [{ required: true, message: '服务名不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="服务分组">
          <a-select
            show-search
            :placeholder="$t('global.placeholder.select')"
            v-decorator="['groupName', {initialValue: model.groupName,rules:[{ required: true, message: '服务分组不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.group_name" :key="key">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="资源名称">
          <a-input v-decorator="['resource', {initialValue: model.resource,rules: []}]"/>
        </a-form-item>
        <a-form-item label="路由id">
          <a-input v-decorator="['routeId', {initialValue: model.routeId,rules: []}]"/>
        </a-form-item>
        <a-form-item label="服务状态">
          <a-select
            show-search
            :placeholder="$t('global.placeholder.select')"
            v-decorator="['state', {initialValue: model.state,rules:[{ required: true, message: '服务状态不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="服务访问限制">
          <a-input-number min="0" v-decorator="['maxAccess', {initialValue: model.maxAccess,rules: [{ required: true, message: '服务访问限制不能为空！' }]}]"/>次
        </a-form-item>
        <a-form-item label="时间窗口">
          <a-input-number min="0" v-decorator="['times', {initialValue: model.times,rules: [{ required: true, message: '时间窗口不能为空！' }]}]"/>秒
        </a-form-item>
        <a-form-item label="优先级">
          <a-input-number min="0" v-decorator="['priority', {initialValue: model.priority,rules: []}]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getInfo, addInfo, editInfo } from '@/api/platform/gateway/service'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        group_name: {},
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('group_name').then(res => {
        this.group_name = res.data
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
