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
              <a-form-item label="生效日期">
                <a-input v-decorator="['effectiveDate',{rules: []}]" placeholder="请输入生效日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="失效日期">
                <a-input v-decorator="['expiryDate',{rules: []}]" placeholder="请输入失效日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="客户协议审核">
                <a-select show-search placeholder="请选择" v-decorator="['signAuditType',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="签约验证手机">
                <a-select show-search placeholder="请选择" v-decorator="['signCheckSms',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="签约成功发短信">
                <a-select show-search placeholder="请选择" v-decorator="['signSendSms',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="处理方式">
                <a-select show-search placeholder="请选择" v-decorator="['transDealType',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易审核">
                <a-select show-search placeholder="请选择" v-decorator="['transAuditType',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易检查客户协议">
                <a-select show-search placeholder="请选择" v-decorator="['transCheckProtocol',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易验证手机">
                <a-select show-search placeholder="请选择" v-decorator="['transCheckSms',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="交易成功发短信">
                <a-select show-search placeholder="请选择" v-decorator="['transSendSms',{rules: []}]">
                  <a-select-option value="">全部</a-select-option>
                  <a-select-option v-for="(value, key) in this.switch_state" :key="key">
                    ({{ key }}){{ value.dictValue }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="入网状态">
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:cust:merchantproducttrans:download')">下载</a-button>
        <a-button type="primary" icon="plus" @click="handleAdd" v-if="hasPerm('payment:cust:merchantproducttrans:add')">新建</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="delete" @click="handleMenuClick" v-if="hasPerm('payment:cust:merchantproducttrans:delete')">
              <a-icon type="delete"/>
              删除
            </a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            批量操作
            <a-icon type="down"/>
          </a-button>
        </a-dropdown>
        <a-button type="primary" icon="download" @click="handleDownloadTemplate" v-if="hasPerm('payment:cust:merchantproducttrans:upload')">批量导入模板</a-button>
        <a-upload
          name="file"
          :customRequest="handleUpload"
        >
          <a-button type="primary" icon="upload" v-if="hasPerm('payment:cust:merchantproducttrans:upload')">批量导入</a-button>
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
        :scroll="{ x: 2500}"
        @change="tableChange"
        size="small"
        bordered
      >
        <!--字段扩展显示customRender-->
        <template v-slot:transType="text">
          {{ dictFilter(text,'trans_type') }}
        </template>
        <template v-slot:signAuditType="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:signCheckSms="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:signSendSms="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:transDealType="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:transAuditType="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:transCheckProtocol="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:transCheckSms="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:transSendSms="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <template v-slot:state="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <!--行操作-->
        <template v-slot:action="text, record">
          <span>
            <a href="javascript:" @click="handleEdit(record)" v-if="hasPerm('payment:cust:merchantproducttrans:edit')">编辑</a>
            <a-divider type="vertical"/>
            <a-popconfirm title="确定删除吗？" @confirm="handleDelete(record)" v-if="hasPerm('payment:cust:merchantproducttrans:delete')">
              <a-icon slot="icon" type="question-circle-o" style="color: red"/>
              <a href="javascript:">删除</a>
            </a-popconfirm>
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
  import { getList, downloadFile, downloadTemplateFile, uploadFile, deleteByIds } from '@/api/payment/cust/merchantproducttrans'
  import save from './save'
  export default {
    mixins: [curdMixins],
    components: {
      save
    },
    data () {
      this.columns = [
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
          title: '单笔金额上限',
          dataIndex: 'limitMaxAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '日限额',
          dataIndex: 'dayLimitAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '日限笔数',
          dataIndex: 'dayLimitNumber'
        },
        {
          title: '月金额限额',
          dataIndex: 'monthLimitAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '月笔数限额',
          dataIndex: 'monthLimitNumber'
        },
        {
          title: '生效日期',
          dataIndex: 'effectiveDate'
        },
        {
          title: '失效日期',
          dataIndex: 'expiryDate'
        },
        {
          title: '客户协议审核',
          dataIndex: 'signAuditType',
          scopedSlots: { customRender: 'signAuditType' }
        },
        {
          title: '签约验证手机',
          dataIndex: 'signCheckSms',
          scopedSlots: { customRender: 'signCheckSms' }
        },
        {
          title: '签约成功发短信',
          dataIndex: 'signSendSms',
          scopedSlots: { customRender: 'signSendSms' }
        },
        {
          title: '处理方式',
          dataIndex: 'transDealType',
          scopedSlots: { customRender: 'transDealType' }
        },
        {
          title: '交易审核',
          dataIndex: 'transAuditType',
          scopedSlots: { customRender: 'transAuditType' }
        },
        {
          title: '交易检查客户协议',
          dataIndex: 'transCheckProtocol',
          scopedSlots: { customRender: 'transCheckProtocol' }
        },
        {
          title: '交易验证手机',
          dataIndex: 'transCheckSms',
          scopedSlots: { customRender: 'transCheckSms' }
        },
        {
          title: '交易成功发短信',
          dataIndex: 'transSendSms',
          scopedSlots: { customRender: 'transSendSms' }
        },
        {
          title: '入网状态',
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
        // 多选操作
        multiOperator: [
          {
            key: 'delete',
            method: deleteByIds
          }
        ],
        trans_type: {},
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
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
      })
      // 获取字典数据
      this.getDictValue('switch_state').then(res => {
        this.switch_state = res.data
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
      // 删除,mixins调用
      delete (record) {
        return deleteByIds({ ids: [record.id] })
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
