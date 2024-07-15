<template>
  <div>
    <div>
      <span>姓名</span>
      <el-input v-model="params.name" style="width: 200px" placeholder="请输入姓名"></el-input>
      <span>月份</span>
      <el-date-picker
          value-format='yyyy-MM'
          type="month"
          placeholder='请选择月份'
          v-model="params.month"
          style="margin-left: 10px"
      />
      <el-button type="warning" style="margin-left: 10px" @click="findBySearch()">查询</el-button>
      <el-button type="warning" style="margin-left: 10px" @click="reset()">重置</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="add()">新增</el-button>

    </div>
    <div>
      <el-table
          :data="tableData"
          style="width: 100%;margin-top: 10px"
          border
          stripe
          :header-cell-style="{background: '#eef1f6',color: '#606266',
        textAlign:'center',fontWeight:'bold',borderWidth:'3px'}">

        <el-table-column
            prop="userId"
            label="员工编号"
            align="center">
        </el-table-column>
        <el-table-column
            prop="userName"
            label="姓名"
            align="center">
        </el-table-column>
        <el-table-column
            prop="deptName"
            label="部门名称"
            align="center">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="联系电话"
            align="center">
        </el-table-column>
        <el-table-column
            prop="basic"
            label="基础工资"
            align="center">
        </el-table-column>
        <el-table-column
            prop="bonus"
            label="奖金"
            align="center">
        </el-table-column>
        <el-table-column
            prop="allowance"
            label="补贴"
            align="center">
        </el-table-column>
        <el-table-column
            prop="insurence"
            label="保险"
            align="center">
        </el-table-column>
        <el-table-column
            prop="fine"
            label="考勤考核"
            align="center">
        </el-table-column>
        <el-table-column
            prop="total"
            label="总计"
            align="center">
        </el-table-column>
        <el-table-column
            prop="date"
            label="日期"
            align="center">
        </el-table-column>
        <el-table-column
            prop="Option"
            label="操作"
            align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="edit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
              <el-button slot="reference" size="mini" type="danger" style="margin-left: 10px">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="block">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="params.pageNum"
          :page-sizes="[10,15]"
          :page-size="params.pageSize"
          layout="total, prev, pager, next, sizes"
          :total="total">
      </el-pagination>
    </div>
    <div>
      <el-dialog title="工资信息" :visible.sync="dialogFormVisible" width='30%'>
        <el-form :model="form">
          <el-form-item label="员工编号" label-width="15%">
            <el-input v-model="form.userId" autocomplete="off" style="width: 80%" @input="fetchUserName"></el-input>
          </el-form-item>
          <el-form-item label="姓名" label-width="15%">
            <el-input v-model="form.userName" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="部门" label-width="15%">
            <el-select v-model="form.deptId" placeholder="部门">
              <el-option v-for="dept in departments" :key="dept.id" :label="dept.name" :value="dept.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="联系电话" label-width="15%">
            <el-input v-model="form.phone" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="基础工资" label-width="15%">
            <el-input v-model="form.basic" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="奖金" label-width="15%">
            <el-input v-model="form.bonus" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="补贴" label-width="15%">
            <el-input v-model="form.allowance" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="五险一金" label-width="15%">
            <el-input v-model="form.insurence" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="考勤考核" label-width="15%">
            <el-input v-model="form.fine" autocomplete="off" style="width: 80%" disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="日期" label-width="15%">
            <el-date-picker
                value-format='yyyy-MM'
                type="month"
                placeholder='请选择日期'
                v-model="form.date"
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      params: {
        userId:'',
        name: '',
        month: '',
        userName: '',
        pageNum: 1,
        pageSize: 10,
      },
      tableData: [],
      total: 0,
      dialogFormVisible: false,
      form: {},
      departments: [],
      dayNum: 31,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.findBySearch();
    this.fetchDepartments();
  },
  //定义页面控件触发的方法事件
  methods: {
    findBySearch() {
      request.get("/salary/search", {
        params: this.params
      }).then(res => {
        if (res.code === "0") {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {

        }
      })
    },
    add() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    edit(obj) {
      this.form = obj;
      this.dialogFormVisible = true;
      this.findBySearch();
    },
    reset() {
      this.params = {
        name: '',
        month: '',
        pageNum: '1',
        pageSize: '10'
      }
      this.findBySearch();
    },
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.findBySearch();
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.findBySearch();
    },
    submit() {
      request.post("/salary", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.dialogFormVisible = false;
          this.findBySearch();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      })
    },
    del(id) {
      request.delete("/salary/" + id).then(res => {
        if (res.code === "0") {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.findBySearch();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      })
    },
    fetchDepartments() {
      request.get("/dept/search", {
        params: this.params
      }).then(res => {
        if (res.code === "0") {
          this.departments = res.data.list;
        } else {
          this.$message.error("查询部门信息错误")
        }
      })
    },
    fetchUserName(){
      request.get("/sign/getUserName", {
        params: {
          userId: this.form.userId
        }
      }).then(res => {
        if (res.code === "0") {
          this.form.userName = res.data.name;
          this.form.phone=res.data.phone;
        } else {
          this.$message.error(res.msg)
        }
      })
    },


  }
}
</script>
