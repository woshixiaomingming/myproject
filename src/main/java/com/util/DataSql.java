package com.util;

/**
 * 配置数据库的信息
 */
public class DataSql {

    private String dataDB;

    private Class classEntity;

    private boolean isConfig;

    private String dataIp;

    private String username;

    private String password;

    public String getDataDB() {
        return dataDB;
    }

    public void setDataDB(String dataDB) {
        this.dataDB = dataDB;
    }

    public Class getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(Class classEntity) {
        this.classEntity = classEntity;
    }

    public boolean isConfig() {
        return isConfig;
    }

    public void setConfig(boolean config) {
        isConfig = config;
    }

    public String getDataIp() {
        return dataIp;
    }

    public void setDataIp(String dataIp) {
        this.dataIp = dataIp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DataSql () {

    }
}
