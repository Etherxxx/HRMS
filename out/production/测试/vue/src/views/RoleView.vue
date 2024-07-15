<template>
  <div>
    <div>
      <el-input v-model="params.name" style="width: 200px" placeholder="请输入内容"></el-input>
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
          row-key="id"
          :header-cell-style="{background: '#eef1f6',color: '#606266',
        textAlign:'center',fontWeight:'bold',borderWidth:'3px'}">
        <el-table-column
            prop="id"
            label="角色编号"
            align="center">
        </el-table-column>
        <el-table-column
            prop="portray"
            label="角色描述"
            align="center">
        </el-table-column>
        <el-table-column
            prop="name"
            label="角色名称"
            align="center">
        </el-table-column>
        <el-table-column
            prop="remark"
            label="描述"
            align="center">
        </el-table-column>
        <el-table-column
            prop="Option"
            label="操作"
            align="center">
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
          :page-sizes="[5,10,15]"
          :page-size="params.pageSize"
          layout="total, prev, pager, next, sizes"
          :total="total">
      </el-pagination>
    </div>
    <div>
      <el-dialog title="请填写角色信息" :visible.sync="dialogFormVisible" width='30%'>
        <el-form :model="form">
          <el-form-item label="角色描述" label-width="15%" >
            <el-input v-model="form.portray" autocomplete="off" style="width: 90%" ></el-input>
          </el-form-item>
          <el-form-item label="角色名称" label-width="15%">
            <el-input v-model="form.name" autocomplete="off" style="width: 90%" placeholder="请以ROLE_ADMIN形式输入"></el-input>
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
  data() {
    return {
      params: {
        name: '',
        portray: '',
        pageNum: '1',
        pageSize: '5'
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
      request.get("/role/search", {
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
      request.post("/role", this.form).then(res => {
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
      request.delete("/role/" + id).then(res => {
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
