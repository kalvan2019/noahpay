// eslint-disable-next-line
import { BasicLayout, BlankLayout, PageView, RouteView, Iframe } from '@/layouts'

// 前端路由表
const constantRouterComponents = {
  // 基础页面 layout 必须引入
  BasicLayout: BasicLayout,
  BlankLayout: BlankLayout,
  RouteView: RouteView,
  PageView: PageView,
  Iframe: Iframe,
  '403': () => import(/* webpackChunkName: "error" */ '@/views/exception/403'),
  '404': () => import(/* webpackChunkName: "error" */ '@/views/exception/404'),
  '500': () => import(/* webpackChunkName: "error" */ '@/views/exception/500'),
  // exception
  'Exception403': () => import(/* webpackChunkName: "fail" */ '@/views/exception/403'),
  'Exception404': () => import(/* webpackChunkName: "fail" */ '@/views/exception/404'),
  'Exception500': () => import(/* webpackChunkName: "fail" */ '@/views/exception/500'),
  // platform
  'home': () => import('@/views/home')
}

// 前端未找到页面路由（固定不用改）
const notFoundRouter = {
  path: '*', redirect: '/404', hidden: true
}

// 根级菜单
const rootRouter = {
  name: 'index',
  path: '/',
  component: 'BasicLayout',
  redirect: '/home',
  title: '',
  icon: 'home',
  children: []
}
// 个人中心页面
const userAccount = [
  // {
  //   'name': 'account',
  //   'meta': {
  //     'title': '个人页',
  //     'icon': 'user',
  //     'show': false
  //   },
  //   'redirect': '/account/center',
  //   'component': 'RouteView'
  // },
  // {
  //   'name': 'center',
  //   'meta': {
  //     'title': '个人中心',
  //     'show': false
  //   },
  //   'component': 'AccountCenter'
  // },
  // // 特殊三级菜单
  // {
  //   'name': 'settings',
  //   'pid': '10028',
  //   'id': '10030',
  //   'meta': {
  //     'title': '个人设置',
  //     'hideHeader': true,
  //     'hideChildren': true,
  //     'show': false
  //   },
  //   'redirect': '/account/settings/base',
  //   'component': 'AccountSettings'
  // },
  // {
  //   'name': 'BaseSettings',
  //   'path': '/account/settings/base',
  //   'meta': {
  //     'title': '基本设置',
  //     'show': false
  //   },
  //   'component': 'BaseSettings'
  // },
  // {
  //   'name': 'SecuritySettings',
  //   'path': '/account/settings/security',
  //   'meta': {
  //     'title': '安全设置',
  //     'show': false
  //   },
  //   'component': 'SecuritySettings'
  // },
  // {
  //   'name': 'CustomSettings',
  //   'path': '/account/settings/custom',
  //   'meta': {
  //     'title': '个性化设置',
  //     'show': false
  //   },
  //   'component': 'CustomSettings'
  // },
  // {
  //   'name': 'BindingSettings',
  //   'path': '/account/settings/binding',
  //   'meta': {
  //     'title': '账户绑定',
  //     'show': false
  //   },
  //   'component': 'BindingSettings'
  // },
  // {
  //   'name': 'NotificationSettings',
  //   'path': '/account/settings/notification',
  //   'meta': {
  //     'title': '新消息通知',
  //     'show': false
  //   },
  //   'component': 'NotificationSettings'
  // },
  {
    'title': 'HOME',
    'type': 'root',
    'hideChildren': 1,
    'icon': 'home',
    'path': '/home',
    'redirect': '',
    'name': 'home',
    'code': 'admin',
    'priority': 1,
    'id': 0,
    'parentId': 0,
    'createTime': null,
    'updateTime': null,
    'children': []
  }
]
/**
 * 动态生成菜单
 * @param data
 * @returns {Promise<Router>}
 */
export const generatorDynamicRouter = (data) => {
  return new Promise((resolve, reject) => {
    data = [...data]
    const menuNav = []
    const childrenNav = []
    //  后端数据, 根级树数组,  根级 PID
    // console.log(JSON.stringify(data))
    // 增加静态网页
    listToTree(userAccount, childrenNav, 0)
    // 增加数据库配置菜单
    listToTree(data, childrenNav, 0)
    rootRouter.children = childrenNav
    menuNav.push(rootRouter)
    // console.log('menuNav', menuNav)
    const routers = generator(menuNav)
    routers.push(notFoundRouter)
    // console.log('routers', JSON.stringify(routers))
    resolve(routers)
  })
}

/**
 * 格式化树形结构数据 生成 vue-router 层级路由表
 *
 * @param routerMap
 * @param parent
 * @returns {*}
 */
export const generator = (routerMap, parent) => {
  return routerMap.map(item => {
    const { path, name, title, icon, redirect, hideChildren, hidden, hiddenHeaderContent, target } = item || {}
    const currentRouter = {
      // 如果路由设置了 path，则作为默认 path，否则 路由地址 动态拼接生成如 /dashboard/workplace
      path: path || `${parent && parent.path || ''}/${name}`,
      // 路由名称，建议唯一
      name: name || '',
      // 该路由对应页面的 组件 :方案1
      // component: constantRouterComponents[item.component || item.key],
      // 该路由对应页面的 组件 :方案2 (动态加载) 根据菜单path找到views
      component: (constantRouterComponents[item.component || name]) || (() => import(`@/views${path}`)),
      // meta: 页面标题, 菜单图标, 页面权限(供指令权限用，可去掉)
      meta: {
        title: title,
        icon: icon,
        hiddenHeaderContent: hiddenHeaderContent,
        target: target,
        permission: name
      },
      redirect: redirect,
      hideChildrenInMenu: hideChildren === 0,
      hidden: hidden
    }
    // 子路由的父级路由必须有 `router-view` 才能让子路由渲染出来
    if (item.menu && item.children && item.children.length > 0 && item.hideChildren !== 0) {
      currentRouter.component = constantRouterComponents['RouteView']
    }
    if (currentRouter.path.startsWith('http')) {
      // 链接跳转不需要component
      currentRouter.meta.target = '_blank'
      delete currentRouter.component
    } else {
      // 为了防止出现后端返回结果不规范，处理有可能出现拼接出两个 反斜杠
      currentRouter.path = currentRouter.path.replace('//', '/')
    }
    // 是否有子菜单，并递归处理
    if (item.children && item.children.length > 0) {
      currentRouter.children = generator(item.children, currentRouter)
    }
    return currentRouter
  })
}

/**
 * 去除button级别菜单
 * @param list 源数组
 * @param tree 树
 * @param parentId 父ID
 */
const listToTree = (list, tree, parentId) => {
  list.forEach(item => {
    // 首页需要渲染,按钮权限不渲染菜单
    if (item.type === 'button') {
      return
    }
    // 拥有下级菜单的标识
    item.menu = true
    // 判断是否为父级菜单
    if (item.parentId === parentId) {
      const child = {
        ...item,
        children: []
      }
      // 迭代 list， 找到当前菜单相符合的所有子菜单
      if (item.children && item.children.length > 0) {
        listToTree(item.children, child.children, item.id)
      }
      // 删掉不存在 children 值的属性
      if (child.children.length <= 0) {
        delete child.children
      }
      // 加入到树中
      tree.push(child)
    }
  })
}
