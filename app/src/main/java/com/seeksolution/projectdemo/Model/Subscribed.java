package com.seeksolution.projectdemo.Model;

public class Subscribed {
    public String user_id;
    public CurrentPackage current_package;

    public Subscribed(String user_id, CurrentPackage current_package) {
        this.user_id = user_id;
        this.current_package = current_package;
    }

    public String getUser_id() {
        return user_id;
    }

    public CurrentPackage getCurrent_package() {
        return current_package;
    }
}
