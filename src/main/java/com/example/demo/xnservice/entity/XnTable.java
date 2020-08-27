package com.example.demo.xnservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

import java.io.Serializable;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author zhengxingqu
 * @since 2020-08-13
 */
//@ApiModel(value = "XnTable对象", description = "")
public class XnTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @NotBlank(message = "scriptName不能为空")
    @TableField("script_name")
    private String scriptName;

    @TableField("result_file")
    private String resultFile;

    @TableField("run_result")
    private String runResult;

    //    @TableField("createTime")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("deleted")
    @TableLogic
    private Boolean deleted;

    private String threads;

    private Boolean status;

    private String runDurationTime;

    private String rampTime;

    private String logName;

    @Override
    public String toString() {
        return "XnTable{" +
                "id='" + id + '\'' +
                ", scriptName='" + scriptName + '\'' +
                ", resultFile='" + resultFile + '\'' +
                ", runResult='" + runResult + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                ", threads='" + threads + '\'' +
                ", status=" + status +
                ", runDurationTime='" + runDurationTime + '\'' +
                ", rampTime='" + rampTime + '\'' +
                ", logName='" + logName + '\'' +
                '}';
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getRunDurationTime() {
        return runDurationTime;
    }

    public void setRunDurationTime(String runDurationTime) {
        this.runDurationTime = runDurationTime;
    }

    public String getRampTime() {
        return rampTime;
    }

    public void setRampTime(String rampTime) {
        this.rampTime = rampTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getThreads() {
        return threads;
    }

    public void setThreads(String threads) {
        this.threads = threads;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getResultFile() {
        return resultFile;
    }

    public void setResultFile(String resultFile) {
        this.resultFile = resultFile;
    }

    public String getRunResult() {
        return runResult;
    }

    public void setRunResult(String runResult) {
        this.runResult = runResult;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
