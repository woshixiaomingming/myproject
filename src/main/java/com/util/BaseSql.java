package com.util;

import com.annototion.DataModel;

public class BaseSql<T extends Bean> extends Data {

    public BaseSql() {

    }

    private T bean;

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    /**
     * 获取当前的数据的信息
     */
    public void init () {
        //获取当前的注解下的数据
        Class cls = bean.getClass();
        setClassEntity(cls);
        //获取注解信息
        DataModel dataModel = (DataModel) cls.getAnnotation(DataModel.class);
        //判断是否开启注解
        if (dataModel.isDefealt()) {

        } else {

        }
    }
}
