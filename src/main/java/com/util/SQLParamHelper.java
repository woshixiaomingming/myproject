package com.util;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Timestamp;

public class SQLParamHelper {

    private static Logger logger = Logger.getLogger(SQLParamHelper.class);

    /**
     * 插入当前数据
     * @param param
     * @param statement
     * @return
     */
    public static void setParam (Object[] param, PreparedStatement statement) {
        try {
            if (param != null && param.length > 0) {
                for (int i = 0, size = param.length; i < size; i++) {
                    if (Integer.class.isInstance(param[i])) {
                        statement.setInt(i + 1, Integer.parseInt(param[i].toString()));
                    }
                    if (Short.class.isInstance(param[i])) {
                        statement.setShort(i + 1, Short.parseShort(param[i].toString()));
                    }
                    if (Double.class.isInstance(param[i])) {
                        statement.setDouble(i + 1, Double.parseDouble(param[i].toString()));
                    }
                    if (Character.class.isInstance(param[i]) || String.class.isInstance(param[i])) {
                        statement.setString(i + 1, param[i].toString());
                    }
                    if (BigDecimal.class.isInstance(param[i])) {
                        statement.setBigDecimal(i + 1, new BigDecimal(param[i].toString()));
                    }
                    if (Byte.class.isInstance(param[i])) {
                        statement.setByte(i + 1, Byte.parseByte(param[i].toString()));
                    }
                    if (Byte[].class.isInstance(param[i])) {
                        statement.setBytes(i + 1, param[i].toString().getBytes());
                    }
                    if (Float.class.isInstance(param[i])) {
                        statement.setFloat(i + 1, Float.parseFloat(param[i].toString()));
                    }
                    if (Date.class.isInstance(param[i])) {
                        statement.setDate(i + 1, Date.valueOf(param[i].toString()));
                    }
                    if (Time.class.isInstance(param[i])) {
                        statement.setTime(i + 1, Time.valueOf(param[i].toString()));
                    }
                    if (Timestamp.class.isInstance(param[i])) {
                        statement.setTimestamp(i + 1, Timestamp.valueOf(param[i].toString()));
                    }
                    if (Long.class.isInstance(param[i])) {
                        statement.setLong(i + 1, Long.valueOf(param[i].toString()));
                    }
                    if (Boolean.class.isInstance(param[i])) {
                        if (param[i].equals(true)) {
                            statement.setInt(i + 1, 1);
                        } else {
                            statement.setInt(i + 1, 0);
                        }
                    }
                    if (param[i] != null) {
                        statement.setString(i + 1, String.valueOf(param[i]));
                    } else {
                        statement.setString(i + 1, null);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据插入sql失败");
        }
    }
}
