<template>
    <div>
        <el-table :data="jobs" style="width: 100%" height="625" stripe>
            <el-table-column prop="id" label="Id" width="60">
            </el-table-column>
            <el-table-column prop="name" label="职位名称">
            </el-table-column>
            <el-table-column prop="remark" label="职位简介">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.row.id)">编辑</el-button>
                    <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
                </template>
</el-table-column>
</el-table>
<el-pagination background layout="prev, pager, next, total" :total="totalCount" @current-change="handleCurrentChange" :current-page.sync="currentPage"
    :page-size="15">
</el-pagination>

<el-dialog title="编辑职位" :visible.sync="dialogVisible">
    <el-form :model="theJob">
        <el-form-item label="职位名称">
            <el-input v-model="theJob.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="职位描述">
            <el-input type="textarea" v-model="theJob.remark"></el-input>
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
                jobs: [],
                theJob: {
                    id: '',
                    name: '',
                    remark: ''
                }
            }
        },
        created() {
            this.getJobData(1)
        },
        methods: {
            getJobData(pageNumber) {
                console.log("getJobData by page=" + pageNumber);
                this.$reqApi.get('/job/page/'+pageNumber, null, data => {
                    console.log(data);
                    this.jobs = data;
                });

                // 获取记录总数，更新totalCount
                this.$reqApi.get('/job/count', null, data => {
                    console.log("getJobData job count="+data.data.count);
                    this.totalCount = data.data.count;
                });                
            },
            deleteJobById(id) {
                console.log("deleteJobById, id = " + id);
                this.$reqApi.delete('/job/' + id, null, response => {
                    console.log(response);
                    if (response.meta.success) {
                        console.log("删除职位成功，刷新数据");
                        this.currentPage = 1;
                        this.getJobData(1);
                        this.$message({
                            type: 'info',
                            message: '删除职位成功'
                        });
                    } else {
                        console.log("删除职位失败");
                        this.$message({
                            typs: 'danger',
                            message: '删除职位失败： ' + response.meta.message
                        });
                    }
                } );
            },
            getJobById (id) {
                console.log("getJobById id = " + id);
                this.$reqApi.get('/job/' + id, null, data => {
                    console.log(data);
                    this.theJob = data;
                });
            },
            updateJob () {
                console.log("updateJob");
                this.$reqApi.post('/job', this.theJob, response => {
                    console.log(response);
                    if (response.meta.success) {
                        console.log("修改职位成功，刷新数据");
                        this.getJobData(this.currentPage);
                        this.$message({
                            type: 'info',
                            message: '编辑职位成功'
                        });
                    } else {
                        console.log("修改职位失败");
                        this.$message({
                            type: 'danger',
                            message: '编辑职位失败：' + response.meta.message
                        });
                    }
                })                
            },

            /* 页面控件事件响应 */
            handleCurrentChange(val) {
                console.log("Val = " + val + ", currentPage = " + this.currentPage);
                this.getJobData(val);
            },
            handleEdit(id) {
                console.log("Edit: the id = " + id);
                this.getJobById(id);
                this.dialogVisible = true;
            },
            handleDelete(id) {
                console.log("Delete: the id = " + id);
                this.$confirm('此操作将删除该职位，是否继续？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    console.log("确定要删除职位, id = " + id);
                    this.deleteJobById(id);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消职位删除'
                    });
                });
            },
            handleUpdate() {
                console.log("Update: " + this.theJob.id + ", " + this.theJob.name + ", " + this.theJob.remark);
                this.dialogVisible = false;
                this.updateJob();
            }
        }
    }
</script>