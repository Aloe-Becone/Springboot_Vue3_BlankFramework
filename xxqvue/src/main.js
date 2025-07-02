import './assets/main.css'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import axios from 'axios';
import { createApp } from 'vue'
import App from './App.vue'
import VueRouter from './router/router.js'


// 使用 createApp 方法创建 Vue 应用实例
const app = createApp(App);

// 后台服务器ip
const serverURL = 'http://localhost:9099/';

// 创建axios实例
const axiosInstance = axios.create({
    // 设置axios的基地址
    baseURL: serverURL,
    // 设置超时
    timeout: 1000,
});

// 提供 axios 实例:
// 在Vue3中使用provide()进行挂载全局属性，并在子组件中使用inject()方法注入
// 取代app.config.globalProperties方法(适用于Vue2子组件中使用this获取)
app.provide('$request', axiosInstance);

// 使用 ElementPlus
app.use(ElementPlus, {
    theme: 'dark' // 设置为暗模式
})

// 使用 Vue Router
app.use(VueRouter);

// 挂载 Vue 应用实例到 DOM
app.mount('#app');

