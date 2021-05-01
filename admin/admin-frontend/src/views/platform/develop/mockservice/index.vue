<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--Search Parameters-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="系统">
                <a-input v-decorator="['serviceId',{rules: []}]" :placeholder="$t('global.placeholder.input')+系统"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="服务地址">
                <a-input v-decorator="['service',{rules: []}]" :placeholder="$t('global.placeholder.input')+服务地址"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="mock地址">
                <a-input v-decorator="['mockUrl',{rules: []}]" :placeholder="$t('global.placeholder.input')+mock地址"/>
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('platform:develop:mockservice:download')">{{ $t('global.btn.download') }}</a-button>
        <a-button type="primary" icon="plus" @click="handleAdd" v-if="hasPerm('platform:develop:mockservice:add')">{{ $t('global.btn.add') }}</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="delete" @click="handleMenuClick" v-if="hasPerm('platform:develop:mockservice:delete')">
              <a-icon type="delete"/>
              {{ $t('global.btn.delete') }}
            </a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            {{ $t('global.btn.batch') }}
            <a-icon type="down"/>
          </a-button>
        </a-dropdown>
        <a-button type="primary" icon="download" @click="handleDownloadTemplate" v-if="hasPerm('platform:develop:mockservice:upload')">{{ $t('global.btn.template') }}</a-button>
        <a-upload
          name="file"
          :customRequest="handleUpload"
        >
          <a-button type="primary" icon="upload" v-if="hasPerm('platform:develop:mockservice:upload')">{{ $t('global.btn.upload') }}</a-button>
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
        <template v-slot:state="text">
          <a-badge :status="text | dictIconFilter(that,'switch_state')" :text="text | dictFilter(that,'switch_state')" />
        </template>
        <!--Row Operator-->
        <template v-slot:action="text, record">
          <span>
            <a href="javascript:" @click="handleInfo(record)" v-if="hasPerm('platform:develop:mockservice:info')">{{ $t('global.btn.info') }}</a>
            <a-divider type="vertical"/>
            <a href="javascript:" @click="handleEdit(record)" v-if="hasPerm('platform:develop:mockservice:edit')">{{ $t('global.btn.edit') }}</a>
            <a-divider type="vertical"/>
            <a-popconfirm :title="$t('global.confirm.delete')" @confirm="handleDelete(record)" v-if="hasPerm('platform:develop:mockservice:delete')">
              <a-icon slot="icon" type="question-circle-o" style="color: red"/>
              <a href="javascript:">{{ $t('global.btn.delete') }}</a>
            </a-popconfirm>
            <a-divider type="vertical"/>
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
  import { getList, downloadFile, downloadTemplateFile, uploadFile, deleteByIds } from '@/api/platform/develop/mockservice'
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
          title: '自增id',
          dataIndex: 'id'
        },
        {
          title: '系统',
          dataIndex: 'serviceId'
        },
        {
          title: '服务地址',
          dataIndex: 'service'
        },
        {
          title: 'mock地址',
          dataIndex: 'mockUrl',
          ellipsis: true
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
      }
    }
  }
</script>
<style scoped>
</style>
