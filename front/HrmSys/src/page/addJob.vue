<template>
    <div>
        <el-form ref="jobform" :model="job" rules="rules" label-width="80px">
            <el-form-item label="职位名称" required:true prop="name">
                <el-input v-model="job.name"></el-input>
            </el-form-item>
            <el-form-item label="职位描述" required:true prop="remark">
                <el-input type="textarea" v-model="job.remark"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit('jobform')">立即创建</el-button>
                <el-button @click="resetForm('jobform')">取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                job: {
                    name: '',
                    remark: ''
                }
            }
        },
        rules: {
            name: [{
                required: true,
                message: '请输入职位名称',
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
                message: '请填写职位简介',
                trigger: 'blur'
            }]
        },
        methods: {
            onSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log("add new Job");
                        this.$reqApi.put('/job', this.job, res => {
                            console.log(res);
                            if (res.meta.success) {
                                this.resetForm('jobform');
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