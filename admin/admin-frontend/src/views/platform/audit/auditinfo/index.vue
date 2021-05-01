<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--Search Parameters-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="数据类型">
                <a-select show-search :placeholder="$t('global.placeholder.select')" v-decorator="['dataType',{rules: []}]">
                  <a-select-option value="">{{ $t('global.show.all') }}</a-select-option>
                  <a-select-option v-for="(value, key) in this.log_group" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="发起人">
                <a-input v-decorator="['applicant',{rules: []}]" :placeholder="$t('global.placeholder.input')+发起人"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="审核类型">
                <a-select show-search :placeholder="$t('global.placeholder.select')" v-decorator="['auditType',{rules: []}]">
                  <a-select-option value="">{{ $t('global.show.all') }}</a-select-option>
                  <a-select-option v-for="(value, key) in this.log_type" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="审核人">
                <a-input v-decorator="['auditUser',{rules: []}]" :placeholder="$t('global.placeholder.input')+审核人"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="审核状态">
                <a-select show-search :placeholder="$t('global.placeholder.select')" v-decorator="['auditState',{rules: []}]">
                  <a-select-option value="">{{ $t('global.show.all') }}</a-select-option>
                  <a-select-option v-for="(value, key) in this.audit_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="创建时间">
                <a-input type="hidden" v-decorator="['createTime',{initialValue:initCreateTime+'',rules: []}]"/>
                <a-range-picker
                  allowClear
                  :defaultValue="initCreateTime"
                  format="YYYY-MM-DD HH:mm:ss"
                  show-time
                  @change="onCreateTimeChange"
                />
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('platform:audit:auditinfo:download')">{{ $t('global.btn.download') }}</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="auditingPass" @click="handleMenuClick" v-if="hasPerm('platform:audit:auditinfo:auditing')">
              <a-icon type="check"/>
              {{ $t('global.btn.pass') }}
            </a-menu-item>
            <a-menu-item key="auditingRefuse" @click="handleMenuClick" v-if="hasPerm('platform:audit:auditinfo:auditing')">
              <a-icon type="close"/>
              {{ $t('global.btn.refuse') }}
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
        <template v-slot:dataType="text">
          {{ dictFilter(text,'log_group') }}
        </template>
        <template v-slot:auditType="text">
          {{ dictFilter(text,'log_type') }}
        </template>
        <template v-slot:auditState="text">
          <a-badge :status="text | dictIconFilter(that,'audit_state')" :text="text | dictFilter(that,'audit_state')" />
        </template>
        <!--Row Operator-->
        <template v-slot:action="text, record">
          <span>
            <a href="javascript:" @click="handleInfo(record)" v-if="hasPerm('platform:audit:auditinfo:info')">{{ $t('global.btn.info') }}</a>
            <a-divider type="vertical"/>
            <template v-if="record.auditState === 2">
              <a-popconfirm :title="$t('global.confirm.pass')" @confirm="handleAuditing(record,true)" v-if="hasPerm('platform:audit:auditinfo:auditing')">
                <a-icon slot="icon" type="question-circle-o" style="color: red"/>
                <a href="javascript:">{{ $t('global.btn.pass') }}</a>
              </a-popconfirm>
              <a-divider type="vertical"/>
              <a-popconfirm :title="$t('global.confirm.refuse')" @confirm="handleAuditing(record,false)" v-if="hasPerm('platform:audit:auditinfo:auditing')">
                <a-icon slot="icon" type="question-circle-o" style="color: red"/>
                <a href="javascript:">{{ $t('global.btn.refuse') }}</a>
              </a-popconfirm>
              <a-divider type="vertical"/>
            </template>
          </span>
        </template>
      </a-table>
      <!--Custom Component-->
      <info ref="info" @refresh="refresh" />
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, downloadFile, auditingByIds } from '@/api/platform/audit/auditinfo'
  import info from './info'
  export default {
    mixins: [curdMixins],
    components: {
      info
    },
    data () {
      this.columns = [
        {
          title: '数据类型',
          dataIndex: 'dataType',
          scopedSlots: { customRender: 'dataType' }
        },
        {
          title: '审核类型',
          dataIndex: 'auditType',
          scopedSlots: { customRender: 'auditType' }
        },
        {
          title: '变更后数据',
          dataIndex: 'dataNew',
          ellipsis: true
        },
        {
          title: '发起人',
          dataIndex: 'applicant'
        },
        {
          title: '审核人',
          dataIndex: 'auditUser'
        },
        {
          title: '审核状态',
          dataIndex: 'auditState',
          scopedSlots: { customRender: 'auditState' }
        },
        {
          title: '创建时间',
          dataIndex: 'createTime'
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
            key: 'auditingPass',
            method: auditingByIds,
            ext: true
          },
          {
            key: 'auditingRefuse',
            method: auditingByIds,
            ext: false
          }
        ],
        log_group: {},
        log_type: {},
        audit_state: {},
        initCreateTime: [this.moment().add(-1, 'd').format('YYYY-MM-DD'), this.moment().add(1, 'd').format('YYYY-MM-DD')],
        genTemp: null
      }
    },
    created () {
      // 获取字典数据
      this.getDictValue('log_group').then(res => {
        this.log_group = res.data
      })
      // 获取字典数据
      this.getDictValue('log_type').then(res => {
        this.log_type = res.data
      })
      // 获取字典数据
      this.getDictValue('audit_state').then(res => {
        this.audit_state = res.data
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
      // 审核,mixins调用
      auditing (record, pass) {
        return auditingByIds({ ids: [record.id] }, pass)
      },
      // date format
      onCreateTimeChange (date, dateString) {
         this.form.setFieldsValue({ 'createTime': dateString + '' })
      }
    }
  }
</script>
<style scoped>
</style>
