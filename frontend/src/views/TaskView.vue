<template>
  <div class="task">
    <h1>督察任务管理</h1>
    <el-table
      :data="tableData"
      style="width: 100%">
      <!-- 点击序号路由跳转至supervise页面 -->
      <el-table-column
        prop="taskId"
        label="序号"
        width="180">
        <template slot-scope="scope">
          <router-link :to="`/supervise/${scope.row.taskId}`">{{ scope.row.taskId }}</router-link>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        label="流程状态"
        width="180">
      </el-table-column>
      <el-table-column
        prop="currentNode"
        label="当前节点">
      </el-table-column>
      <el-table-column
        prop="supervise.company"
        label="建管单位">
      </el-table-column>
      <el-table-column
        prop="supervise.name"
        label="单项名称">
      </el-table-column>
      <el-table-column
        prop="supervise.construction"
        label="施工单位">
      </el-table-column>
      <el-table-column
        prop="supervision"
        label="监理单位">
      </el-table-column>
      <el-table-column
        prop="checkDate"
        label="检查日期">
      </el-table-column>
      <el-table-column
        prop="department"
        label="所属部门">
      </el-table-column>
      <el-table-column
        prop="supervise.leader"
        label="督导组组长">
      </el-table-column>
      <el-table-column
        prop="checkType"
        label="检查类型">
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
    this.$http.post('/api/graphql', {
      query: `
        query {
          tasks {
            taskId
            status
            currentNode
            supervise {
              superviseId
              company
              construction
              name
              leader
            }
          }
        }
      `
    }).then(res => {
      console.log(res)
      this.tableData = res.data.data.tasks
    })
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