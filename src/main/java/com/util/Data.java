package com.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Data extends DataSql {

    private static Logger logger = Logger.getLogger(Data.class);

    public Data () {
        super();
    }


    public <T extends Bean> List<T> QueryT (String sql, Object[] param) {
        return QueryT(sql, param, getClassEntity(), getDataDB(), getDataIp(), getUsername(), getPassword(), isConfig());
    }

    public int insert (String sql, Object[] param) {
        return insert(sql, param, getDataDB(), getDataIp(), getUsername(), getPassword(), isConfig());
    }

    public int update (String sql, Object[] param) {
        return update(sql, param, getDataDB(), getDataIp(), getUsername(), getPassword(), isConfig());
    }

    public boolean doTrans (List<Allsql> sql) {
        return doTrans(sql, getDataDB(), getDataIp(), getUsername(), getPassword(), isConfig());
    }

    public <T extends Bean> List<T> QueryT (String sql, Object[] param, Class<T> cls, Object... obj) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<T> query = new ArrayList<T>();
        DataSql dataSql = DataSql.findDataSql(obj);
        try {
            connection = com.util.Connection.getConnection(dataSql);
            statement = connection.prepareStatement(sql);
            SQLParamHelper.setParam(param, statement);
            resultSet = statement.executeQuery();
            if (resultSet == null)
                query = null;
            else
                query = ResultPraseBean.getResultPraseBean(resultSet, cls, dataSql);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用当前数据源" + dataSql.getDataDB() + "的连接异常");
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
    public int update (String sql, Object[] param, Object... obj) {
        Connection connection = null;
        PreparedStatement statement = null;
        int result = -1;
        DataSql dataSql = DataSql.findDataSql(obj);
        try {
            connection = com.util.Connection.getConnection(dataSql);
            statement = connection.prepareStatement(sql);
            SQLParamHelper.setParam(param, statement);
            result = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用当前数据源" + dataSql.getDataDB() + "的连接异常");
        } finally {
            com.util.Connection.closeConnection(connection, null, statement);
        }
        return result;
    }

    public boolean doTrans (List<Allsql> sql, Object... obj) {
        Connection connection = null;
        PreparedStatement statement = null;
        DataSql dataSql = DataSql.findDataSql(obj);
        try {
            connection = com.util.Connection.getConnection(dataSql);
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
            logger.error("使用当前数据源" + dataSql.getDataDB() + "的连接异常");
            return false;
        } finally {
            com.util.Connection.closeConnection(connection, null, statement);
        }
        return true;
    }

    public boolean delete (String sql, Object[] param, Object... obj) {
        Connection connection = null;
        PreparedStatement statement = null;
        DataSql dataSql = DataSql.findDataSql(obj);
        try {
            connection = com.util.Connection.getConnection(dataSql);
            statement = connection.prepareStatement(sql);
            SQLParamHelper.setParam(param, statement);
            return statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用当前数据源" + dataSql.getDataDB() + "的连接异常");
            return false;
        } finally {
            com.util.Connection.closeConnection(connection, null, statement);
        }
    }

    public int insert (String sql, Object[] param, Object... obj) {
        Connection connection = null;
        PreparedStatement statement = null;
        int result = -1;
        DataSql dataSql = DataSql.findDataSql(obj);
        try {
            connection = com.util.Connection.getConnection(dataSql);
            statement = connection.prepareStatement(sql);
            SQLParamHelper.setParam(param, statement);
            result = statement.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用当前数据源" + dataSql.getDataDB() + "的连接异常");
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
