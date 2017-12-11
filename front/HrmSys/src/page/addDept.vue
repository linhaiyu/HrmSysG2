<template>
  <div>
    <el-form ref="deptform" :model="dept" rules="rules" label-width="80px">
      <el-form-item label="部门名称" required:true prop="name">
        <el-input v-model="dept.name"></el-input>
      </el-form-item>
      <el-form-item label="部门描述" required:true prop="remark">
        <el-input type="textarea" v-model="dept.remark"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('deptform')">立即创建</el-button>
        <el-button @click="resetForm('deptform')">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
    export default {
        data() {
            return {
                dept: {
                    name: '',
                    remark: ''
                }
            }
        },
        rules: {
            name: [{
                    required: true,
                    message: '请输入部门名称',
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
                message: '请填写部门简介',
                trigger: 'blur'
            }]
        },
        methods: {
            onSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log("add new Dept");
                        this.$reqApi.put('/dept', this.dept, res => {
                            console.log(res);
                            if (res.meta.success) {
                                this.resetForm('deptform');
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