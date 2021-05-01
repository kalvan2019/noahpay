<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--Search Parameters-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="字典类型">
                <a-select show-search v-model="queryParam.dictType" :placeholder="$t('global.placeholder.select')" default-value="0">
                  <a-select-option value="">{{ $t('global.show.all') }}</a-select-option>
                  <template>
                    <a-select-option color="blue" v-for="dict in this.allDict" :key="dict.dictType">({{ dict.dictType }}){{ dict.dictName }}</a-select-option>
                  </template>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="数据KEY">
                <a-input v-model="queryParam.dictKey" :placeholder="$t('global.placeholder.input')+数据KEY"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="显示内容">
                <a-input v-model="queryParam.dictValue" :placeholder="$t('global.placeholder.input')+显示内容"/>
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('platform:develop:dict:download')">{{ $t('global.btn.download') }}</a-button>
        <a-button type="primary" icon="plus" @click="handleAdd" v-if="hasPerm('platform:develop:dict:add')">{{ $t('global.btn.add') }}</a-button>
        <a-button type="primary" @click="handleUpdateCache" v-if="hasPerm('platform:develop:dict:updateCache')">更新缓存</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="delete" @click="handleMenuClick" v-if="hasPerm('platform:develop:dict:delete')">
              <a-icon type="delete"/>
              {{ $t('global.btn.delete') }}
            </a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            {{ $t('global.btn.batch') }}
            <a-icon type="down"/>
          </a-button>
        </a-dropdown>
        <a-button type="primary" icon="download" @click="handleDownloadTemplate" v-if="hasPerm('platform:develop:dict:upload')">{{ $t('global.btn.template') }}</a-button>
        <a-upload
          name="file"
          :customRequest="handleUpload"
        >
          <a-button type="primary" icon="upload" v-if="hasPerm('platform:develop:dict:upload')">{{ $t('global.btn.upload') }}</a-button>
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
        <template v-slot:dictIcon="text">
          <a-badge :status="text===''? 'default':text" :text="dictFilter(text,'dict_icon')" />
        </template>
        <template v-slot:selectEnable="text">
          <a-badge :status="text | dictIconFilter(that,'switch_state')" :text="text | dictFilter(that,'switch_state')" />
        </template>
        <!--Row Operator-->
        <template v-slot:action="text, record">
          <span>
            <a href="javascript:" @click="handleEdit(record)" v-if="hasPerm('platform:develop:dict:edit')">{{ $t('global.btn.edit') }}</a>
            <a-divider type="vertical"/>
            <a-popconfirm :title="$t('global.confirm.delete')" @confirm="handleDelete(record)" v-if="hasPerm('platform:develop:dict:delete')">
              <a-icon slot="icon" type="question-circle-o" style="color: red"/>
              <a href="javascript:">{{ $t('global.btn.delete') }}</a>
            </a-popconfirm>
            <a-divider type="vertical"/>
          </span>
        </template>
      </a-table>
      <!--Custom Component-->
      <save ref="save" @refresh="refresh" />
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { allType, getList, downloadFile, downloadTemplateFile, uploadFile, deleteByIds, updateCache } from '@/api/platform/develop/dict'
  import save from './save'
  export default {
    mixins: [curdMixins],
    components: {
      save
    },
    data () {
      this.columns = [
        {
          title: '字典类型',
          dataIndex: 'dictType'
        },
        {
          title: '字典类型名',
          dataIndex: 'dictName'
        },
        {
          title: '数据KEY',
          dataIndex: 'dictKey'
        },
        {
          title: '显示内容',
          dataIndex: 'dictValue'
        },
        {
          title: '徽标状态',
          dataIndex: 'dictIcon',
          scopedSlots: { customRender: 'dictIcon' }
        },
        {
          title: 'select启用',
          dataIndex: 'selectEnable',
          scopedSlots: { customRender: 'selectEnable' }
        },
        {
          title: '排序',
          dataIndex: 'priority'
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
        dict_icon: {},
        switch_state: {},
        allDict: {},
        genTemp: null
      }
    },
    created () {
      this.getDictValue('dict_icon').then(res => {
        this.dict_icon = res.data
      })
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      allType().then(res => {
        this.allDict = res.data
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
