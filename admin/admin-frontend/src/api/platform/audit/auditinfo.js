import request from '@/utils/request'

const base = 'platform/audit/auditinfo'

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
export function getInfo (param, attrName) {
  return request({
    url: base + '/info/' + param[attrName],
    method: 'get'
  })
}
/**
 * 列表下载
 * @param param
 */
export function downloadFile (param) {
  return request({
    url: base + '/download',
    method: 'post',
    responseType: 'blob',
    data: { ...param, crypto: true }
  })
}
/**
 * 审核通过/拒绝
 * @param param
 * @param pass
 */
export function auditingByIds (param, pass) {
  return request({
    url: base + '/auditing',
    method: 'post',
    data: { ...param, pass: pass, crypto: true }
  })
}
