// 登录页面

<template>
  <el-row type="flex" justify="center" id="bodylogin">
    <div class="login">
      <p class="denglu">家庭消费记录系统</p>
      <el-form label-width="80px" style="max-width: 460px">
        <el-form-item label="账户">
          <el-input
            v-model="userMsg.name"
            @keyup.enter="loginSystem"
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="密码">
          <el-input
            v-model="userMsg.passward"
            show-password
            @keyup.enter="loginSystem"
            style="width: 200px"
          />
        </el-form-item>
      </el-form>

      <el-button
        style="width: 200px"
        type="primary"
        plain
        @click="loginSystem"
        :loading-icon="Eleme"
        :loading="load"
        >登录</el-button
      >
    </div>
  </el-row>
</template>

<script setup>
import { nextTick, reactive, ref } from "vue";
import { Eleme } from "@element-plus/icons-vue";
import { ElTable,ElMessageBox } from "element-plus";
import axios from "axios";
import { onBeforeRouteLeave, useRoute, useRouter } from "vue-router";

let load = ref(false);//加载状态

const router = useRouter();
const userMsg = reactive({ //登录时输入框内容
  name: "",
  passward: "",
});

function loginSystem() {
  //登录
  let data = JSON.stringify(userMsg);
  load.value = true; //登录图标状态
  axios.defaults.headers.post["Content-Type"] = "application/json";
  axios.post("/api/login", data).then(
    (response) => {
      if (response.data == "登陆成功") {
        //后端返回信息
        sessionStorage.setItem("login", userMsg.name);//设置会话存储
        router.push("/user");
      } ElMessageBox.alert(response.data);
     
      load.value = false;
    },
    (error) => {
      console.log(error.Message);
      load.value = false;
    }
  );
}
</script>

<style>
.login {
  /* 背景图片 */

  background-image: url(../assets/login.jpg);
  background-size: 100% 100%;
  margin-top: 10rem;
  border-radius: 12px;
  /* 内边距 */
  padding: 20px;
  text-align: center;
  box-shadow: 0 8px 5px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

/* 登录文字 */
.denglu {
  color: black;
  font-size: 20px;
}

/* 背景图片 */
body {
  background-image: url(../assets/background.jpg);
  background-size: 100% 100%;
  background-attachment: fixed;
}

.el-form-item__label {
  color: black;
  font-size: 15px;
}
</style>

