package org.example.config;

public class DataBaseConfig {
    //public static final String URL = "jdbc:mariadb://127.0.0.1:3306/AM_DB_25_03?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
    public static final String URL;
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static final String DRIVER_CLASS_NAME = "org.mariadb.jdbc.Driver";

    static {
        String url = "jdbc:mariadb://127.0.0.1:3306/AM_DB_25_03";
        String queryParam = "?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
        URL = url + queryParam;
    }

}
