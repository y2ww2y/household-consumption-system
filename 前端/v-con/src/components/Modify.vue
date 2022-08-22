// 修改组件
<template>
  <div>
    <teleport to="body">
      <div v-if="isShow" class="mask2">
        <div class="modify">
          <h3>修改</h3>
          <Form ref="form" style="margin-right:50px"/>
          <el-button @click="$emit('finishsuc',form.ruleForm,'')">确定</el-button>
          <el-button @click="$emit('turnDown')">取消</el-button>
        </div>
      </div>
    </teleport>
  </div>
</template>

<script>
import { ref } from "vue";
import Form from "./Form";
export default {
  name: "Modify",
  components:{Form},
  props:['isShow',],
  emits:['turnDown','finishsuc'],
  setup() {
    let data=ref();
    const form=ref(null) //获取Form dom的方法
    return { data,form };
  },
};
</script>

<style scoped>
.modify {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
   width:500px;
  height:  350px;
  background-color: skyblue;
}

/* 遮罩层 */
.mask2{
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