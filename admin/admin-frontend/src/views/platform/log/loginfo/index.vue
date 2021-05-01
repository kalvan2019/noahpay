<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--Search Parameters-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="操作用户">
                <a-input v-model="queryParam.logUser" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="创建时间">
                <a-input type="hidden" v-decorator="['logTime',{initialValue:initTimeRange+''}]"/>
                <a-range-picker
                  allowClear
                  :defaultValue="initTimeRange"
                  format="YYYY-MM-DD HH:mm:ss"
                  show-time
                  @change="onRangePickerChange"
                />
              </a-form-item>
            </a-col>
            <!--Hide Parameters-->
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="日志组">
                  <a-select show-search v-model="queryParam.logGroup" :placeholder="$t('global.placeholder.select')" default-value="0">
                    <a-select-option value="">{{ $t('global.show.all') }}</a-select-option>
                    <template>
                      <a-select-option v-for="(value, key) in this.log_group" :key="key">({{ key }}){{ value.dictValue }}</a-select-option>
                    </template>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="日志类型">
                  <a-select show-search v-model="queryParam.logType" :placeholder="$t('global.placeholder.select')" default-value="0">
                    <a-select-option value="">{{ $t('global.show.all') }}</a-select-option>
                    <template>
                      <a-select-option v-for="(value, key) in this.log_type" :key="key">({{ key }}){{ value.dictValue }}</a-select-option>
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('platform:log:loginfo:download')">{{ $t('global.btn.download') }}</a-button>
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
        <template v-slot:logGroup="text">
          {{ dictFilter(text,'log_group') }}
        </template>
        <template v-slot:logType="text">
          {{ dictFilter(text,'log_type') }}
        </template>
      </a-table>
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, downloadFile } from '@/api/platform/log/log'
  export default {
    mixins: [curdMixins],
    components: {
    },
    data () {
        this.columns = [
            {
                title: '操作用户',
                dataIndex: 'logUser'
            },
            {
                title: '日志组',
                dataIndex: 'logGroup',
                scopedSlots: { customRender: 'logGroup' }
            },
            {
                title: '操作类型',
                dataIndex: 'logType',
                scopedSlots: { customRender: 'logType' }
            },
            {
                title: '操作路径',
                dataIndex: 'logAction'
            },
            {
                title: '操作说明',
                dataIndex: 'logRemark',
                ellipsis: true
            },
            {
                title: 'ip地址',
                dataIndex: 'logIp'
            },
            {
                title: '操作时间',
                dataIndex: 'logTime',
                sorter: true
            },
            {
                title: '更新前信息',
                dataIndex: 'logOldDetail'
            },
            {
                title: '更新后信息',
                dataIndex: 'logDetail'
            }
        ]
      return {
          log_group: {},
          log_type: {},
          initTimeRange: [this.moment().add(-1, 'd').format('YYYY-MM-DD'), this.moment().add(1, 'd').format('YYYY-MM-DD')]
      }
    },
    created () {
        this.getDictValue('log_group').then(res => {
            this.log_group = res.data
        })
        this.getDictValue('log_type').then(res => {
            this.log_type = res.data
        })
    },
    methods: {
      list (param) {
        return getList(param)
      },
      download (param) {
          return downloadFile(param)
      },
      onRangePickerChange (date, dateString) {
        // 字符串格式传到后台
        this.form.setFieldsValue({ 'logTime': dateString + '' })
      }
    }
  }
</script>
<style scoped>
</style>
