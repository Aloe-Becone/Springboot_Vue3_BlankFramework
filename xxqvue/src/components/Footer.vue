<template >
  <el-footer class="app-footer">
    <div class="footer-content">
      <div class="copyright" >
        <p class="ps-foot-text">&copy; 2025 王梓镔小学期实训项目. 保留所有权利.</p>
      </div>
      <div class="links">
        <el-link type="info" :underline="false" href="#">关于我们</el-link>
        <el-link type="info" :underline="false" href="#">联系方式</el-link>
        <el-link type="info" :underline="false" href="#">隐私政策</el-link>
        <el-link type="info" :underline="false" href="#">使用条款</el-link>
      </div>
    </div>
  </el-footer>
</template>

<script>
export default {
  name: 'Footer'
}
</script>

<style scoped>
.app-footer {
  background-color: var(--el-bg-color-page);
  color: var(--el-text-color-regular);
  border-top: 1px solid var(--el-border-color-light);
  padding: 20px 0;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.copyright {
  font-size: 14px;
  color: var(--el-text-color-secondary);
}

.links {
  display: flex;
  gap: 20px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .footer-content {
    flex-direction: column;
    text-align: center;
    gap: 10px;
  }

  .links {
    flex-wrap: wrap;
    justify-content: center;
    gap: 10px;
  }
}
</style>