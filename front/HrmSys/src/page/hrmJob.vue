<template>
    <div>
        <el-container>
            <!-- 顶部选项 -->
            <el-header>
                <el-menu default-active="/hrmJob/listJob" class="el-menu-demo" mode="horizontal" background-color="#545c64" text-color="#fff"
                    active-text-color="#ffd04b" :router="true">
                    <el-menu-item index="/hrmJob/listJob">职位列表</el-menu-item>
                    <el-menu-item index="/hrmJob/addJob">添加职位</el-menu-item>
                </el-menu>
            </el-header>
            <el-main>
                <router-view class="view"></router-view>
            </el-main>
        </el-container>
    </div>
</template>
<script>


</script>
<style>
    .el-header {
        background-color: #545c64;
        color: #fff;
        line-height: 60px;
    }
</style>