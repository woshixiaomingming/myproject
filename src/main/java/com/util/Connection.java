package com.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

public class Connection {

    public static DruidPooledConnection getConnection (String db) {
        try {
            DruidDataSource druidDataSource = DataSource.getDataSource(db);
            DruidPooledConnection connection = druidDataSource.getConnection();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("无法与数据库建立连接");
        }
    }

}
