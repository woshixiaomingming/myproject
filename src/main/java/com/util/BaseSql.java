package com.util;

import com.annototion.DataModel;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseSql<T extends Bean> extends Data {

    public BaseSql() {
        super();
        init();
    }

    private Class<T> cls;

    public Class<T> getCls() {
        return cls;
    }

    /**
     * 获取当前的数据的信息
     */
    public void init () {
        //获取当前的注解下的数据
        Class clss = getClass();
        Type type = clss.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] types = parameterizedType.getActualTypeArguments();
            this.cls = (Class<T>) types[0];
        } else {
            this.cls = (Class<T>) type;
        }
        setClassEntity(cls);
        //获取注解信息
        DataModel dataModel = (DataModel) cls.getAnnotation(DataModel.class);
        if (StringUtils.isEmpty(dataModel.dateBase())) {
            throw new NullPointerException("请在bean的注解上配置主库");
        }
        setDataDB(dataModel.dateBase());
        //判断是否开启注解
        if (dataModel.isDefealt()) {
            setConfig(true);
            if (StringUtils.isEmpty(dataModel.dataIp()) || StringUtils.isEmpty(dataModel.password()) || StringUtils.isEmpty(dataModel.username())) {
                throw new NullPointerException("请在bean的注解上配置主库");
            }
            setDataIp(dataModel.dataIp());
            setPassword(dataModel.password());
            setUsername(dataModel.username());
        }
    }
}
