<template>
  <div>
    <el-table :data="deptData" style="width: 100%" height="625" stripe>
      <el-table-column prop="id" label="Id" width="60">
      </el-table-column>
      <el-table-column prop="name" label="部门名称">
      </el-table-column>
      <el-table-column prop="remark" label="部门简介">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row.id)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
</el-table-column>
</el-table>
<el-pagination background layout="prev, pager, next, total" :total="totalCount" @current-change="handleCurrentChange" :current-page.sync="currentPage" :page-size="15">
</el-pagination>

<el-dialog title="编辑部门" :visible.sync="dialogVisible">
    <el-form :model="theDept">
        <el-form-item label="部门名称">
            <el-input v-model="theDept.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="部门描述">
            <el-input type="textarea" v-model="theDept.remark"></el-input>
        </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleUpdate()">确 定</el-button>
    </div>
</el-dialog>
</div>
</template>
<script>
    export default {
        data() {
            return {
                currentPage: 1,
                totalCount: 0,
                dialogVisible: false,
                deptData: [],
                theDept: {
                    id: '',
                    name: '',
                    remark: ''
                }
            }
        },
        created() {
            this.getDeptData(1);
        },
        methods: {
            getDeptData (pageNumber) {
                // 通过当前页获取数据
                this.$reqApi.get('/dept/page/'+ pageNumber, null, data => {
                    console.log("getDeptData by page");
                    console.log(data);
                    this.deptData = data;
                });

                // 获取记录总数，更新totalCount
                this.$reqApi.get('/dept/count', null, data => {
                    console.log("getDeptData dept count");
                    console.log(data);
                    this.totalCount = data.data.count;
                });
            },
            getDeptData_old() {
                //   var v = this
                //   this.$reqApi.get('/dept', null, function (data) {
                //       console.log(data)
                //       v.deptData = data
                //   })

                this.$reqApi.get('/dept', null, data => {
                    console.log("getDeptData");
                    console.log(data);
                    this.deptData = data;
                });
            },
            deleteDeptById(id) {
                console.log("deleteDeptBy id = " + id);

                this.$reqApi.delete('/dept/' + id, null, res => {
                    console.log(res);
                    if (res.meta.success) {
                        console.log("删除成功，刷新数据");
                        this.currentPage = 1;
                        this.getDeptData(1);
                        this.$message({
                            type: 'info',
                            message: '删除成功'
                        });
                    } else {
                        console.log("删除失败")
                        this.$message({
                            type: 'danger',
                            message: '删除失败：' + res.meta.message
                        });
                    }
                }, res => {
                    console.log(res);
                    this.$message({
                        type: 'danger',
                        message: '删除失败'
                    });
                });
            },
            getDeptById(id) {
                console.log("getDeptById id = " + id);
                this.$reqApi.get('/dept/' + id, null, data => {
                    console.log(data);
                    this.theDept = data;
                });
            },
            updateDept() {
                console.log("updateDept");
                this.$reqApi.post('/dept', this.theDept, res => {
                    console.log(res);
                    if (res.meta.success) {
                        console.log("修改成功，刷新数据");
                        this.getDeptData(this.currentPage);
                        this.$message({
                            type: 'info',
                            message: '编辑成功'
                        });
                    } else {
                        console.log("修改失败");
                        this.$message({
                            type: 'danger',
                            message: '编辑失败：' + res.meta.message
                        });
                    }
                })
            },

            /**  页面控件事件响应
             */
            handleCurrentChange(val) {
                console.log("Val = " + val + ", currentPage = " + this.currentPage);
                this.getDeptData(val);
            },
            handleEdit(id) {
                // 编辑部门
                console.log("Edit: the id = " + id);
                this.getDeptById(id);
                this.dialogVisible = true;
            },
            handleDelete(id) {
                // 删除部门
                console.log("Delete: the id = " + id);
                this.$confirm('此操作将删除部门，是否继续？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    console.log("确定要删除部门, id = " + id);
                    this.deleteDeptById(id);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消删除'
                    });
                });
            },
            handleUpdate() {
                // 更新部门
                console.log("Update: " + this.theDept.id + ", " + this.theDept.name + ", " + this.theDept.remark);
                this.dialogVisible = false;
                this.updateDept();
            }
        }
    }
</script>
<style>

</style>