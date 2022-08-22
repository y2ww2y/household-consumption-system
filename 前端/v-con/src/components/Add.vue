// 添加组件
<template>
  <div>
    <teleport to="body">
      <div v-if="isShow" class="mask">
        <div class="add">
          <h3>添加</h3>
          <Form ref="form" style="margin-right:50px"/> 
          <el-button @click="$emit('finishsuc',form.ruleForm)">确定</el-button>
          <el-button @click="$emit('turnDown')">取消</el-button>
        </div>
      </div>
    </teleport>
  </div>
</template>

<script>
import { reactive, ref,} from "vue";
import Form from "./Form";
export default {
  name: "Add",
  components:{Form},
  props:['isShow',],
  emits:['finishsuc','turnDown'],
  setup() {
    const form=ref(null)//获得Form组件dom
    let data=ref(null);//本来用来接收Form的数据，发现可以直接传
   

    return {data,form};
  },
};
</script>

<style>
.add {
  position: absolute;
  top:50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  width:500px;
  height: 350px;
  background-color: skyblue;
}

/* 遮罩层 */
.mask {
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
  background-color: rgba(0, 0, 0, 0.7);
  z-index: 100;
}
</style>