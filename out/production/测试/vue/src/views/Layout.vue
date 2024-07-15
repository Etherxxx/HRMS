<template>
  <div>
    <el-container>
      <el-header style="background-color: #4c535a">
        <img alt="" src="@/assets/logo.png" style="width: 40px;position: relative;top: 10px">
        <span style="font-size: 20px;margin-left: 15px;color: white">人力资源管理系统</span>

        <el-dropdown style="float: right;height: 60px;line-height: 60px" trigger="click">
          <span class="el-dropdown-link" style="color: white;font-size: 15px">{{ user.name }}<i
              class="el-icon-arrow-down el-icon--right"></i></span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>
              <div @click="logout()">
                退出登录
              </div>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
    </el-container>
    <el-container>
      <el-aside style="overflow: hidden; min-height: 100vh; background-color:#545c64; width: 250px">
        <el-menu
            active-text-color="#ffd04b"
            background-color="#545c64"
            default-active="$router.path"
            router
            text-color="#fff">
          <el-menu-item index="/">
            <i class="el-icon-s-home"></i>
            <span slot="title">首页</span>
          </el-menu-item>
          <el-submenu index="1" v-if="user.role!='ROLE_EMPLOYEE'">
            <template slot="title">
              <i class="el-icon-s-cooperation"></i>
              <span>权限管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/role"><i class="el-icon-s-custom"></i>角色管理</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="2" v-if="user.role!='ROLE_EMPLOYEE'">
            <template slot="title">
              <i class="el-icon-s-management"></i>
              <span>人员管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/admin"><i class="el-icon-user"></i>用户信息</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <el-menu-item index="/dept" v-if="user.role==='ROLE_ADMIN'"><i class="el-icon-s-operation"></i>部门信息</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-edit"></i>
              <span>考勤管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/sign"><i class="el-icon-date"></i>考勤表现</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-edit"></i>
              <span>薪资管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/salary"><i class="el-icon-date"></i>员工薪资</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="5">
            <template slot="title">
              <i class="el-icon-s-check"></i>
              <span>系统管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/audit"><i class="el-icon-suitcase"></i>请假审核</el-menu-item>
              <el-menu-item index="/notice"><i class="el-icon-data-analysis"></i>系统公告</el-menu-item>
              <el-menu-item index="/log" v-if="user.role==='ROLE_ADMIN'"><i class="el-icon-edit-outline"></i>日志管理</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </div>
</template>

<style>
.el-menu {
  border-right: none !important;
}
</style>

<script>
export default {
  name: "Layout",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  methods:{
    logout(){
      localStorage.removeItem("user");
      this.$router.replace("/login");
    }
  }
}
</script>
