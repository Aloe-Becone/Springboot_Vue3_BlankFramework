<template style="width:100%; ">
  <el-menu mode="horizontal"
           :router="true"
           :default-active="$route.path"
  >
    <el-menu-item index="/wzb/index">首页</el-menu-item>
    <el-menu-item index="/wzb/view1">页面 1</el-menu-item>
    <el-menu-item index="/wzb/view2">页面 2</el-menu-item>
    <el-menu-item index="/wzb/view3">页面 3</el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: 'Header',
  methods: {

  }
}
</script>