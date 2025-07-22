<template>
  <el-header style="height: auto; padding: 0">
    <el-card
        shadow="never"
        style="border-radius: 0; border: none; padding: 0 20px; background-color: var(--vt-c-white-mute);"
        body-style="display: flex; align-items: center; padding: 0"
    >
      <!-- 左侧菜单 -->
      <el-menu
          mode="horizontal"
          :default-active="activeIndex"
          :router="true"
          style="flex: 1; border-bottom: none; height: 64px"
          class="custom-menu"
      >
        <el-menu-item index="/index/notice" class="menu-item">公告</el-menu-item>
        <el-menu-item index="/index/comment" class="menu-item">校友交流</el-menu-item>
        <el-menu-item index="/index/activity" class="menu-item">校友活动</el-menu-item>
        <el-menu-item index="/index/myActivity" class="menu-item">我的活动</el-menu-item>
        <el-menu-item index="/index/myApply" class="menu-item">我的申请</el-menu-item>
        <el-menu-item index="/index/myInfo" class="menu-item">我的信息</el-menu-item>
      </el-menu>

      <!-- 右侧头像 -->
      <div style="display: flex; align-items: center; gap: 12px; margin-left: 20px">
        <span style="font-size: 14px; color: #606266">欢迎，{{ $user.username }}</span>
        <el-dropdown>
          <el-avatar
              shape="square"
              :size="40"
              :src="$serverURL + $user.avatar"
              style="cursor: pointer; border: 1px solid #f0f0f0"
          />
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="handleProfile">个人中心</el-dropdown-item>
              <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-card>
  </el-header>
</template>

<script setup>
import {inject,  ref} from "vue";
import router from "@/router/router.js";
import {ElMessage, ElMessageBox} from "element-plus";

const $user = inject('$user')
const $serverURL = inject('$serverURL')

const activeIndex = ref('') // 激活项

const handleProfile = () => {
  activeIndex.value = '' // 清空激活状态
  router.push('/index/myInfo')
}

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    localStorage.removeItem('user')
    ElMessage.success('退出成功')
    router.push('/login')
  }).catch(() => {
    ElMessage.info('已取消退出')
  })
}

</script>

<style scoped>
/* 菜单项悬停效果 */
.custom-menu .menu-item {
  height: 100%;
  display: flex;
  align-items: center;
  transition: all 0.3s;
  border-bottom: 2px solid transparent;
  font-size: 15px;
}

.custom-menu .menu-item:hover {
  background-color: var(--vt-c-white-mute);
  border-bottom-color: var(--el-color-primary);
}

/* 激活菜单项样式 */
.custom-menu .el-menu-item.is-active {
  color: var(--el-color-primary);
  border-bottom-color: var(--el-color-primary);
}

/* 移除默认下划线 */
.el-menu--horizontal {
  border-bottom: none !important;
}
</style>