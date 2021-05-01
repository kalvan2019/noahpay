import request from '@/utils/request'
const base = 'common'
export function getDictValue (dictType) {
  return request({
    url: base + `/getDictValue/${dictType}`,
    method: 'get'
  })
}
