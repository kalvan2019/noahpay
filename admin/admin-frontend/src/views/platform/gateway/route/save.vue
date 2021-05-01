<template>
  <a-modal
    :title="`网关路由表` + title"
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
        <a-form-item label="路由id">
          <a-input v-decorator="['routeId', {initialValue: model.routeId,rules: [{ required: true, message: '路由id不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="uri路径">
          <a-input v-decorator="['uri', {initialValue: model.uri,rules: [{ required: true, message: 'uri路径不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="判定器">
          <a-input type="hidden" v-decorator="['predicates', {initialValue: model.predicates,rules: [{ required: true, message: '判定器格式不正确！' }]}]"/>
          <b-code-editor
            v-model="model.predicates"
            theme="dracula"
            ref="editor"
            @on-change="onPredicatesChange"
            style="line-height: 21px;width: 400px"
          >
          </b-code-editor>
        </a-form-item>
        <a-form-item label="过滤器">
          <a-input type="hidden" v-decorator="['filters', {initialValue: model.filters,rules: [{ required: true, message: '过滤器格式不正确！' }]}]"/>
          <b-code-editor
            v-model="model.filters"
            theme="dracula"
            ref="editor"
            @on-change="onFiltersChange"
            style="line-height: 21px;width: 400px"
          >
          </b-code-editor>
        </a-form-item>
        <a-form-item label="优先级">
          <a-input-number min="0" v-decorator="['orders', {initialValue: model.orders,rules: [{ required: true, message: '优先级不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="描述">
          <a-input v-decorator="['description', {initialValue: model.description,rules: [{ required: true, message: '描述不能为空！' }]}]"/>
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
  import { getInfo, addInfo, editInfo } from '@/api/platform/gateway/route'
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
      },
      onPredicatesChange (date) {
        try {
          if (JSON.parse(date.trim())) {
            this.form.setFieldsValue({ 'predicates': date.replace(/\n/g, '') })
          }
        } catch (e) {
          this.form.setFieldsValue({ 'predicates': '' })
        }
      },
      onFiltersChange (date) {
        try {
          if (JSON.parse(date.trim())) {
            this.form.setFieldsValue({ 'filters': date.replace(/\n/g, '') })
          }
        } catch (e) {
          this.form.setFieldsValue({ 'filters': '' })
        }
      }
    }
  }
</script>
<style scoped>
</style>
