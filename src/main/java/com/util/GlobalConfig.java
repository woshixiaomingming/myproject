package com.util;

import java.util.ResourceBundle;

public class GlobalConfig {

        private static ResourceBundle rb = null;
        private static GlobalConfig config = null;
        private static Object lock = new Object();
        private static String config_file = "main";

        public GlobalConfig () {
            if (rb == null) {
               rb = ResourceBundle.getBundle(config_file);
            }
        }

        public static void getInstance () {
            if (config == null) {
                synchronized (lock) {
                    config = new GlobalConfig();
                }
            }
        }

        public static String getValue (String key) {
            if (config == null) {
                getInstance();
            }
            try {
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
}
