<template>
    <div>
        <el-table :data="documents" style="width: 100%" height="625" stripe>
            <el-table-column prop="id" label="Id" width="60">
            </el-table-column>
            <el-table-column prop="title" label="文档标题">
            </el-table-column>
            <el-table-column prop="remark" label="文档简介">
            </el-table-column>
            <el-table-column prop="user.userName" label="持有者">
            </el-table-column>
            <el-table-column prop="fileName" label="文件名称">
            </el-table-column>
            <el-table-column prop="createDate" label="创建日期">
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

        <el-dialog title="编辑文档" :visible.sync="dialogVisible" :modal="true">
            <el-form :model="theDocument">
                <el-form-item label="文档标题">
                    <el-input v-model="theDocument.title" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="文档简介">
                    <el-input v-model="theDocument.remark" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="" prop="file">
                    <el-upload class="upload-demo" drag ref="uploadfile" :action="xxx" :auto-upload="false" :before-upload="newFile">
                        <div class="el-upload__text">将文件拖到此处，或
                            <em>点击上传</em>
                        </div>
                    </el-upload>
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
                documents: [],
                theDocument: {
                    id: '',
                    title: '',
                    fileName: '',
                    remark: '',
                    createDate: '',
                    file:{}
                },
                formData: new FormData()
            }
        },
        created() {
            this.getDocument(1)
        },
        methods: {
            getDocument(pageIndex) {
                console.log("getDocument page=" + pageIndex);
                this.$reqApi.get('/document/page/' + pageIndex, null, data => {
                    console.log(data);
                    this.documents = data;
                });

                // 获取记录总数，更新totalCount
                this.$reqApi.get('/document/count', null, data => {
                    console.log("getDocument doc count=" + data.data.count);
                    this.totalCount = data.data.count;
                });
            },
            deleteDocumentById(id) {
                console.log("deleteDocumentById, id=" + id);
                this.$reqApi.delete('/document/' + id, null, response => {
                    console.log(response);
                    if (response.meta.success) {
                        console.log("删除成功，刷新数据");
                        this.currentPage = 1;
                        // this.getDocument(1);
                        this.$message({
                            type: 'info',
                            message: '删除成功'
                        });
                    } else {
                        console.log("删除失败");
                        this.$message({
                            typs: 'danger',
                            message: '删除失败： ' + response.meta.message
                        });
                    }
                });
            },
            getDocumentById(id) {
                console.log("getDocumentById id = " + id);
                this.$reqApi.get('/document/' + id, null, data => {
                    console.log(data);
                    this.theDocument = data;
                });
                console.log("print this.theDocument: ");
                console.log(this.theDocument);
            },
            updateDocument() {
                console.log("updateDocument");

                this.$reqApi.post2('/document', this.formData, response => {
                    console.log(response);
                    if (response.meta.success) {
                        console.log("修改成功，刷新数据");
                        this.getDocument(this.currentPage);
                        this.$message({
                            type: 'info',
                            message: '编辑成功'
                        });
                    } else {
                        console.log("修改失败");
                        this.$message({
                            type: 'danger',
                            message: '编辑失败：' + response.meta.message
                        });
                    }
                });
            },

            /* 页面控件事件响应 */
            handleCurrentChange(val) {
                console.log("Val = " + val + ", currentPage = " + this.currentPage);
                this.getDocument(val);
            },
            handleEdit(id) {
                console.log("Edit: the id = " + id);
                this.getDocumentById(id);

                this.formData = new FormData();
                this.formData.append('id', this.theDocument.id);            
                this.dialogVisible = true;
            },
            handleDelete(id) {
                console.log("Delete: the id = " + id);
                this.$confirm('此操作将删除该文档，是否继续？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    console.log("确定要删除文档, id = " + id);
                    this.deleteDocumentById(id);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消删除'
                    });
                });
            },
            handleUpdate () {
                console.log("Update: " + this.theDocument.id + ", " + this.theDocument.title + ", " + this.theDocument.remark);
                this.$refs.uploadfile.submit()
                this.dialogVisible = false;

                this.formData.append('title', this.theDocument.title);
                this.formData.append('fileName', this.theDocument.fileName);
                this.formData.append('remark', this.theDocument.remark);   
                console.log("formData: ");
                console.log(this.formData);
                this.updateDocument();
            },
            newFile (file) {
                console.log("new file: ");
                console.log(file);
                this.formData.append('file', file);
                return false;
            }
        }
    }
</script>