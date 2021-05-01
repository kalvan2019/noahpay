<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--查询区-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="商户号">
                <a-input v-decorator="['merchantNo',{rules: []}]" placeholder="请输入商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="商户名">
                <a-input v-decorator="['merchantName',{rules: []}]" placeholder="请输入商户名"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道商户号">
                <a-input v-decorator="['channelMerchantNo',{rules: []}]" placeholder="请输入渠道商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易日期">
                <a-input v-decorator="['tradeDate',{rules: []}]" placeholder="请输入交易日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道编号">
                <a-input v-decorator="['channelNo',{rules: []}]" placeholder="请输入渠道编号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="行别">
                <a-input v-decorator="['bankType',{rules: []}]" placeholder="请输入行别"/>
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:settle:reportchannel:download')">下载</a-button>
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
  import { getList, downloadFile } from '@/api/payment/settle/reportchannel'
  export default {
    mixins: [curdMixins],
    components: {
    },
    data () {
      this.columns = [
        {
          title: '商户号',
          dataIndex: 'merchantNo'
        },
        {
          title: '商户名',
          dataIndex: 'merchantName'
        },
        {
          title: '渠道商户号',
          dataIndex: 'channelMerchantNo'
        },
        {
          title: '交易日期',
          dataIndex: 'tradeDate'
        },
        {
          title: '渠道编号',
          dataIndex: 'channelNo'
        },
        {
          title: '行别',
          dataIndex: 'bankType'
        },
        {
          title: '渠道附加属性',
          dataIndex: 'channelExtNo'
        },
        {
          title: '成功金额',
          dataIndex: 'successAmount'
        },
        {
          title: '成功交易笔数',
          dataIndex: 'successNumber'
        },
        {
          title: '渠道成本总金额',
          dataIndex: 'channelCostAmount'
        },
        {
          title: '商户成本总金额',
          dataIndex: 'merchantCostAmount'
        },
        {
          title: '子商户成本总金额',
          dataIndex: 'subMerchantCostAmount'
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
        initUpdateTime: [this.moment().add(-1, 'd').format('YYYY-MM-DD'), this.moment().add(1, 'd').format('YYYY-MM-DD')],
        genTemp: null
      }
    },
    created () {
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
