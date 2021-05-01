<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--Search Parameters-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="所属应用">
                <a-select show-search v-model="queryParam.systemCode" :placeholder="$t('global.placeholder.select')">
                  <a-select-option value="">{{ $t('global.show.all') }}</a-select-option>
                  <template>
                    <a-select-option v-for="(value, key) in this.system_code" :key="key">({{ key }}){{ value.dictValue }}</a-select-option>
                  </template>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="!advanced && 8 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="handleSearch">{{ $t('global.btn.search') }}</a-button>
                <a-button style="margin-left: 8px" @click="handleReset">{{ $t('global.btn.reset') }}</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <!--Global Operator-->
      <div class="table-operator">
        <a-button type="primary" icon="plus" @click="handleAdd()" v-if="hasPerm('platform:permission:menu:add')">{{ $t('global.btn.add') }}</a-button>
      </div>
      <!--Data Table-->
      <a-table
        :key="ellipsis"
        :rowKey=" (record => record.id)"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="false"
        :loading="loading"
        :scroll="{ x: 1400}"
        @change="tableChange"
        size="small"
        :defaultExpandAllRows="false"
      >
        <!--CustomRender-->
        <template v-slot:icon="icon">
          <a-icon v-if="icon" :type="icon"/>
        </template>
        <!--Row Operator-->
        <template v-slot:action="text, record">
          <span>
            <a href="javascript:" @click="handleAdd(record)" v-if="hasPerm('platform:permission:menu:add')">子菜单</a>
            <a-divider type="vertical"/>
            <a href="javascript:" @click="handleEdit(record)" v-if="hasPerm('platform:permission:menu:edit')">{{ $t('global.btn.edit') }}</a>
            <a-divider type="vertical"/>
            <a-popconfirm :title="$t('global.confirm.delete')" @confirm="handleDelete(record)" v-if="hasPerm('platform:permission:menu:delete')">
              <a-icon slot="icon" type="question-circle-o" style="color: red"/>
              <a href="javascript:">{{ $t('global.btn.delete') }}</a>
            </a-popconfirm>
          </span>
        </template>
      </a-table>
      <!--Custom Component-->
      <save ref="save" @refresh="refresh"></save>
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, deleteByIds } from '@/api/platform/permission/menu'
  import save from './save'
  export default {
    mixins: [curdMixins],
    components: {
      save
    },
    data () {
      this.columns = [
        {
          title: '菜单名称',
          dataIndex: 'title'
        },
        {
          title: '路由地址',
          dataIndex: 'path',
          customRender: (value, record) => {
            return {
              children: record.redirect ? `${value}[redirect:${record.redirect}]` : value,
              attrs: {}
            }
          }
        },
        {
          title: '图标',
          dataIndex: 'icon',
          scopedSlots: { customRender: 'icon' }
        },
        {
          title: '组件',
          dataIndex: 'name'
        },
        {
          title: '优先级',
          dataIndex: 'priority'
        },
        {
          title: '类型',
          dataIndex: 'type'
        },
        {
          title: '权限码',
          dataIndex: 'code'
        },
        {
          title: '隐藏子菜单',
          dataIndex: 'hideChildren',
          customRender: (value) => {
            return {
              children: value === 0 ? '隐藏' : '',
              attrs: {}
            }
          }
        },
        {
          title: this.$t('global.btn.operator'),
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' },
          fixed: 'right'
        }
      ]
      return {
        ellipsis: false,
        system_code: {}
      }
    },
    created () {
      this.getDictValue('system_code').then(res => {
        this.system_code = res.data
      })
    },
    methods: {
      list (param) {
        return getList(param)
      },
      queryBack () {
        // 通过改变key 实现 defaultExpandAllRows 生效
        this.ellipsis = true
        // this.$refs['.ant-table-row-expand-icon ant-table-row-collapsed'].click()
      },
      delete (record) {
        return deleteByIds({ ids: [record.id] })
      }
    }
  }
</script>
<style scoped>
</style>
