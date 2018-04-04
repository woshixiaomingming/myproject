package com.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Data {

    private static Logger logger = Logger.getLogger(Data.class);
    public static String db = "main";

    public static  <T extends Bean> List<T> QueryT (String sql, Object[] param, Class<T> classEntity) {
        return QueryT(sql, param, db, classEntity);
    }

    public static <T extends Bean> List<T> QueryT (String sql, Object[] param, String db, Class<T> classEntity) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<T> query = new ArrayList<T>();
        try {
            connection = com.util.Connection.getConnection(db);
            statement = connection.prepareStatement(sql);
            //SQLParamHelper.setParam(param, statement);
            resultSet = statement.executeQuery();
            query = ResultPraseBean.getResultPraseBean(resultSet, classEntity, db);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用当前数据源" + db + "的连接异常");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    resultSet = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("关闭数据源" + db + "数据库连接异常：" + e);
            }
        }
        return query;
    }

}
