import store from '@/store'

export function actionToObject (json) {
  try {
    return JSON.parse(json)
  } catch (e) {
    console.log('err', e.message)
  }
  return []
}

// 用来控制按钮的显示
export function hasPermission (permission) {
  const myPermissions = store.getters.code
  return myPermissions.indexOf(permission) > -1
}
