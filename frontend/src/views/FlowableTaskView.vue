<template>
  <div class="task">
    <h1>流程任务管理</h1>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="name"
        label="任务名称"
        width="180">
      </el-table-column>
      <el-table-column
          prop="assignee"
          label="任务分配人"
          width="180">
      </el-table-column>
      <!-- 点击进行路由跳转 -->
      <el-table-column
        prop="id"
        label="任务处理"
        width="180">
        <template slot-scope="scope">
          <router-link :to="`${scope.row.formName.replace(':id','1')}`">{{ scope.row.formName }}</router-link>
        </template>
      </el-table-column>
      <!-- 任务完成按钮 -->
      <el-table-column
        prop="id"
        label="任务完成"
        width="180">
        <template slot-scope="scope">
          <el-button type="primary" @click="completeTask(scope.row.id)">完成</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData:[
      ]
    }
  },
  mounted() {
    this.$http.post('/bpmn/query', {"assignee": "user2"}).then(res => {
      console.log(res)
      this.tableData = res.data
    })
  },
  methods: {
    completeTask(id) {
      this.$http.post('/bpmn/complete', {"taskId": id}).then(res => {
        console.log(res)
        this.$message({
          message: '任务完成',
          type: 'success'
        })
        // 刷新页面
        location.reload()
      })
    }
  }
}
</script>

<style>
.task {
  margin-top: 20px;
}
h1 {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 20px;
  color: black;
}
</style>