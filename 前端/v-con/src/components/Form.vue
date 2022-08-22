// Form 组件,提供消息记录添加修改的表格
<template>
  <el-form
    ref="ruleFormRef"
    :model="ruleForm"
    label-width="150px"
    class="demo-ruleForm"
    :size="formSize"
    status-icon
  >
    <el-form-item label="消费金额" prop="money">
      <el-input v-model="ruleForm.money" />
    </el-form-item>
    <!-- required -->
    <el-form-item label="消费时间">
      <el-col :span="11">
        <el-form-item prop="date1">
          <el-date-picker
            v-model="ruleForm.date1"
            value-format="YYYY-MM-DD"
            type="date"
            label="Pick a date"
            placeholder="Pick a date"
            style="width: 100%"
          />
        </el-form-item>
      </el-col>
      <el-col class="text-center" :span="2">
        <span class="text-gray-500">-</span>
      </el-col>
      <el-col :span="11">
        <el-form-item prop="date2">
          <el-time-picker
            value-format="HH:mm:ss"
            v-model="ruleForm.date2"
            label="Pick a time"
            placeholder="Pick a time"
            style="width: 100%"
          />
        </el-form-item>
      </el-col>
    </el-form-item>
    <el-form-item label="消费类型" prop="type">
      <el-radio-group v-model="ruleForm.type">
        <el-radio label="非固定开支" name="type" />
        <el-radio label="衣食住行" name="type" />
        <el-radio label="水电煤气" name="type" />
      </el-radio-group>
    </el-form-item>
    <el-form-item label="消费详情" prop="descr">
      <el-input v-model="ruleForm.descr" type="textarea" />
    </el-form-item>
  </el-form>

</template>

<script setup>
import { reactive, ref } from 'vue'
const formSize = ref('default')

const ruleForm = reactive({
  money: null,
  // region:'',
  // money: '',
  date1: '',
  date2: '',
  // delivery: false,
  type: '',
  // resource: '',
  descr: '',
})//接收表单数据
defineExpose({
  ruleForm
});
</script>
