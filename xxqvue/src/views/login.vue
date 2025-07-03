<template>
  <el-tabs v-model="activeTab" stretch class="login_panel">
    <el-tab-pane label="登录" name="login">
      <el-card style="margin-top: 20px;">
        <el-form
            ref="loginFormRef"
            :model="loginForm"
            :rules="loginRules"
            label-width="auto"
            label-position="top"
        >
          <el-form-item label="用户名" prop="username">
            <el-input
                v-model="loginForm.username"
                placeholder="请输入用户名"
                clearable
            />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
                v-model="loginForm.password"
                placeholder="请输入密码"
                type="password"
                show-password
                clearable
            />
          </el-form-item>
          <el-form-item>
            <el-button
                type="primary"
                :loading="loginLoading"
                @click="submitLoginForm"
            >
              登录
            </el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-tab-pane>

    <el-tab-pane label="注册" name="register">
      <el-card style="margin-top: 20px;">
        <el-form
            ref="registerFormRef"
            :model="registerForm"
            :rules="registerRules"
            label-width="auto"
            label-position="top"
            status-icon
        >
          <el-form-item label="用户名" prop="username">
            <el-input
                v-model="registerForm.username"
                placeholder="请输入用户名"
                clearable
            />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
                v-model="registerForm.password"
                placeholder="请输入密码"
                type="password"
                show-password
                clearable
            />
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input
                v-model="registerForm.confirmPassword"
                placeholder="请再次输入密码"
                type="password"
                show-password
                clearable
            />
          </el-form-item>
          <el-form-item>
            <el-button
                type="primary"
                :loading="registerLoading"
                @click="submitRegisterForm"
            >
              注册
            </el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-tab-pane>
  </el-tabs>
</template>

<script setup>
import {ref, inject, reactive} from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()

const $request = inject('$request'),
      $user = inject('$user')

// 当前激活的标签页
const activeTab = ref('login')

// 登录表单数据
const loginForm = ref({
  username: '',
  password: '',
})

// 注册表单数据
const registerForm = ref({
  username: '',
  password: '',
  confirmPassword: ''
})

// 验证密码一致性函数
const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== registerForm.value.password) {
    callback(new Error('两次输入的密码不一致!'))
  } else {
    callback()
  }
}

// 登录表单验证规则
const loginRules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'change' },
    { min: 3, max: 16, message: '长度在3到16个字符', trigger: 'change' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'change' },
    { min: 6, max: 20, message: '长度在6到20个字符', trigger: 'change' }
  ]
})

// 注册表单验证规则
const registerRules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'change' },
    { min: 3, max: 16, message: '长度在3到16个字符', trigger: 'change' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'change' },
    { min: 6, max: 20, message: '长度在6到20个字符', trigger: 'change' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'change' },
    { validator: validatePassword, trigger: 'change' }
  ]
})


// 加载状态
const loginLoading = ref(false)
const registerLoading = ref(false)

// 表单引用
const loginFormRef = ref(null)
const registerFormRef = ref(null)

// 处理登录
const submitLoginForm = () => {
  console.log(loginForm.value)
  loginLoading.value = true

  $request.post('/user/login', {
    username: loginForm.value.username,
    password: loginForm.value.password,
  }).then(res => {
    if (res.data.code === '200') {
      // 登录成功，将用户信息保存到localStorage
      // 1. 保存用户信息并等待完成
      localStorage.setItem('user', JSON.stringify(res.data.data));

      // 2. 显示消息
      ElMessage({
        message: "登录成功",
        type: 'success',
      });

      console.log($user);

      // 3. 跳转
      if (res.data.data.role === 'ADMIN') {
        router.replace('/admin');
      } else {
        router.replace('/index');
      }
    } else {
      // 登录失败
      ElMessage({
        message: res.data.msg,
        type: 'error',
      })
    }
  })
  loginLoading.value = false
}

// 处理注册
const submitRegisterForm = () => {
  registerLoading.value = true

  $request.post('/user/reg', {
    username: registerForm.value.username,
    password: registerForm.value.password,
  }).then(res => {
    if (res.data.code === '200') {
      // 注册成功
      ElMessage({
        message: "注册成功",
        type: 'success',
      })
      console.log(res.data.data)
    }
    else {
      // 注册失败
      ElMessage({
        message: res.data.msg,
        type: 'error',
      })
    }
  })
  // 结束缓冲
  registerLoading.value = false
}
</script>

<style scoped>
.auth-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
  margin-top: 10px;
}

.login_panel{
  width: 30%;
  height: 60%;
  margin: 10% auto;
  background-color: #fff;
  border-radius: 5px;
}

.el-card {
  margin-top: 20px;
}
</style>