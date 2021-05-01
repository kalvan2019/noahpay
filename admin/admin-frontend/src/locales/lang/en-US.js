import antdEnUS from 'ant-design-vue/es/locale-provider/en_US'
import momentEU from 'moment/locale/eu'
import global from './en-US/global'

import setting from './en-US/setting'
import user from './en-US/user'
import result from './en-US/result'
import platform from './en-US/platform'

const components = {
  antLocale: antdEnUS,
  momentName: 'eu',
  momentLocale: momentEU
}

export default {
  message: '-',
  ...components,
  ...global,
  ...setting,
  ...user,
  ...result,
  ...platform
}
