import request from '@/utils/request'

const base = 'payment/trade/transbill'

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
 * 汇总统计
 * @param param
 */
export function getSum (param) {
  return request({
    url: base + '/sum',
    method: 'post',
    data: { ...param, crypto: true }
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
 * 补单
 * @param param
 */
export function notifyMerchant (param) {
  return request({
    url: base + '/notifyMerchant',
    method: 'post',
    data: { ...param, crypto: true }
  })
}
/**
 * 同步状态
 * @param param
 */
export function syncQuery (param) {
  return request({
    url: base + '/syncQuery',
    method: 'post',
    data: { ...param, crypto: true }
  })
}
