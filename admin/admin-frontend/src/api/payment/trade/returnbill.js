import request from '@/utils/request'

const base = 'payment/trade/returnbill'

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
 * 下载批量导入模板
 * @param param
 */
export function downloadTemplateFile (param) {
  return request({
    url: base + '/downloadTemplate',
    method: 'get',
    responseType: 'blob'
  })
}
/**
 * 批量导入信息
 * @param file
 */
export function uploadFile (file) {
  const formData = new FormData()
  formData.append('file', file.file)
  return request({
    url: base + '/upload',
    method: 'post',
    data: formData
  })
}
