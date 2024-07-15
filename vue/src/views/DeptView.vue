<template>
  <div>
    <div>
      <el-input v-model="params.name" style="width: 200px" placeholder="请输入部门名称"></el-input>
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
            label="部门名称">
        </el-table-column>
        <el-table-column
            prop="managerName"
            label="部门负责人">
        </el-table-column>
        <el-table-column
            prop="startTime"
            label="上班时间">
        </el-table-column>
        <el-table-column
            prop="endTime"
            label="下班时间">
        </el-table-column>
        <el-table-column
            prop="remark"
            label="备注">
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
      <el-dialog title="部门信息" :visible.sync="dialogFormVisible" width='30%'>
        <el-form :model="form">
          <el-form-item label="部门名称" label-width="15%">
            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="负责人" label-width="15%">
            <el-input v-model="form.managerName" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="上下班时间" label-width="16%">
            <el-time-select style="width: 42%;margin-right: 10px"
                placeholder="起始时间"
                v-model="form.startTime"
                :picker-options="{start: '08:00',step: '00:15',end: '10:00'}">
            </el-time-select>
            <el-time-select style="width: 42%"
                placeholder="结束时间"
                v-model="form.endTime"
                :picker-options="{start: '17:00',step: '00:15',end: '22:00',}">
            </el-time-select>
          </el-form-item>
          <el-form-item label="备注" label-width="15%">
            <el-input type="textarea" v-model="form.remark" autocomplete="off" style="width: 90%"></el-input>
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
        managerName:'',
        pageNum: '1',
        pageSize: '10'
      },
      tableData: [],
      total: 0,
      dialogFormVisible: false,
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.findBySearch();
  },
  //定义页面控件触发的方法事件
  methods: {
    findBySearch() {
      request.get("/dept/search", {
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
      request.post("/dept", this.form).then(res => {
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
      request.delete("/dept/" + id).then(res => {
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
    }

  }
}
</script>
