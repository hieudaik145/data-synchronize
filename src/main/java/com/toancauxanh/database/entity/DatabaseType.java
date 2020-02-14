package com.toancauxanh.database.entity;

import lombok.Getter;

@Getter
public enum DatabaseType {

    MYSQL("jdbc:mysql://","com.mysql.jdbc.Driver"),
    
    MARIA_DB("jdbc:mariadb://","org.mariadb.jdbc.Driver"),
    
    MS_SQL_SERVER("jdbc:sqlserver://","com.microsoft.sqlserver.jdbc.SQLServerDriver"),

    ORACLE("jdbc:oracle:thin:@","oracle.jdbc.driver.OracleDriver");
    
    private String jdbc;
    
    private String driver;

    private DatabaseType(String jdbc, String driver) {
        this.jdbc = jdbc;
        this.driver = driver;
    }

}
