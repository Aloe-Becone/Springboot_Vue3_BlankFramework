<template>
  <el-header>
    <el-row justify="space-between">
      <el-menu mode="horizontal" :router="true"
               style="width: 100%; border-radius: 5px; ">
        <el-menu-item index="/index/view1">页面 1</el-menu-item>
        <el-menu-item index="/index/view2">页面 2</el-menu-item>
        <el-menu-item index="/index/myInfo">我的信息</el-menu-item>
      </el-menu>
    </el-row>
  </el-header>
</template>

<script>
import {ref, inject} from "vue";

// 注入实例
const $request = inject('$request');


export default {
  name: 'Header',
}
</script>