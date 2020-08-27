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

        <el-main>
          <el-form :inline="true" style="margin-top: 20px">
            <el-row>
              <el-col :span="5">
                <el-form-item label="请输入查询条件">
                  <el-input placeholder="输入查询条件" v-model="scriptName"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-button-group>
                  <el-button type="primary" icon="el-icon-search" @click="searchScriptResult()">查询</el-button>
                  <el-button type="primary" icon="el-icon-share" @click="getScriptsResult()">全部</el-button>
                </el-button-group>
              </el-col>
              <el-col :span="4">
                <el-form-item label="条件">
                  <el-select v-model="searchVo.condition" placeholder="请选择">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-con :span="3">
                <el-form-item label="errorCount">
                  <el-input placeholder="请输入errorCount查询条件" v-model="searchVo.errorCount"></el-input>
                </el-form-item>
              </el-con>
              <el-con :span="3">
                <el-form-item label="90%响应时间时间">
                  <el-input placeholder="请输入90%用户响应时间时间查询条件" v-model="searchVo.oneRestime"></el-input>
                </el-form-item>
              </el-con>
              <el-con :span="3">
                <el-form-item label="吞吐量">
                  <el-input placeholder="请输入吞吐量查询条件" v-model="searchVo.throughput"></el-input>
                </el-form-item>
              </el-con>
              <el-con :span="3">
                <el-form-item label="平均响应时间">
                  <el-input placeholder="请输入平均响应时间查询条件" v-model="searchVo.meanRestime"></el-input>
                </el-form-item>
              </el-con>
              <el-con :span="2">
                <el-form-item>
                  <el-button type="primary" @click="filterScript()">筛选</el-button>
                </el-form-item>
              </el-con>
              <!--              <el-col :span="2">-->
              <!--                <el-button type="primary" @click="downloadExcel()">下载</el-button>-->
              <!--              </el-col>-->
              <!--              <el-col :span="4">-->
              <!--                <el-select v-model="envName" placeholder="请选择环境名称">-->
              <!--                  <el-option-->
              <!--                    v-for="item in urlList"-->
              <!--                    :key="item.environmentalName"-->
              <!--                    :label="item.environmentalName"-->
              <!--                    :value="item.environmentalName">-->
              <!--                  </el-option>-->
              <!--                </el-select>-->
              <!--              </el-col>-->

              <!--              <el-col :span="4">-->
              <!--                <el-select v-model="projectName" placeholder="请选择项目名称" @change="findProjectName">-->
              <!--                  <el-option-->
              <!--                    v-for="item in projectList"-->
              <!--                    :key="item.projectName"-->
              <!--                    :label="item.projectName"-->
              <!--                    :value="item.projectName">-->
              <!--                  </el-option>-->
              <!--                </el-select>-->
              <!--              </el-col>-->
            </el-row>
          </el-form>

          <el-table :data="tableData" @selection-change="handleSelectionChange">
            <el-table-column
              type="index" label="序号"></el-table-column>
            <el-table-column
              type="selection"
              width="55">
            </el-table-column>
            <el-table-column prop="scriptName" label="脚本名称" width="140">
            </el-table-column>
            <el-table-column prop="transaction" label="接口/事务名称" width="140">
            </el-table-column>
            <el-table-column prop="errorCount" label="errorCount" width="120">
            </el-table-column>
            <el-table-column prop="oneRestime" label="90%用户运行时间">
            </el-table-column>
            <el-table-column prop="throughput" label="吞吐量">
            </el-table-column>
            <el-table-column prop="meanRestime" label="平均响应时间">
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column prop="updateTime" label="修改时间"></el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="100">
              <template slot-scope="scope">
                <el-button @click="deleteScript(scope.row)" type="text" size="small">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-main>
      </el-container>
    </el-container>
    <div>
      <el-col :span="4" style="text-align: left">
        <el-button type="danger" icon="el-icon-delete" size="mini" style="margin-top: 30px;margin-left:60%"
                   @click="deleteScripts()">批量删除
        </el-button>
      </el-col>
    </div>
    <div style="margin-left: 70%;margin-top: 30px">
      <el-pagination
        :current-page=current
        :page-sizes="[10, 20, 50, 100]"
        :page-size=limit
        layout="total, sizes, prev, pager, next, jumper"
        :total=total
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange">
      </el-pagination>
    </div>
  </div>
</template>

<script>
    export default {
        name: "ScriptResult",
        data() {
            return {
                tableData: [],
                scriptName: '',
                current: 1,
                limit: 10,
                total: 0,
                options: [{value: ">=", label: ">="},
                    {value: "<=", label: "<="}
                ],
                searchVo: {
                    errorCount: 0,
                    oneRestime: "",
                    throughput: "",
                    meanRestime: "",
                    condition: ">=",
                },
                idList: [],
                multipleSelection: []
            }
        },

        created() {
            this.getScriptsResult();
        },

        methods: {
            getScriptsResult() {
                let that = this;
                this.$axios.get("/xnservice/script-info/" + that.current + "/" + that.limit).then(function (res) {

                    if (res.data.code === 20000) {
                        that.tableData = res.data.data.rows;
                        that.total = res.data.total;
                    }
                }).catch(function (err) {
                    console.log(err)
                })
            },

            handleSizeChange(val) {
                let that = this;
                that.limit = val;
                this.getScriptsResult();
            },
            handleCurrentChange(val) {
                let that = this;
                that.current = val;
                this.getScriptsResult();
            },

            deleteScript(row) {
                let that = this;
                that.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    that.$axios.delete('/xnservice/script-info/' + row.id).then(function (res) {
                        if (res.data.code === 20000) {
                            that.$message({
                                type: 'success',
                                message: res.data.message
                            });
                            that.dialogVisible = false;
                            that.getScriptsResult();
                            that.reload();
                        }
                    }).catch(function (err) {
                        console.log(err)
                        that.$message.error(err);
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },

            searchScriptResult() {
                let that = this;
                this.$axios.get("/xnservice/script-info/getName", {"params": {"scriptName": this.scriptName}}).then(function (res) {
                    console.log(res)
                    that.tableData = res.data.data.data;
                    this.reload();
                }).catch(function (err) {

                })
            },

            filterScript() {
                let that = this;
                this.$axios.post("/xnservice/script-info/search", this.searchVo).then(function (res) {
                    if (res.data.code === 20000) {
                        console.log(res)
                        that.tableData = res.data.data.data;
                    }
                }).catch(function (err) {
                    console.log(err)
                })
            },

            handleSelectionChange(multipleSelection) {
                this.multipleSelection = multipleSelection;
            },

            deleteScripts() {
                let that = this;
                that.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    that.multipleSelection.forEach(i => {
                        that.idList.push(i.id)
                    });
                    that.$axios.delete('/xnservice/script-info', {
                        data: {
                            "ids": that.idList,
                        }
                    }).then((res) => {
                        that.getScriptsResult();
                        that.reload();
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        that.idList = [];
                    }).catch((err) => {
                        console.log(err)
                    })

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                    that.idList = [];
                });
            },


        }
    }
</script>

<style scoped>

</style>
