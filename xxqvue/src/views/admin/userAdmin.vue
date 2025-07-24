<template>
  <div class="user-management-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>用户管理</span>
          <div>
            <el-button type="primary" @click="openAddDialog">
              添加用户
            </el-button>
          </div>
        </div>
      </template>

      <!-- 用户表格 -->
      <el-table
          :data="userList"
          style="width: 100%"
          border
          stripe
          v-loading="loading"
      >
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column label="头像" width="80">
          <template #default="{row}">
            <el-avatar
                v-if="row.avatar"
                :src="$serverURL + row.avatar"
                shape="square"
                :preview-src-list="[$serverURL + row.avatar]"
            />
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="sex" label="性别" width="80" />
        <el-table-column prop="role" label="角色" width="100">
          <template #default="{ row }">
            <el-tag :type="row.role === 'ADMIN' ? 'danger' : 'primary'">
              {{ row.role === 'ADMIN' ? '管理员' : '普通用户' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="school" label="学校" width="150" />
        <el-table-column prop="major" label="专业" width="150" />
        <el-table-column prop="grade" label="年级" width="100" />
        <el-table-column prop="phone" label="电话" width="120" />
        <el-table-column prop="info" label="备注" show-overflow-tooltip />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="openEditDialog(row.id)">
              编辑
            </el-button>
            <el-button v-if="row.role === 'USER'" size="small" type="danger" @click="handleDelete(row.id)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

<!--      &lt;!&ndash; 分页 &ndash;&gt;-->
<!--      <div class="pagination-container">-->
<!--        <el-pagination-->
<!--            v-model:current-page="currentPage"-->
<!--            v-model:page-size="pageSize"-->
<!--            :page-sizes="[10, 20, 30, 50]"-->
<!--            :background="true"-->
<!--            layout="total, sizes, prev, pager, next, jumper"-->
<!--            :total="total"-->
<!--            @size-change="getUsers"-->
<!--            @current-change="getUsers"-->
<!--        />-->
<!--      </div>-->
    </el-card>

    <!-- 添加/编辑用户对话框 -->
    <el-dialog
        v-model="editVisible"
        title='编辑用户'
        width="50%"
    >
      <editInfo ref="editInfoRef"></editInfo>
    </el-dialog>

    <el-dialog
        v-model="addVisible"
        title='添加用户'
        width="50%"
    >
      <addUser></addUser>
    </el-dialog>
    
  </div>
</template>

<script setup>
import {ref, reactive, onMounted, inject, nextTick} from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import editInfo from './editInfo.vue'
import addUser from './addUser.vue'

// 全局组件
const $request = inject('$request')
const $user = inject('$user')
const $serverURL = inject('$serverURL')

// 用户数据
const userList = ref([])
const selectedUsers = ref([])

const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 对话框数据
const editVisible = ref(false)
const addVisible = ref(false)
const editInfoRef = ref() // 子组件引用


// 获取用户列表
const getUsers = async () => {
  loading.value = true
  $request.get('/user/getAll').then(res => {
    if (res.data.code === '200') {
      console.log(res.data.data)
      userList.value = res.data.data
      nextTick()
    }
    else {
      console.log(res.data.msg)
      // 获取失败
      ElMessage({
        message: '获取用户信息失败',
        type: 'error',
      })
    }
  })
  loading.value = false
}


// 打开编辑对话框
const openEditDialog = (id) => {
  editVisible.value = true
  editInfoRef.value.getInfo(id) // 调用子组件暴露的方法

}

// 打开添加对话框
const openAddDialog = () => {
  addVisible.value = true
}

// 删除用户
const handleDelete = (id) => {
  ElMessageBox.confirm('确认删除该用户吗？', '警告', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    $request.get(`/user/delete/${id}`).then(res => {
      if (res.data.code === '200') {
        ElMessage({
          message: '删除成功',
          type: 'success',
        })
      }
      else {
        console.log(res.data.msg)
        // 获取失败
        ElMessage({
          message: '删除失败',
          type: 'error',
        })
      }
    })
    await getUsers()
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 初始化加载数据
onMounted(() => {
  getUsers()
})

</script>

<style scoped>
.user-management-container {
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
</style>