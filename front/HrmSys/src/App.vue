<template>
  <div id="app">
    <el-container>
      <!-- 头部导航 -->
      <el-header style="text-align: right">
        <span>用户: Max</span>
        <el-dropdown>
          <i class="el-icon-setting" style="margin-left: 15px; margin-right: 15px; color:#fff">Settings</i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>退出</el-dropdown-item>
            <el-dropdown-item>关于</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

      <el-container>
        <!-- 左侧导航 -->
        <el-aside>
          <el-menu default-active="/hrmIndex" class="el-menu-vertical-demo" :router="true">
            <el-menu-item index="/hrmIndex" :class="{'isActive': active}">首页</el-menu-item>
            <el-menu-item index="/hrmDept/listDept" :class="{'isActive': !active}">部门管理</el-menu-item>
            <el-menu-item index="/hrmJob/listJob" :class="{'isActive': !active}">职位管理</el-menu-item>
            <el-menu-item index="/hrmEmployee" :class="{'isActive': !active}">职员管理</el-menu-item>
            <el-menu-item index="/hrmNotice/listNotice" :class="{'isActive': !active}">通知管理</el-menu-item>
            <el-menu-item index="/hrmDocument/listDocument" :class="{'isActive': !active}">文档管理</el-menu-item>
            <el-menu-item index="/hrmTrain/listTrain" :class="{'isActive': !active}">培训管理</el-menu-item>
            <el-menu-item index="/hrmUser" :class="{'isActive': !active}">系统用户</el-menu-item>
          </el-menu>
        </el-aside>

        <!-- 右侧主区域 -->
        <el-main>
          <router-view class="view" />
        </el-main>
      </el-container>

    </el-container>
  </div>
</template>

<script>
  export default {
    name: 'app',
    data: function () {
      return {
        active: true
      }
    }
  }

</script>

<style>
  body {
    margin: 0;
  }

  #app {
    min-width: 800px;
    margin: 0 auto;
    font-family: "Helvetica Neue", "PingFang SC", Arial, sans-serif;
  }
  /* 头部导航 */

  .el-header {
    background-color: #545c64;
    color: #fff;
    line-height: 60px;
  }

</style>
