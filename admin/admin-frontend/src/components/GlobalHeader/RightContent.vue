<template>
  <div :class="wrpCls">
    <a-select show-search :placeholder="$t('global.placeholder.select')" :default-value="currentSystemCode" @change="switchSystem">
      <template>
        <a-select-option v-for="(value, key) in this.system_code" :key="key">{{ value.dictValue }}</a-select-option>
      </template>
    </a-select>
    <avatar-dropdown :menu="showMenu" :current-user="currentUser" :class="prefixCls" />
    <select-lang :class="prefixCls" />
  </div>
</template>

<script>
import AvatarDropdown from './AvatarDropdown'
import SelectLang from '@/components/SelectLang'
import { mapGetters } from 'vuex'
import { SYSTEM_CODE } from '@/store/mutation-types'
import storage from 'store'
// import { message } from 'ant-design-vue'
// import Router from 'vue-router'

export default {
  name: 'RightContent',
  components: {
    AvatarDropdown,
    SelectLang
  },
  props: {
    prefixCls: {
      type: String,
      default: 'ant-pro-global-header-index-action'
    },
    isMobile: {
      type: Boolean,
      default: () => false
    },
    topMenu: {
      type: Boolean,
      required: true
    },
    theme: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      showMenu: true,
      currentUser: {},
      system_code: {},
      currentSystemCode: storage.get(SYSTEM_CODE)
    }
  },
  created () {
    this.getDictValue('system_code').then(res => {
      this.system_code = res.data
    })
  },
  computed: {
    ...mapGetters(['userInfo']),
    wrpCls () {
      return {
        'ant-pro-global-header-index-right': true,
        [`ant-pro-global-header-index-${(this.isMobile || !this.topMenu) ? 'light' : this.theme}`]: true
      }
    }
  },
  mounted () {
    setTimeout(() => {
      this.currentUser = {
        name: this.userInfo.nickName
      }
    }, 1500)
  },
  methods: {
    switchSystem (value) {
      // const hideMessage = message.loading('正在切换应用！', 0)
      storage.set(SYSTEM_CODE, value, 7 * 24 * 60 * 60 * 1000)
      this.$router.push({ path: '/' })
      window.location.reload()
      // this.$store.dispatch('GetInfo').then(res => {
      //     const permissions = res.data.permissions
      //     this.$store.dispatch('GenerateRoutes', permissions).then(() => {
      //       const createRouter = new Router({
      //         mode: 'history',
      //         base: 'admin-ui'
      //       })
      //       this.$router.match = createRouter.match
      //       this.$router.addRoutes(this.$store.getters.addRouters)
      //       // 跳转到首页不然当前路径会报无路由
      //       this.$router.push('/')
      //       hideMessage()
      //     })
      //   })
      //   .catch(() => {
      //   })
    }
  }
}
</script>
