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
              <el-col :span="8">
                <el-form-item label="请输入查询条件">
                  <el-input placeholder="输入查询条件" v-model="scriptName"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-button-group>
                  <el-button type="primary" icon="el-icon-search" @click="searchScript()">查询</el-button>
                  <el-button type="primary" icon="el-icon-share" @click="getScripts()">全部</el-button>
                  <el-button type="primary" icon="el-icon-share" @click="downloadScript()">下载</el-button>
                  <!--                  <el-button type="primary" icon="el-icon-plus" @click="addCase()">添加</el-button>-->

                </el-button-group>
              </el-col>
              <el-col :span="2">
                <el-upload
                  action="http://localhost:8085/eduservice/xn-table/add"
                  accept=".jmx">
                  <el-button type="primary">点击上传</el-button>
                </el-upload>
              </el-col>
            </el-row>
          </el-form>

          <el-table :data="tableData" @selection-change="handleSelectionChange" @row-dblclick="dbclick" stripe border sort>
            <el-table-column
              type="index" label="序号" width="80px"></el-table-column>
            <el-table-column
              type="selection"
              width="55">
            </el-table-column>
            <el-table-column prop="scriptName" label="脚本名称" width="140">
            </el-table-column>
            <el-table-column prop="resultFile" label="结果文件" width="140">
            </el-table-column>
            <el-table-column prop="runResult" label="运行结果" width="120px">
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="160px" sortable>
            </el-table-column>
            <el-table-column prop="updateTime" label="修改时间" width="160px" sortable>
            </el-table-column>
            <el-table-column prop="threads" label="线程数" width="120px" sortable>
              <template slot-scope="scope">
                <el-form :model="scope.row">
                  <el-form-item size="mini" label-width="50px">
                    <el-input v-if="scope.row.status" v-model="scope.row.threads"
                              style="width:100%;hight:100%"></el-input>
                    <span v-else>{{scope.row.threads}}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column prop="runDurationTime" label="持续运行时间" width="140px" sortable>
              <template slot-scope="scope">
                <el-form :model="scope.row">
                  <el-form-item size="mini" label-width="50px">
                    <el-input v-if="scope.row.status" v-model="scope.row.runDurationTime"
                              style="width:100%;hight:100%"></el-input>
                    <span v-else>{{scope.row.runDurationTime}}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column prop="rampTime" label="循环次数" width="120px" sortable>
              <template slot-scope="scope">
                <el-form :model="scope.row">
                  <el-form-item size="mini" label-width="50px">
                    <el-input v-if="scope.row.status" v-model="scope.row.rampTime"
                              style="width:100%;hight:100%"></el-input>
                    <span v-else>{{scope.row.rampTime}}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="200">
              <template slot-scope="scope">
                <el-button @click="deleteScript(scope.row)" type="text" size="small">删除</el-button>
                <el-button type="text" size="small" @click="runScript(scope.row)" v-loading="scope.row.runLoading">运行</el-button>
                <el-button type="text" size="small" @click="openScript(scope.row)">修改</el-button>
                <el-button type="text" size="small" @click="openLog(scope.row)">查看日志</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-main>
      </el-container>
    </el-container>
    <div>
      <el-col :span="4" style="text-align: left">
        <el-button type="danger" icon="el-icon-delete" size="middle" style="margin-top: 30px;margin-left:60%"
                   @click="deleteScripts()">批量删除
        </el-button>
      </el-col>
      <el-col :span="2" style="text-align: left">
        <el-button type="type=primary" size="middle" style="margin-top: 30px;"
                   @click="runManyScripts()">批量运行
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
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="80%">
      <el-input suffix-icon="el-icon-edit" v-model="content" type="textarea" :autosize="{minRows: 10}" v-if="logDialog === false"></el-input>
      <el-input suffix-icon="el-icon-edit" v-model="logContent" type="textarea" :autosize="{minRows: 10}" v-if="logDialog"></el-input>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="submit(scriptModifyForm)">确 定</el-button>
  </span>
    </el-dialog>
  </div>

</template>


<style>
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

<script>
    import {quillEditor} from 'vue-quill-editor'
    import 'quill/dist/quill.core.css'
    import 'quill/dist/quill.snow.css'
    import 'quill/dist/quill.bubble.css'

    export default {
        data() {
            return {
                tableData: [],
                scriptName: "",
                current: 1,
                limit: 10,
                total: 0,
                idList: [],
                multipleSelection: [],
                urlList: [],
                dialogVisible: false,
                editorOption: {},
                content: "",
                logDialog: false,
                isLoading: false,
                scriptModifyForm: {
                    scriptForm: "",
                    threads: "",
                    rampTime: "",
                    runDurationTime: "",
                },
                logContent: "",
            }
        },

        created() {
            this.getScripts();
        },

        methods: {
            getScripts() {
                let that = this;
                this.$axios.get("/eduservice/xn-table/" + that.current + "/" + that.limit).then(function (res) {
                    if (res.data.code === 20000) {
                        res.data.data.rows.map(item =>{
                            item.loading = false;
                        });
                        that.tableData = res.data.data.rows;
                        that.total = res.data.total;

                    }
                }).catch(function (err) {
                    console.log(err)
                })
            },

            deleteScript(row) {
                let that = this;
                that.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    that.$axios.delete('/eduservice/xn-table/' + row.id).then(function (res) {
                        if (res.data.code === 20000) {
                            that.$message({
                                type: 'success',
                                message: res.data.message
                            });
                            that.dialogVisible = false;
                            that.getScripts();
                            that.reload();
                        }
                    }).catch(function (err) {
                        console.log(err)
                        that.$message.error(err);
                    })
                    // this.$message({
                    //     type: 'success',
                    //     message: '删除成功!'
                    // });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },

            searchScript() {
                let that = this;
                this.$axios.get("/eduservice/xn-table/getName", {"params": {"scriptName": this.scriptName}}).then(function (res) {
                    console.log(res)
                    that.tableData = res.data.data.data;
                    this.reload();
                }).catch(function (err) {

                })
            },

            handleSizeChange(val) {
                let that = this;
                that.limit = val;
                this.getScripts();
            },
            handleCurrentChange(val) {
                let that = this;
                that.current = val;
                this.getScripts();
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
                    that.$axios.delete('/eduservice/xn-table', {
                        data: {
                            "ids": that.idList,
                        }
                    }).then((res) => {
                        that.getScripts();
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
            dbclick(row, event, column) {
                row.status = !row.status
                if (row.threads !== "") {
                    this.$axios.put("/eduservice/xn-table/", row).then(function (res) {
                    }).catch(function (err) {
                        console.log(err)
                    })
                    this.$axios.post("/eduservice/xn-table/modifyThreads", row).then(function (res) {
                    })
                }
            },

            runScript(row) {
                let that = this;
                that.logContent = "";
                that.$set(row, 'runLoading', true)
                this.$axios.post("/eduservice/xn-table/run", row).then(function (res) {
                    if (res.data.code === 20000) {
                        that.$set(row, 'runLoading', false)
                        this.message.success("运行成功")
                    }
                }).catch(function (err) {
                    that.$set(row, 'runLoading', false)
                    console.log(err)
                })
            },

            openScript(row) {
                let that = this;
                that.scriptModifyForm = {};
                that.dialogVisible = true;
                that.dialogTitle = "修改模块";
                that.scriptModifyForm = JSON.parse(JSON.stringify(row));
                this.$axios.post("/eduservice/xn-table/getScriptContent", row).then(function (res) {
                    if (res.data.code === 20000) {
                        that.content = res.data.data.content;
                    }
                }).catch(function (err) {
                    console.log(err)
                })
            },

            readLogInfo(row){
                let that = this;
              this.$axios.get("/eduservice/xn-table/getLog/" + row.id).then(function (res) {
                  if(res.data.code === 20000){
                    that.logContent = that.logContent + "\n" + res.data.data.data;
                  }
              }).catch(function (err) {
                  console.log(err)
              })
            },

            openLog(row){
                let that = this;
                that.dialogVisible = true;
                that.logDialog = true;
                that.logContent = ""
                var myVar = setInterval(function () {
                    that.readLogInfo(row);
                    if(that.logContent.indexOf("generating dashboard") !== -1 || that.logContent.indexOf("Dashboard generated") !== -1) {
                        clearInterval(myVar)
                        console.log(that.logContent.indexOf("Dashboard generated"))
                    }
                }, 1000);



            },

            submit() {
                let that = this;

                this.$axios.put("/eduservice/xn-table/modify", {
                    "scriptName": that.scriptModifyForm.scriptName,
                    "id": that.scriptModifyForm.id,
                    "content": that.content
                }).then(function (res) {
                    if (res.data.code === 20000) {

                    }
                }).catch(function (err) {
                    console.log(err)
                })
            },

            downloadScript() {
                let that = this;
                that.idList = []
                that.multipleSelection.forEach(i => {
                    that.idList.push(i.id)
                });

                this.$axios({
                    method: 'post',
                    url: '/eduservice/xn-table/download',
                    data: that.idList,
                }).then(function (res) {
                    if(res.data.code === 20000){
                        that.urlList = res.data.data.data;
                    }
                    that.urlList.forEach(i=>{
                        console.log(i)
                    })
                }).catch(function (err) {
                    console.log(err)
                })
            },

            runManyScripts(){
                let that = this;
                that.idList = []
                that.multipleSelection.forEach(i => {
                    that.idList.push(i.id)
                });
                this.$axios.post("/eduservice/xn-table/runs", that.idList).then(function (res) {

                }).catch(function (err) {
                    console.log(err)
                })
            }
        }
    }
</script>
