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
              <a-form-item label="下游协议号">
                <a-input v-decorator="['merchantProtocolNo',{rules: []}]" placeholder="请输入下游协议号"/>
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
              <a-form-item label="渠道商户号">
                <a-input v-decorator="['channelMerchantNo',{rules: []}]" placeholder="请输入渠道商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="生效时间">
                <a-input v-decorator="['effectiveTime',{rules: []}]" placeholder="请输入生效时间"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="失效时间">
                <a-input v-decorator="['expiryTime',{rules: []}]" placeholder="请输入失效时间"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="银行协议号">
                <a-input v-decorator="['bankProtocolNo',{rules: []}]" placeholder="请输入银行协议号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="银行帐号">
                <a-input v-decorator="['bankAccountNo',{rules: []}]" placeholder="请输入银行帐号"/>
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
              <a-form-item label="客户手机">
                <a-input v-decorator="['mobile',{rules: []}]" placeholder="请输入客户手机"/>
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
              <a-form-item label="证件号码">
                <a-input v-decorator="['certificateNo',{rules: []}]" placeholder="请输入证件号码"/>
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:cust:merchantconsumerprotocol:download')">下载</a-button>
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
        :scroll="{ x: 2700}"
        @change="tableChange"
        size="small"
        bordered
      >
        <!--字段扩展显示customRender-->
        <template v-slot:channelNo="text">
          {{ dictFilter(text,'channel_no') }}
        </template>
        <template v-slot:bankType="text">
          {{ dictFilter(text,'bank_type') }}
        </template>
        <template v-slot:payType="text">
          {{ dictFilter(text,'pay_type') }}
        </template>
        <template v-slot:certificateType="text">
          {{ dictFilter(text,'certificate_type') }}
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
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, downloadFile } from '@/api/payment/cust/merchantconsumerprotocol'
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
          title: '下游协议号',
          dataIndex: 'merchantProtocolNo'
        },
        {
          title: '子商户号',
          dataIndex: 'subMerchantNo'
        },
        {
          title: '渠道编号',
          dataIndex: 'channelNo',
          scopedSlots: { customRender: 'channelNo' }
        },
        {
          title: '渠道商户号',
          dataIndex: 'channelMerchantNo'
        },
        {
          title: '渠道子商户号',
          dataIndex: 'channelSubMerchantNo'
        },
        {
          title: '生效时间',
          dataIndex: 'effectiveTime'
        },
        {
          title: '失效时间',
          dataIndex: 'expiryTime'
        },
        {
          title: '单笔金额上限',
          dataIndex: 'limitMaxAmount'
        },
        {
          title: '银行协议号',
          dataIndex: 'bankProtocolNo'
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
          title: '银行失效日期',
          dataIndex: 'bankAccountExpiry'
        },
        {
          title: '账户级别',
          dataIndex: 'bankAccountLevel'
        },
        {
          title: '行别',
          dataIndex: 'bankType',
          scopedSlots: { customRender: 'bankType' }
        },
        {
          title: '行名',
          dataIndex: 'bankName'
        },
        {
          title: '客户手机',
          dataIndex: 'mobile'
        },
        {
          title: '扩展字段',
          dataIndex: 'extData'
        },
        {
          title: '支付类型',
          dataIndex: 'payType',
          scopedSlots: { customRender: 'payType' }
        },
        {
          title: '证件类型',
          dataIndex: 'certificateType',
          scopedSlots: { customRender: 'certificateType' }
        },
        {
          title: '证件号码',
          dataIndex: 'certificateNo'
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
        channel_no: {},
        bank_type: {},
        pay_type: {},
        certificate_type: {},
        switch_state: {},
        initUpdateTime: [this.moment().add(-1, 'd').format('YYYY-MM-DD'), this.moment().add(1, 'd').format('YYYY-MM-DD')],
        genTemp: null
      }
    },
    created () {
      // 获取字典数据
      this.getDictValue('channel_no').then(res => {
        this.channel_no = res.data
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
      this.getDictValue('certificate_type').then(res => {
        this.certificate_type = res.data
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
