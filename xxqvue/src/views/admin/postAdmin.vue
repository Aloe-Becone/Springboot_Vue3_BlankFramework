<template>
  <div class="post-management-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>帖子管理</span>
          <div>
            <el-button type="danger" :disabled="!selectedPosts.length" @click="batchDelete">
              批量删除
            </el-button>
          </div>
        </div>
      </template>

      <!-- 帖子表格 -->
      <el-table
          :data="postList"
          style="width: 100%"
          border
          stripe
          v-loading="loading"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="userId" label="用户ID" width="100" />
        <el-table-column prop="title" label="标题" width="150" show-overflow-tooltip />
        <el-table-column prop="content" label="内容" show-overflow-tooltip />
        <el-table-column label="图片" width="150">
          <template #default="{ row }">
            <el-image
                v-for="(img, index) in row.imagesList"
                :key="index"
                :src="$serverURL + img"
                style="width: 40px; height: 40px; margin-right: 5px;"
                fit="cover"
                :preview-teleported="false"
                :preview-src-list="[]"
            />
          </template>
        </el-table-column>

        <el-table-column prop="likeCount" label="点赞数" width="100">
          <template #default="{ row }">
            <el-tag :type="row.likeCount > 100 ? 'success' : 'info'">
              {{ row.likeCount }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button size="small" type="danger" @click="handleDelete(row.id)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
<!--      <div class="pagination-container">-->
<!--        <el-pagination-->
<!--            v-model:current-page="currentPage"-->
<!--            v-model:page-size="pageSize"-->
<!--            :page-sizes="[10, 20, 30, 50]"-->
<!--            :background="true"-->
<!--            layout="total, sizes, prev, pager, next, jumper"-->
<!--            :total="total"-->
<!--            @size-change="getPosts"-->
<!--            @current-change="getPosts"-->
<!--        />-->
<!--      </div>-->
    </el-card>

  </div>
</template>

<script setup>
import { ref, onMounted, inject, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

// 全局组件
const $request = inject('$request')
const $serverURL = inject('$serverURL')

// 帖子数据
const postList = ref([])
const selectedPosts = ref([])

const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)


// 加载帖子列表
const getPosts = async () => {
  try {
    loading.value = true
    $request.get('/post/getAll', {}).then(res => {
      if (res.data.code === '200') {
        console.log(res.data.data)
        postList.value = res.data.data
      } else {
        ElMessage.error(res.data.msg)
      }
    }).catch(err => {
      ElMessage.error('请求失败')
    })
  }
  catch (error) {
    ElMessage.error('加载帖子失败')
  } finally {
    loading.value = false
  }
}

// 多选处理
const handleSelectionChange = (selection) => {
  selectedPosts.value = selection
}

// 删除帖子
const handleDelete = (id) => {
  ElMessageBox.confirm('确认删除该帖子吗？', '警告', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    const res = await $request.delete(`/post/delete/${id}`)
    if (res.data.code === '200') {
      ElMessage.success('删除成功')
      await getPosts()
    } else {
      ElMessage.error(res.data.msg || '删除失败')
    }
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 批量删除
const batchDelete = () => {
  if (!selectedPosts.value.length) return

  ElMessageBox.confirm(`确认删除选中的 ${selectedPosts.value.length} 条帖子吗？`, '警告', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    const ids = selectedPosts.value.map(item => item.id)
    const res = await $request.post('/post/batchDelete', { ids })
    if (res.data.code === '200') {
      ElMessage.success('批量删除成功')
      await getPosts()
    } else {
      ElMessage.error(res.data.msg || '批量删除失败')
    }
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 初始化加载数据
onMounted(() => {
  getPosts()
})
</script>

<style scoped>
.post-management-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.el-image {
  cursor: pointer;
  transition: transform 0.3s;
}

.el-image:hover {
  transform: scale(1.1);
}
</style>