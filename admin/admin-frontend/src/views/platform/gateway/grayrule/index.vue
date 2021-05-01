<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--Search Parameters-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="规则名">
                <a-input v-decorator="['ruleName',{rules: []}]" :placeholder="$t('global.placeholder.input')+规则名"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="灰度规则">
                <a-input v-decorator="['rule',{rules: []}]" :placeholder="$t('global.placeholder.input')+灰度规则"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="状态">
                <a-select show-search :placeholder="$t('global.placeholder.select')" v-decorator="['state',{rules: []}]">
                  <a-select-option value="">{{ $t('global.show.all') }}</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('platform:gateway:grayrule:download')">{{ $t('global.btn.download') }}</a-button>
        <a-button type="primary" icon="plus" @click="handleAdd" v-if="hasPerm('platform:gateway:grayrule:add')">{{ $t('global.btn.add') }}</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="delete" @click="handleMenuClick" v-if="hasPerm('platform:gateway:grayrule:delete')">
              <a-icon type="delete"/>
              {{ $t('global.btn.delete') }}
            </a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            {{ $t('global.btn.batch') }}
            <a-icon type="down"/>
          </a-button>
        </a-dropdown>
      </div>
      <!--Sum-->
      <!--Data Table-->
      <a-table
        :rowKey=" (record => record.id)"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="pagination"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        :loading="loading"
        @change="tableChange"
        size="small"
        bordered
      >
        <!--CustomRender-->
        <template v-slot:state="text, record" v-if="hasPerm('platform:gateway:grayrule:edit')">
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
            <a href="javascript:" @click="handleInfo(record)" v-if="hasPerm('platform:gateway:grayrule:info')">{{ $t('global.btn.info') }}</a>
            <a-divider type="vertical"/>
            <a href="javascript:" @click="handleEdit(record)" v-if="hasPerm('platform:gateway:grayrule:edit')">{{ $t('global.btn.edit') }}</a>
            <a-divider type="vertical"/>
            <a-popconfirm :title="$t('global.confirm.delete')" @confirm="handleDelete(record)" v-if="hasPerm('platform:gateway:grayrule:delete')">
              <a-icon slot="icon" type="question-circle-o" style="color: red"/>
              <a href="javascript:">{{ $t('global.btn.delete') }}</a>
            </a-popconfirm>
          </span>
        </template>
      </a-table>
      <!--Custom Component-->
      <save ref="save" @refresh="refresh" />
      <info ref="info" @refresh="refresh" />
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, downloadFile, deleteByIds, editInfo } from '@/api/platform/gateway/grayrule'
  import save from './save'
  import info from './info'
  export default {
    mixins: [curdMixins],
    components: {
      save,
      info
    },
    data () {
      this.columns = [
        {
          title: '规则名',
          dataIndex: 'ruleName'
        },
        {
          title: '灰度规则',
          dataIndex: 'rule'
        },
        {
          title: '状态',
          dataIndex: 'state',
          scopedSlots: { customRender: 'state' }
        },
        {
          title: '创建时间',
          dataIndex: 'createTime'
        },
        {
          title: '更新时间',
          dataIndex: 'updateTime'
        },
        {
          title: this.$t('global.btn.operator'),
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ]
      return {
        // Multiple Choices
        multiOperator: [
          {
            key: 'delete',
            method: deleteByIds
          }
        ],
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
    },
    methods: {
      // filter params ,mixins call
      filterForm (values) {
        return values
      },
      // search list,mixins call
      list (param) {
        return getList(param)
      },
      // download list,mixins call
      download (param) {
        return downloadFile(param)
      },
      // delete,mixins call
      delete (record) {
        return deleteByIds({ ids: [record.id] })
      },
      // 快速修改状态
      edit (values) {
        return editInfo(values)
      }
    }
  }
</script>
<style scoped>
</style>
