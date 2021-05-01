<template>
  <global-footer class="footer custom-render">
    <template v-slot:links>
      <a href="#" target="_blank">{{ $t('global.home') }}</a>
      <a href="#" target="_blank">{{ $t('global.support') }}</a>
    </template>
    <template v-slot:copyright>
      <a href="#" target="_blank">{{ COPYRIGHT }}</a>
    </template>
  </global-footer>
</template>

<script>
import { GlobalFooter } from '@ant-design-vue/pro-layout'

export default {
  name: 'ProGlobalFooter',
  data () {
    return {
      COPYRIGHT: process.env.VUE_APP_COPYRIGHT
    }
  },
  components: {
    GlobalFooter
  }
}
</script>
