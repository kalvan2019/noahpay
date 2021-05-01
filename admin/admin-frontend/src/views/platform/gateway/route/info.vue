<template>
  <a-modal
    :title="`网关路由表` + title"
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
        <a-descriptions-item label="路由id">{{ model.routeId }}</a-descriptions-item>
        <a-descriptions-item label="uri路径">{{ model.uri }}</a-descriptions-item>
      </a-descriptions>
      <a-descriptions title="">
        <a-descriptions-item label="判定器">
          <b-code-editor
            v-model="model.predicates"
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
        <a-descriptions-item label="过滤器">
          <b-code-editor
            v-model="model.filters"
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
        <a-descriptions-item label="优先级">{{ model.orders }}</a-descriptions-item>
        <a-descriptions-item label="描述">{{ model.description }}</a-descriptions-item>
        <a-descriptions-item label="状态">{{ this.dictFilter(model.state,'switch_state') }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
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
  import { getInfo } from '@/api/platform/gateway/route'
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
