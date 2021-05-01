<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--查询区-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="调账流水id">
                <a-input v-decorator="['adjustTransId',{rules: []}]" placeholder="请输入调账流水id"/>
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
              <a-form-item label="凭证类型">
                <a-select show-search placeholder="请选择" v-decorator="['voucherType',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.voucher_type" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="记账流水">
                <a-input v-decorator="['accountTransId',{rules: []}]" placeholder="请输入记账流水"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="会计日期">
                <a-input v-decorator="['accountDate',{rules: []}]" placeholder="请输入会计日期"/>
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
              <a-form-item label="状态">
                <a-select show-search placeholder="请选择" v-decorator="['state',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:account:accountadjustbill:download')">下载</a-button>
        <a-button type="primary" icon="plus" @click="handleAdd" v-if="hasPerm('payment:account:accountadjustbill:add')">新建</a-button>
        <a-button type="primary" icon="download" @click="handleDownloadTemplate" v-if="hasPerm('payment:account:accountadjustbill:upload')">批量导入模板</a-button>
        <a-upload
          name="file"
          :customRequest="handleUpload"
        >
          <a-button type="primary" icon="upload" v-if="hasPerm('payment:account:accountadjustbill:upload')">批量导入</a-button>
        </a-upload>
      </div>
      <!--汇总-->
      <a-alert showIcon>
        <template slot="message">
          <span style="margin-right: 12px">已选择: <a style="font-weight: 600">{{ this.selectedRowKeys.length }}</a>记录</span>
          <template v-if="hasPerm('payment:account:accountadjustbill:sum')">
            <span style="margin-right: 12px">总入账金额: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.incomeAmount, 100, 2) }}</a></span>
            <span style="margin-right: 12px">总出账金额: <a style="font-weight: 600">{{ this.amountFix(this.dataSum.outgoAmount, 100, 2) }}</a></span>
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
        <template v-slot:voucherType="text">
          {{ dictFilter(text,'voucher_type') }}
        </template>
        <template v-slot:state="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <!--行操作-->
        <template v-slot:action="text, record">
          <span>
          </span>
        </template>
      </a-table>
      <!--自定义组件-->
      <save ref="save" @refresh="refresh" />
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, getSum, downloadFile, downloadTemplateFile, uploadFile } from '@/api/payment/account/accountadjustbill'
  import save from './save'
  export default {
    mixins: [curdMixins],
    components: {
      save
    },
    data () {
      this.columns = [
        {
          title: '调账流水id',
          dataIndex: 'adjustTransId'
        },
        {
          title: '交易类型',
          dataIndex: 'transType',
          scopedSlots: { customRender: 'transType' }
        },
        {
          title: '凭证类型',
          dataIndex: 'voucherType',
          scopedSlots: { customRender: 'voucherType' }
        },
        {
          title: '记账流水',
          dataIndex: 'accountTransId'
        },
        {
          title: '会计日期',
          dataIndex: 'accountDate'
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
          title: '调账原因',
          dataIndex: 'adjustReason'
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
        voucher_type: {},
        switch_state: {},
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
      this.getDictValue('voucher_type').then(res => {
        this.voucher_type = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
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
      // 导入模板下载,mixins调用
      downloadTemplate () {
        return downloadTemplateFile()
      },
      // 导入,mixins调用
      upload (file) {
        return uploadFile(file)
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
