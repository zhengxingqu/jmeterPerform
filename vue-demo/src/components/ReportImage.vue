<template>
  <div style="height: auto">
    <el-container style="height: 500px; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246);height: auto">
        <el-menu :default-openeds="['1', '3']">
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-message"></i>导航一</template>
            <el-menu-item-group>
              <el-menu-item index="1-1">
                <router-link to="script">脚本列表</router-link>
              </el-menu-item>
              <el-menu-item index="1-1">
                <router-link to="scriptResult">脚本结果</router-link>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <el-dropdown>
            <i class="el-icon-setting" style="margin-right: 15px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>查看</el-dropdown-item>
              <el-dropdown-item>新增</el-dropdown-item>
              <el-dropdown-item>删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <span>王小虎</span>
        </el-header>

        <el-row :gutter="21">
          <el-col :span="8">
            <div class="grid-content bg-purple" style="margin-top: 30px">饼图</div>
            <div style="margin-top: 10px">
              <el-button type="primary" size="middle" @click="errorReportGenetor()">errorCount</el-button>
              <el-button type="primary" size="middle">90%响应时间</el-button>
              <el-button type="primary" size="middle">平均响应时间</el-button>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content bg-purple" style="margin-top: 30px">折线图</div>
            <div style="margin-top: 10px">
              <el-button type="primary" size="middle">errorCount</el-button>
              <el-button type="primary" size="middle">90%响应时间</el-button>
              <el-button type="primary" size="middle">平均响应时间</el-button>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content bg-purple" style="margin-top: 30px">柱状图</div>
            <div style="margin-top: 10px">
              <el-button type="primary" size="middle">errorCount</el-button>
              <el-button type="primary" size="middle">90%响应时间</el-button>
              <el-button type="primary" size="middle">平均响应时间</el-button>
            </div>
          </el-col>
        </el-row>
      </el-container>
    </el-container>
  </div>
</template>

<script>
    import echart from 'echarts'

    export default {
        name: "reportImage",
        data() {
            return {
                scriptInfoList: []
            }
        },

        created() {
            this.getScriptsResult();
        },

        methods: {
            getScriptsResult() {
                let that = this;
                this.$axios.get("/xnservice/script-info/").then(function (res) {

                    if (res.data.code === 20000) {
                        that.scriptInfoList = res.data.data.rows;
                        console.log(that.scriptInfoList)
                        that.total = res.data.total;
                    }
                }).catch(function (err) {
                    console.log(err)
                })
            },
            errorReportGenetor() {
                    var echarts = require('echarts');
// 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById("main"));
// 绘制图表
                    myChart.setOption({
                        title: {
                            text: 'errorCode柱状图'
                        },
                        tooltip: {},
                        xAxis: {
                            data: ['errorCount', 'meanRestime', 'minRestime', 'maxRestime', 'oneRestime', 'twoRestime', 'threeRestime', 'throughput']
                        },
                        yAxis: {},
                        series: [{
                            name: '销量',
                            type: 'bar',
                            data: [5, 20, 36, 10, 10, 20, 30, 25]
                        }]
                    });
            }
        }
    }
</script>

<style scoped>
  .el-row {
    margin-bottom: 20px;
  }

  .el-col {
    border-radius: 4px;
  }

  .bg-purple-dark {
    background: #99a9bf;
  }

  .bg-purple {
    background: #d3dce6;
  }

  .bg-purple-light {
    background: #e5e9f2;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }

  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
    height: 100%;
  }
</style>
