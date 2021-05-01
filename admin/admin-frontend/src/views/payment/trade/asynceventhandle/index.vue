<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--查询区-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="交易流水号">
                <a-input v-decorator="['transId',{rules: []}]" placeholder="请输入交易流水号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易流水号">
                <a-input v-decorator="['payId',{rules: []}]" placeholder="请输入交易流水号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易日期">
                <a-input v-decorator="['transDate',{rules: []}]" placeholder="请输入交易日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易类型">
                <a-select show-search placeholder="请选择" v-decorator="['transType',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.trans_type" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="账户号">
                <a-input v-decorator="['accountNo',{rules: []}]" placeholder="请输入账户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="执行次数">
                <span :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-input v-decorator="['dealTimesBegin',{rules: []}]" placeholder="请输入执行次数"/>
                </span>
                -
                <span :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-input v-decorator="['dealTimesEnd',{rules: []}]" placeholder="请输入执行次数"/>
                </span>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="状态">
                <a-select show-search placeholder="请选择" v-decorator="['state',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.event_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="更新时间">
                <a-input type="hidden" v-decorator="['updateTime',{initialValue:initUpdateTime+'',rules: []}]"/>
                <a-range-picker
                  allowClear
                  :defaultValue="initUpdateTime"
                  format="YYYY-MM-DD HH:mm:ss"
                  show-time
                  @change="onUpdateTimeChange"
                />
              </a-form-item>
            </a-col>
            <!--隐藏查询条件-->
            <template v-if="advanced">
            </template>
            <a-col :md="!advanced && 8 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="handleSearch">查询</a-button>
                <a-button style="margin-left: 8px" @click="handleReset">重置</a-button>
                <a @click="() => this.advanced = !this.advanced" style="margin-left: 8px">
                  {{ advanced ? '收起' : '展开' }}
                  <a-icon :type="advanced ? 'up' : 'down'"/>
                </a>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <!--全局操作-->
      <div class="table-operator">
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:trade:asynceventhandle:download')">下载</a-button>
      </div>
      <!--汇总-->
      <!--数据表格-->
      <a-table
        :rowKey=" (record => record.id)"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="pagination"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        :loading="loading"
        :scroll="{ x: 1500}"
        @change="tableChange"
        size="small"
        bordered
      >
        <!--字段扩展显示customRender-->
        <template v-slot:transType="text">
          {{ dictFilter(text,'trans_type') }}
        </template>
        <template v-slot:eventType="text">
          {{ dictFilter(text,'event_type') }}
        </template>
        <template v-slot:strategyType="text">
          {{ dictFilter(text,'strategy_type') }}
        </template>
        <template v-slot:state="text">
          {{ dictFilter(text,'event_state') }}
        </template>
        <!--行操作-->
        <template v-slot:action="text, record">
          <span>
          </span>
        </template>
      </a-table>
      <!--自定义组件-->
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, downloadFile } from '@/api/payment/trade/asynceventhandle'
  export default {
    mixins: [curdMixins],
    components: {
    },
    data () {
      this.columns = [
        {
          title: '交易流水号',
          dataIndex: 'transId'
        },
        {
          title: '交易流水号',
          dataIndex: 'payId'
        },
        {
          title: '交易日期',
          dataIndex: 'transDate'
        },
        {
          title: '交易类型',
          dataIndex: 'transType',
          scopedSlots: { customRender: 'transType' }
        },
        {
          title: '账户号',
          dataIndex: 'accountNo'
        },
        {
          title: '事件类型',
          dataIndex: 'eventType',
          scopedSlots: { customRender: 'eventType' }
        },
        {
          title: '事件内容json',
          dataIndex: 'eventContent'
        },
        {
          title: '执行策略',
          dataIndex: 'strategyType',
          scopedSlots: { customRender: 'strategyType' }
        },
        {
          title: '支付网关返回码',
          dataIndex: 'resultCode'
        },
        {
          title: '支付网关备注',
          dataIndex: 'resultNote'
        },
        {
          title: '执行次数',
          dataIndex: 'dealTimes'
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
          title: '操作',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ]
      return {
        trans_type: {},
        event_type: {},
        strategy_type: {},
        event_state: {},
        initUpdateTime: [this.moment().add(-1, 'd').format('YYYY-MM-DD'), this.moment().add(1, 'd').format('YYYY-MM-DD')],
        genTemp: null
      }
    },
    created () {
      // 获取字典数据
      this.getDictValue('trans_type').then(res => {
        this.trans_type = res.data
      })
      // 获取字典数据
      this.getDictValue('event_type').then(res => {
        this.event_type = res.data
      })
      // 获取字典数据
      this.getDictValue('strategy_type').then(res => {
        this.strategy_type = res.data
      })
      // 获取字典数据
      this.getDictValue('event_state').then(res => {
        this.event_state = res.data
      })
    },
    methods: {
      // 表单参数过滤,mixins调用
      filterForm (values) {
        return values
      },
      // 列表查询,mixins调用
      list (param) {
        return getList(param)
      },
      // 下载list,mixins调用
      download (param) {
        return downloadFile(param)
      },
       // 日期格式转换
      onUpdateTimeChange (date, dateString) {
         this.form.setFieldsValue({ 'updateTime': dateString + '' })
      }
    }
  }
</script>
<style scoped>
</style>
