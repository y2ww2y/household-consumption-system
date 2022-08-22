import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/theme-chalk/index.css';
import App from './App.vue'
import router from './router'
import *  as echarts from 'echarts'  //官方库
import  ECharts  from 'vue-echarts'; //扩展库


const app=createApp(App)
app.config.globalProperties.$echarts=echarts
app.use(ElementPlus,{ size: 'small', zIndex: 3000 })
app.use(router)
app.component('ECharts',ECharts)

app.mount('#app')
