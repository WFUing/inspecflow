<!-- 绘制表单：第一栏，基本信息，包含单项名称，建管单位，施工单位
第二栏，关联日计划信息，包含作业内容，风险等级，工作负责人
第三栏，违章明细，是一个表格，包含序号，是否整改，违章等级，违章性质，整改要求，责任单位，违章示例，扣分，处罚金额-->
<template>
  <div class="form">
    <el-form :model="form" label-width="80px">
      <!-- 表单标题 -->
      <h1>督察详情</h1>
      <br/>
      <!-- 标题：基本信息 -->
      <!-- 输入框宽160px,一行显示三个表单项-->
      <h3>基本信息</h3>
      <!--空行-->
      <br/>
      <el-row>
        <el-col :span="8">
          <el-form-item label="单项名称">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="建管单位">
            <el-input v-model="form.company"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="施工单位">
            <el-input v-model="form.construction"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 标题：关联日计划信息 -->
      <h3>关联日计划信息</h3>
      <!--空行-->
      <br/>
      <el-row>
        <el-col :span="8">
          <el-form-item label="作业内容">
            <el-input v-model="form.content"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="风险等级">
            <el-input v-model="form.level"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="负责人">
            <el-input v-model="form.leader"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 标题：违章明细 -->
      <h3>违章明细</h3>
      <!--空行-->
      <br/>
      <el-table :data="form.details" style="width: 100%">
        <el-table-column
          prop="detailId"
          label="序号">
        </el-table-column>
        <el-table-column
          prop="isRectify"
          label="是否整改">
        </el-table-column>
        <el-table-column
          prop="violationLevel"
          label="违章等级">
        </el-table-column>
        <el-table-column
          prop="violationNature"
          label="违章性质">
        </el-table-column>
        <el-table-column
          prop="rectifyRequire"
          label="整改要求">
        </el-table-column>
        <el-table-column
          prop="responsibleCompany"
          label="责任单位">
        </el-table-column>
        <el-table-column
          prop="violationExample"
          label="违章示例">
        </el-table-column>
        <el-table-column
          prop="deductMarks"
          label="扣分">
        </el-table-column>
        <el-table-column
          prop="punishmentAmount"
          label="处罚金额">
        </el-table-column>
      </el-table>
      <el-row>
        <br/>
        <el-col :span="8">
          <!-- 保存按钮 -->
          <el-button type="primary" @click="handleEdit(form)">保存</el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
      },
    }
  },
  methods: {
    handleEdit(row) {
      console.log(row)
      this.$http.post('/api/graphql', {
        query: `
          mutation($superviseId: Int, $isRectify: Boolean, $violationLevel: String, $violationNature: String, $rectifyRequire: String, $responsibleCompany: String, $violationExample: String, $deductMarks: Int, $punishmentAmount: Int) {
            updateSuperviseDetailByDetailId(superviseId: $superviseId, isRectify: $isRectify, violationLevel: $violationLevel, violationNature: $violationNature, rectifyRequire: $rectifyRequire, responsibleCompany: $responsibleCompany, violationExample: $violationExample, deductMarks: $deductMarks, punishmentAmount: $punishmentAmount) {
              superviseId
              company
              construction
              content
              leader
              level
              name
              details {
                detailId
                isRectify
                violationLevel
                violationExample
                violationNature
                rectifyRequire
                responsibleCompany
                deductMarks
                punishmentAmount
              }
            }
          }
        `,
        variables: {
          superviseId: row.superviseId,
          isRectify: row.isRectify,
          violationLevel: row.violationLevel,
          violationNature: row.violationNature,
          rectifyRequire: row.rectifyRequire,
          responsibleCompany: row.responsibleCompany,
          violationExample: row.violationExample,
          deductMarks: row.deductMarks,
          punishmentAmount: row.punishmentAmount
        }
      }).then(res => {
        console.log(res)
        this.form = res.data.data.updateSuperviseDetailByDetailId
      })
    }
  },
  mounted() {
    this.$http.post('/api/graphql', {
      query: `
       query($superviseId: Int) {
        superviseBySuperviseId(superviseId: $superviseId) {
          superviseId
          company
          construction
          content
          leader
          level
          name
          details {
            detailId
            isRectify
            violationLevel
            violationExample
            violationNature
            rectifyRequire
            responsibleCompany
            deductMarks
            punishmentAmount
          }
        }
      }
      `,
      variables: {
        superviseId: parseInt(this.$route.params.id)
      }
    }).then(res => {
      console.log(res)
      this.form = res.data.data.superviseBySuperviseId
    })
  }
}
</script>

<style>
.form {
  width: 100%;
  height: 100%;
  background-color: white;
}
h1 {
  color: black;
}
h3 {
  color: black;
}
</style>