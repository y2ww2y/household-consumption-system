// 人员信息

<template>

    <el-button
      :disabled="addStatus"
      style="margin-top: 160px; margin-left: 200px"
      @click="addUser"
      >添加</el-button
    >
    <el-button v-if="!addStatus" style="margin-top: 160px;width:100px" @click="changePowerUp" plain>转让权限</el-button>

    <!-- 添加用户 -->
    <teleport to="body">
      <div v-if="isShowa" class="mask2">
        <div class="modify">
          <h3>添加用户</h3>
          <el-form label-width="80px" style="max-width: 460px">
            <el-form-item label="账户">
              <el-input v-model="userMsg.name" style="width: 200px" />
            </el-form-item>
            <el-form-item label="密码">
              <el-input
                v-model="userMsg.password"
                show-password
                style="width: 200px"
              />
            </el-form-item>
          </el-form>
          <h4>输入新的用户和密码</h4>
          <el-button @click="addOneUser">确定</el-button>
          <el-button @click="isShowa = false">取消</el-button>
        </div>
      </div>
    </teleport>
    <!-- 转让权限 -->
    <teleport to="body">
      <div v-if="isShowd" class="mask2">
        <div class="modify">
          <h3>转让权限</h3>
          <el-select v-model="value" placeholder="Select" size="small">
            <el-option
              v-for="item in options"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
          <h4>请选择要转让的用户名称</h4>
          <el-button @click="changePower">确定</el-button>
          <el-button @click="isShowd = false">取消</el-button>
        </div>
      </div>
    </teleport>

    <!-- 修改账户 -->
    <teleport to="body">
      <div v-if="isShowm" class="mask2">
        <div class="modify">
          <h3>修改账户</h3>
          <el-form label-width="80px" style="max-width: 460px">
            <el-form-item label="账户">
              <el-input v-model="userMsg.name" style="width: 200px" :placeholder="userMsg.name"/>
            </el-form-item>
            <el-form-item label="密码">
              <el-input
                v-model="userMsg.password"
                show-password
                style="width: 200px"
              />
            </el-form-item>
          </el-form>
          <h4>请输入修改的用户名或密码</h4>
          <el-button @click="changeDetail">确定</el-button>
          <el-button @click="isShowm = false">取消</el-button>
        </div>
      </div>
    </teleport>
    <!-- 显示表格 -->
    <el-table :data="tableData" table-layout="auto"  empty-text="暂无数据">
      <el-table-column type="index" label="序号"/>
      <el-table-column property="name" label="用户名" width="120" />
      <el-table-column property="role" label="身份"/>

      <el-table-column width="200px">
        <template #default="scope">
          <el-button-group>
            <el-button
              @click="deleteUser(scope.row)"
              type="danger"
              :disabled="scope.row.power"
              >注销</el-button
            >
            <el-button
              @click="changeUser(scope.row)"
              :disabled="scope.row.power"
              >修改</el-button
            >
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
  
</template>

<script setup>
import { nextTick, onMounted, reactive, ref, toRefs } from "vue";
import {
  Timer,
  Eleme,
  Document,
  Menu as IconMenu,
  Location,
  Setting,
  Search,
  Pointer,
} from "@element-plus/icons-vue"; //小图例
import { ElTable } from "element-plus";
import axios from "axios";
import {useRouter} from "vue-router"

axios.defaults.headers.post["Content-Type"] = "application/json";

const value = ref("");//转让权限选择框的值
const options = ref([]);//选择框内容列表
const addStatus = ref(true);//添加按钮状态，用户是管理员才可添加
const tableData = ref([]);//展示数据的载体
const isShowa = ref(false); //显示添加弹窗
const isShowd = ref(false); //显示更改权限弹窗
const isShowm = ref(false); //显示修改弹窗
let adminName = ref(""); //管理员姓名
const userMsg = reactive({ //修改账户用的信息
  name: "",
  password: "",
  id:"",
});
onMounted(() => {
  //挂载前执行
  select();
});

// 查询数据
function select() {
  axios.get("/api/selectUser").then(
    //获取选项
    (res) => {
      tableData.value = res.data;
      options.value = [];
      tableData.value.forEach((e) => {
        e.role = e.role == 0 ? "普通成员" : "管理员";
        if (e.role == "管理员") adminName.value = e.name;
        else options.value.push(e.name);
        if (e.role == "普通成员" && e.name == sessionStorage.getItem("login")) {
          e.power = false;
        } else {
          e.power = true;
        }
      });
      options.value.push("");
      if (sessionStorage.getItem("login") == adminName.value) {
        tableData.value.forEach((e) => (e.power = false));
        addStatus.value = false;
      }
    },
    (err) => {
      console.log(err.message);
    }
  );
}

//添加用户的弹窗
function addUser() {
  userMsg.name = "";
  userMsg.password = "";
  isShowa.value = true;
}

// 发送添加请求
function addOneUser() {
  if (userMsg.name == "") {
    alert("请输入账号");
  } else if (userMsg.password == "") {
    alert("请输入密码");
  } else {
    axios.post("/api/addUser", JSON.stringify(userMsg)).then(
      (res) => {
        if (res.data == "添加成功") {
          select();
        }
        alert(res.data);
        isShowa.value = false;
      },
      (err) => {
        console.log(err.message);
      }
    );
  }
}

function changePowerUp(){
  if(confirm("是否转让管理权限")){
    isShowd.value = true;
  }
}
// 删除用户
function deleteUser(user) {
  value.value = "";
  let target = event.target; //button不失焦的处理
  if (target.nodeName == "BUTTON" || target.nodeName == "SPAN") {
    target.parentNode.blur();
  }
  target.blur();
  if (user.name == adminName.value) {
    alert("你是管理员！将管理权限转给别人之后才能注销");
    if (confirm("确认转让自己的管理权限吗")) {
      isShowd.value = true;
    }
  } else if (confirm("确认注销该账号吗，删除后相关的消费记录将一并删除")) {
    axios.delete(`/api/deleteUser/${user.id}`).then(
      (res) => {
        if (res.data == "注销成功") {
          select();
        
        }
        alert(res.data);
      },
      (err) => {
        console.log(err.message);
      }
    );
  }
}

// 更改权限
function changePower() {
  const lastAdmin = adminName.value;
  const newAdmin = value.value;
  console.log(lastAdmin, newAdmin);
  axios.get(`/api/changeRole/${lastAdmin}/${newAdmin}`).then(
    (res) => {
      if (res.data == "权限转让成功") {
        select();
      }
      alert(res.data);
      isShowd.value = false;
    },
    (err) => {
      console.log(err.message);
    }
  );
}

//修改弹窗
function changeUser(user){
  userMsg.name = user.name;
  userMsg.password = "";
  userMsg.id=user.id;
  isShowm.value = true;
}

// 修改信息
function changeDetail(){
  if (userMsg.name == ""&&userMsg.password == "") {
    alert("请至少输入更改的一项");
  } else {
    axios.post("/api/modifyUser", JSON.stringify(userMsg)).then(
      (res) => {
        if (res.data == "修改成功") {
          select();
        }
        alert(res.data);
        isShowm.value = false;
      },
      (err) => {
        console.log(err.message);
      }
    );
  }
}
</script>

<style scoped>
.el-table {
  width: 50%;
  position: absolute;
  top: 280px;
  left: 400px;
}
.modify {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  width: 300px;
  height: 250px;
  background-color: skyblue;
}

/* 遮罩层 */
.mask2 {
  /* position: absolute;
    top:0;bottom:0;left:0;right:0;
    background-color: rgba(0, 0, 0, 0.7); */
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  overflow: hidden;
  outline: 0;
  -webkit-overflow-scrolling: touch;
  filter: alpha(opacity=60);
  background-color: rgba(0, 0, 0, 0.1);
  z-index: 100;
}
</style>