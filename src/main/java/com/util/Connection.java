package com.util;

import org.apache.log4j.Logger;

import javax.sql.DataSource;

public class Connection {

    private static Logger logger = Logger.getLogger(Connection.class);

    public static java.sql.Connection getConnection (String db) {
        try {
            DataSource druidDataSource = MyDataSource.getDataSource(db);
            java.sql.Connection connection = druidDataSource.getConnection();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("无法与数据库建立连接:" + e);
            throw new RuntimeException("无法与数据库建立连接");
        }
    }
}
