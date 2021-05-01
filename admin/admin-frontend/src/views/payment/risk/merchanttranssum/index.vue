<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--查询区-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="12" :sm="24">
              <a-form-item label="交易日期">
                <span :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-input v-decorator="['transDateBegin',{rules: []}]" placeholder="请输入交易日期"/>
                </span>
                -
                <span :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-input v-decorator="['transDateEnd',{rules: []}]" placeholder="请输入交易日期"/>
                </span>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="商户号">
                <a-input v-decorator="['merchantNo',{rules: []}]" placeholder="请输入商户号"/>
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:risk:merchanttranssum:download')">下载</a-button>
      </div>
      <!--汇总-->
      <a-alert showIcon>
        <template slot="message">
          <span style="margin-right: 12px">已选择: <a style="font-weight: 600">{{ this.selectedRowKeys.length }}</a>记录</span>
          <template v-if="hasPerm('payment:risk:merchanttranssum:sum')">
            <span style="margin-right: 12px">总日使用笔数: <a style="font-weight: 600">{{ this.dataSum.dayUseNumber }}</a></span>
            <span style="margin-right: 12px">总日使用金额: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.dayUseAmount, 100, 2) }}</a></span>
            <span style="margin-right: 12px">总月使用笔数: <a style="font-weight: 600">{{ this.dataSum.monthUseNumber }}</a></span>
            <span style="margin-right: 12px">总月使用金额: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.monthUseAmount, 100, 2) }}</a></span>
          </template>
        </template>
      </a-alert>
      <!--数据表格-->
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
        <!--字段扩展显示customRender-->
        <template v-slot:transType="text">
          {{ dictFilter(text,'trans_type') }}
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
  import { getList, getSum, downloadFile } from '@/api/payment/risk/merchanttranssum'
  export default {
    mixins: [curdMixins],
    components: {
    },
    data () {
      this.columns = [
        {
          title: '交易日期',
          dataIndex: 'transDate'
        },
        {
          title: '商户号',
          dataIndex: 'merchantNo'
        },
        {
          title: '交易类型',
          dataIndex: 'transType',
          scopedSlots: { customRender: 'transType' }
        },
        {
          title: '日使用笔数',
          dataIndex: 'dayUseNumber'
        },
        {
          title: '日使用金额',
          dataIndex: 'dayUseAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '月使用笔数',
          dataIndex: 'monthUseNumber'
        },
        {
          title: '月使用金额',
          dataIndex: 'monthUseAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
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
        genTemp: null
      }
    },
    created () {
      // 获取字典数据
      this.getDictValue('trans_type').then(res => {
        this.trans_type = res.data
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
      // 汇总查询,mixins调用
      sum (param) {
          return getSum(param)
      },
      // 下载list,mixins调用
      download (param) {
        return downloadFile(param)
      }
    }
  }
</script>
<style scoped>
</style>
