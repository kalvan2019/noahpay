import { mapState } from 'vuex'

const i18nMixin = {
  computed: {
    ...mapState({
      currentLang: state => state.app.lang
    })
  },
  methods: {
    setLang (lang) {
      this.$store.dispatch('setLang', lang)
      // 菜单和字典是由后端配置,切换语言需要同步刷新
      window.location.reload()
    }
  }
}

export default i18nMixin
