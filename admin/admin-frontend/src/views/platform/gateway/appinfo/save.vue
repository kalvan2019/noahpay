<template>
  <a-modal
    :title="`app信息` + title"
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
          <a-input v-decorator="['appId', {initialValue: model.appId,rules: [{ required: true, message: 'appId不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="app名称">
          <a-input v-decorator="['appName', {initialValue: model.appName,rules: [{ required: true, message: 'app名称不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="app分组">
          <a-select
            show-search
            :placeholder="$t('global.placeholder.select')"
            v-decorator="['groupName', {initialValue: model.groupName,rules:[{ required: true, message: 'app分组不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.group_name" :key="key">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="绑定ip">
          <a-input v-decorator="['bindIp', {initialValue: model.bindIp,rules: []}]"/>
        </a-form-item>
        <a-form-item label="绑定域名">
          <a-input v-decorator="['bindDomain', {initialValue: model.bindDomain,rules: []}]"/>
        </a-form-item>
        <a-form-item label="绑定密钥">
          <a-input v-decorator="['bindKey', {initialValue: model.bindKey,rules: []}]"/>
        </a-form-item>
        <a-form-item label="app状态">
          <a-select
            show-search
            :placeholder="$t('global.placeholder.select')"
            v-decorator="['state', {initialValue: model.state,rules:[{ required: true, message: 'app状态不能为空！' }]}]">
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
  import { getInfo, addInfo, editInfo } from '@/api/platform/gateway/appinfo'
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
