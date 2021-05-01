<template>
  <a-modal
    :title="`路由规则配置` + title"
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
        <a-form-item label="路由规则">
          <a-input v-decorator="['routeRule', {initialValue: model.routeRule,rules: [{ required: true, message: '路由规则不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="渠道编号">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['channelNo', {initialValue: model.channelNo,rules:[{ required: true, message: '渠道编号不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.channel_no" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="行别">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['bankType', {initialValue: model.bankType,rules:[{ required: true, message: '行别不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.bank_type" :key="key">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="银行帐户类型">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['bankAccountType', {initialValue: model.bankAccountType,rules:[{ required: true, message: '银行帐户类型不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.bank_account_type" :key="parseInt(key)">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="单笔金额下限">
          <a-input v-decorator="['limitMinAmount', {initialValue: this.amountFix(model.limitMinAmount, 100, 2),rules: [{ required: true, message: '单笔金额下限不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="单笔金额上限">
          <a-input v-decorator="['limitMaxAmount', {initialValue: this.amountFix(model.limitMaxAmount, 100, 2),rules: [{ required: true, message: '单笔金额上限不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="开始日期">
          <a-input v-decorator="['beginDate', {initialValue: model.beginDate,rules: [{ required: true, message: '开始日期不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="结束日期">
          <a-input v-decorator="['endDate', {initialValue: model.endDate,rules: [{ required: true, message: '结束日期不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="开始时间">
          <a-input v-decorator="['beginTime', {initialValue: model.beginTime,rules: [{ required: true, message: '开始时间不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="结束时间">
          <a-input v-decorator="['endTime', {initialValue: model.endTime,rules: [{ required: true, message: '结束时间不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="优先级">
          <a-input v-decorator="['priority', {initialValue: model.priority,rules: [{ required: true, message: '优先级不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="权重因子">
          <a-input v-decorator="['weight', {initialValue: model.weight,rules: [{ required: true, message: '权重因子不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="备注">
          <a-input v-decorator="['remark', {initialValue: model.remark,rules: [{ required: true, message: '备注不能为空！' }]}]"/>
        </a-form-item>
        <a-form-item label="状态">
          <a-select
            show-search
            placeholder="请选择"
            v-decorator="['state', {initialValue: model.state,rules:[{ required: true, message: '启用状态不能为空！' }]}]">
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
  import { getInfo, addInfo, editInfo } from '@/api/payment/route/routerule'
  export default {
    mixins: [detailMixins],
    data () {
      return {
        channel_no: {},
        bank_type: {},
        bank_account_type: {},
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('channel_no').then(res => {
        this.channel_no = res.data
      })
      this.getDictValue('bank_type').then(res => {
        this.bank_type = res.data
      })
      this.getDictValue('bank_account_type').then(res => {
        this.bank_account_type = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
    },
    methods: {
      filterForm (values) {
        values.limitMinAmount = this.amountFix(parseFloat(values.limitMinAmount), 1 / 100, 0)
        values.limitMaxAmount = this.amountFix(parseFloat(values.limitMaxAmount), 1 / 100, 0)
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
