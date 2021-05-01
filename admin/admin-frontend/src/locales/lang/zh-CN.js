import antd from 'ant-design-vue/es/locale-provider/zh_CN'
import momentCN from 'moment/locale/zh-cn'
import global from './zh-CN/global'

import setting from './zh-CN/setting'
import user from './zh-CN/user'
import result from './zh-CN/result'
import platform from './zh-CN/platform'

const components = {
  antLocale: antd,
  momentName: 'zh-cn',
  momentLocale: momentCN
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
