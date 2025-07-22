<template>
  <el-aside>
    <el-row justify="space-between">
      <el-menu mode="vertical" :router="true"
               style="height: 100vh; width: 80%">
        <el-menu-item index="/admin/userAdmin">用户管理</el-menu-item>
        <el-menu-item index="/admin/noticeAdmin">公告管理</el-menu-item>
        <el-menu-item index="/admin/activityAdmin">活动管理</el-menu-item>
        <el-menu-item index="/admin/postAdmin">帖子管理</el-menu-item>
        <el-menu-item index="/admin/myInfo">我的信息</el-menu-item>
      </el-menu>
    </el-row>
  </el-aside>
</template>

<script setup>
</script>