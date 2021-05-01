<template>
  <a-modal
    :title="$t('admin.title') + ` ${title}`"
    :width="800"
    destroyOnClose
    centered
    :visible="visible"
    :confirmLoading="loading"
    @ok="visible=!visible"
    @cancel="visible=!visible"
  >
    <a-card :bordered="true">
      <a-descriptions :title="$t('admin.userInfo')">
        <a-descriptions-item :label="$t('admin.userCode')">{{ model.userCode }}</a-descriptions-item>
        <a-descriptions-item :label="$t('admin.userName')">{{ model.userName }}</a-descriptions-item>
        <a-descriptions-item :label="$t('admin.mobile')">{{ model.mobile }}</a-descriptions-item>
        <a-descriptions-item :label="$t('admin.email')">{{ model.email }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions :title="$t('admin.loginInfo')">
        <a-descriptions-item :label="$t('admin.loginCount')">{{ model.loginCount }}</a-descriptions-item>
        <a-descriptions-item :label="$t('admin.errorCount')">{{ model.loginErrorCount }}</a-descriptions-item>
        <a-descriptions-item :label="$t('admin.lastLoginIp')">{{ model.lastLoginIp }}</a-descriptions-item>
        <a-descriptions-item :label="$t('admin.lockTime')">{{ model.lockedExpiredTime }}</a-descriptions-item>
        <a-descriptions-item :label="$t('admin.lastLoginTime')">{{ model.lastLoginTime }}</a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-descriptions :title="$t('admin.roleInfo')">
        <a-descriptions-item label="">
          <template v-if="model.roles">
            <a-tag color="blue" v-for="role in model.roles" :key="role.id">{{ role.roleName }}</a-tag>
          </template>
        </a-descriptions-item>
      </a-descriptions>
      <a-divider style="margin-bottom: 32px"/>
      <a-steps :direction="isMobile && 'vertical' || 'horizontal'" :current="2" progressDot>
        <a-step>
          <template v-slot:title>
            <span>{{ $t('admin.createTime') }}</span>
          </template>
          <template v-slot:description>
            <div class="antd-pro-pages-profile-advanced-style-stepDescription">
              {{ model.createTime }}
            </div>
          </template>
        </a-step>
        <a-step>
          <template v-slot:title>
            <span>{{ $t('admin.updateTime') }}</span>
          </template>
          <template v-slot:description>
            <div class="antd-pro-pages-profile-advanced-style-stepDescription">
              {{ model.updateTime }}
            </div>
          </template>
        </a-step>
        <a-step>
          <template v-slot:title>
            <span>{{ $t('admin.state') }}</span>
          </template>
          <template v-slot:description>
            <div class="antd-pro-pages-profile-advanced-style-stepDescription">
              {{ switch_state[model.state].dictValue }}
            </div>
          </template>
        </a-step>
      </a-steps>
    </a-card>
  </a-modal>
</template>
<script>
  import { baseMixin } from '@/store/app-mixin'
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getInfo } from '@/api/platform/system/admin'
  export default {
    mixins: [baseMixin, detailMixins],
    data () {
      return {
        switch_state: {}
      }
    },
    created () {
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
    },
    methods: {
      info (record) {
        return getInfo(record)
      }
    }
  }
</script>
<style scoped>
</style>
