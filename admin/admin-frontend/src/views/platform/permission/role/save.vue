<template>
  <a-modal
    ref="modal"
    :title="`角色${title}`"
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
        <a-form-item label="角色名称">
          <a-input v-decorator="['roleName', {initialValue: model.roleName,rules: [{ required: true, message: '角色名不能空！' }]}]"/>
        </a-form-item>
        <a-form-item label="状态">
          <a-select
            show-search
            :placeholder="$t('global.placeholder.select')"
            v-decorator="['state', {initialValue: model.state?model.state:0,rules:[{ required: true, message: '状态不能为空！' }]}]">
            <template>
              <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">({{ key }}){{ value.dictValue }}</a-select-option>
            </template>
          </a-select>
        </a-form-item>
        <a-form-item label="角色描述">
          <a-textarea v-decorator="['roleDesc', {initialValue: model.roleDesc,rules: [{ required: true, message: '角色名描述不能空！' }]}]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getInfo, addInfo, editInfo } from '@/api/platform/permission/role'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        roles: [],
        switch_state: {}
      }
    },
    created () {
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
    },
    methods: {
      info (param) {
        return getInfo(param)
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
