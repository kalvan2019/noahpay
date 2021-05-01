<template>
  <a-modal
    :title="`灰度规则配置` + title"
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
        <a-form-item label="规则名">
          <a-input v-decorator="['ruleName', {initialValue: model.ruleName,rules: [{ required: true, message: '规则名不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="灰度规则">
          <a-input type="hidden" v-decorator="['rule', {initialValue: model.rule,rules: [{ required: true, message: '规则格式不正确！' }]}]"/>
          <b-code-editor
            v-model="model.rule"
            theme="dracula"
            ref="editor"
            @on-change="onJsonChange"
            style="line-height: 21px;width: 400px"
          >
          </b-code-editor>
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
  import { getInfo, addInfo, editInfo } from '@/api/platform/gateway/grayrule'
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
      onJsonChange (date) {
        try {
          if (JSON.parse(date.trim())) {
            this.form.setFieldsValue({ 'rule': date.replace(/\n/g, '') })
          }
        } catch (e) {
          this.form.setFieldsValue({ 'rule': '' })
        }
      }
    }
  }
</script>
<style scoped>
</style>
