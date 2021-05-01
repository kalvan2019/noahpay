import request from '@/utils/request'

const base = 'payment/account/accounttransbatch'

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
