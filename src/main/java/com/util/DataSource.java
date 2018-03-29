package com.util;

import com.alibaba.druid.pool.DruidDataSource;

import java.util.concurrent.ConcurrentHashMap;

public class DataSource {

    private static ConcurrentHashMap<String, DruidDataSource> datasource = new ConcurrentHashMap<String, DruidDataSource>();
    private static ConcurrentHashMap<String, ConnectProp> connectProp = new ConcurrentHashMap<String, ConnectProp>();

    public static DruidDataSource getDataSource (String db) {
        DruidDataSource dataSource = datasource.get(db);
        if (dataSource == null) {
            ConnectProp connectProp = initConnectProp(db);
            if (connectProp != null) {
                //初始化最新的数据库连接
                DruidDataSource druidDataSource = setDataSource(connectProp.ip, connectProp.db, connectProp.username, connectProp.password);
                datasource.put(db, druidDataSource);
                return druidDataSource;
            } else {
                return null;
            }
        }
        return dataSource;
    }

    public static DruidDataSource setDataSource (String ip, String db, String username, String password) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://" + ip + "/" + db + "?useUnicode=true&amp;characterEncoding=UTF-8");
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(5);
        druidDataSource.setMaxActive(20);
        druidDataSource.setMinIdle(5);
        druidDataSource.setMaxWait(30000);
        return druidDataSource;
    }

    public static ConnectProp initConnectProp (String db) {
        ConnectProp connectProp1 = null;
        try {
            if (null == connectProp.get(db)) {
                String mysqlDB = GlobalConfig.getValue("mysql" + db);
                if (db == mysqlDB) {
                    String mysqlIP = GlobalConfig.getValue("mysql" + db + "IP");
                    String mysqlUsername = GlobalConfig.getValue("mysql" + db + "username");
                    String mysqlPassword = GlobalConfig.getValue("mysql" + db + "password");
                    connectProp1 = new ConnectProp(mysqlIP, db, mysqlUsername, mysqlPassword);
                    connectProp.put(db, connectProp1);
                    return connectProp1;
                } else {
                    return null;
                }
            } else {
                return connectProp.get(db);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
