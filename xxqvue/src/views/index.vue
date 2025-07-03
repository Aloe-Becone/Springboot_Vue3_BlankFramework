<template>
  <Header></Header>
  <router-view></router-view>
  <Footer></Footer>
</template>

<script>
//导入组件
import Footer from '../components/Footer.vue'
import Header from '../components/Header.vue'

export default {
  //注册组件
  components: {
    Footer,
    Header,
  }
}
</script>