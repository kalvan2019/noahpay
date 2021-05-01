<template>
  <a-modal
    :title="`渠道支持银行卡类型组` + title"
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
        <a-descriptions-item label="行别组">{{ model.bankTypeGroup }}</a-descriptions-item>
        <a-descriptions-item label="行别">{{ this.dictFilter(model.bankType,'bank_type') }}</a-descriptions-item>
        <a-descriptions-item label="状态">{{ this.dictFilter(model.state,'switch_state') }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions title="">
        <a-descriptions-item label="更新时间">{{ model.updateTime }}</a-descriptions-item>
        <a-descriptions-item label="创建时间">{{ model.createTime }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
    </a-card>
  </a-modal>
</template>
<script>
  import { baseMixin } from '@/store/app-mixin'
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getInfo } from '@/api/payment/route/channelsupportbanktypegroup'
  export default {
    mixins: [baseMixin, detailMixins],
    data () {
      return {
        bank_type: {},
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('bank_type').then(res => {
        this.bank_type = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
    },
    methods: {
      // 详情查看,mixins调用
      info (record) {
        return getInfo(record, 'id')
      }
    }
  }
</script>
<style scoped>
</style>
