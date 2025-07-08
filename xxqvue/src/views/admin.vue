<template>
  <el-container>
    <Side></Side>
    <el-main>
      <router-view></router-view>
    </el-main>
    <Footer></Footer>
  </el-container>
</template>

<script>
//导入组件
import Footer from '../components/Footer.vue'
import Side from '../components/Side.vue'

export default {
  //注册组件
  components: {
    Footer,
    Side,
  }
}
</script>