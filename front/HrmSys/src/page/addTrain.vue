<template>
    <div>
        <el-form ref="trainform" :model="train" rules="rules" label-width="80px">
            <el-form-item label="培训名称" required:true prop="name">
                <el-input v-model="train.obj.name"></el-input>
            </el-form-item>
            <el-form-item label="培训简介" required:true prop="remark">
                <el-input type="textarea" v-model="train.obj.remark"></el-input>
            </el-form-item>
            <el-form-item label="培训教师">
                <el-select v-model="train.obj.teacher" value-key="id" placeholder="请选择培训教师">
                    <el-option v-for="item in train.teachers" :label="item.name" :value="item" :key="item.id"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit('trainform')">立即创建</el-button>
                <el-button @click="resetForm('trainform')">取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                train: {
                    obj:{
                        id: '',
                        name: '',
                        remark: '',
                        teacher: {},
                        date: ''
                    },
                    teachers: []
                }
            }
        },
        rules: {
            name: [{
                required: true,
                message: '请输入培训名称',
                trigger: 'blur'
            },
            {
                min: 3,
                max: 15,
                message: '长度在 3 到 15 个字符',
                trigger: 'blur'
            }
            ],
            remark: [{
                required: true,
                message: '请填写培训简介',
                trigger: 'blur'
            }]
        },
        created() {
            this.getAllteachers();
        },        
        methods: {
            getAllteachers() {
                console.log("addTrain: getAllteachers");
                this.$reqApi.get('/train/allteacher', null, data => {
                    console.log(data);
                    this.train.teachers = data;
                });

                console.log("print this.train: ");
                console.log(this.train);
            },
         
            onSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log("add new Train");
                        console.log(this.train.obj);
                        this.$reqApi.put('/train', this.train.obj, res => {
                            console.log(res);
                            if (res.meta.success) {
                                this.resetForm('trainform');
                                this.$message({
                                    type: 'info',
                                    message: '添加成功'
                                });
                            } else {
                                this.$message({
                                    type: 'danger',
                                    message: '添加失败：' + res.meta.message
                                });
                            }
                        })
                    } else {
                        console.log('error submit');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>