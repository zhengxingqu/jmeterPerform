package com.example.demo.xnservice.vo;

public class ScriptModifyVo {
    private String id;
    private String scriptName;
    private String content;
    private String threads;
    private String runDurationTime;
    private String rampTime;

    public String getThreads() {
        return threads;
    }

    public void setThreads(String threads) {
        this.threads = threads;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
