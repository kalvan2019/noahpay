<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--查询区-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="交易流水">
                <a-input v-decorator="['transId',{rules: []}]" placeholder="请输入交易流水"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易日期">
                <a-input v-decorator="['transDate',{rules: []}]" placeholder="请输入交易日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易类型">
                <a-input v-decorator="['transType',{rules: []}]" placeholder="请输入交易类型"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="订单号">
                <a-input v-decorator="['orderId',{rules: []}]" placeholder="请输入订单号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="订单日期">
                <a-input v-decorator="['orderDate',{rules: []}]" placeholder="请输入订单日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="商户号">
                <a-input v-decorator="['merchantNo',{rules: []}]" placeholder="请输入商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道编号">
                <a-input v-decorator="['channelNo',{rules: []}]" placeholder="请输入渠道编号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="退款交易状态">
                <a-select show-search placeholder="请选择" v-decorator="['state',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.trans_state" :key="key">
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
        :scroll="{ x: 3500}"
        @change="tableChange"
        size="small"
        bordered
      >
        <!--字段扩展显示customRender-->
        <template v-slot:state="text">
          {{ dictFilter(text,'trans_state') }}
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
  import { getList } from '@/api/payment/trade/refundbill'
  export default {
    mixins: [curdMixins],
    components: {
    },
    data () {
      this.columns = [
        {
          title: '交易流水',
          dataIndex: 'transId'
        },
        {
          title: '交易日期',
          dataIndex: 'transDate'
        },
        {
          title: '交易类型',
          dataIndex: 'transType'
        },
        {
          title: '订单号',
          dataIndex: 'orderId'
        },
        {
          title: '订单日期',
          dataIndex: 'orderDate'
        },
        {
          title: '退款订单说明',
          dataIndex: 'orderNote'
        },
        {
          title: '退款金额',
          dataIndex: 'orderAmount'
        },
        {
          title: '域名',
          dataIndex: 'orderDomain'
        },
        {
          title: 'IP',
          dataIndex: 'orderIp'
        },
        {
          title: '终端IP',
          dataIndex: 'orderTerminalIp'
        },
        {
          title: '设备指纹',
          dataIndex: 'orderTerminalDevice'
        },
        {
          title: '后台通知地址',
          dataIndex: 'notifyBgUrl'
        },
        {
          title: '商户号',
          dataIndex: 'merchantNo'
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
          title: '银行帐户类型',
          dataIndex: 'bankAccountType'
        },
        {
          title: '行别',
          dataIndex: 'bankType'
        },
        {
          title: '行名',
          dataIndex: 'bankName'
        },
        {
          title: '支付类型',
          dataIndex: 'payType'
        },
        {
          title: '支付网关返回码',
          dataIndex: 'payResultCode'
        },
        {
          title: '支付网关备注',
          dataIndex: 'payResultNote'
        },
        {
          title: '渠道编号',
          dataIndex: 'channelNo'
        },
        {
          title: '送往渠道流水号',
          dataIndex: 'channelSendSn'
        },
        {
          title: '送往渠道时间',
          dataIndex: 'channelSendTime'
        },
        {
          title: '送往渠道扩展数据',
          dataIndex: 'channelSendExt'
        },
        {
          title: '渠道商户号',
          dataIndex: 'channelMerchantNo'
        },
        {
          title: '渠道商户名',
          dataIndex: 'channelMerchantName'
        },
        {
          title: '渠道子商户号',
          dataIndex: 'channelSubMerchantNo'
        },
        {
          title: '送往渠道金额',
          dataIndex: 'channelAmount'
        },
        {
          title: '渠道会计日期',
          dataIndex: 'channelAccountDate'
        },
        {
          title: '渠道返回流水号',
          dataIndex: 'channelRecvSn'
        },
        {
          title: '渠道返回时间',
          dataIndex: 'channelRecvTime'
        },
        {
          title: '渠道返回扩展数据',
          dataIndex: 'channelRecvExt'
        },
        {
          title: '渠道返回码',
          dataIndex: 'channelReturnCode'
        },
        {
          title: '渠道返回备注',
          dataIndex: 'channelResultNote'
        },
        {
          title: '退款交易状态',
          dataIndex: 'state',
          scopedSlots: { customRender: 'state' }
        },
        {
          title: '通知状态',
          dataIndex: 'notifyState'
        },
        {
          title: '对账状态',
          dataIndex: 'checkState'
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
        trans_state: {},
        genTemp: null
      }
    },
    created () {
      // 获取字典数据
      this.getDictValue('trans_state').then(res => {
        this.trans_state = res.data
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
      }
    }
  }
</script>
<style scoped>
</style>
