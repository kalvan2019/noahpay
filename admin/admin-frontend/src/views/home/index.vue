<template>
  <iframe src="http://test.com/echarts-monitor-view" name="main_page_iframe" width="100%" height="800px" frameborder="0"/>
</template>

<script>
export default {
  name: 'Welcome',
  mounted () {
    // const that = this
    // window.onresize = () => {
    //   const ifdoc = that.$refs.main_page_iframe.contentDocument
    //   const cHeight = Math.max(ifdoc.body.clientHeight, ifdoc.documentElement.clientHeight)
    //   const sHeight = Math.max(ifdoc.body.scrollHeight, ifdoc.documentElement.scrollHeight)
    //   that.$refs.main_page_iframe.style.height = Math.max(cHeight, sHeight) + 'px'
    // }
  }
}
</script>
