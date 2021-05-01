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
            <a-col :md="12" :sm="24">
              <a-form-item label="订单日期">
                <a-input type="hidden" v-decorator="['orderDate',{initialValue:initOrderDate+'',rules: [{ required: true, message: '订单日期不能为空！' }]}]"/>
                <a-range-picker
                  allowClear
                  :defaultValue="initOrderDate"
                  format="YYYYMMDD"
                  show-time
                  @change="onOrderDateChange"
                />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="支付日期">
                <a-input v-decorator="['payDate',{rules: []}]" placeholder="请输入支付日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="银行帐号">
                <a-input v-decorator="['bankAccountNo',{rules: []}]" placeholder="请输入银行帐号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="银行帐户类型">
                <a-select show-search placeholder="请选择" v-decorator="['bankAccountType',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.bank_account_type" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="行别">
                <a-select show-search placeholder="请选择" v-decorator="['bankType',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.bank_type" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="银行协议号">
                <a-input v-decorator="['bankProtocol',{rules: []}]" placeholder="请输入银行协议号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="客户手机">
                <a-input v-decorator="['mobile',{rules: []}]" placeholder="请输入客户手机"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="证件号码">
                <a-input v-decorator="['certificateNo',{rules: []}]" placeholder="请输入证件号码"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="支付类型">
                <a-select show-search placeholder="请选择" v-decorator="['payType',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.pay_type" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道编号">
                <a-select show-search placeholder="请选择" v-decorator="['channelNo',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.channel_no" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="送往渠道流水号">
                <a-input v-decorator="['channelSendSn',{rules: []}]" placeholder="请输入送往渠道流水号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道返回流水号">
                <a-input v-decorator="['channelRecvSn',{rules: []}]" placeholder="请输入渠道返回流水号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道返回码">
                <a-input v-decorator="['channelResultCode',{rules: []}]" placeholder="请输入渠道返回码"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="渠道返回描述">
                <a-input v-decorator="['channelResultNote',{rules: []}]" placeholder="请输入渠道返回描述"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易状态">
                <a-select show-search placeholder="请选择" v-decorator="['state',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.trans_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="对账状态">
                <a-select show-search placeholder="请选择" v-decorator="['checkState',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.check_state" :key="key">
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:trade:transpaybill:download')">下载</a-button>
      </div>
      <!--汇总-->
      <a-alert showIcon>
        <template slot="message">
          <span style="margin-right: 12px">已选择: <a style="font-weight: 600">{{ this.selectedRowKeys.length }}</a>记录</span>
          <template v-if="hasPerm('payment:trade:transpaybill:sum')">
            <span style="margin-right: 12px">总送往渠道金额: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.channelAmount, 100, 2) }}</a></span>
          </template>
        </template>
      </a-alert>
      <!--数据表格-->
      <a-table
        :rowKey=" (record => record.transId)"
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
        <template v-slot:bankAccountType="text">
          {{ dictFilter(text,'bank_account_type') }}
        </template>
        <template v-slot:bankType="text">
          {{ dictFilter(text,'bank_type') }}
        </template>
        <template v-slot:payType="text">
          {{ dictFilter(text,'pay_type') }}
        </template>
        <template v-slot:channelNo="text">
          {{ dictFilter(text,'channel_no') }}
        </template>
        <template v-slot:state="text">
          <a-badge :status="text | dictIconFilter(that,'trans_state')" :text="text | dictFilter(that,'trans_state')" />
        </template>
        <template v-slot:checkState="text">
          {{ dictFilter(text,'check_state') }}
        </template>
        <!--行操作-->
        <template v-slot:action="text, record">
          <span>
            <a href="javascript:" @click="handleInfo(record)" v-if="hasPerm('payment:trade:transpaybill:info')">详情</a>
            <a-divider type="vertical"/>
          </span>
        </template>
      </a-table>
      <!--自定义组件-->
      <info ref="info" @refresh="refresh" />
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, getSum, downloadFile } from '@/api/payment/trade/transpaybill'
  import info from './info'
  export default {
    mixins: [curdMixins],
    components: {
      info
    },
    data () {
      this.columns = [
        {
          title: '交易流水号',
          dataIndex: 'transId',
          fixed: 'left'
        },
        {
          title: '交易状态',
          dataIndex: 'state',
          scopedSlots: { customRender: 'state' },
          fixed: 'left'
        },
        {
          title: '订单日期',
          dataIndex: 'orderDate'
        },
        {
          title: '支付日期',
          dataIndex: 'payDate'
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
          dataIndex: 'bankAccountType',
          scopedSlots: { customRender: 'bankAccountType' }
        },
        {
          title: '行别',
          dataIndex: 'bankType',
          scopedSlots: { customRender: 'bankType' }
        },
        {
          title: '客户手机',
          dataIndex: 'mobile'
        },
        {
          title: '证件号码',
          dataIndex: 'certificateNo'
        },
        {
          title: '支付类型',
          dataIndex: 'payType',
          scopedSlots: { customRender: 'payType' }
        },
        {
          title: '返回码',
          dataIndex: 'payResultCode'
        },
        {
          title: '返回描述',
          dataIndex: 'payResultNote'
        },
        {
          title: '渠道编号',
          dataIndex: 'channelNo',
          scopedSlots: { customRender: 'channelNo' }
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
          title: '渠道商户号',
          dataIndex: 'channelMerchantNo'
        },
        {
          title: '渠道商户名',
          dataIndex: 'channelMerchantName'
        },
        {
          title: '送往渠道金额',
          dataIndex: 'channelAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
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
          title: '渠道返回码',
          dataIndex: 'channelResultCode'
        },
        {
          title: '渠道返回描述',
          dataIndex: 'channelResultNote'
        },
        {
          title: '对账状态',
          dataIndex: 'checkState',
          scopedSlots: { customRender: 'checkState' }
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
        initOrderDate: [this.moment().add(-1, 'd').format('YYYYMMDD'), this.moment().add(1, 'd').format('YYYYMMDD')],
        bank_account_type: {},
        bank_type: {},
        pay_type: {},
        channel_no: {},
        trans_state: {},
        check_state: {},
        initCreateTime: [this.moment().add(-1, 'd').format('YYYY-MM-DD'), this.moment().add(1, 'd').format('YYYY-MM-DD')],
        genTemp: null
      }
    },
    created () {
      // 获取字典数据
      this.getDictValue('bank_account_type').then(res => {
        this.bank_account_type = res.data
      })
      // 获取字典数据
      this.getDictValue('bank_type').then(res => {
        this.bank_type = res.data
      })
      // 获取字典数据
      this.getDictValue('pay_type').then(res => {
        this.pay_type = res.data
      })
      // 获取字典数据
      this.getDictValue('channel_no').then(res => {
        this.channel_no = res.data
      })
      // 获取字典数据
      this.getDictValue('trans_state').then(res => {
        this.trans_state = res.data
      })
      // 获取字典数据
      this.getDictValue('check_state').then(res => {
        this.check_state = res.data
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
      },
       // 日期格式转换
      onOrderDateChange (date, dateString) {
         this.form.setFieldsValue({ 'orderDate': dateString + '' })
      },
       // 日期格式转换
      onCreateTimeChange (date, dateString) {
         this.form.setFieldsValue({ 'createTime': dateString + '' })
      }
    }
  }
</script>
<style scoped>
</style>
