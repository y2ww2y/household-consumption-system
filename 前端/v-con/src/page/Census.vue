// 统计页面

<template>
  <h3>统计模块</h3>
  <el-select v-model="year" filterable placeholder="年份">
    <el-option
      v-for="item in getYears"
      :key="item"
      :label="item"
      :value="item"
    /> </el-select
  >年
  <el-select v-model="month" filterable placeholder="月份">
    <el-option
      v-for="item in getMonths"
      :key="item"
      :label="item"
      :value="item"
    /> </el-select
  >月
  <el-button @click="select">查询</el-button>
  <e-charts class="chart" :option="option" id="myChart" />
</template>

<script setup>
import { onMounted, ref, reactive, getCurrentInstance } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const router = useRouter();
const { proxy } = getCurrentInstance(); //相当于vue2的this,后面判断echarts实例
const objs = ref([]); //后面要设置为空数组，reactive不好操作，names同理，临时中间变量
const names = ref([]);
let myChart;//chart实例名
const getYears = ref([""]);//选择框选择年份
const getMonths = ref([ //选择框月份
  "",
  "1",
  "2",
  "3",
  "4",
  "5",
  "6",
  "7",
  "8",
  "9",
  "10",
  "11",
  "12",
]);
const year = ref(new Date().getFullYear()); //选中月份，默认本年

const month = ref("");//选中月份
const option = ref({ //echarts所需要的变量
  title: { left: "center", top: 10 },
  legend: { left: "right", orient: "vertical" },
  tooltip: {},
  dataset: {
    dimensions: ["product", "非固定开支", "衣食住行", "水电煤气"], //维度，对应横坐标
    source: [],
  },
  xAxis: { type: "category" },
  yAxis: {},
  series: [{ type: "bar" }, { type: "bar" }, { type: "bar" }],
});

onMounted(() => {
  myChart = proxy.$echarts.getInstanceByDom(document.getElementById("myChart")); //获取chart实例，vue-echarts会自动init,这里主要是为了获取后设置窗口大小自适应

  axios
    .get("/api/getYears")
    .then((result) => {
      getYears.value = result.data;
    })
    .catch((err) => {
      console.log(err.message);
    });
  select();
  // 让图表自适应窗口变化
  window.onresize = function () {
    if (router.currentRoute.value.path=="/user/census") { //切换时组件被销毁不设置条件函数仍然触发，但chart实例已经没有了
      return myChart.resize();
    }
  };
});

// 查询信息
function select() {
  myChart.showLoading();
  if (month.value == "") {
    selectCensus();
  } else {
    selectCensusByMonth();
  }
}

//按月查询
function selectCensusByMonth() {
  axios
    .get(`/api/census/${year.value}/${month.value}`)
    .then((result) => {
      myChart.hideLoading();
      objs.value = [];
      names.value = [];
      result.data.forEach((element) => {
        const obj = { product: "", 非固定开支: "", 衣食住行: "", 水电煤气: "" };
        if (names.value.indexOf(element.name) == -1) {
          names.value.push(element.name);
          obj.product = element.name;
          obj[element.type] = element.ms;
          objs.value.push(obj);
        } else {
          objs.value.forEach((o) => {
            if (o.product == element.name) {
              o[element.type] = element.ms;
            }
          });
        }
      });
      option.value.dataset.source = objs.value;
      option.value.title.text = `${year.value}年${month.value}月消费统计`;
    })
    .catch((err) => {
      console.log(err.message);
    });
}

//按年查询
function selectCensus() {
  axios
    .get(`/api/census/${year.value}`)
    .then((result) => {
      myChart.hideLoading();
      console.log(result);
      objs.value = [];
      names.value = [];
      result.data.forEach((element) => {
        const obj = { product: "", 非固定开支: "", 衣食住行: "", 水电煤气: "" };
        if (names.value.indexOf(element.name) == -1) {
          names.value.push(element.name);
          obj.product = element.name;
          obj[element.type] = element.ms;
          objs.value.push(obj);
        } else {
          objs.value.forEach((o) => {
            if (o.product == element.name) {
              o[element.type] = element.ms;
            }
          });
        }
      });
      option.value.dataset.source = objs.value;
      option.value.title.text = `${year.value}年消费统计`;
    })
    .catch((err) => {
      console.log(err.message);
    });
}
</script>

<style scoped>
.chart {
  width: 100%;
  height: 400px;
}
</style>