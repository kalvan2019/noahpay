<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <!--查询区-->
      <div class="table-page-search-wrapper">
        <a-form :form="form" layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="营业执照注册号">
                <a-input v-decorator="['businessLicenseNo',{rules: []}]" placeholder="请输入营业执照注册号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="经营范围">
                <a-input v-decorator="['businessScope',{rules: []}]" placeholder="请输入经营范围"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="企业状态">
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
        <a-button type="primary" icon="download" @click="handleDownload" v-if="hasPerm('payment:cust:enterpriseinfo:download')">下载</a-button>
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
        :scroll="{ x: 1800}"
        @change="tableChange"
        size="small"
        bordered
      >
        <!--字段扩展显示customRender-->
        <template v-slot:industry="text">
          {{ dictFilter(text,'industry') }}
        </template>
        <template v-slot:state="text">
          {{ dictFilter(text,'switch_state') }}
        </template>
        <!--行操作-->
        <template v-slot:action="text, record">
          <span>
            <a href="javascript:" @click="handleEdit(record)" v-if="hasPerm('payment:cust:enterpriseinfo:edit')">编辑</a>
            <a-divider type="vertical"/>
          </span>
        </template>
      </a-table>
      <!--自定义组件-->
    </a-card>
  </page-header-wrapper>
</template>
<script>
  import curdMixins from '@/utils/mixins/curdMixins'
  import { getList, downloadFile } from '@/api/payment/cust/enterpriseinfo'
  export default {
    mixins: [curdMixins],
    components: {
    },
    data () {
      this.columns = [
        {
          title: '营业执照注册号',
          dataIndex: 'businessLicenseNo'
        },
        {
          title: '经营范围',
          dataIndex: 'businessScope'
        },
        {
          title: '年营业额',
          dataIndex: 'businessAmount',
          customRender: (value) => {
            value = this.amountFix(value, 100, 2)
            return {
              children: value,
              attrs: {}
            }
          }
        },
        {
          title: '营业执照有效期',
          dataIndex: 'businessLicenseExpiry'
        },
        {
          title: '企业名',
          dataIndex: 'companyName'
        },
        {
          title: '营业执照所在地',
          dataIndex: 'companyAddress'
        },
        {
          title: '行业',
          dataIndex: 'industry',
          scopedSlots: { customRender: 'industry' }
        },
        {
          title: '企业网站',
          dataIndex: 'website'
        },
        {
          title: '企业固定电话',
          dataIndex: 'telephone'
        },
        {
          title: '企业状态',
          dataIndex: 'state',
          scopedSlots: { customRender: 'state' }
        },
        {
          title: '销户时间',
          dataIndex: 'closeTime'
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
        industry: {},
        switch_state: {},
        initCreateTime: [this.moment().add(-1, 'd').format('YYYY-MM-DD'), this.moment().add(1, 'd').format('YYYY-MM-DD')],
        genTemp: null
      }
    },
    created () {
      // 获取字典数据
      this.getDictValue('industry').then(res => {
        this.industry = res.data
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
      onCreateTimeChange (date, dateString) {
         this.form.setFieldsValue({ 'createTime': dateString + '' })
      }
    }
  }
</script>
<style scoped>
</style>
