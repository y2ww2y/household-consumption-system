
//  系统主要页面
 
 <template>
  <div class="common-layout" style="height: 100%">
    <el-container style="height: 100%">
      <!-- 最上边 -->
      <el-header height="7%">
        <span class="title">家庭消费记录系统</span>
        <el-dropdown
          style="width: 10%; background-color: aquamarine; left: 90%; top: 20px"
          size="large"
        >
          <span class="el-dropdown-link">
            用户:{{ loginName }}
            <el-icon class="el-icon--right">
              <arrow-down />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="quit">注销登录</el-dropdown-item>
              <el-dropdown-item disabled divided>待开发</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <el-container style="height: 100%">
        <!--  侧边框 -->
        <el-aside width="200px">
          <el-row class="tac">
            <el-col>
              <el-menu
                class="el-menu-vertical-demo"
                :router="true"
                :default-active="activeIndex"
                width="100%"
              >
                <!-- <el-menu-item  @click="mainTable"> -->
                <el-menu-item index="/user/table">
                  <el-icon><location /></el-icon>
                  <span> 消息记录 </span>
                </el-menu-item>
                <!-- <el-menu-item  @click="census"> -->
                <el-menu-item index="/user/census">
                  <el-icon><icon-menu /></el-icon>
                  <span>统计数据</span>
                </el-menu-item>
                <!-- <el-menu-item index="3" disabled>
                  <el-icon><document /></el-icon>
                  <span>Navigator Three</span>
                </el-menu-item> -->
                <!-- <el-menu-item  @click="userList"> -->
                <el-menu-item index="/user/list">
                  <el-icon><setting /></el-icon>
                  <span>人员列表</span>
                </el-menu-item>
              </el-menu>
            </el-col>
          </el-row>
        </el-aside>
        <!--  end -->

        <!-- 主要内容 -->
        <el-main>
          <router-view />
        </el-main>
      </el-container>
      <!-- 最下面 -->
      <el-footer height="5%"
        ><span id="help"
          ><a :href="address" target="_blank">gitee地址</a
          ></span
        ></el-footer
      >
    </el-container>
  </div>
</template>


<script setup >
import { inject, onBeforeMount, onMounted, reactive, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { ArrowDown } from "@element-plus/icons-vue";

import {
  Timer,
  Eleme,
  Document,
  Menu as IconMenu,
  Location,
  Setting,
  Search,
} from "@element-plus/icons-vue";
import { ElTable, ElMessageBox } from "element-plus";
import axios from "axios";

let activeIndex = ref("/user/table");
const router = useRouter();
const loginName = ref(""); //当前登录用户
// 下面几个变量本来想做一个用户切换懒得做又舍不得删
const userList = ref([]);  //用户信息列表
const userNameList = ref([]);//用户姓名表
const adminName = ref("");//管理姓名
const selectUserList = ref([]);//选择姓名表 
const address=ref("https://gitee.com/y2w/household-consumption-system.git")

/** 
 之前做的用于侧边框的路由跳转，后面发现<el-menu>有现成的属性:router="true",设置index就行
*function mainTable() {
*  router.push("/user/table");
*}
*function census() {
*  router.push("/user/census");
*}
*function userList() {
*  router.push("/user/list");
*}
*/

//之前做的验证登录
// onBeforeMount(() => {
//   if (!sessionStorage.getItem("login")) {
//     router.push("/");
//   }
// });

onMounted(() => {
  // 发现进来后登录页面的图还在，就搜了一个解决方法，在路径后面会有一个#reloaded,不影响运行，验证登录改成路由守卫后没有这种问题了
  // if (window.location.href.indexOf("#reloaded") == -1) {
  //   window.location.href = window.location.href + "#reloaded";
  //   window.location.reload();
  // }
  activeIndex.value = router.currentRoute.value.path;//动态获取当前相对路径，给侧边框提供默认选中框，不然在原本页面下刷新会丢失高亮选中
  selectUser();//挂载前执行一次，获取一些数据
});
function selectUser() {
  userNameList.value = [];
  selectUserList.value = [];
  loginName.value = sessionStorage.getItem("login");
  axios.get("/api/selectUser").then(
    //获取选项
    (res) => {
      userList.value = res.data;
      userList.value.forEach((e) => {
        if (e.role == 1) {
          adminName.value = e.name;
        }
        if (e.name != loginName) {
          selectUserList.value.push(e.name);
        }
      });
    },
    (err) => {
      console.log(err.message);
    }
  );
}

function quit() { 
  if (confirm("确认退出")) {
    sessionStorage.removeItem("login");//注销登录，删掉会话存储的信息。
    router.push("/");
  }
}
</script>



<style>
main,
aside {
  border-left: 1px solid rgb(110, 97, 97);
  background-color: rgb(250, 253, 232);
}
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 10px;
}

.grid-content {
  border-radius: 4px;
  min-height: 10px;
}

.block {
  padding: 10px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  flex: 1;
}
.block:last-child {
  border-right: none;
}
.el-button {
  width: 50px;
  height: 30px;
}
header,
footer {
  background-color: aquamarine;
}
.title {
  color: black;
  position: absolute;
  left: 40%;
  top: 8px;
  font-size: 25px;
  text-shadow: 0px 1px 0px #c0c0c0, 0px 2px 0px #b0b0b0, 0px 3px 0px #a0a0a0,
    0px 4px 0px #909090, 0px 5px 10px rgba(0, 0, 0, 0.9);
}

.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>

