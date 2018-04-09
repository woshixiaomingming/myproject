package com.util;

import org.apache.log4j.Logger;

import javax.sql.DataSource;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static void closeConnection (java.sql.Connection connection, ResultSet resultSet, PreparedStatement preparedStatement) {
        try {
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
            if (preparedStatement != null) {
                preparedStatement.close();
                preparedStatement = null;
            }
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("关闭连接异常：" + e);
            throw new RuntimeException("关闭连接异常");
        }
    }
}
