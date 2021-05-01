<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--查询区-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道编号">
                <a-input v-decorator="['channelNo',{rules: []}]" placeholder="请输入渠道编号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道商户号">
                <a-input v-decorator="['channelMerchantNo',{rules: []}]" placeholder="请输入渠道商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="对账类型">
                <a-input v-decorator="['checkType',{rules: []}]" placeholder="请输入对账类型"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易状态">
                <a-input v-decorator="['transState',{rules: []}]" placeholder="请输入交易状态"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="对账状态">
                <a-input v-decorator="['checkState',{rules: []}]" placeholder="请输入对账状态"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="对账日期">
                <a-input v-decorator="['checkDate',{rules: []}]" placeholder="请输入对账日期"/>
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:settle:channelcheckbill:download')">下载</a-button>
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
  import { getList, downloadFile } from '@/api/payment/settle/channelcheckbill'
  export default {
    mixins: [curdMixins],
    components: {
    },
    data () {
      this.columns = [
        {
          title: '渠道编号',
          dataIndex: 'channelNo'
        },
        {
          title: '渠道商户号',
          dataIndex: 'channelMerchantNo'
        },
        {
          title: '送往渠道流水号',
          dataIndex: 'channelSendSn'
        },
        {
          title: '渠道返回流水号',
          dataIndex: 'channelRecvSn'
        },
        {
          title: '对账类型',
          dataIndex: 'checkType'
        },
        {
          title: '交易状态',
          dataIndex: 'transState'
        },
        {
          title: '对账状态',
          dataIndex: 'checkState'
        },
        {
          title: '对账日期',
          dataIndex: 'checkDate'
        },
        {
          title: '交易时间',
          dataIndex: 'transTime'
        },
        {
          title: '订单金额',
          dataIndex: 'orderAmount'
        },
        {
          title: '渠道手续费',
          dataIndex: 'channelFee'
        },
        {
          title: '银行帐号',
          dataIndex: 'bankAccountNo'
        },
        {
          title: '银行户名',
          dataIndex: 'bankAccountName'
        },
        {
          title: '原送往渠道流水号',
          dataIndex: 'orgChannelSendSn'
        },
        {
          title: '原订单金额',
          dataIndex: 'orgOrderAmount'
        },
        {
          title: '原渠道手续费',
          dataIndex: 'orgChannelFee'
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
