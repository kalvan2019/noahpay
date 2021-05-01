import request from '@/utils/request'

const base = 'platform/permission/menu'

/**
 * 列表查询
 * @param param
 */
export function getList (param) {
  return request({
    url: base + '/list',
    method: 'post',
    data: { ...param, crypto: true }
  })
}
/**
 * 详情查询
 * @param param
 */
export function getInfo (param) {
  return request({
    url: base + '/info/' + param.id,
    method: 'get'
  })
}
/**
 * 新增信息
 * @param param
 */
export function addInfo (param) {
  return request({
    url: base + '/add',
    method: 'post',
    data: { ...param, crypto: true }
  })
}
/**
 * 修改信息
 * @param param
 */
export function editInfo (param) {
  return request({
    url: base + '/edit',
    method: 'post',
    data: { ...param, crypto: true }
  })
}
/**
 * 删除信息
 * @param param
 */
export function deleteByIds (param) {
  return request({
    url: base + '/delete',
    method: 'post',
    data: { ...param, crypto: true }
  })
}
