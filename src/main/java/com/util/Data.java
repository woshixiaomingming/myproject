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
            SQLParamHelper.setParam(param, statement);
            resultSet = statement.executeQuery();
            if (resultSet == null)
                query = null;
            else
                query = ResultPraseBean.getResultPraseBean(resultSet, classEntity, db);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用当前数据源" + db + "的连接异常");
        } finally {
            com.util.Connection.closeConnection(connection, resultSet, statement);
        }
        return query;
    }

    /**
     *
     * @param sql
     * @param param
     * @param db
     * @param classEntity
     * @param <T>
     * @return
     */
    public static <T extends Bean> int update (String sql, Object[] param, String db, Class<T> classEntity) {
        Connection connection = null;
        PreparedStatement statement = null;
        int result = -1;
        try {
            connection = com.util.Connection.getConnection(db);
            statement = connection.prepareStatement(sql);
            SQLParamHelper.setParam(param, statement);
            result = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用当前数据源" + db + "的连接异常");
        } finally {
            com.util.Connection.closeConnection(connection, null, statement);
        }
        return result;
    }

    public static <T extends Bean> int updateTrans (List<> sql, Object[] param, String db, Class<T> classEntity) {

    }

}
