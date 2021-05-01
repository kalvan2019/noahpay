<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--查询区-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="子商户号">
                <a-input v-decorator="['subMerchantNo',{rules: []}]" placeholder="请输入子商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="子商户客户号">
                <a-input v-decorator="['custNo',{rules: []}]" placeholder="请输入子商户客户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="归属商户号">
                <a-input v-decorator="['merchantNo',{rules: []}]" placeholder="请输入归属商户号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="结算银行帐号">
                <a-input v-decorator="['bankAccountNo',{rules: []}]" placeholder="请输入结算银行帐号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="客户状态">
                <a-select show-search placeholder="请选择" v-decorator="['state',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:cust:submerchantinfo:download')">下载</a-button>
        <a-button type="primary" icon="plus" @click="handleAdd" v-if="hasPerm('payment:cust:submerchantinfo:add')">新建</a-button>
        <a-button type="primary" icon="download" @click="handleDownloadTemplate" v-if="hasPerm('payment:cust:submerchantinfo:upload')">批量导入模板</a-button>
        <a-upload
          name="file"
          :customRequest="handleUpload"
        >
          <a-button type="primary" icon="upload" v-if="hasPerm('payment:cust:submerchantinfo:upload')">批量导入</a-button>
        </a-upload>
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
        <template v-slot:bankAccountType="text">
          {{ dictFilter(text,'bank_account_type') }}
        </template>
        <template v-slot:bankType="text">
          {{ dictFilter(text,'bank_type') }}
        </template>
        <template v-slot:state="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <!--行操作-->
        <template v-slot:action="text, record">
          <span>
            <a href="javascript:" @click="handleEdit(record)" v-if="hasPerm('payment:cust:submerchantinfo:edit')">编辑</a>
            <a-divider type="vertical"/>
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
  import { getList, downloadFile, downloadTemplateFile, uploadFile } from '@/api/payment/cust/submerchantinfo'
  import save from './save'
  export default {
    mixins: [curdMixins],
    components: {
      save
    },
    data () {
      this.columns = [
        {
          title: '子商户号',
          dataIndex: 'subMerchantNo'
        },
        {
          title: '子商户名',
          dataIndex: 'subMerchantName'
        },
        {
          title: '子商户客户号',
          dataIndex: 'custNo'
        },
        {
          title: '归属商户号',
          dataIndex: 'merchantNo'
        },
        {
          title: '结算银行帐号',
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
          title: '行名',
          dataIndex: 'bankName'
        },
        {
          title: '客户状态',
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
        bank_account_type: {},
        bank_type: {},
        switch_state: {},
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
      // 导入模板下载,mixins调用
      downloadTemplate () {
        return downloadTemplateFile()
      },
      // 导入,mixins调用
      upload (file) {
        return uploadFile(file)
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
