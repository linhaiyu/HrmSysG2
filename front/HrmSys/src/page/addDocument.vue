<template>
    <div>
        <el-form ref="documentForm" :model="document" rules="rules" label-width="80px">
            <el-form-item label="名称" required:true prop="title">
                <el-input v-model="document.title"></el-input>
            </el-form-item>
            <el-form-item label="简介" required:true prop="remark">
                <el-input type="textarea" v-model="document.remark"></el-input>
            </el-form-item>
            <el-form-item label="">
                <el-upload class="upload-demo" drag ref="uploadfile" :action="xxx" :auto-upload="false" :before-upload="newFile">
                    <div class="el-upload__text">将文件拖到此处，或
                        <em>点击上传</em>
                    </div>
                </el-upload>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit('documentForm')">立即创建</el-button>
                <el-button @click="resetForm('documentForm')">取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                document: {
                    title: '',
                    remark: ''
                },
                formData: new FormData()
            }
        },
        rules: {
            title: [{
                required: true,
                message: '请输入名称',
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
                message: '请填写简介',
                trigger: 'blur'
            }]
        },
        methods: {
            onSubmit(formName) {
                this.formData = new FormData();
                this.$refs.uploadfile.submit();
                
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log("add new Doc");


                        this.formData.append('title', this.document.title);
                        this.formData.append('remark', this.document.remark);

                        this.$reqApi.post2('/document/add', this.formData, res => {
                            console.log(res);
                            if (res.meta.success) {
                                this.resetForm('documentForm');
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
            },
            newFile(file) {
                console.log("new file: ");
                console.log(file);
                this.formData.append('file', file);
                return false;
            }
        }
    }
</script>