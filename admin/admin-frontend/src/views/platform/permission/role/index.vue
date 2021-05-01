<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--Search Parameters-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="角色名称">
                <a-input v-decorator="['roleName',{rules: []}]" :placeholder="$t('global.placeholder.input')+角色名称"/>
              </a-form-item>
            </a-col>
            <!--Hide Parameters-->
            <template v-if="advanced">
            </template>
            <a-col :md="!advanced && 8 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="handleSearch">{{ $t('global.btn.search') }}</a-button>
                <a-button style="margin-left: 8px" @click="handleReset">{{ $t('global.btn.reset') }}</a-button>
                <a @click="() => this.advanced = !this.advanced" style="margin-left: 8px">
                  {{ advanced ? $t('global.btn.fold') : $t('global.btn.expanded') }}
                  <a-icon :type="advanced ? 'up' : 'down'"/>
                </a>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <!--Global Operator-->
      <div class="table-operator">
        <a-button type="primary" icon="plus" @click="handleAdd()" v-if="hasPerm('platform:permission:role:add')">{{ $t('global.btn.add') }}</a-button>
      </div>
      <!--Data Table-->
      <a-table
        :rowKey=" (record => record.id)"
        :columns="columns"
        :dataSource="dataSource"
        :loading="loading"
      >
        <!--CustomRender-->
        <template v-slot:serial="text, record, index">
          {{ index + 1 }}
        </template>
        <template v-slot:state="text, record" v-if="hasPerm('platform:permission:role:edit')">
          <a>
            <a-popconfirm placement="top" :title="text===0? $t('global.confirm.close') : $t('global.confirm.open')" @confirm="() => handleSwitchState(text,record)">
              <a-badge :status="text | dictIconFilter(that,'switch_state')" :text="text | dictFilter(that,'switch_state')" />
            </a-popconfirm>
          </a>
        </template>
        <template v-slot:state="text" v-else>
          <a-badge :status="text | dictIconFilter(that,'switch_state')" :text="text | dictFilter(that,'switch_state')" />
        </template>
        <!--Row Operator-->
        <template v-slot:action="text, record">
          <span>
            <a href="javascript:" @click="$refs.authority.edit(record)" v-if="hasPerm('platform:permission:role:grant')">授权</a>
            <a-divider type="vertical"/>
            <a href="javascript:" @click="handleEdit(record)" v-if="hasPerm('platform:permission:role:edit')">{{ $t('global.btn.edit') }}</a>
            <a-divider type="vertical"/>
            <a-popconfirm :title="$t('global.confirm.delete')" @confirm="handleDelete(record)" v-if="hasPerm('platform:permission:role:delete')">
              <a-icon slot="icon" type="question-circle-o" style="color: red"/>
              <a href="javascript:">{{ $t('global.btn.delete') }}</a>
            </a-popconfirm>
          </span>
        </template>
      </a-table>
      <!--Custom Component-->
      <save ref="save" @refresh="refresh"></save>
      <authority ref="authority"></authority>
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, deleteByIds, editInfo } from '@/api/platform/permission/role'
  import save from './save'
  import authority from './authority'
  export default {
    mixins: [curdMixins],
    components: {
      save,
      authority
    },
    data () {
      this.columns = [
        {
          title: '#',
          scopedSlots: { customRender: 'serial' }
        },
        {
          title: '角色',
          dataIndex: 'roleName'
        },
        {
          title: '状态',
          dataIndex: 'state',
          scopedSlots: { customRender: 'state' }
        },
        {
          title: '描述',
          dataIndex: 'roleDesc',
          ellipsis: true
        },
        {
          title: '创建时间',
          dataIndex: 'createTime',
          sorter: true
        },
        {
          title: '更新时间',
          dataIndex: 'updateTime',
          sorter: true
        },
        {
          title: this.$t('global.btn.operator'),
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ]
      return {}
    },
    created () {
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
    },
    methods: {
      list (param) {
        return getList(param)
      },
      delete (record) {
        return deleteByIds({ ids: [record.id] })
      },
      edit (values) {
        return editInfo(values)
      }
    }
  }
</script>
<style scoped>
</style>
