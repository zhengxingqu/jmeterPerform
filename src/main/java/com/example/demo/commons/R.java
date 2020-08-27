package com.example.demo.commons;

import java.util.HashMap;
import java.util.Map;

public class R {
    private String message;
    private Boolean successful;
    private Map<String, Object> data = new HashMap<>();
    private Integer code;
    private Integer total;

    private R() {

    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public static R success() {
        R r = new R();
        r.setCode(StatusCode.SUCCESS.getCode());
        r.setSuccessful(true);
        r.setMessage("成功");
        return r;
    }

    public static R error() {
        R r = new R();
        r.setCode(StatusCode.ERROR.getCode());
        r.setSuccessful(false);
        r.setMessage("失败");
        return r;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R successful(Boolean successful) {
        this.setSuccessful(successful);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R total(String s, Integer total){
        this.setTotal(total);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
