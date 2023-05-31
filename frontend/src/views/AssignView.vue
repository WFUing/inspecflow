<template>
  <!-- 序号、派发状态、建管单位、项-->
  <div class="assign">
    <h1>督察任务派发</h1>
    <el-table
        :data="tableData"
        style="width: 3vm">
      <el-table-column
          prop="assignId"
          label="序号"
          width="180">
      </el-table-column>
      <el-table-column
          prop="status"
          label="派发状态"
          width="180">
      </el-table-column>
      <el-table-column
          prop="company"
          label="建管单位">
      </el-table-column>
      <el-table-column
          prop="project"
          label="项目名称">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
// 通过 http://localhost:8080/graphql获取数据
  data() {
    return {
      tableData:[]
    }
  },
  mounted() {
    this.$http.post('/api/graphql', {
      query: `
        query{assigns{assignId status company project}}
      `
    }).then(res => {
      console.log(res)
      this.tableData = res.data.data.assigns
    })
  }
}
</script>

<style>
.assign {
  margin-top: 20px;
}
h1 {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 20px;
  color: black;
}
</style>