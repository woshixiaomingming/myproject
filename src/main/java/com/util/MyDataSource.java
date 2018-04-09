package com.util;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

public class MyDataSource {

    private static Logger logger = Logger.getLogger(MyDataSource.class);
    private static ConcurrentHashMap<String, DataSource> datasource = new ConcurrentHashMap<String, DataSource>();
    private static ConcurrentHashMap<String, ConnectProp> connectProp = new ConcurrentHashMap<String, ConnectProp>();

    public synchronized static javax.sql.DataSource getDataSource (String db) {
        javax.sql.DataSource dataSource = datasource.get(db);
        if (dataSource == null) {
            ConnectProp connectProp = initConnectProp(db);
            if (connectProp != null) {
                //初始化最新的数据库连接
                javax.sql.DataSource druidDataSource = setDataSource(connectProp.ip, connectProp.db, connectProp.username, connectProp.password);
                datasource.put(db, druidDataSource);
                return druidDataSource;
            } else {
                logger.error("未找到当前的数据源");
                return null;
            }
        }
        return dataSource;
    }

    public synchronized static javax.sql.DataSource setDataSource (String ip, String db, String username, String password) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://" + ip + "/" + db + "?useUnicode=true&characterEncoding=UTF-8");
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
                String mysqlDB = GlobalConfig.getValue("mysql_" + db);
                if (db.equals(mysqlDB)) {
                    String mysqlIP = GlobalConfig.getValue("mysql_" + db + "_IP");
                    String mysqlUsername = GlobalConfig.getValue("mysql_" + db + "_username");
                    String mysqlPassword = GlobalConfig.getValue("mysql_" + db + "_password");
                    connectProp1 = new ConnectProp(mysqlIP, db, mysqlUsername, mysqlPassword);
                    connectProp.put(db, connectProp1);
                    return connectProp1;
                } else {
                    logger.error("请配置当前的数据源" + db + "信息");
                    return null;
                }
            } else {
                return connectProp.get(db);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取数据源的信息产生异常" +  e);
            return null;
        }
    }
}
