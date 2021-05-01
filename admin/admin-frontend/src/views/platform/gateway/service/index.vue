<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--Search Parameters-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="服务">
                <a-input v-decorator="['service',{rules: []}]" :placeholder="$t('global.placeholder.input')+服务"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="服务分组">
                <a-select show-search :placeholder="$t('global.placeholder.select')" v-decorator="['groupName',{rules: []}]">
                  <a-select-option value="">{{ $t('global.show.all') }}</a-select-option>
                  <a-select-option v-for="(value, key) in this.group_name" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="服务状态">
                <a-select show-search :placeholder="$t('global.placeholder.select')" v-decorator="['state',{rules: []}]">
                  <a-select-option value="">{{ $t('global.show.all') }}</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="服务访问限制">
                <span :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-input v-decorator="['maxAccessBegin',{rules: []}]" :placeholder="$t('global.placeholder.input')+服务访问限制"/>
                </span>
                -
                <span :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-input v-decorator="['maxAccessEnd',{rules: []}]" :placeholder="$t('global.placeholder.input')+服务访问限制"/>
                </span>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="时间窗口">
                <span :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-input v-decorator="['timesBegin',{rules: []}]" :placeholder="$t('global.placeholder.input')+时间窗口"/>
                </span>
                -
                <span :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-input v-decorator="['timesEnd',{rules: []}]" :placeholder="$t('global.placeholder.input')+时间窗口"/>
                </span>
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('platform:gateway:service:download')">{{ $t('global.btn.download') }}</a-button>
        <a-button type="primary" icon="plus" @click="handleAdd" v-if="hasPerm('platform:gateway:service:add')">{{ $t('global.btn.add') }}</a-button>
        <a-button type="primary" @click="handleUpdateCache" v-if="hasPerm('platform:gateway:service:updateCache')">更新缓存</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="delete" @click="handleMenuClick" v-if="hasPerm('platform:gateway:service:delete')">
              <a-icon type="delete"/>
              {{ $t('global.btn.delete') }}
            </a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            {{ $t('global.btn.batch') }}
            <a-icon type="down"/>
          </a-button>
        </a-dropdown>
        <a-button type="primary" icon="download" @click="handleDownloadTemplate" v-if="hasPerm('platform:gateway:service:upload')">{{ $t('global.btn.template') }}</a-button>
        <a-upload
          name="file"
          :customRequest="handleUpload"
        >
          <a-button type="primary" icon="upload" v-if="hasPerm('platform:gateway:service:upload')">{{ $t('global.btn.upload') }}</a-button>
        </a-upload>
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
        <template v-slot:groupName="text">
          {{ dictFilter(text,'group_name') }}
        </template>
        <template v-slot:state="text, record" v-if="hasPerm('platform:gateway:service:edit')">
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
            <a href="javascript:" @click="handleInfo(record)" v-if="hasPerm('platform:gateway:service:info')">{{ $t('global.btn.info') }}</a>
            <a-divider type="vertical"/>
            <a-dropdown v-if="hasPerm('platform:gateway:service:edit') || hasPerm('platform:gateway:service:delete') || hasPerm('platform:gateway:service:updateCache')">
              <a class="ant-dropdown-link">
                {{ $t('global.btn.more') }} <a-icon type="down" />
              </a>
              <a-menu slot="overlay">
                <a-menu-item v-if="hasPerm('platform:gateway:service:edit')">
                  <a href="javascript:" @click="handleEdit(record)">{{ $t('global.btn.edit') }}</a>
                </a-menu-item>
                <a-menu-item v-if="hasPerm('platform:gateway:service:delete')">
                  <a-popconfirm :title="$t('global.confirm.delete')" @confirm="handleDelete(record)">
                    <a-icon slot="icon" type="question-circle-o" style="color: red"/>
                    <a href="javascript:">{{ $t('global.btn.delete') }}</a>
                  </a-popconfirm>
                </a-menu-item>
                <a-menu-item v-if="hasPerm('platform:gateway:service:updateCache')">
                  <a href="javascript:" @click="handleUpdateCache(record)">更新缓存</a>
                </a-menu-item>
              </a-menu>
            </a-dropdown>
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
  import { getList, downloadFile, downloadTemplateFile, uploadFile, deleteByIds, updateCache, editInfo } from '@/api/platform/gateway/service'
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
          title: '服务',
          dataIndex: 'service',
          ellipsis: true,
          width: 200,
          customRender: (value, record) => {
            return `${value}(${record.serviceName})`
          }
        },
        {
          title: '服务分组',
          dataIndex: 'groupName',
          scopedSlots: { customRender: 'groupName' }
        },
        {
          title: '资源名称',
          dataIndex: 'resource'
        },
        {
          title: '路由id',
          dataIndex: 'routeId'
        },
        {
          title: '服务状态',
          dataIndex: 'state',
          scopedSlots: { customRender: 'state' }
        },
        {
          title: '服务访问限制',
          dataIndex: 'maxAccess'
        },
        {
          title: '时间窗口(秒)',
          dataIndex: 'times'
        },
        {
          title: '优先级',
          dataIndex: 'priority'
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
        group_name: {},
        switch_state: {},
        genTemp: null
      }
    },
    created () {
      // 获取字典数据
      this.getDictValue('group_name').then(res => {
        this.group_name = res.data
      })
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
      // template download,mixins call
      downloadTemplate () {
        return downloadTemplateFile()
      },
      // upload,mixins call
      upload (file) {
        return uploadFile(file)
      },
      // delete,mixins call
      delete (record) {
        return deleteByIds({ ids: [record.id] })
      },
      // 快速修改状态
      edit (values) {
        return editInfo(values)
      },
      // 自定义方法
      handleUpdateCache (param) {
        updateCache(param).then(res => {
          if (res.state === 0) {
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      }
    }
  }
</script>
<style scoped>
</style>
