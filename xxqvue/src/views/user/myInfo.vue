<template>
  <div class="student-container">
    <el-card class="student-form">
      <template #header>
        <div class="card-header">
          <span>个人信息管理</span>
          <el-button type="danger" @click="handleLogout">退出登录</el-button>
        </div>
      </template>

      <el-form
          ref="formRef"
          :model="studentForm"
          :rules="rules"
          label-width="120px"
          label-position="top"
      >
        <!-- 学校信息 -->
        <el-form-item label="学校名称" prop="school">
          <el-input v-model="studentForm.school" placeholder="请输入学校名称" />
        </el-form-item>

        <!-- 个人信息 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="studentForm.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="studentForm.sex">
                <el-radio value="男">男</el-radio>
                <el-radio value="女">女</el-radio>
                <el-radio value="其他">其他</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 学业信息 -->
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="入学年份" prop="grade">
              <el-date-picker
                  v-model="studentForm.grade"
                  type="year"
                  placeholder="选择入学年份"
                  value-format="YYYY"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="学号" prop="number">
              <el-input v-model="studentForm.number" placeholder="请输入学号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="专业" prop="major">
              <el-input v-model="studentForm.major" placeholder="请输入专业" />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 联系方式 -->
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="studentForm.phone" placeholder="请输入手机号码" />
        </el-form-item>

        <!-- 个人信息 -->
        <el-form-item label="备注" prop="info">
          <el-input
              v-model="studentForm.info"
              autosize
              type="textarea"
              placeholder="输入个人看简介"
          />
        </el-form-item>

        <!-- 操作按钮 -->
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交信息</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, inject } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()
const $request = inject('$request')
const $user = inject('$user')

// 表单数据
const studentForm = reactive({
  school: '',
  name: '',
  number: '',
  sex: '男',
  grade: '',
  major: '',
  phone: '',
  info: ''
})

// 原始数据备份（使用 computed 保持响应性）
const originalData = computed(() => JSON.parse(JSON.stringify(studentForm)))

// 是否已修改
const isModified = computed(() => {
  return JSON.stringify(studentForm) !== JSON.stringify(originalData.value)
})

// 表单验证规则
const rules = reactive({
  school: [{ required: true, message: '请输入学校名称', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  grade: [{ required: true, message: '请选择入学年份', trigger: 'change' }],
  major: [{ required: true, message: '请输入专业', trigger: 'blur' }],
  phone: [
    { required: true, message: '请输入联系方式', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  number: [
    { required: true, message: '请输入学号', trigger: 'blur' },
    { pattern: /^\d+$/, message: '请输入正确的学号', trigger: 'blur' }
  ]
})

// 表单引用
const formRef = ref(null)

// 提交表单
const submitForm = () => {
  console.log(formRef.value)
  formRef.value?.validate((valid) => {
    if (valid) {
      $request.post('/user/update', {
        id: $user.id,
        name: studentForm.name,
        number: studentForm.number,
        school: studentForm.school,
        sex: studentForm.sex,
        grade: studentForm.grade,
        major: studentForm.major,
        phone: studentForm.phone,
        info: studentForm.info
      }).then(res => {
        if (res.data.code === '200') {
          ElMessage.success('修改成功')
          // 更新原始数据
          Object.assign(studentForm, res.data.data)
        } else {
          ElMessage.error(res.data.msg)
        }
      }).catch(err => {
        ElMessage.error('请求失败')
      })
    }
  })
}

// 重置表单
const resetForm = () => {
  formRef.value?.resetFields()
}

// 退出登录
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

// 加载已有数据
onMounted(() => {
  // 使用模板字符串拼接URL路径参数
  $request.get(`/user/getInfo/${$user.id}`)
      .then(res => {
        if (res.data.code === '200') {
          Object.assign(studentForm, res.data.data)
        }
      })
      .catch(err => {
        ElMessage.error('加载用户信息失败')
      })
})
</script>

<style scoped>
.student-container {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-tag {
  margin-right: 10px;
  margin-bottom: 10px;
}

.input-new-tag {
  width: 150px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
