import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import AdminView from "@/views/AdminView.vue";
import Layout from "@/views/Layout.vue";
import AuditView from "@/views/AuditView.vue";
import LogView from "@/views/LogView.vue";
import NoticeView from "@/views/NoticeView.vue";
import DeptView from "@/views/DeptView.vue";
import RoleView from "@/views/RoleView.vue";
import SignView from "@/views/SignView.vue";
import SalaryView from "@/views/SalaryView.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    component: LoginView
  },
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    children:[
      {
        path: '',
        name: 'home',
        component: HomeView
      },
      {
        path: 'admin',
        component: AdminView
      },
      {
        path: 'audit',
        component: AuditView
      },
      {
        path: 'log',
        component: LogView
      },
      {
        path: 'notice',
        component: NoticeView
      },
      {
        path: 'dept',
        component: DeptView
      },
      {
        path: 'role',
        component: RoleView
      },
      {
        path: 'sign',
        component: SignView
      },
      {
        path: 'salary',
        component: SalaryView
      },
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//路由守卫
router.beforeEach((to, from, next) => {
  const user=localStorage.getItem("user");
  if(!user && to.path !=='/login'){
    return next("/login");
  }
  //用户信息存在，继续路由跳转
  next();

})


export default router
