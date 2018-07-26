package com.util;

import java.io.Serializable;

/**
 * 数据库配置信息
 */
public class ConnectProp implements Serializable {

    public String ip;
    public String db = "main";
    public String username;
    public String password;

    public ConnectProp (String ip, String db, String username, String password) {
        this.ip = ip;
        this.db = db;
        this.username = username;
        this.password = password;
    }

    public ConnectProp () {

    }
}
