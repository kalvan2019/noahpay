<template>
  <a-modal
    ref="modal"
    :title="`菜单${title}`"
    :width="1000"
    destroyOnClose
    centered
    :visible="visible"
    :confirmLoading="loading"
    @ok="handleSubmit('id')"
    @cancel="visible=!visible"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <a-form-item label="菜单类型">
          <a-radio-group
            @change="typeChange"
            v-decorator="['type', {initialValue: model.type?model.type:type}]"
            buttonStyle="solid">
            <a-radio-button value="root">目录</a-radio-button>
            <a-radio-button value="menu">菜单</a-radio-button>
            <a-radio-button value="button">按钮/权限</a-radio-button>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="主键ID" v-show="model.id" >
          <a-input v-decorator="['id', { initialValue: model.id }]" disabled />
        </a-form-item>
        <a-form-item :label="type==='button'?'按钮/权限':'菜单名称'">
          <a-input :placeholder="type==='button'?'请输入按钮/权限':'请输入菜单名称'" v-decorator="['title', {initialValue: model.title,rules: [{ required: true, message: '名称不能空！' }]}]"/>
        </a-form-item>
        <a-form-item label="所属应用" v-if="type==='root'">
          <a-select
            show-search
            :placeholder="$t('global.placeholder.select')"
            v-decorator="['systemCode', {initialValue: model.systemCode?model.systemCode:'',rules:[{ required: true, message: '所属应用不能为空！' }]}]">
            <a-select-option v-for="(value, key) in this.system_code" :key="key">
              ({{ key }}){{ value.dictValue }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="父级菜单" v-if="type!=='root'">
          <TreeSelect
            :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
            :treeData="menuList"
            treeCheckStrictly
            :getPopupContainer="(trigger)=>{return trigger.parentElement}"
            :placeholder="$t('global.placeholder.select')"
            v-decorator="['parentId', {initialValue: model.parentId,rules: [{ required: true, message: '父菜单不能空！' }]}]"
          >
          </TreeSelect>
        </a-form-item>
        <a-divider />
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item label="权限码">
              <a-input placeholder="权限code 例如 user:add" v-decorator="['code', {initialValue: model.code,rules: [{ required: true, message: '菜单图标不能空！' }]}]"/>
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item label="排序">
              <a-input :placeholder="$t('global.placeholder.input')+排序" v-decorator="['priority', {initialValue: model?model.priority:1,rules: [{ required: true, message: '排序不能空！' }]}]"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item v-if="type!=='button'">
              <span slot="label">
                <a-tooltip title="前端vue组件 views文件夹下路径，例：platform/permission/menu/index。注：目录级填写：RouteView(不带面包屑)，PageView(带面包屑)，菜单级内链打开http链接填写：Iframe">
                  <a-icon type="question-circle-o" />
                </a-tooltip>&nbsp;
                前端组件
              </span>
              <a-input :placeholder="$t('global.placeholder.input')+前端组件" v-decorator="['name', {initialValue: model.name,rules: [{ required: true, message: '组件名不能空！' }]}]"/>
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item label="隐藏子菜单" v-if="type!=='button'">
              <a-input type="hidden" v-decorator="['hideChildren',{initialValue:model.hideChildren}]"/>
              <a-switch :defaultChecked="model.hideChildren === 0 && true || false" @change="onChangeHideChildren" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item label="路由地址" v-if="type!=='button'">
              <span slot="label">
                <a-tooltip title="浏览器显示的URL，例：/menu，对应打开页面为菜单页面">
                  <a-icon type="question-circle-o" />
                </a-tooltip>&nbsp;
                路由地址
              </span>
              <a-input :placeholder="$t('global.placeholder.input')+路由地址" v-decorator="['path', {initialValue: model.path,rules: [{ required: true, message: '路由地址不能空！' }]}]"/>
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item v-if="type!=='button'">
              <span slot="label">
                <a-tooltip title="如需打开首页加载此目录下菜单，请填写加载菜单路由，设为首页后其他设置的主页将被替代">
                  <a-icon type="question-circle-o" />
                </a-tooltip>&nbsp;
                重定向
              </span>
              <a-input :placeholder="$t('global.placeholder.input')+重定向地址" v-decorator="['redirect', {initialValue: model.redirect,rules: [{}]}]"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item label="菜单图标" v-if="type!=='button'">
              <a-icon :type="showIcon"/>
              <a-input :placeholder="$t('global.placeholder.select')" disabled="disabled" v-decorator="['icon', {initialValue: model.icon}]" >
                <a-icon slot="addonAfter" @click="visibleIcon = true" type="setting" />
              </a-input>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
    <a-modal
      :width="850"
      :visible="visibleIcon"
      @cancel="visibleIcon = false"
      footer=""
      :mask="false"
      :closable="false"
      :destroyOnClose="true"
    >
      <icon-selector @change="handleIconChange" :value="model.icon"/>
    </a-modal>
  </a-modal>
</template>
<script>
  import { TreeSelect } from 'ant-design-vue'
  import IconSelector from '@/components/IconSelector'
  import detailMixins from '@/utils/mixins/detailMixins'
  import { getList, getInfo, addInfo, editInfo } from '@/api/platform/permission/menu'
  export default {
    mixins: [detailMixins],
    components: {
      TreeSelect,
      IconSelector
    },
    data () {
      return {
        menuList: [],
        type: 'root',
        showIcon: '',
        visibleIcon: false,
        system_code: {}
      }
    },
    created () {
      this.getDictValue('system_code').then(res => {
        this.system_code = res.data
      })
      getList().then(res => {
        this.menuList = this.convertMenuList(res.data)
      })
    },
    methods: {
      info (record) {
        return getInfo(record)
      },
      filterForm (values) {
        if (values.parentId && values.parentId === this.model.id) {
          this.$message.error('上级菜单不能是当前菜单')
          return
        }
        if (values.type === 'root') {
          values.parentId = 0
        }
        if (values.type === 'button') {
          values.path = '#'
        }
        return values
      },
      openAddBack (record) {
        this.model.parentId = record.id
        this.type = record.type
        this.showIcon = record.icon
        if (record.type === 'root') {
          this.type = 'menu'
        }
      },
      add (values) {
        return addInfo(values)
      },
      openEditBack () {
        this.type = this.model.type
        this.showIcon = this.model.icon
      },
      edit (values) {
        return editInfo(values)
      },
      handleIconChange (icon) {
        this.showIcon = icon
        this.form.setFieldsValue({ 'icon': icon })
        this.visibleIcon = false
      },
      typeChange (data) {
        this.type = data.target.value
      },
      convertMenuList (menu) {
        if (!menu) {
          return []
        } else {
          const menuList = []
          menu.map(item => {
            const rs = {}
            rs.title = item.title
            rs.value = item.id
            rs.key = item.id
            if (item.children && item.children.length > 0) {
              rs.children = this.convertMenuList(item.children)
            }
            menuList.push(rs)
          })
          return menuList
        }
      },
      onChangeHideChildren (checked) {
        this.form.setFieldsValue({ 'hideChildren': checked ? 0 : 1 })
      }
    }
  }
</script>
<style scoped>
</style>
