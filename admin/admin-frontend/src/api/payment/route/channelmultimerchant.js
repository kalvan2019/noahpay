import request from '@/utils/request'

const base = 'payment/route/channelmultimerchant'

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
