<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--Search Parameters-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item :label="$t('admin.userCode')">
                <a-input v-model="queryParam.userCode" :placeholder="$t('global.placeholder.input')+$t('admin.userCode')"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item :label="$t('admin.userName')">
                <a-input v-model="queryParam.userName" :placeholder="$t('global.placeholder.input')+$t('admin.userName')"/>
              </a-form-item>
            </a-col>
            <!--Hide Parameters-->
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item :label="$t('admin.state')">
                  <a-select show-search v-model="queryParam.state" :placeholder="$t('global.placeholder.select')" default-value="0">
                    <a-select-option value="">{{ $t('global.show.all') }}</a-select-option>
                    <template>
                      <a-select-option v-for="(value, key) in this.switch_state" :key="key">({{ key }}){{ value.dictValue }}</a-select-option>
                    </template>
                  </a-select>
                </a-form-item>
              </a-col>
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('platform:system:admin:download')">{{ $t('global.btn.download') }}</a-button>
        <a-button type="primary" icon="plus" @click="handleAdd" v-if="hasPerm('platform:system:admin:add')">{{ $t('global.btn.add') }}</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="delete" @click="handleMenuClick" v-if="hasPerm('platform:system:admin:delete')">
              <a-icon type="delete"/>
              {{ $t('global.btn.delete') }}
            </a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            {{ $t('global.btn.batch') }}
            <a-icon type="down"/>
          </a-button>
        </a-dropdown>
        <a-button type="primary" icon="download" @click="handleDownloadTemplate" v-if="hasPerm('platform:system:admin:upload')">{{ $t('global.btn.template') }}</a-button>
        <a-upload
          name="file"
          :customRequest="handleUpload"
        >
          <a-button type="primary" icon="upload" v-if="hasPerm('platform:system:admin:upload')">{{ $t('global.btn.upload') }}</a-button>
        </a-upload>
      </div>
      <!--Data Table-->
      <a-table
        :rowKey=" (record => record.id)"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="pagination"
        :loading="loading"
        @change="tableChange"
        size="small"
        bordered
      >
        <!--CustomRender-->
        <template v-slot:serial="text, record, index">
          {{ index + 1 }}
        </template>
        <template v-slot:state="text, record" v-if="hasPerm('platform:system:admin:edit')">
          <a>
            <a-popconfirm placement="top" :title="text===0? $t('global.confirm.close') : $t('global.confirm.open')" @confirm="() => handleSwitchState(text,record)">
              <a-badge :status="text | dictIconFilter(that,'switch_state')" :text="text | dictFilter(that,'switch_state')" />
            </a-popconfirm>
          </a>
        </template>
        <template v-slot:state="text" v-else>
          <a-badge :status="text | dictIconFilter(that,'switch_state')" :text="text | dictFilter(that,'switch_state')" />
        </template>
        <template v-slot:roles="roles">
          <template v-if="roles">
            <a-tag color="blue" v-for="role in roles" :key="role.id">{{ role.roleName }}</a-tag>
          </template>
        </template>
        <!--Row Operator-->
        <template v-slot:action="text, record">
          <span>
            <a href="javascript:" @click="handleInfo(record)" v-if="hasPerm('platform:system:admin:info')">{{ $t('global.btn.info') }}</a>
            <a-divider type="vertical"/>
            <a href="javascript:" @click="handleEdit(record)" v-if="hasPerm('platform:system:admin:edit')">{{ $t('global.btn.edit') }}</a>
            <a-divider type="vertical"/>
            <a-popconfirm :title="$t('global.confirm.delete')" @confirm="handleDelete(record)" v-if="hasPerm('platform:system:admin:delete')">
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
  import { getList, downloadFile, downloadTemplateFile, uploadFile, deleteByIds, editInfo } from '@/api/platform/system/admin'
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
          title: '#',
          scopedSlots: { customRender: 'serial' }
        },
        {
          title: this.$t('admin.userCode'),
          dataIndex: 'userCode'
        },
        {
          title: this.$t('admin.userName'),
          dataIndex: 'userName'
        },
        {
          title: this.$t('admin.mobile'),
          dataIndex: 'mobile'
        },
        {
          title: this.$t('admin.state'),
          dataIndex: 'state',
          scopedSlots: { customRender: 'state' }
        },
        {
          title: this.$t('admin.roles'),
          dataIndex: 'roles',
          scopedSlots: { customRender: 'roles' }
        },
        {
          title: this.$t('admin.lastLoginTime'),
          dataIndex: 'lastLoginTime',
          sorter: true
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
        switch_state: {}
      }
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
      download (param) {
        return downloadFile(param)
      },
      downloadTemplate () {
        return downloadTemplateFile()
      },
      upload (file) {
        return uploadFile(file)
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
