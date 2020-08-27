package com.example.demo.commons;

public enum StatusCode {
    SUCCESS(20000),ERROR(20001);

    private int code;
    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
