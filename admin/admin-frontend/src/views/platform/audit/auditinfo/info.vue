<template>
  <a-modal
    :title="`审核信息` + title"
    :width="800"
    destroyOnClose
    centered
    :visible="visible"
    :confirmLoading="loading"
    @ok="visible=!visible"
    @cancel="visible=!visible"
  >
    <a-card :bordered="true">
      <a-descriptions title="">
        <a-descriptions-item label="id">{{ model.id }}</a-descriptions-item>
        <a-descriptions-item label="数据类型">{{ this.dictFilter(model.dataType,'log_group') }}</a-descriptions-item>
      </a-descriptions>
      <a-descriptions title="">
        <a-descriptions-item label="变更前数据">
          <b-code-editor
            v-model="model.dataOld"
            :line-wrap="false"
            theme="dracula"
            :readonly="true"
            ref="editor"
            style="line-height: 21px;width: 400px"
          >
          </b-code-editor>
        </a-descriptions-item>
      </a-descriptions>
      <a-descriptions title="">
        <a-descriptions-item label="变更后数据">
          <b-code-editor
            v-model="model.dataNew"
            :line-wrap="false"
            theme="dracula"
            :readonly="true"
            ref="editor"
            style="line-height: 21px;width: 400px"
          >
          </b-code-editor>
        </a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="发起人">{{ model.applicant }}</a-descriptions-item>
        <a-descriptions-item label="审核类型">{{ this.dictFilter(model.auditType,'log_type') }}</a-descriptions-item>
        <a-descriptions-item label="审核说明">{{ model.auditNote }}</a-descriptions-item>
        <a-descriptions-item label="审核人">{{ model.auditUser }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="审核备注">{{ model.auditRemark }}</a-descriptions-item>
        <a-descriptions-item label="审核状态">{{ this.dictFilter(model.auditState,'audit_state') }}</a-descriptions-item>
        <a-descriptions-item label="创建时间">{{ model.createTime }}</a-descriptions-item>
        <a-descriptions-item label="更新时间">{{ model.updateTime }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
    </a-card>
  </a-modal>
</template>
<script>
  import { baseMixin } from '@/store/app-mixin'
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getInfo } from '@/api/platform/audit/auditinfo'
  export default {
    mixins: [baseMixin, detailMixins],
    data () {
      return {
        log_group: {},
        log_type: {},
        audit_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('log_group').then(res => {
        this.log_group = res.data
      })
      this.getDictValue('log_type').then(res => {
        this.log_type = res.data
      })
      this.getDictValue('audit_state').then(res => {
        this.audit_state = res.data
      })
    },
    methods: {
      // info,mixins call
      info (record) {
        return getInfo(record, 'id')
      }
    }
  }
</script>
<style scoped>
</style>
