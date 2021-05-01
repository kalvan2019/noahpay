<template>
  <a-modal
    :title="`接入方服务授权表` + title"
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
        <a-descriptions-item label="自增id">{{ model.id }}</a-descriptions-item>
        <a-descriptions-item label="appId">{{ model.appId }}</a-descriptions-item>
        <a-descriptions-item label="服务">{{ model.service }}</a-descriptions-item>
        <a-descriptions-item label="服务状态">{{ this.dictFilter(model.state,'switch_state') }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="服务访问限制">{{ model.maxAccess }}</a-descriptions-item>
        <a-descriptions-item label="时间窗口">{{ model.times }}</a-descriptions-item>
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
  import { getInfo } from '@/api/platform/gateway/authorize'
  export default {
    mixins: [baseMixin, detailMixins],
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
      // info,mixins call
      info (record) {
        return getInfo(record, 'id')
      }
    }
  }
</script>
<style scoped>
</style>
