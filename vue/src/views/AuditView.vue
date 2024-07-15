<template>
  <div>
    <div>
      <el-input v-model="params.name" style="width: 200px" placeholder="输入请假理由以查询"></el-input>
      <el-button type="warning" style="margin-left: 10px" @click="findBySearch()">查询</el-button>
      <el-button type="warning" style="margin-left: 10px" @click="reset()">重置</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="add()" v-if="user.role==='ROLE_EMPLOYEE'">新增
      </el-button>

    </div>
    <div>
      <el-table
          :data="tableData"
          style="width: 100%">
        <el-table-column
            prop="name"
            label="请假理由">
        </el-table-column>
        <el-table-column
            prop="time"
            label="请假日期">
        </el-table-column>
        <el-table-column
            prop="day"
            label="请假天数">
        </el-table-column>
        <el-table-column
            prop="userName"
            label="请假用户">
        </el-table-column>
        <el-table-column prop="status" label="审核状态">
          <template slot-scope="scope">
            <el-tag :type="computeTagType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="reason"
            label="审核意见">
        </el-table-column>
        <el-table-column prop="Option" label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)" v-if="user.role==='ROLE_EMPLOYEE'">编辑</el-button>
            <el-button type="warning" @click="audit(scope.row)" v-if="user.role!='ROLE_EMPLOYEE'">审核</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
              <el-button slot="reference" type="danger" style="margin-left: 10px" v-if="user.role==='ROLE_ADMIN'">
                删除
              </el-button>
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
          :page-sizes="[5,10,15]"
          :page-size="params.pageSize"
          layout="total, prev, pager, next, sizes"
          :total="total">
      </el-pagination>

    </div>
    <div>
      <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width='45%'>
        <el-form :model="form">
          <el-form-item label="请假理由" label-width="15%">
            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="请假日期" label-width="15%">
            <el-date-picker
                v-model="form.time"
                type="date"
                style="width: 90%"
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="请假天数" label-width="15%">
            <el-input v-model="form.day" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <div>
      <el-dialog title="审核" :visible.sync="auditFormVisible" width='45%'>
        <el-form :model="form">
          <el-form-item label="审核状态" label-width="15%">
            <el-radio v-model="form.status" label="审核通过"></el-radio>
            <el-radio v-model="form.status" label="驳回"></el-radio>
          </el-form-item>
          <el-form-item label="审核意见" label-width="15%">
          <el-input v-model="form.reason" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="审核人" label-width="15%">
            <el-input v-model="form.userName" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="auditFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "AuditView",
  data() {
    return {
      params: {
        name: '',
        userId: '',
        pageNum: '1',
        pageSize: '5'
      },
      tableData: [],
      total: 0,
      dialogFormVisible: false,
      auditFormVisible: false,
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.form.userId = this.user.id;
    this.findBySearch();
  },
  //定义页面控件触发的方法事件
  methods: {
    findBySearch() {
      request.get("/audit/search", {
        params: this.params
      }).then(res => {
        if (res.code === "0") {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    add() {
      this.form = {};
      this.form.userId = this.user.id;
      this.dialogFormVisible = true;
    },
    edit(obj) {
      this.form = obj;
      this.dialogFormVisible = true;
      this.findBySearch();
    },
    audit(obj) {
      this.form = obj;
      this.auditFormVisible = true;
    },
    reset() {
      this.params = {
        name: '',
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
      request.post("/audit", this.form).then(res => {
        if (res.code === '0') {
          this.$message.success("操作成功")
          this.dialogFormVisible = false;
          this.auditFormVisible = false;
          this.findBySearch();
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    del(id) {
      request.delete("/audit/" + id).then(res => {
        if (res.code === "0") {
          this.$message.success("删除成功")
          this.findBySearch();
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    computeTagType(status) {
      if (status === '驳回') {
        return 'danger';
      } else if(status === '审核通过'){
        return 'success'; // 或者其他类型
      }
    }

  }
}
</script>
