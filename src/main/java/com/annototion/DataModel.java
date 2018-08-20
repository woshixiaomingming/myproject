package com.annototion;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataModel {
    /**
     * 表名
     * @return
     */
    String table() default "";

    /**
     * 库名
     * @return
     */
    String dateBase() default "";

    /**
     * 数据库的配置ip
     * @return
     */
    String dataIp() default "";

    /**
     * 是否使用默认的数据库和表
     * @return
     */
    boolean isDefealt() default false;

    /**
     * 数据库用户名
     * @return
     */
    String username() default "";

    /**
     * 数据库密码
     * @return
     */
    String password() default "";

}
