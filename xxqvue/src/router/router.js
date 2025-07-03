import {createRouter, createWebHistory} from "vue-router";

import login from '../views/login.vue';
import index from '../views/index.vue';
import view1 from '../views/user/view1.vue';
import comment from '../views/user/comment.vue';
import myInfo from '../views/user/myInfo.vue';


// 创建路由对象
const routes =
[
  {
    path: '/', // URL路径
    redirect: '/login',
  },
  {
    path: '/login',
    component: login,
  },
  {
    path: '/index',
    component: index,
    redirect: '/index/view1',
    meta: { requiredRole: 'USER' },
    children:
    [
      {
        path: '/index/view1',
        component: view1,
        meta: { requiredRole: 'USER' },
      },
      {
        path: '/index/comment',
        component: comment,
        meta: { requiredRole: 'USER' },
      },
      {
        path: '/index/myInfo',
        component: myInfo,
        meta: { requiredRole: 'USER' },
      },
	],
  },
];

// 创建VueRouter实例
const router = createRouter({
  history: createWebHistory(), // 使用history模式
  routes
});

router.beforeEach((to, from, next) => {
  // 1. 同步获取用户信息
  const user = JSON.parse(localStorage.getItem('user') || '{}');
  const userRole = user.role;

  // 2. 公共路由放行
  if (!to.meta.requiredRole) {
    next();
    return; // 明确终止执行
  }

  // 3. 角色验证
  if (userRole === to.meta.requiredRole) {
    next();
  } else {
    next('/login');
  }
});

// 导出router实例，以便在main.js中使用
export default router;