const getters = {
  isMobile: state => state.app.isMobile,
  lang: state => state.app.lang,
  theme: state => state.app.theme,
  color: state => state.app.color,
  token: state => state.user.token,
  nickname: state => state.user.name,
  welcome: state => state.user.welcome,
  avatar: state => state.user.avatar,
  roles: state => state.user.roles,
  userInfo: state => state.user.info,
  code: state => state.user.code,
  addRouters: state => state.permission.addRouters,
  multiTab: state => state.app.multiTab
}

export default getters
