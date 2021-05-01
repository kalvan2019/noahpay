<template>
  <a-modal
    :title="$t('admin.title') + ` ${title}`"
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
        <a-form-item label="ID" v-show="model.id" >
          <a-input v-decorator="['id', { initialValue: model.id }]" disabled />
        </a-form-item>
        <a-form-item :label="$t('admin.userCode')">
          <a-input v-decorator="['userCode', {initialValue: model.userCode,rules: [{ required: true, min: 3, max: 15, message: this.$t('admin.userCode.validate') }]}]"/>
        </a-form-item>
        <a-form-item :label="$t('admin.userName')">
          <a-input v-decorator="['userName', {initialValue: model.userName,rules: [{ required: true, message: this.$t('global.warn.not.null') }]}]"/>
        </a-form-item>
        <a-form-item :label="$t('admin.password')">
          <a-input-password v-decorator="['password', {initialValue: model.password,rules: [{ required: true, message: this.$t('global.warn.not.null') }]}]"/>
        </a-form-item>
        <a-form-item :label="$t('admin.mobile')">
          <a-input v-decorator="['mobile', {initialValue: model.mobile,rules: [{ required: true, message: this.$t('global.warn.not.null') }]}]"/>
        </a-form-item>
        <a-form-item :label="$t('admin.email')">
          <a-input v-decorator="['email', {initialValue: model.email,rules: [{ required: true, message: this.$t('global.warn.not.null') }]}]"/>
        </a-form-item>
        <a-form-item :label="$t('admin.state')">
          <a-select
            show-search
            :placeholder="$t('global.placeholder.select')"
            v-decorator="['state', {initialValue: model.state?model.state:0,rules:[{ required: true, message: this.$t('global.warn.not.null') }]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item :label="$t('admin.roles')">
          <a-select
            :placeholder="$t('global.placeholder.select')"
            mode="multiple"
            v-decorator="['roleId', {initialValue: model.roles?model.roles.map(item=>item.id):[],rules:[{ required: true, message: this.$t('global.warn.not.null') }]}]"
            style="width: 100%">
            <a-select-option v-for="role in roles" :key="role.id">{{ role.roleName }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import md5 from 'md5'
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getAllRole } from '@/api/platform/permission/role'
  import { getInfo, addInfo, editInfo } from '@/api/platform/system/admin'
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
      getAllRole().then(res => {
        if (res.state === 0) {
          this.roles = res.data
        } else {
          this.$message.error(res.message)
        }
      })
    },
    methods: {
      info (record) {
        return getInfo(record)
      },
      filterForm (values) {
        if (JSON.stringify(values.password) !== JSON.stringify(this.model.password)) {
          values.password = md5(values.password)
        }
        return values
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
