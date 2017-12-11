<template>
    <div>
        <el-form ref="noticeForm" :model="notice" rules="rules" label-width="80px">
            <el-form-item label="通知名称" required:true prop="title">
                <el-input v-model="notice.title"></el-input>
            </el-form-item>
            <el-form-item label="通知内容" required:true prop="content">
                <el-input type="textarea" v-model="notice.content"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit('noticeForm')">立即创建</el-button>
                <el-button @click="resetForm('noticeForm')">取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                notice: {
                    title: '',
                    content: ''
                }
            }
        },
        rules: {
            name: [{
                required: true,
                message: '请输入通知名称',
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
                message: '请填写通知内容',
                trigger: 'blur'
            }]
        },
        methods: {
            onSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log("add new Notice");
                        this.$reqApi.put('/notice', this.notice, response => {
                            console.log(response);
                            if (response.meta.success) {
                                this.resetForm('noticeForm');
                                this.$message({
                                    type: 'info',
                                    message: '添加成功'
                                });
                            } else {
                                this.$message({
                                    type: 'danger',
                                    message: '添加失败：' + response.meta.message
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