<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--查询区-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="合作方编号">
                <a-input v-decorator="['partnerNo',{rules: []}]" placeholder="请输入合作方编号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="商户编号">
                <a-input v-decorator="['merchantNo',{rules: []}]" placeholder="请输入商户编号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="银行帐号">
                <a-input v-decorator="['bankAccountNo',{rules: []}]" placeholder="请输入银行帐号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="会计开始日期">
                <a-input v-decorator="['accountBeginDate',{rules: []}]" placeholder="请输入会计开始日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="会计结束日期">
                <a-input v-decorator="['accountEndDate',{rules: []}]" placeholder="请输入会计结束日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="分润状态">
                <a-input v-decorator="['state',{rules: []}]" placeholder="请输入分润状态"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="订单日期">
                <a-input v-decorator="['orderId',{rules: []}]" placeholder="请输入订单日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="提现日期">
                <a-input v-decorator="['settleDate',{rules: []}]" placeholder="请输入提现日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="修改时间">
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:settle:partnerprofit:download')">下载</a-button>
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
        :scroll="{ x: 2800}"
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
  import { getList, downloadFile } from '@/api/payment/settle/partnerprofit'
  export default {
    mixins: [curdMixins],
    components: {
    },
    data () {
      this.columns = [
        {
          title: '合作方编号',
          dataIndex: 'partnerNo'
        },
        {
          title: '商户编号',
          dataIndex: 'merchantNo'
        },
        {
          title: '银行帐号',
          dataIndex: 'bankAccountNo'
        },
        {
          title: '会计开始日期',
          dataIndex: 'accountBeginDate'
        },
        {
          title: '会计结束日期',
          dataIndex: 'accountEndDate'
        },
        {
          title: '会计入账日期',
          dataIndex: 'accountingDate'
        },
        {
          title: '消费笔数',
          dataIndex: 'transTotalCount'
        },
        {
          title: '消费金额',
          dataIndex: 'transTotalAmount'
        },
        {
          title: '消费收益',
          dataIndex: 'transProfitAmount'
        },
        {
          title: '结算笔数',
          dataIndex: 'dfTotalCount'
        },
        {
          title: '结算金额',
          dataIndex: 'dfTotalAmount'
        },
        {
          title: '结算收益',
          dataIndex: 'dfProfitAmount'
        },
        {
          title: '退款笔数',
          dataIndex: 'rfTotalCount'
        },
        {
          title: '退款金额',
          dataIndex: 'rfTotalAmount'
        },
        {
          title: '退款收益',
          dataIndex: 'rfProfitAmount'
        },
        {
          title: '退票笔数',
          dataIndex: 'returnTicketCount'
        },
        {
          title: '退票金额',
          dataIndex: 'returnTicketAmount'
        },
        {
          title: '退票收益',
          dataIndex: 'returnTicketProfitAmount'
        },
        {
          title: '合作方收益',
          dataIndex: 'totalProfitAmount'
        },
        {
          title: '入账流水号',
          dataIndex: 'accountInId'
        },
        {
          title: '分润状态',
          dataIndex: 'state'
        },
        {
          title: '订单日期',
          dataIndex: 'orderId'
        },
        {
          title: '提现日期',
          dataIndex: 'settleDate'
        },
        {
          title: '创建时间',
          dataIndex: 'createTime'
        },
        {
          title: '修改时间',
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
