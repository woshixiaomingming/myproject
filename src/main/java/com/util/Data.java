package com.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Data extends DataSql {

    private static Logger logger = Logger.getLogger(Data.class);

    public <T extends Bean> List<T> QueryT (String sql, Object[] param) {
        return QueryT(sql, param, getDataDB(), getClassEntity());
    }

    public int insert (String sql, Object[] param) {
        return insert(sql, param, getDataDB());
    }

    public int update (String sql, Object[] param) {
        return update(sql, param, getDataDB());
    }

    public boolean doTrans (List<Allsql> sql) {
        return doTrans(sql, getDataDB());
    }

    public <T extends Bean> List<T> QueryT (String sql, Object[] param, String dataDB, Class<T> classEntity) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<T> query = new ArrayList<T>();
        try {
            connection = com.util.Connection.getConnection(dataDB);
            statement = connection.prepareStatement(sql);
            SQLParamHelper.setParam(param, statement);
            resultSet = statement.executeQuery();
            if (resultSet == null)
                query = null;
            else
                query = ResultPraseBean.getResultPraseBean(resultSet, classEntity, dataDB);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用当前数据源" + dataDB + "的连接异常");
        } finally {
            com.util.Connection.closeConnection(connection, resultSet, statement);
        }
        return query;
    }

    /**
     *
     * @param sql
     * @param param
     * @param dataDB
     * @return
     */
    public int update (String sql, Object[] param, String dataDB) {
        Connection connection = null;
        PreparedStatement statement = null;
        int result = -1;
        try {
            connection = com.util.Connection.getConnection(dataDB);
            statement = connection.prepareStatement(sql);
            SQLParamHelper.setParam(param, statement);
            result = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用当前数据源" + dataDB + "的连接异常");
        } finally {
            com.util.Connection.closeConnection(connection, null, statement);
        }
        return result;
    }

    public boolean doTrans (List<Allsql> sql, String dataDB) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = com.util.Connection.getConnection(dataDB);
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
            logger.error("使用当前数据源" + dataDB + "的连接异常");
            return false;
        } finally {
            com.util.Connection.closeConnection(connection, null, statement);
        }
        return true;
    }

    public boolean delete (String sql, Object[] param, String dataDB) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = com.util.Connection.getConnection(dataDB);
            statement = connection.prepareStatement(sql);
            SQLParamHelper.setParam(param, statement);
            return statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用当前数据源" + dataDB + "的连接异常");
            return false;
        } finally {
            com.util.Connection.closeConnection(connection, null, statement);
        }
    }

    public int insert (String sql, Object[] param, String dataDB) {
        Connection connection = null;
        PreparedStatement statement = null;
        int result = -1;
        try {
            connection = com.util.Connection.getConnection(dataDB);
            statement = connection.prepareStatement(sql);
            SQLParamHelper.setParam(param, statement);
            result = statement.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用当前数据源" + dataDB + "的连接异常");
            return result;
        } finally {
            com.util.Connection.closeConnection(connection, null, statement);
        }
    }

    public <T extends Bean> T GetOne (String sql, Object[] param) {
        List<T> object = QueryT(sql, param);
        return object == null || object.size() < 1 ? null : object.get(0);
    }
}
