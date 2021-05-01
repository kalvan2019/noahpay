/**
 * curl 组件封装
 * 业务实现类
 * initQuery:  初始化查询列表
 * list()      查询数据
 * listBack()  查询结果后的回调
 * delete()    删除一行数据
 */
import moment from 'moment'
export default {
  data () {
    return {
      that: this,
      advanced: false,
      selectedRowKeys: [],
      dataSource: [],
      dataSum: {},
      pagination: {
        current: 1,
        pageSize: 10,
        pageSizeOptions: [
          '10', '20', '30', '40'
        ],
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total) => `${total} ` + this.$t('global.show.row'),
        total: 0
      },
      queryParam: {},
      filterParams: {},
      loading: false,
      pageParam: {
        sortParam: '',
        direction: ''
      },
      form: this.$form.createForm(this),
      initQuery: true
    }
  },
  created () {
  },
  mounted () {
    if (this.initQuery) {
      this.handleSearch()
    }
  },
  // 字典转换
  filters: {
    dictIconFilter (dictKey, that, dictMapName) {
      dictKey = dictKey + ''
      if (!that[dictMapName] || !that[dictMapName][dictKey]) {
        return 'default'
      }
      return that[dictMapName][dictKey].dictIcon
    },
    dictFilter (dictKey, that, dictMap) {
      dictKey = dictKey + ''
      if (!that[dictMap] || !that[dictMap][dictKey]) {
        return dictKey
      }
      // return `(${dictKey})${that[dictMap][dictKey].dictValue}`
      return that[dictMap][dictKey].dictValue
    }
  },
  methods: {
    // 日期控件
    moment,
    // 后台字典转换方法
    dictFilter (dictKey, dictMap) {
      dictKey = dictKey + ''
      if (!this[dictMap] || !this[dictMap][dictKey]) {
        return dictKey
      }
      return this[dictMap][dictKey].dictValue
    },
    /**
     * 金额单位转换
     * @param amount 原始金额数字类型
     * @param unit 向上转换单位的除数，如分->元则填100,如元->分则填1/100
     * @param fixed 保留几位小数点
     * @returns {string}
     */
    amountFix (amount, unit, fixed) {
      if (typeof amount !== 'number' || isNaN(amount)) {
        return ''
      }
      if (typeof unit !== 'number' || isNaN(unit)) {
        unit = 100
      }
      if (typeof fixed !== 'number' || isNaN(fixed)) {
        fixed = 2
      }
      const str = (amount / unit).toFixed(fixed) + ''
      // 取到整数部分
      const intSum = str.substring(0, str.indexOf('.')).replace(/\B(?=(?:\d{3})+$)/g, ',')
      // 取到小数部分搜索
      let dot = str.substring(str.length, str.indexOf('.'))
      if (fixed > 2) {
        // 最后为0则不显示
        if (dot.substring(dot.length - 1) === '0') {
          dot = dot.substring(0, dot.length - 1)
        }
      }
      return intSum + dot
    },
    // 查询数据方法封装
    search () {
      // 子函数
      if (!this.list) {
        return
      }
      this.form.validateFields((errors, values) => {
        if (!errors) {
          this.loading = true
          if (this.filterForm) {
            values = this.filterForm(values)
          }
          this.$nextTick(() => {
            const param = {
              page: this.pagination.current,
              limit: this.pagination.pageSize,
              ...this.queryParam,
              ...this.pageParam,
              ...this.filterParams,
              ...values
            }
            this.list(param).then(res => {
              this.loading = false
              if (res.state === 0) {
                this.dataSource = res.data
                this.pagination.total = res.count
                if (this.sum) {
                  this.sum(param).then(res => {
                    // 汇总数据
                    if (res.data) {
                      this.dataSum = res.data
                    }
                  })
                }
              } else {
                this.$message.error(res.message)
              }
              if (this.queryBack) {
                this.$nextTick(() => {
                  this.queryBack(res)
                })
              }
            }).catch(res => {
              this.loading = false
            })
          })
        }
      })
    },
    // 查询按钮事件
    handleSearch () {
      this.pagination.current = 1
      this.refresh()
    },
    // 查询重置事件
    handleReset () {
      this.queryParam = {}
      this.form.resetFields()
      this.refresh()
    },
    // 详情按钮事件
    handleInfo (record) {
      this.$refs.info.handleInfo(record)
    },
    // 新增按钮事件
    handleAdd (record) {
      this.$refs.save.handleAdd(record)
    },
    // 切换状态
    handleSwitchState (state, record, idAttr) {
      if (state === 0) {
        // 关闭
        state = 1
      } else if (state === 1) {
        // 开启
        state = 0
      }
      const values = { id: record.id, state: state }
      this.loading = true
      this.edit(values).then(res => {
        this.loading = false
        if (res.state === 0) {
          this.$message.success(res.message)
          this.$emit('refresh')
          this.visible = false
        } else {
          this.$message.error(res.message)
        }
      }).catch(res => {
        this.loading = false
      })
    },
    // 模板下载按钮事件
    handleDownloadTemplate () {
      if (!this.downloadTemplate) {
        return
      }
      this.loading = true
      this.$nextTick(() => {
        this.downloadTemplate().then(res => {
          this.loading = false
          this.parseDownload(res)
        }).catch((error) => {
          this.loading = false
          console.log(error)
        })
      })
    },
    // 上传按钮事件
    handleUpload (file) {
      if (!this.upload) {
        return
      }
      this.loading = true
      this.$nextTick(() => {
        this.upload(file).then(res => {
          this.loading = false
          if (res.state === 0) {
            this.$message.success(res.message)
            file.onSuccess()
            this.refresh()
          } else {
            this.$message.error(res.message)
            file.onError()
          }
        }).catch((error) => {
          this.loading = false
          console.log(error)
        })
      })
    },
    // 下载按钮事件
    handleDownload () {
      if (!this.download) {
        return
      }
      this.form.validateFields((errors, values) => {
        if (!errors) {
          this.loading = true
          if (this.filterForm) {
            values = this.filterForm(values)
          }
          this.$nextTick(() => {
            const param = {
              ...this.queryParam,
              ...this.pageParam,
              ...this.filterParams,
              ...values
            }
            this.download(param).then(res => {
              this.loading = false
              this.parseDownload(res)
            }).catch((error) => {
              this.loading = false
              console.log(error)
            })
          })
        }
      })
    },
    // 处理下载响应
    parseDownload (res) {
      const content = res.data
      const blob = new Blob([content]) // 构造一个blob对象来处理数据
      if (content.type !== 'multipart/form-data') {
        const reader = new FileReader()
        reader.readAsText(blob)
        reader.onload = e => {
          this.$message.error(JSON.parse(e.target.result).message)
        }
        return
      }
      const fileName = decodeURI(res.headers['filename']) // 导出文件名
      // 对于<a>标签，只有 Firefox 和 Chrome（内核） 支持 download 属性
      // IE10以上支持blob但是依然不支持download
      if ('download' in document.createElement('a')) { // 支持a标签download的浏览器
        const link = document.createElement('a') // 创建a标签
        link.download = fileName // a标签添加属性
        link.style.display = 'none'
        link.href = URL.createObjectURL(blob)
        document.body.appendChild(link)
        link.click() // 执行下载
        URL.revokeObjectURL(link.href) // 释放url
        document.body.removeChild(link) // 释放标签
      } else { // 其他浏览器
        navigator.msSaveBlob(blob, fileName)
      }
    },
    // 修改按钮事件
    handleEdit (record) {
      this.$refs.save.handleEdit({ ...record })
    },
    // 删除按钮事件
    handleDelete (record) {
      this.delete(record).then(res => {
        if (res.state === 0) {
          this.$message.success(res.message)
          this.refresh()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 审核按钮事件
    handleAuditing (record, pass) {
      this.auditing(record, pass).then(res => {
        if (res.state === 0) {
          this.$message.success(res.message)
          this.refresh()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 多选操作按钮事件
    handleMenuClick (e) {
      if (!this.multiOperator) {
        return
      }
      const operator = this.multiOperator.find(item => item.key === e.key)
      if (!operator || !operator.method) {
        return
      }
      const ids = this.selectedRowKeys.join(',')
      this.$confirm({
        title: this.$t('global.confirm.batch') + `${this.selectedRowKeys.length} ` + this.$t('global.show.row'),
        okText: this.$t('global.btn.ok'),
        okType: 'primary',
        cancelText: this.$t('global.btn.cancel'),
        onOk: () => {
          operator.method({ ids: ids }, operator.ext).then(res => {
            if (res.state === 0) {
              this.$message.success(operator.message || res.message || this.$t('global.show.success'))
              this.refresh()
            } else {
              this.$message.error(res.message)
            }
          })
        },
        onCancel () {
        }
      })
    },
    // table数据改变事件
    tableChange (page, filters, sorter) {
      this.pagination.current = page.current
      this.pagination.pageSize = page.pageSize
      this.filterParams = {}
      Object.keys(filters).forEach(item => {
        if (filters[item] && filters[item].length > 0) {
          this.filterParams[item] = filters[item].join('||')
        } else {
          delete this.filterParams[item]
        }
      })
      if (sorter.columnKey) {
        let prop = sorter.columnKey
        let pre = ''
        if (prop.indexOf('.') > 0) {
          pre = prop.substring(0, prop.indexOf('.'))
          prop = prop.substring(prop.indexOf('.'), prop.length)
        }
        prop = this.$objUtils.objectTotable(prop)
        if (pre) {
          prop = pre + prop
        }
        this.pageParam.sortParam = prop
        this.pageParam.direction = (sorter.order.startsWith('descend') ? '0' : '1')
      } else {
        this.pageParam.sortParam = ''
        this.pageParam.direction = ''
      }
      this.refresh()
    },
    // 多选框事件
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    // 组件刷新事件
    refresh () {
      this.selectedRowKeys = []
      this.search()
    }
  }
}
