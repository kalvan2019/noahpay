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
        <a-form-item label="字典类型">
          <a-input v-decorator="['dictType', {initialValue: model.dictType,rules: [{ required: true}]}]"/>
        </a-form-item>
        <a-form-item label="字典类型名">
          <a-input v-decorator="['dictName', {initialValue: model.dictName,rules: [{ required: true}]}]"/>
        </a-form-item>
        <a-form-item label="数据KEY">
          <a-input v-decorator="['dictKey', {initialValue: model.dictKey,rules: [{ required: true}]}]"/>
        </a-form-item>
        <a-form-item label="数据值">
          <a-input v-decorator="['dictValue', {initialValue: model.dictValue,rules: [{ required: true}]}]"/>
        </a-form-item>
        <a-form-item label="徽标状态">
          <a-select
            show-search
            :placeholder="$t('global.placeholder.select')"
            v-decorator="['dictIcon', {initialValue: model.dictIcon,rules:[]}]">
            <a-select-option v-for="(value, key) in this.dict_icon" :key="key">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="select启用">
          <a-select
            show-search
            :placeholder="$t('global.placeholder.select')"
            v-decorator="['selectEnable', {initialValue: model.selectEnable,rules:[{ required: true}]}]">
            <a-select-option v-for="(value, key) in this.switch_state" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="排序">
          <a-input v-decorator="['priority', {initialValue: model.priority,rules: [{ required: true}]}]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getInfo, addInfo, editInfo } from '@/api/platform/develop/dict'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        dict_icon: {},
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('dict_icon').then(res => {
        this.dict_icon = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
    },
    methods: {
      info (record) {
        return getInfo(record)
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
