package com.example.demo.xnservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.BlockingDeque;

/**
 * <p>
 *
 * </p>
 *
 * @author zhengxingqu
 * @since 2020-08-15
 */
public class ScriptInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String scriptName;

    private String errorCount;

    private String meanRestime;

    private String minRestime;

    private String maxRestime;

    private String oneRestime;

    private String twoRestime;

    private String threeRestime;

    private String throughput;

    private String transaction;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableLogic
    private Boolean deleted;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(String errorCount) {
        this.errorCount = errorCount;
    }

    public String getMeanRestime() {
        return meanRestime;
    }

    public void setMeanRestime(String meanRestime) {
        this.meanRestime = meanRestime;
    }

    public String getMinRestime() {
        return minRestime;
    }

    public void setMinRestime(String minRestime) {
        this.minRestime = minRestime;
    }

    public String getMaxRestime() {
        return maxRestime;
    }

    public void setMaxRestime(String maxRestime) {
        this.maxRestime = maxRestime;
    }

    public String getOneRestime() {
        return oneRestime;
    }

    public void setOneRestime(String oneRestime) {
        this.oneRestime = oneRestime;
    }

    public String getTwoRestime() {
        return twoRestime;
    }

    public void setTwoRestime(String twoRestime) {
        this.twoRestime = twoRestime;
    }

    public String getThreeRestime() {
        return threeRestime;
    }

    public void setThreeRestime(String threeRestime) {
        this.threeRestime = threeRestime;
    }

    public String getThroughput() {
        return throughput;
    }

    public void setThroughput(String throughput) {
        this.throughput = throughput;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "ScriptInfo{" +
                "id=" + id +
                ", scriptName=" + scriptName +
                ", errorCount=" + errorCount +
                ", meanRestime=" + meanRestime +
                ", minRestime=" + minRestime +
                ", maxRestime=" + maxRestime +
                ", oneRestime=" + oneRestime +
                ", twoRestime=" + twoRestime +
                ", threeRestime=" + threeRestime +
                ", throughput=" + throughput +
                ", transaction=" + transaction +
                "}";
    }
}
