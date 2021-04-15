package com.example.lxc.wangyiyunmusic.model;

import com.blankj.utilcode.util.EncryptUtils;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class UserModel extends RealmObject {
    public UserModel() {
    }

    public UserModel(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    @PrimaryKey
    private String phone;

    @Required
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
