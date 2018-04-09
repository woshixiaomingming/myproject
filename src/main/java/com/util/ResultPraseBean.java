package com.util;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultPraseBean {

    private static Logger logger = Logger.getLogger(ResultPraseBean.class);
    private static Map<String, Field> dbTableField = new HashMap<String, Field>();

    public static <T extends Bean> List<T> getResultPraseBean (ResultSet resultSet, Class<T> classEntity, String db) {
        if (resultSet == null) {
            return null;
        }
        List<T> dataBean = null;
        ResultSetMetaData data = null;
        try {
            data = resultSet.getMetaData();
            int column = data.getColumnCount();
            dataBean = new ArrayList<T>();
            while (resultSet.next()) {
                T obj = classEntity.newInstance();
                for (int i = 0; i < column; i++) {
                    String label = data.getColumnLabel(i + 1) == null ? data.getColumnName(i + 1) : data.getColumnLabel(i + 1);
                    Field field = getModelField(db, classEntity, label);
                    if (field == null) {
                        continue;
                    }
                    Object result = resultSet.getObject(label);
                    if (result == null) {
                        continue;
                    }
                    field.setAccessible(true);
                    field.set(obj, result);
                }
                dataBean.add(obj);
            }
            return dataBean;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("结果集转换异常");
            return null;
        }
    }

    public static Field getModelField (String db, Class<?> classEntity, String label) {
        String name = classEntity.getSimpleName();
        Field field = dbTableField.get((db + "_" + name + "_" + label).toLowerCase());
        if (field != null) {
            return field;
        }
        //获取所有的反射类中的field
        Field[] classField = classEntity.getDeclaredFields();
        if (classField == null || classField.length <= 0) {
            return null;
        }
        for (int i = 0, size = classField.length; i < size; i++) {
            dbTableField.put((db + "_" + name + "_" + classField[i].getName()).toLowerCase(), classField[i]);
        }
        return dbTableField.get((db + "_" + name + "_" + label).toLowerCase());
    }
}
