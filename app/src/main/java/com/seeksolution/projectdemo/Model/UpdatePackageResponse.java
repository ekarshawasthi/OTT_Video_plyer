package com.seeksolution.projectdemo.Model;

public class UpdatePackageResponse {
    public String code;
    public String message;
    public Subscribed data;
    public boolean error;
    public boolean status;

    public UpdatePackageResponse(String code, String message, Subscribed data, boolean error, boolean status) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.error = error;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Subscribed getData() {
        return data;
    }

    public boolean isError() {
        return error;
    }

    public boolean isStatus() {
        return status;
    }
}
