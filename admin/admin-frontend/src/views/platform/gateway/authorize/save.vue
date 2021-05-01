<template>
  <a-modal
    :title="`接入方服务授权表` + title"
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
        <a-form-item label="appId">
          <a-select
            show-search
            :placeholder="$t('global.placeholder.select')"
            v-decorator="['appId', {initialValue: model.appId,rules:[{ required: true, message: 'appId不能为空！' }]}]">
            <a-select-option v-for="data in this.all_app" :key="data.appId" :data="data">
              ({{ data.appId }}){{ data.appName }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="服务">
          <a-select
            show-search
            :placeholder="$t('global.placeholder.select')"
            @change="handleChange"
            v-decorator="['service', {initialValue: model.service,rules:[{ required: true, message: '服务不能为空！' }]}]">
            <a-select-option v-for="data in this.all_service" :key="data.service">
              ({{ data.service }}){{ data.serviceName }}
            </a-select-option>
          </a-select>
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
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getInfo, addInfo, editInfo, getAllService, getAllApp } from '@/api/platform/gateway/authorize'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        switch_state: {},
        all_service: {},
        all_app: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      getAllService().then(res => {
        this.all_service = res.data
      })
      getAllApp().then(res => {
        this.all_app = res.data
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
      },
      handleChange (value) {
        this.value = value
      }
    }
  }
</script>
<style scoped>
</style>
