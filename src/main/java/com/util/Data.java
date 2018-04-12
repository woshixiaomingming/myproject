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

    public static int insert (String sql, Object[] param) {
        return insert(sql, param, db);
    }

    public static int update (String sql, Object[] param) {
        return update(sql, param, db);
    }

    public static boolean doTrans (List<Allsql> sql) {
        return doTrans(sql, db);
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
    public static int update (String sql, Object[] param, String db) {
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

    public static boolean doTrans (List<Allsql> sql, String db) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = com.util.Connection.getConnection(db);
            connection.setAutoCommit(false);
            for (Allsql sqls : sql) {
                 statement = connection.prepareStatement(sqls.getSql());
                 SQLParamHelper.setParam(sqls.getObjects(), statement);
                 try {
                     statement.executeUpdate();
                 } catch (Exception e) {
                     e.printStackTrace();
                     logger.error("更新数据事务控制异常");
                     connection.rollback();
                     connection.setAutoCommit(true);
                     return false;
                 }
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用当前数据源" + db + "的连接异常");
            return false;
        } finally {
            com.util.Connection.closeConnection(connection, null, statement);
        }
        return true;
    }

    public static boolean delete (String sql, Object[] param, String db) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = com.util.Connection.getConnection(db);
            statement = connection.prepareStatement(sql);
            SQLParamHelper.setParam(param, statement);
            return statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用当前数据源" + db + "的连接异常");
            return false;
        } finally {
            com.util.Connection.closeConnection(connection, null, statement);
        }
    }

    public static int insert (String sql, Object[] param, String db) {
        Connection connection = null;
        PreparedStatement statement = null;
        int result = -1;
        try {
            connection = com.util.Connection.getConnection(db);
            statement = connection.prepareStatement(sql);
            SQLParamHelper.setParam(param, statement);
            result = statement.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用当前数据源" + db + "的连接异常");
            return result;
        } finally {
            com.util.Connection.closeConnection(connection, null, statement);
        }
    }

    public static <T extends Bean> T GetOne (String sql, Object[] param, Class<T> classEntity) {
        List<T> object = QueryT(sql, param, classEntity);
        return object == null || object.size() == 0 ? null : object.get(0);
    }
}
