package com.seeksolution.projectdemo.Model;

import java.util.ArrayList;

public class SliderResponse {
    public String code;
    public String message;
    public ArrayList<Slider> data;
    public boolean error;
    public boolean status;

    public SliderResponse(String code, String message, ArrayList<Slider> data, boolean error, boolean status) {
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

    public ArrayList<Slider> getData() {
        return data;
    }

    public boolean isError() {
        return error;
    }

    public boolean isStatus() {
        return status;
    }
}
