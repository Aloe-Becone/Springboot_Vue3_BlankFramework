import {createRouter, createWebHistory} from "vue-router";

// 主界面
import login from '../views/login.vue';
import index from '../views/index.vue';
import admin from '../views/admin.vue';

// 用户界面
import notice from '../views/user/notice.vue';
import comment from '../views/user/comment.vue';
import myInfo from '../views/user/myInfo.vue';
import activity from '../views/user/activity.vue';
import myActivity from '../views/user/myActivity.vue';
import myApply from '../views/user/myApply.vue';

// 管理界面
import userAdmin from '../views/admin/userAdmin.vue';
import postAdmin from '../views/admin/postAdmin.vue';
import activityAdmin from '../views/admin/activityAdmin.vue';
import noticeAdmin from '../views/admin/noticeAdmin.vue';

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
    redirect: '/index/notice',
    meta: { requiredRole: 'USER' },
    children:
    [
      {
        path: '/index/notice',
        component: notice,
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
      {
        path: '/index/activity',
        component: activity,
        meta: { requiredRole: 'USER' },
      },
      {
        path: '/index/myActivity',
        component: myActivity,
        meta: { requiredRole: 'USER' },
      },
      {
        path: '/index/myApply',
        component: myApply,
        meta: { requiredRole: 'USER' },
      },
	],
  },
  {
    path: '/admin',
    component: admin,
    redirect: '/admin/userAdmin',
    meta: { requiredRole: 'ADMIN' },
    children:
        [
          {
            path: '/admin/userAdmin',
            component: userAdmin,
            meta: { requiredRole: 'ADMIN' },
          },
          {
            path: '/admin/noticeAdmin',
            component: noticeAdmin,
            meta: { requiredRole: 'ADMIN' },
          },
          {
            path: '/admin/activityAdmin',
            component: activityAdmin,
            meta: { requiredRole: 'ADMIN' },
          },
          {
            path: '/admin/postAdmin',
            component: postAdmin,
            meta: { requiredRole: 'ADMIN' },
          },
          {
            path: '/admin/myInfo',
            component: myInfo,
            meta: { requiredRole: 'ADMIN' },
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