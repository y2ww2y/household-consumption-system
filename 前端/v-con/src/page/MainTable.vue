// 主体展示部分

<template>
  <el-row :gutter="10">
    <el-col :span="6"
      ><div class="grid-content ep-bg-purple" />
      <!-- 消费者 -->
      <el-select v-model="userValue" filterable placeholder="消费者">
        <el-option
          v-for="item in userOptions"
          :key="item.id"
          :label="item.name"
          :value="item.name"
        />
      </el-select>
    </el-col>
    <el-col :span="6"
      ><div class="grid-content ep-bg-purple" />
      <!-- 消费类型 -->
      <el-select v-model="typeValue" filterable placeholder="消费类型">
        <el-option
          v-for="(item, index) in typeOptions"
          :key="index"
          :label="item"
          :value="item"
        /> </el-select
    ></el-col>
    <el-col :span="5">
      <!-- 时间段 -->
      <div class="block">
        <el-date-picker
          v-model="value2"
          format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="datetimerange"
          range-separator="To"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          :editable="false"
        />
      </div>
    </el-col>
    <el-col
      ><el-button
        :icon="Search"
        round
        style="color: blue"
        @click="select"
      />查询</el-col
    >
  </el-row>
  <el-button @click="turnUp1">添加</el-button>
  <el-button @click="deleteSelected" style="width: 100px" type="danger"
    >删除本页所选</el-button
  >
  <Add :isShow="isShowp1" @turnDown="turnShow1" @finishsuc="addf" />

  <!-- 显示表格 -->
  <el-table
    :data="tableData"
    style="width: 100%"
    @selection-change="handleSelectionChange"
    table-layout="auto"
    empty-text="暂无数据"
  >
    <el-table-column type="selection" width="55" />
    <el-table-column label="记录日期" width="150">
      <template #default="scope">{{
        formatDate(scope.row.bdate, "yyyy-MM-dd HH:mm:ss")
      }}</template>
    </el-table-column>
    <el-table-column property="name" label="消费人" width="120" />
    <el-table-column property="amout" label="消费金额" width="120" />
    <el-table-column property="type" label="消费类型" />
    <el-table-column label="详情">
      <template #default="scope">
        <el-popover effect="light" trigger="hover" placement="top" width="20px">
          <template #default>
            <div>desc: {{ scope.row.descr }}</div>
          </template>
          <template #reference>
            <el-tag
              ><el-icon><Pointer /></el-icon
            ></el-tag>
          </template>
        </el-popover>
      </template>
    </el-table-column>

    <el-table-column width="200px">
      <template #default="scope">
        <el-button-group>
          <el-button @click="deleteData(scope.row.bid)" type="danger"
            >删除</el-button
          >
          <el-button @click="turnUp(scope.row.bid)">修改</el-button>
          <Modify
            :isShow="isShowp"
            @turnDown="turnShow"
            @finishsuc="modifyData"
          />
        </el-button-group>
      </template>
      <!-- formatDate(scope.row.bdate, "yyyy-M-d H:m:s") -->
      <!-- <el-button type="danger" size="small" @click="deletegg(scope.$row)">删除</el-button> -->
    </el-table-column>
  </el-table>
  <!-- -webkit-center 将所有组件居中对齐  -->
<div style="text-align: -webkit-center">
  <el-pagination
    background
    layout="prev, pager, next"
    :total="count"
    :current-page="page"
    @current-change="handelChangePage"
    :page-size="5"
    style="width: max-content"
  />
  <!-- max-content 宽度最大内容 -->
  </div>
</template>

<script setup>
import { nextTick, onMounted, reactive, ref } from "vue";
import Add from "../components/Add";
import Modify from "../components/Modify";
import {
  Timer,
  Eleme,
  Document,
  Menu as IconMenu,
  Location,
  Setting,
  Search,
  Pointer,
} from "@element-plus/icons-vue";
import { ElTable } from "element-plus";
import axios from "axios";

axios.defaults.headers.post["Content-Type"] = "application/json";
axios.defaults.headers.put["Content-Type"] = "application/json";

let isShowp = ref(false); //显示修改弹窗
let isShowp1 = ref(false); //显示添加弹窗

let tableData = ref([]); //表格数据

let count = ref(10); //总行数

let page = ref(1); //当前页数
let id = ref(""); //要修改的id

let selection = ref([]);//存储复选框对应id

const userValue = ref(); //查询下拉列表姓名选择值
const typeValue = ref();//查询下拉列表消费类型选择值
const userOptions = ref([]);//姓名列表
const typeOptions = ref(["", "非固定开支", "衣食住行", "水电煤气"]); //类型写死了

// const value1 = ref([
//   formatDate(new Date(2000, 10, 10, 10, 10), "yyyy-MM-dd HH:mm:ss"),
//   formatDate(new Date(2099, 10, 11, 10, 10), "yyyy-MM-dd HH:mm:ss"),
// ]);
const value2 = ref([]); //时间数据

const factor = reactive({ //查询条件
  name: userValue,
  type: typeValue,
  beginDate: formatDate(new Date(2000, 10, 10, 10, 10), "yyyy-MM-dd HH:mm:ss"),
  endDate: formatDate(new Date(2099, 10, 11, 10, 10), "yyyy-MM-dd HH:mm:ss"),
  pages: page,
});

//修改弹窗开启
function turnUp(bid) {
  id.value = bid;
  isShowp.value = true;
}
//添加弹窗开启
function turnUp1() {
  isShowp1.value = true;
}
//修改自定义组件点击取消
function turnShow() {
  isShowp.value = false;
}
//添加自定义组件点击取消
function turnShow1() {
  isShowp1.value = false;
}

// 页码改变
function handelChangePage(currentPage) {
  page.value = currentPage;
  select();
}

// 选择复选框
function handleSelectionChange(v) {
  if (v.length > 0) {
    for (let i = 0; i < v.length; i++) {
      if (selection.value.indexOf(v[i].bid) == -1) {
        selection.value.push(v[i].bid);
      }
    }
  } else selection.value = [];
  console.log(selection.value);
}

// 根据条件查询
function select() {
  // 去不了焦
  if (value2.value != null && value2.value.length > 0) {
    factor.beginDate = value2.value[0];
    factor.endDate = value2.value[1];
  } else {
    factor.beginDate = formatDate(
      new Date(2000, 10, 10, 10, 10),
      "yyyy-MM-dd HH:mm:ss"
    );
    factor.endDate = formatDate(
      new Date(2099, 10, 11, 10, 10),
      "yyyy-MM-dd HH:mm:ss"
    );
  }
  let map = JSON.stringify(factor);
  axios.post("/api/select", map).then(
    (res) => {
      if (res.data != null) {
        tableData.value = res.data.list;
        count.value = res.data.count;
      }
    },
    (err) => {
      console.log(err.message);
    }
  );
}
// 增加记录
function addf(value) {
  const map = { ...value, name: sessionStorage.getItem("login") };
  console.log(map);
  axios.post("/api/addRecords", JSON.stringify(map)).then(
    (res) => {
      if (res.data == "添加成功") {
        page.value = 1;
        userValue.value = "";
        typeValue.value = "";
        value2.value = [];
        select();
        isShowp1.value = false;
      } else {
        alert(res.data);
      }
    },
    (err) => {
      console.log(err.message);
      alert("添加失败");
    }
  );
}

// 修改记录
function modifyData(value) {
  const map = {
    bid: id.value,
    ...value,
    name: sessionStorage.getItem("login"),
  }; // ...是将这个数据展开赋给map
  console.log(map);
  axios.put("/api/modifyRecords", JSON.stringify(map)).then(
    (res) => {
      if (res.data == "修改成功") {
        page.value = 1;
        userValue.value = "";
        typeValue.value = "";
        value2.value = [];
        select();
        isShowp.value = false;
      } else {
        alert(res.data);
      }
    },
    (err) => {
      console.log(err.message);
      alert("修改失败");
    }
  );
}

// 删除记录
function deleteData(bid) {
  let target = event.target; //button不失焦的处理
  if (target.nodeName == "BUTTON" || target.nodeName == "SPAN") {
    target.parentNode.blur();
  }
  target.blur();
  if (confirm("确定删除该数据?")) {
    axios.delete(`/api/deleteRecords/${bid}`).then(
      (res) => {
        if (res.data == "删除成功") {
          userValue.value = "";
          typeValue.value = "";
          value2.value = [];
          select();
        } else {
          alert(res.data);
        }
      },
      (err) => {
        console.log(err.message);
        alert("删除失败");
      }
    );
  }
}

// 删除已选记录
function deleteSelected() {
  let target = event.target; //button不失焦的处理
  if (target.nodeName == "BUTTON" || target.nodeName == "SPAN") {
    target.parentNode.blur();
  }
  target.blur();
  if (selection.value.length == 0) {
    alert("请勾选要删除的记录");
  } else {
    const map = { list: selection.value };
    console.log(JSON.stringify(map));
    axios.post("/api/deleteList", JSON.stringify(map)).then(
      (res) => {
        if (res.data == "删除成功") {
          userValue.value = "";
          typeValue.value = "";
          value2.value = [];
          select();
        } else {
          alert(res.data);
        }
      },
      (err) => {
        console.log(err.message);
        alert("删除失败");
      }
    );
  }
}

//日期格式化
function formatDate(value, args) {
  var dt = new Date(value);
  if (args == "yyyy-M-d") {
    // yyyy-M-d
    let year = dt.getFullYear();
    let month = dt.getMonth() + 1;
    let date = dt.getDate();
    return `${year}-${month}-${date}`;
  } else if (args == "yyyy-M-d H:m:s") {
    // yyyy-M-d H:m:s
    let year = dt.getFullYear();
    let month = dt.getMonth() + 1;
    let date = dt.getDate();
    let hour = dt.getHours();
    let minute = dt.getMinutes();
    let second = dt.getSeconds();
    return `${year}-${month}-${date} ${hour}:${minute}:${second}`;
  } else if (args == "yyyy-MM-dd") {
    // yyyy-MM-dd
    let year = dt.getFullYear();
    let month = (dt.getMonth() + 1).toString().padStart(2, "0");
    let date = dt.getDate().toString().padStart(2, "0");
    return `${year}-${month}-${date}`;
  } else {
    // yyyy-MM-dd HH:mm:ss
    let year = dt.getFullYear();
    let month = (dt.getMonth() + 1).toString().padStart(2, "0");
    let date = dt.getDate().toString().padStart(2, "0");
    let hour = dt.getHours().toString().padStart(2, "0");
    let minute = dt.getMinutes().toString().padStart(2, "0");
    let second = dt.getSeconds().toString().padStart(2, "0");
    return `${year}-${month}-${date} ${hour}:${minute}:${second}`;
  }
}

// #region
/**
 * reactive不能对数组或对象相应，即arr=reactive([])->arr=[数组、对象] X
 * 
 * 可用以下方法
 * 1.const state = reactive({
                arr: []
            });
    state.arr = [1, 2, 3]

    2.const state = ref([])
    state.value = [1, 2, 3]

    3.const arr = reactive([])
    arr.push(...[1, 2, 3])
 */
// #endregion

onMounted(() => {
  //挂载前执行
  axios.get("/api/selectUser").then(
    //获取选项
    (res) => {
      // console.log(res.data);
      userOptions.value = res.data;
      userOptions.value.unshift({ id: 0, name: "" });
    },
    (err) => {
      console.log(err.message);
    }
  );
  //进路由界面先刷新
  select();
});
</script>

<style>
.el-table {
  width: 100%;
}
.el-select .el-date-picker{
  border-style:1px solid black;
}
</style>