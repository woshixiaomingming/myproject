package com.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 配置数据库的信息
 */
public class DataSql<T extends Bean> {

    private String dataDB;

    private Class<T> classEntity;

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
        super();
    }

    public static DataSql findDataSql (Object... obj) {
        String db = null;
        boolean isConfig = false;
        String ip = null;
        String username = null;
        String password = null;
        try {
            db = (String) obj[0];
            isConfig = (Boolean) obj[4];
            if (isConfig) {
                ip = (String) obj[1];
                username = (String) obj[2];
                password = (String) obj[3];
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException("内部错误，未获取到数据库信息！");
        }
        //不符合规则的配置信息，一律标错误
        if (StringUtils.isEmpty(db) || (isConfig && (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(ip)))) {
            throw new NullPointerException("配置信息错误，请联系开发人员");
        }
        DataSql dataSql = new DataSql();
        dataSql.setDataDB(db);
        if (isConfig) {
            dataSql.setConfig(isConfig);
            dataSql.setDataIp(ip);
            dataSql.setUsername(username);
            dataSql.setPassword(password);
        }
        return dataSql;
    }
}
