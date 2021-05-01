<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--Search Parameters-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="12" :sm="24">
              <a-form-item label="报告日期">
                <a-input type="hidden" v-decorator="['reportDate',{initialValue:initReportDate+'',rules: []}]"/>
                <a-range-picker
                  allowClear
                  :defaultValue="initReportDate"
                  format="YYYYMMDD"
                  show-time
                  @change="onReportDateChange"
                />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="appId">
                <a-input v-decorator="['appId',{rules: []}]" :placeholder="$t('global.placeholder.input')+appId"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="服务">
                <a-input v-decorator="['service',{rules: []}]" :placeholder="$t('global.placeholder.input')+服务"/>
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('platform:gateway:metric:download')">{{ $t('global.btn.download') }}</a-button>
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
        <!--Row Operator-->
        <template v-slot:action="text, record">
          <span>
          </span>
        </template>
      </a-table>
      <!--Custom Component-->
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, downloadFile } from '@/api/platform/gateway/metric'
  export default {
    mixins: [curdMixins],
    components: {
    },
    data () {
      this.columns = [
        {
          title: '报告日期',
          dataIndex: 'reportDate'
        },
        {
          title: 'appId',
          dataIndex: 'appId'
        },
        {
          title: '服务',
          dataIndex: 'service'
        },
        {
          title: '通过qps',
          dataIndex: 'passQps'
        },
        {
          title: '限流qps',
          dataIndex: 'blockQps'
        },
        {
          title: '成功qps',
          dataIndex: 'successQps'
        },
        {
          title: '异常次数',
          dataIndex: 'exceptionQps'
        },
        {
          title: '成功总耗时',
          dataIndex: 'rt'
        },
        {
          title: '创建时间',
          dataIndex: 'createTime'
        },
        {
          title: '更新时间',
          dataIndex: 'updateTime'
        }
      ]
      return {
        initReportDate: [this.moment().add(-1, 'd').format('YYYYMMDD'), this.moment().add(1, 'd').format('YYYYMMDD')],
        genTemp: null
      }
    },
    created () {
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
       // date format
      onReportDateChange (date, dateString) {
         this.form.setFieldsValue({ 'reportDate': dateString + '' })
      }
    }
  }
</script>
<style scoped>
</style>
