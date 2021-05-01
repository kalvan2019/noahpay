/**
 * 保存数据组件封装
 * filterForm() 处理请求参数
 * add() 处理请求参数
 * edit() 处理请求参数
 */
export default {
  data () {
    return {
      title: '',
      model: {},
      formLabelWidth: '120px',
      visible: false,
      loading: false,
      fullscreen: false,
      formLayout: {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 7 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 13 }
        }
      },
      form: this.$form.createForm(this)
    }
  },
  created () {
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
    // 详情界面
    handleInfo (record) {
      if (!this.info) {
        return
      }
      this.loading = true
      this.info(record).then(res => {
        if (res.state === 0) {
          this.model = res.data
          this.title = this.$t('global.btn.info')
          this.$nextTick(() => {
            this.visible = true
          })
        } else {
          this.$message.error(res.message)
        }
        this.loading = false
      })
    },
    // 新增界面
    handleAdd (record) {
      this.model = {}
      this.title = this.$t('global.btn.add')
      this.$nextTick(() => {
        this.visible = true
        // 新增页面加载后回调
        if (this.openAddBack) {
            this.openAddBack(record)
        }
      })
    },
    // 编辑界面
    handleEdit (record) {
      this.info(record).then(res => {
        this.loading = false
        if (res.state === 0) {
          this.model = res.data
          this.title = this.$t('global.btn.edit')
          this.$nextTick(() => {
            this.visible = true
          })
        } else {
          this.$message.error(res.message)
        }
        // 编辑页面加载后回调
        if (this.openEditBack) {
            this.openEditBack(res)
        }
      })
    },
    // 新增数据or更新数据
    handleSubmit (idAttr) {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          values = { ...values }
          if (this.filterForm) {
            values = this.filterForm(values)
          }
          let operator = this.add
          if (values.id > 0) {
            // 修改
            operator = this.edit
            // 因为有脱敏显示,避免覆盖db需要比较前端是否有修改
            Object.keys(this.model).forEach(item => {
              if (JSON.stringify(values[item]) === JSON.stringify(this.model[item]) && item !== idAttr) {
                // 未修改过的属性清除后台不更新
                delete values[item]
              }
            })
            if (Object.keys(values).length === 1) {
              this.$message.error(this.$t('global.warn.edit'))
              return
            }
          }
          if (operator) {
            this.loading = true
            operator(values).then(res => {
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
          }
        }
      })
    }
  }
}
