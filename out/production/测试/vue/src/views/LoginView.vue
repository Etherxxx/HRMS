<template>
  <div class="wrapper">
    <div
        style="margin: 150px auto; background-color: rgba(255, 255, 255, 0.7); width:350px; height:300px;padding: 20px; border-radius:10px;box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">
      <div style="height: 60px; line-height: 60px; text-align: center">
        <img
            src="../assets/logo.png"
            alt=""
            style="width: 20px; position: relative; top: 5px;"
        />
        <b style="color: #303133; font-weight: bold">
          人力资源管理系统
        </b>
      </div>
      <el-form :rules="rules" :model="admin" ref="loginForm">
        <el-form-item prop="code">
          <el-input size="medium" placeholder="请输入账号" style="margin: 10px 0" prefix-icon="el-icon-user"
                    v-model.trim="admin.name"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" placeholder="请输入密码" style="margin: 10px 0" prefix-icon="el-icon-lock"
                    show-password
                    v-model.trim="admin.password"></el-input>
        </el-form-item>
<!--        <el-form-item>-->
<!--          <div style="display: flex;justify-content: center">-->
<!--            <el-input v-model="admin.verCode" style="width: 50%;margin-right: 10px" placeholder="请输入验证码"></el-input>-->
<!--            <img :src="captchaUrl" width="140px" height="33px"/>-->
<!--          </div>-->
<!--        </el-form-item>-->
        <div style="margin: 20px 0; text-align: center">
          <el-form-item>
            <el-button type="primary" size="medium" style="width:100%" @click="login()">登 录</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>
<script>

import request from "@/utils/request";

export default {
  name: 'Login',
  data() {
    return {
      admin: {},
      // captchaUrl:'http://localhost:8081/api/captcha'
    }
  },

  methods: {
    login()
    {
      request.post("/admin/login", this.admin).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '登录成功',
            type: 'success'
          });
          localStorage.setItem("user", JSON.stringify(res.data));
          this.$router.replace("/");
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      })
    }
  }
}
</script>
<style>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #ffffff, #3fa6fb);
  overflow: hidden;
}

</style>
