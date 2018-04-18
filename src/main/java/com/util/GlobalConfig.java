package com.util;

import java.util.ResourceBundle;

public class GlobalConfig {

        private static ResourceBundle rb = null;
        private static GlobalConfig config = null;
        private static Object lock = new Object();
        private static final String config_file = "config";
        //String main = GlobalConfig.getValue("mysql_main");

        public GlobalConfig () {
            if (null == rb) {
               rb = ResourceBundle.getBundle(config_file);
            }
        }

        public static void getInstance () {
            synchronized (lock) {
                if (null == config) {
                    config = new GlobalConfig();
                }
            }
        }

        public static String getValue (String key) {
            if (null == config) {
                getInstance();
            }
            try {
                if (null == rb) {
                    return null;
                } else {
                    return rb.getString(key);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

    public static void main(String[] args) {
        System.out.println(getValue("mysql_main"));
    }
}
