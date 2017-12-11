<template>
    <div>
        <el-table :data="notices" style="width: 100%" height="625" stripe @row-dblclick="previewNotice">
            <el-table-column prop="id" label="Id" width="60">
            </el-table-column>
            <el-table-column prop="title" label="通知名称">
            </el-table-column>
            <el-table-column prop="content" label="通知内容">
            </el-table-column>
            <el-table-column prop="date" label="创建日期">
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

        <el-dialog title="编辑通知" :visible.sync="dialogVisible">
            <el-form :model="theNotice">
                <el-form-item label="通知名称">
                    <el-input v-model="theNotice.title" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="通知内容">
                    <el-input type="textarea" v-model="theNotice.content"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleUpdate()">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="通知预览" :visible.sync="preViewDialogVisible" width="50%" center>
            <center><h1>{{ theNotice.title }}</h1></center>
            <span>{{theNotice.content}}</span>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="preViewDialogVisible = false">确 定</el-button>
            </span>
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
                preViewDialogVisible: false,
                notices: [],
                theNotice: {
                    id: '',
                    title: '',
                    content: '',
                    date: ''
                },
            }
        },
        created() {
            this.getNotice(1)
        },
        methods: {
            getNotice(pageIndex) {
                console.log("getNotice page=" + pageIndex);
                this.$reqApi.get('/notice/page/' + pageIndex, null, data => {
                    console.log(data);
                    this.notices = data;
                });

                // 获取记录总数，更新totalCount
                this.$reqApi.get('/notice/count', null, data => {
                    console.log("getNotice notice count=" + data.data.count);
                    this.totalCount = data.data.count;
                });
            },
            deleteNoticeById(id) {
                console.log("deleteNoticeById, id=" + id);
                this.$reqApi.delete('/notice/' + id, null, response => {
                    console.log(response);
                    if (response.meta.success) {
                        console.log("删除通知成功，刷新数据");
                        this.currentPage = 1;
                        this.getNotice(1);
                        this.$message({
                            type: 'info',
                            message: '删除通知成功'
                        });
                    } else {
                        console.log("删除通知失败");
                        this.$message({
                            typs: 'danger',
                            message: '删除通知失败： ' + response.meta.message
                        });
                    }
                });
            },
            getNoticeById (id) {
                console.log("getNoticeById id = " + id);
                this.$reqApi.get('/notice/' + id, null, data => {
                    console.log(data);
                    this.theNotice = data;
                });
            },
            updateNotice () {
                console.log("updateNotice");
                this.$reqApi.post('/notice', this.theNotice, response => {
                    console.log(response);
                    if (response.meta.success) {
                        console.log("修改通知成功，刷新数据");
                        this.getNotice(this.currentPage);
                        this.$message({
                            type: 'info',
                            message: '编辑通知成功'
                        });
                    } else {
                        console.log("修改通知失败");
                        this.$message({
                            type: 'danger',
                            message: '编辑通知失败：' + response.meta.message
                        });
                    }
                });
            },

            /* 页面控件事件响应 */
            handleCurrentChange(val) {
                console.log("Val = " + val + ", currentPage = " + this.currentPage);
                this.getNotice(val);
            },
            handleEdit(id) {
                console.log("Edit: the id = " + id);
                this.getNoticeById(id);
                this.dialogVisible = true;
            },
            handleDelete(id) {
                console.log("Delete: the id = " + id);
                this.$confirm('此操作将删除该通知，是否继续？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    console.log("确定要删除通知, id = " + id);
                    this.deleteNoticeById(id);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消删除通知'
                    });
                });
            },
            handleUpdate() {
                console.log("Update: " + this.theNotice.title +", " + this.theNotice.content);
                this.dialogVisible = false;
                this.updateNotice();
            },
            previewNotice (row) {
                console.log("previewNotice id=" + row.id);
                this.getNoticeById(row.id);
                this.preViewDialogVisible = true;

            }
        }
    }
</script>