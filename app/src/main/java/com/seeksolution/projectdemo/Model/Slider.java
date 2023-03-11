package com.seeksolution.projectdemo.Model;

import java.util.ArrayList;

public class Slider{
    public String id;
    public String slider_name;
    public String slider_pic;

    public Slider(String id, String slider_name, String slider_pic) {
        this.id = id;
        this.slider_name = slider_name;
        this.slider_pic = slider_pic;
    }

    public String getId() {
        return id;
    }

    public String getSlider_name() {
        return slider_name;
    }

    public String getSlider_pic() {
        return slider_pic;
    }

}

