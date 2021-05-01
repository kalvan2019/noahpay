<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--查询区-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="记账id">
                <a-input v-decorator="['accountTransId',{rules: []}]" placeholder="请输入记账id"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="账户号">
                <a-input v-decorator="['accountNo',{rules: []}]" placeholder="请输入账户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="客户号">
                <a-input v-decorator="['custNo',{rules: []}]" placeholder="请输入客户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="商户号">
                <a-input v-decorator="['merchantNo',{rules: []}]" placeholder="请输入商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="会计日期">
                <a-input v-decorator="['accountDate',{rules: []}]" placeholder="请输入会计日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="冲正标识">
                <a-select show-search placeholder="请选择" v-decorator="['undoFlag',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="订单号">
                <a-input v-decorator="['refOrderId',{rules: []}]" placeholder="请输入订单号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易流水号">
                <a-input v-decorator="['refTransId',{rules: []}]" placeholder="请输入交易流水号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易类型">
                <a-select show-search placeholder="请选择" v-decorator="['refTransType',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.trans_type" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="凭证类型">
                <a-select show-search placeholder="请选择" v-decorator="['voucherType',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.voucher_type" :key="key">
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
        @change="tableChange"
        size="small"
        bordered
      >
        <!--字段扩展显示customRender-->
        <template v-slot:undoFlag="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:refTransType="text">
          {{ dictFilter(text,'trans_type') }}
        </template>
        <template v-slot:voucherType="text">
          {{ dictFilter(text,'voucher_type') }}
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
  import { getList } from '@/api/payment/account/accounttransdetail'
  export default {
    mixins: [curdMixins],
    components: {
    },
    data () {
      this.columns = [
        {
          title: '记账id',
          dataIndex: 'accountTransId'
        },
        {
          title: '账户号',
          dataIndex: 'accountNo'
        },
        {
          title: '客户号',
          dataIndex: 'custNo'
        },
        {
          title: '商户号',
          dataIndex: 'merchantNo'
        },
        {
          title: '会计日期',
          dataIndex: 'accountDate'
        },
        {
          title: '开始金额',
          dataIndex: 'beginAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '入账金额',
          dataIndex: 'incomeAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '出账金额',
          dataIndex: 'outgoAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '冻结金额',
          dataIndex: 'freezeAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '解冻金额',
          dataIndex: 'unfreezeAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '结束金额',
          dataIndex: 'endAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '冲正标识',
          dataIndex: 'undoFlag',
          scopedSlots: { customRender: 'undoFlag' }
        },
        {
          title: '订单号',
          dataIndex: 'refOrderId'
        },
        {
          title: '交易流水号',
          dataIndex: 'refTransId'
        },
        {
          title: '交易类型',
          dataIndex: 'refTransType',
          scopedSlots: { customRender: 'refTransType' }
        },
        {
          title: '凭证类型',
          dataIndex: 'voucherType',
          scopedSlots: { customRender: 'voucherType' }
        },
        {
          title: '摘要',
          dataIndex: 'zy'
        },
        {
          title: 'ip',
          dataIndex: 'ip'
        },
        {
          title: '备注',
          dataIndex: 'note'
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
        switch_state: {},
        trans_type: {},
        voucher_type: {},
        initUpdateTime: [this.moment().add(-1, 'd').format('YYYY-MM-DD'), this.moment().add(1, 'd').format('YYYY-MM-DD')],
        genTemp: null
      }
    },
    created () {
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('trans_type').then(res => {
        this.trans_type = res.data
      })
      // 获取字典数据
      this.getDictValue('voucher_type').then(res => {
        this.voucher_type = res.data
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
       // 日期格式转换
      onUpdateTimeChange (date, dateString) {
         this.form.setFieldsValue({ 'updateTime': dateString + '' })
      }
    }
  }
</script>
<style scoped>
</style>
