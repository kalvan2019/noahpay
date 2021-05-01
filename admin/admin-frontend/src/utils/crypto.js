import { JSEncrypt } from 'jsencrypt'
import CryptoJS from 'crypto-js'

const PUBLICE_KEY = '-----BEGIN PUBLIC KEY-----MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCTyXNJTZsd1h4Qt/Il1j24u7oZsTbD1Bfaaxf0Lg5WxgvNWNQqW+Qq2UtXdpxnKOjZMOYJosuJqsnmfVBpqyUsGDTHVzaPjOEm82IJvSH435h7Ndi+s1jhIp3o5KfJu6DaLe+E0iTPMgln6fAfZ10iLw9Y+egSu8ZjW7BQo/pkqQIDAQAB-----END PUBLIC KEY-----'

const getRandom = function (len) {
    len = len || 32
    const charsStr = 'ABCDEFJHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890'
    const maxPos = charsStr.length
    let pwd = ''
    for (let i = 0; i < len; i++) {
        pwd += charsStr.charAt(Math.floor(Math.random() * maxPos))
    }
    return pwd
}
const getRasCode = function (key) {
    const encrypt = new JSEncrypt()
    encrypt.setPrivateKey(PUBLICE_KEY)
    return encrypt.encrypt(key)
}
const getAesCode = function (data, key) {
    key = CryptoJS.enc.Latin1.parse(key)
    const iv = CryptoJS.enc.Latin1.parse('16-Bytes--String')
    return CryptoJS.AES.encrypt(data, key, { iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 }).toString()
}

export default {

    encrypt: function (param) {
        const random = getRandom(16)
        const rasKey = getRasCode(random)
        const aesVal = {}
        aesVal.data = getAesCode(param, random)
        aesVal.key = rasKey
        return aesVal
    },
    encryptAll: function (param) {
        const random = getRandom(16)
        const rasKey = getRasCode(random)
        const res = {}
        for (const key in param) {
            if (param[key] !== 0 && !param[key]) {
              if (typeof param[key] !== 'string') {
                continue
              }
            }

            let val = param[key] + ''
            val = val.trim()
            const aesVal = getAesCode(val, random)
            res[key] = aesVal
        }
        res.key = rasKey
        return res
    }

}
