import {createRouter, createWebHistory} from "vue-router";

import index from '../views/index.vue';
import view1 from '../views/view1.vue';
import view2 from '../views/view2.vue';
import view3 from '../views/view3.vue';


// 创建路由对象
const routes = [
  {
    path: '/', // URL路径
      redirect: '/wzb/index',
  },
  {
    path: '/wzb/index',
    component: index,
    meta: { title: '首页' }, // 设置标题

    // children: [
    // {
    //   path: '/Guestbook/NULL',
    //   component: NULL,
	// },
	// ],
  },
  {
    path: '/wzb/view1',
    component: view1,
    meta: { title: 'view1' },
  },
  {
    path: '/wzb/view2',
    component: view2,
    meta: { title: 'view2' },
  },
  {
    path: '/wzb/view3',
    component: view3,
    meta: { title: 'view3' },
  },
];

// 创建VueRouter实例
const router = createRouter({
  history: createWebHistory(), // 使用history模式
  routes
});

// 导出router实例，以便在main.js中使用
export default router;