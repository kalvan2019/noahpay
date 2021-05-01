<template>
  <a-drawer
    title="授权"
    :destroyOnClose="true"
    width="400px"
    placement="right"
    @close="visible=false"
    :visible="visible"
  >
    <a-spin :spinning="loading">
      <Tree
        v-if="treeData.length>0"
        :treeData="treeData"
        :replace-fields="{key: 'id'}"
        :checkedKeys="checkIds"
        :checkable="true"
        :multiple="true"
        :defaultExpandAll="false"
        :auto-expand-parent="autoExpandParent"
        :expanded-keys="expandedKeys"
        @expand="onExpand"
        @check="onCheck"
      >
      </Tree>
      <a-button style="margin: 10px 10px" type="primary" @click="handleSubmit">保存权限</a-button>
    </a-spin>
  </a-drawer>
</template>
<script>
  import { Tree } from 'ant-design-vue'
  import { grant, getMenuList } from '@/api/platform/permission/role'
  export default {
    components: {
      Tree
    },
    data () {
      return {
        visible: false,
        treeData: [],
        checkIds: [],
        autoExpandParent: true,
        expandedKeys: [],
        halfCheckedKeys: [],
        loading: false,
        roleId: null
      }
    },
    methods: {
      edit (record) {
        this.roleId = record.id
        this.visible = true
        this.loading = true
        getMenuList(record).then(res => {
          this.loading = false
          if (res.state === 0) {
            this.checkIds = []
            // 替换treeNode中 title,key,children字段为treeData中对应的字段
            this.treeData = res.data.menuList
            this.deepRole(res.data.menuList, res.data.roleMenu ? res.data.roleMenu : [])
          } else {
            this.$message.error(res.message)
          }
        })
      },
      deepRole (allMenu, roleMenu) {
        let rs = true // allMenu 下有一个权限不存在就返回false会影响父菜单传值
        let half = false // 当rs 返回false时但有部分子权限时记录父菜单
        allMenu.forEach(menu => {
          if (menu.children && menu.children.length > 0) {
            // 有子菜单递归往下找
            if (!this.deepRole(menu.children, roleMenu)) {
              rs = false
              if (half) {
                this.halfCheckedKeys.push(menu.id)
              }
            } else {
              const checkId = this.findRoleId(menu.id, roleMenu)
              if (checkId) {
                this.checkIds.push(checkId)
              } else {
                rs = false
              }
            }
          } else {
            const checkId = this.findRoleId(menu.id, roleMenu)
            if (checkId) {
              this.checkIds.push(checkId)
              half = true
            } else {
              rs = false
            }
          }
          if (menu.type === 'root') {
            this.expandedKeys.push(menu.id)
          }
        })
        return rs
      },
      onExpand (expandedKeys) {
        this.expandedKeys = expandedKeys
        this.autoExpandParent = false
      },
      findRoleId (id, roleMenu) {
        const role = roleMenu.find(item => item.id === id)
        return role ? role.id : null
      },
      handleSubmit () {
        this.loading = true
        grant({
          roleId: this.roleId,
          menuIds: [...this.checkIds, ...this.halfCheckedKeys]
        }).then(res => {
          this.loading = false
          if (res.state === 0) {
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      },
      onCheck (checkedKeys, info) {
        this.halfCheckedKeys = info.halfCheckedKeys
        this.checkIds = checkedKeys
      }
    }
  }
</script>

<style scoped>

</style>
