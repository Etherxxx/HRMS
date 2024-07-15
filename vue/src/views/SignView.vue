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
            align="center"
            fixed/>
        <el-table-column
            prop="userName"
            label="姓名"
            align="center"
            fixed/>
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
            prop="startTime"
            label="上班签到时间"
            align="center">
        </el-table-column>
        <el-table-column
            prop="endTime"
            label="下班签到时间"
            align="center">
        </el-table-column>
        <el-table-column
            prop="status"
            label="考勤状态"
            align="center">
          <template slot-scope="scope">
            <el-tag :type="computeTagType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="date"
            label="日期"
            align="center">
        </el-table-column>
        <el-table-column
            prop="comment"
            label="备注"
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
      <el-dialog title="考勤信息" :visible.sync="dialogFormVisible" width='30%'>
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
          <el-form-item label="上下班时间" label-width="16%">
            <el-time-picker style="width: 40%;margin-right: 10px"
                            placeholder="上班签到时间"
                            v-model="form.startTime"
                            format = 'HH:mm:ss'
                            value-format = 'HH:mm:ss'
                            :picker-options="{selectableRange: '7:00:00 - 12:00:00'}">
            </el-time-picker>
            <el-time-picker style="width: 40%;margin-right: 10px"
                            placeholder="下班签退时间"
                            v-model="form.endTime"
                            format = 'HH:mm:ss'
                            value-format = 'HH:mm:ss'
                            :picker-options="{selectableRange: '12:00:00 - 23:00:00'}">
            </el-time-picker>
          </el-form-item>
          <el-form-item label="日期" label-width="15%">
            <el-date-picker
                value-format='yyyy-MM-dd'
                type="date"
                placeholder='请选择日期'
                v-model="form.date"
            />
          </el-form-item>
          <el-form-item label="状态" label-width="15%">
            <el-select v-model="form.status" placeholder="备注">
              <el-option label="正常" value="正常"></el-option>
              <el-option label="迟到" value="迟到"></el-option>
              <el-option label="早退" value="早退"></el-option>
              <el-option label="旷工" value="旷工"></el-option>
              <el-option label="休假" value="休假"></el-option>
              <el-option label="加班" value="加班"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="备注" label-width="15%">
            <el-select v-model="form.comment" placeholder="备注">
              <el-option label="管理补交" value="管理补交"></el-option>
              <el-option label="审核修改" value="审核修改"></el-option>
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
      request.get("/sign/search", {
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
      request.post("/sign", this.form).then(res => {
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
      request.delete("/sign/" + id).then(res => {
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
    computeTagType(status) {
      if (status === '正常') {
        return 'success';
      } else if(status === '旷工'){
        return 'danger';
      }else if(status === '迟到'){
        return 'warning';
      }else if(status === '早退'){
        return 'warning';
      }else if(status === '休假'){
        return 'info';
      }else if(status === '加班'){
        return '';
      }
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
