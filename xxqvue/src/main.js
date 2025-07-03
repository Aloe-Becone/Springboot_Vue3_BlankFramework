import ElementPlus from 'element-plus'
import VueRouter from './router/router.js'
import axios from 'axios';
import App from './App.vue'
import { createApp } from 'vue'

import './assets/main.css'
import 'element-plus/dist/index.css'
import router from "./router/router.js";

// 使用 createApp 方法创建 Vue 应用实例
const app = createApp(App);

// 后台服务器ip
const serverURL = 'http://localhost:9099/';

// 获取缓存的用户信息
const user = JSON.parse(localStorage.getItem("user") || '{}')

// 创建axios实例
const axiosInstance = axios.create({
    // 设置axios的基地址
    baseURL: serverURL,
    // 设置超时
    timeout: 1000,
});

axiosInstance.interceptors.request.use(
    (config) => {
        // 1. 白名单路径
        const allowUrls = ['/login', '/reg', ];
        const isAllowUrl = allowUrls.some(url => config.url.includes(url));

        // 2. 如果是白名单路径，直接放行
        if (isAllowUrl) {
            return config;
        }

        // 3. 非白名单路径需要检查 Token
        const token = localStorage.getItem('token') || user.token; // 优先从本地存储获取
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        } else {
            // 4. 无 Token 时跳转登录页（非白名单路径）
            router.replace('/login').then(r => {
                console.log("无效的token！")
            });

        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// 添加响应拦截器，用于处理token过期等情况
axiosInstance.interceptors.response.use(
    (response) => {
        // 响应正常
        return response
    },
    (error) => {
        // 响应错误
        if (error.response && error.response.status === 401) {
            // 如果返回401未授权，可能是token过期，跳转到登录页
            router.replace('/login').then(r => {
                console.log("无效的token！")
            });
        }
        return Promise.reject(error)
    }
)

// 在Vue3中使用provide()进行挂载全局属性，并在子组件中使用inject()方法注入
// 取代app.config.globalProperties方法(适用于Vue2子组件中使用this获取)

// 提供 axios 实例:
app.provide('$request', axiosInstance);

// 提供后端服务器URL
app.provide('$serverURL', serverURL);

// 提供当前用户
app.provide('$user', user);


// 使用 ElementPlus
app.use(ElementPlus, {
    theme: 'dark' // 设置为暗模式
})

// 使用 Vue Router
app.use(VueRouter);


// 挂载 Vue 应用实例到 DOM
app.mount('#app');