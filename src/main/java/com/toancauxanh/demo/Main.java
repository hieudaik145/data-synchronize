package com.toancauxanh.demo;

import com.toancauxanh.database.entity.DatabaseType;
import com.toancauxanh.database.entity.InfoDatabase;

public class Main {

    public static void main(String[] args) {

        InfoDatabase infoDatabase = new InfoDatabase();

        infoDatabase.setHost("localhost");
        infoDatabase.setDatabaseName("database_mariadb_test");
        infoDatabase.setPassword("123456789");
        infoDatabase.setUser("root");
        infoDatabase.setPort("3306");
        infoDatabase.setTypeDB(DatabaseType.MARIA_DB);

        InfoDatabase infoDBMysql = new InfoDatabase();

        infoDBMysql.setHost("localhost");
        infoDBMysql.setDatabaseName("database_mysql_test");
        infoDBMysql.setPassword("123456789");
        infoDBMysql.setUser("root");
        infoDBMysql.setTypeDB(DatabaseType.MYSQL);
        infoDBMysql.setPort("3306");

    }
}
