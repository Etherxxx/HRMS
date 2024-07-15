<template>
  <div>
    <div>
      <el-input v-model="params.name" style="width: 200px" placeholder="请输入姓名"></el-input>
      <el-input v-model="params.phone" style="width: 200px;margin-left: 10px" placeholder="请输入电话"></el-input>
      <el-button type="warning" style="margin-left: 10px" @click="findBySearch()">查询</el-button>
      <el-button type="warning" style="margin-left: 10px" @click="reset()">重置</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="add()">新增</el-button>

    </div>
    <div>
      <el-table
          :data="tableData"
          style="width: 100%">
        <el-table-column
            prop="name"
            label="姓名">
        </el-table-column>
        <el-table-column
            prop="sex"
            label="性别">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话">
        </el-table-column>
        <el-table-column
            prop="deptName"
            label="部门">
        </el-table-column>
        <el-table-column
            prop="roleRemark"
            label="角色">
        </el-table-column>
        <el-table-column
            prop="Option"
            label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
              <el-button slot="reference" type="danger" style="margin-left: 10px">删除</el-button>
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
      <el-dialog title="员工信息编辑" :visible.sync="dialogFormVisible" width='30%'>
        <el-form :model="form">
          <el-form-item label="姓名" label-width="15%">
            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="性别" label-width="15%">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
            <!--            <el-select v-model="form.sex" placeholder="请选择性别">-->
            <!--              <el-option label="男" value="男"></el-option>-->
            <!--              <el-option label="女" value="女"></el-option>-->
            <!--            </el-select>-->
          </el-form-item>
          <el-form-item label="年龄" label-width="15%">
            <el-input v-model="form.age" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="电话" label-width="15%">
            <el-input v-model="form.phone" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="角色" label-width="15%">
            <el-select v-model="form.role" placeholder="角色">
              <el-option label="部门经理" value="ROLE_MANAGER"></el-option>
              <el-option label="员工" value="ROLE_EMPLOYEE"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="部门" label-width="15%">
            <el-select v-model="form.deptId" placeholder="部门">
              <el-option v-for="dept in departments" :key="dept.id" :label="dept.name" :value="dept.id"></el-option>
            </el-select>
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
  name: "AdminView",

  data() {
    return {
      params: {
        name: '',
        phone: '',
        deptId: '',
        pageNum: 1,
        pageSize: 10
      },
      tableData: [],
      departments: [],
      total: 0,
      dialogFormVisible: false,
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.findBySearch();
    this.fetchDepartments();
  },
  //定义页面控件触发的方法事件
  methods: {
    // load() {
    //   request.get("/admin").then(res => {
    //     if (res.code === "0") {
    //       this.tableData = res.data;
    //     } else {
    //
    //     }
    //   })
    // },
    findBySearch() {
      request.get("/admin/search", {
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
        phone: '',
        deptId: '',
        pageNum: '1',
        pageSize: '5'
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
      request.post("/admin", this.form).then(res => {
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
      request.delete("/admin/" + id).then(res => {
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
          this.departments=res.data.list;
        } else {
          this.$message.error("查询部门信息错误")
        }
      })
    },

  }
}
</script>
