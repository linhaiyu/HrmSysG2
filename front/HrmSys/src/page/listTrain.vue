<template>
    <div>
        <el-table :data="trains" style="width: 100%" height="625" stripe>
            <el-table-column prop="id" label="Id" width="60">
            </el-table-column>
            <el-table-column prop="name" label="培训名称">
            </el-table-column>
            <el-table-column prop="remark" label="培训简介">
            </el-table-column>
            <el-table-column prop="teacher.name" label="培训教师">
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

        <el-dialog title="编辑培训" :visible.sync="dialogVisible" :modal="true"> 
            <el-form :model="theTrain">
                <el-form-item label="培训名称">
                    <el-input v-model="theTrain.train.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="培训简介">
                    <el-input type="textarea" v-model="theTrain.train.remark"></el-input>
                </el-form-item>
                <el-form-item label="培训教师">
                    <el-select v-model="theTrain.train.teacher" value-key="id" placeholder="请选择培训教师">
                        <el-option v-for="item in theTrain.teachers" :label="item.name" :value="item" :key="item.id"></el-option>
                    </el-select>
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
        data () {
            return {
                currentPage: 1,
                totalCount: 0,
                dialogVisible: false,
                trains: [],
                theTrain: {
                    train: {
                        id: '',
                        name: '',
                        remark: '',
                        teacher: {},
                        date: '',
                    },
                    teachers: []
                },                
            }
        },
        created () {
            this.getAllteachers();
            this.getTrainData (1)
        },
        methods: {
            getAllteachers () {
                console.log("getAllteachers");
                this.$reqApi.get('/train/allteacher', null, data => {
                    console.log(data);
                    this.theTrain.teachers = data;
                });
            },
            getTrainData (pageIndex) {
                console.log("getTrainData page=" + pageIndex);
                this.$reqApi.get('/train/page/' + pageIndex, null , data => {
                    console.log(data);
                    this.trains = data;
                });

                // 获取记录总数，更新totalCount
                this.$reqApi.get('/train/count', null, data => {
                    console.log("getTrainData train count="+data.data.count);
                    this.totalCount = data.data.count;
                });
            },
            deleteTrainById (id) {
                console.log("deleteTrainById, id=" + id);
                this.$reqApi.delete('/train/' + id, null, response => {
                    console.log(response);
                    if (response.meta.success) {
                        console.log("删除培训成功，刷新数据");
                        this.currentPage = 1;
                        this.getTrainData(1);
                        this.$message({
                            type: 'info',
                            message: '删除培训成功'
                        });
                    } else {
                        console.log("删除培训失败");
                        this.$message({
                            typs: 'danger',
                            message: '删除培训失败： ' + response.meta.message
                        });                        
                    }
                });
            },
            getTrainById (id) {
                console.log("getTrainById id = " + id);
                this.$reqApi.get('/train/' + id, null, data => {
                    console.log(data);
                    this.theTrain.train = data;
                });      
                console.log("print this.theTrain: ");
                console.log(this.theTrain);          
            },
            updateTrain () {
                console.log("updateTrain");
                this.$reqApi.post('/train', this.theTrain.train, response => {
                    console.log(response);
                    if (response.meta.success) {
                        console.log("修改培训成功，刷新数据");
                        this.getTrainData(this.currentPage);
                        this.$message({
                            type: 'info',
                            message: '编辑培训成功'
                        });
                    } else {
                        console.log("修改培训失败");
                        this.$message({
                            type: 'danger',
                            message: '编辑培训失败：' + response.meta.message
                        });
                    }
                });                
            },

            /* 页面控件事件响应 */
            handleCurrentChange(val) {
                console.log("Val = " + val + ", currentPage = " + this.currentPage);
                this.getTrainData(val);
            },
            handleEdit(id) {
                console.log("Edit: the id = " + id);
                this.getTrainById(id);
                this.dialogVisible = true;
            },
            handleDelete(id) {
                console.log("Delete: the id = " + id);
                this.$confirm('此操作将删除该培训，是否继续？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    console.log("确定要删除培训, id = " + id);
                    this.deleteTrainById(id);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消培训删除'
                    });
                });
            },
            handleUpdate() {
                console.log("Update: " + this.theTrain.train.id + ", " + this.theTrain.train.name + ", " + this.theTrain.train.remark);
                this.dialogVisible = false;
                this.updateTrain();
            }
        }
    }
</script>