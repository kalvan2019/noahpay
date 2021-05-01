import storage from 'store'
import { login, getInfo, logout } from '@/api/login'
import { ACCESS_TOKEN, SYSTEM_CODE } from '@/store/mutation-types'
import { welcome } from '@/utils/util'

const user = {
  state: {
    token: '',
    name: '',
    welcome: '',
    avatar: '',
    roles: [],
    info: {},
    code: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, { name, welcome }) => {
      state.name = name
      state.welcome = welcome
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_INFO: (state, info) => {
      state.info = info
    },
    SET_CODE: (state, code) => {
      state.code = code
    }
  },

  actions: {
    // 登录
    Login ({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(res => {
          if (res.state === 0) {
            const data = res.data
            storage.set(ACCESS_TOKEN, data.token, 7 * 24 * 60 * 60 * 1000)
            commit('SET_TOKEN', data.token)
            resolve()
          } else {
            reject(res.message)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo(storage.get(SYSTEM_CODE)).then(res => {
          if (res.state === 0) {
            const data = res.data
            storage.set(SYSTEM_CODE, data.systemCode, 7 * 24 * 60 * 60 * 1000)
            commit('SET_ROLES', data.permissions)
            commit('SET_INFO', data.user)
            commit('SET_CODE', data.code)
            commit('SET_NAME', { name: data.user.nickName, welcome: welcome() })
            commit('SET_AVATAR', data.user.avatar)
            resolve(res)
          } else {
            reject(res.message)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    Logout ({ commit, state }) {
      return new Promise((resolve) => {
        logout(state.token).then(() => {
          resolve()
        }).catch(() => {
          resolve()
        }).finally(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_CODE', [])
          storage.remove(ACCESS_TOKEN)
          storage.remove(SYSTEM_CODE)
        })
      })
    }
  }
}

export default user
