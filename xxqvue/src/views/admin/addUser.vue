<template>
  <div class="student-container">
    <el-card class="student-form">
      <template #header>
        <div class="card-header">
          <span>个人信息管理</span>
        </div>
      </template>

      <el-form
          ref="formRef"
          :model="studentForm"
          :rules="rules"
          label-width="120px"
          label-position="top"
      >
        <!-- 账号信息 -->
        <el-form-item label="账号名" prop="username">
          <el-input v-model="studentForm.username" placeholder="请输入账号名" />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input v-model="studentForm.password" placeholder="请输入密码" />
        </el-form-item>

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
        <el-form-item label="个人简介" prop="info">
          <el-input
              v-model="studentForm.info"
              autosize
              type="textarea"
              placeholder="输入个人看简介"
          />
        </el-form-item>


        <!-- 操作按钮 -->
        <el-form-item label="身份" prop="role">
          <el-radio-group v-model="studentForm.role">
            <el-radio value="ADMIN">管理员</el-radio>
            <el-radio value="USER">普通用户</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm">提交信息</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>

      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed, inject } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()
const $request = inject('$request')
const $user = inject('$user')

// 表单数据
const studentForm = reactive({
  username: '',
  password: '',
  role: 'USER',
  school: '',
  name: '',
  number: '',
  sex: '男',
  grade: '',
  major: '',
  phone: '',
  info: '',
})

// 表单引用
const formRef = ref(null)

// 表单验证规则
const rules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'change' },
    { min: 3, max: 16, message: '长度在3到16个字符', trigger: 'change' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'change' },
    { min: 6, max: 20, message: '长度在6到20个字符', trigger: 'change' }
  ],
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


// 提交表单
const submitForm = () => {
  console.log(formRef.value)
  formRef.value?.validate((valid) => {
    if (valid) {
      $request.post('/user/add', studentForm).then(res => {
        if (res.data.code === '200') {
          ElMessage.success('添加成功')
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
