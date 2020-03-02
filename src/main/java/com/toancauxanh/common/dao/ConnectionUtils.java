package com.toancauxanh.common.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.toancauxanh.database.entity.DatabaseType;
import com.toancauxanh.database.entity.InfoDatabaseDto;

public class ConnectionUtils {

    public static Connection getConnection(InfoDatabaseDto infoDatabase) {

        String url = "";

        if (infoDatabase.getTypeDB() == DatabaseType.MS_SQL_SERVER) {

            url = "jdbc:sqlserver://" + infoDatabase.getHost() + ":" + infoDatabase.getPort() + ";databaseName="
                    + infoDatabase.getDatabaseName();
        } else if (infoDatabase.getTypeDB() == DatabaseType.MARIA_DB || infoDatabase.getTypeDB() == DatabaseType.MYSQL) {

            url = infoDatabase.getTypeDB().getJdbc() + infoDatabase.getHost() + ":" + infoDatabase.getPort() + "/"
                    + infoDatabase.getDatabaseName()
                    + "?useSSL=false&cachePrepStmts=true&prepStmtCacheSize=500&prepStmtCacheSqlLimit=2048&useUnicode=true&characterEncoding=UTF-8&useLegacyDatetimeCode=false&serverTimezone=UTC";
        } else {
            
            url = "jdbc:oracle:thin:@"+infoDatabase.getHost()+":"+infoDatabase.getPort()+":"+infoDatabase.getDatabaseName()+"";
            
        }
        Connection conn = null;
        try {

            Class.forName(infoDatabase.getTypeDB().getDriver());

            conn = DriverManager.getConnection(url, infoDatabase.getUser(), infoDatabase.getPassword());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;

    }

    public static Connection getConnectionStaging() {

        String url = "jdbc:mariadb://localhost/Database_staging?cachePrepStmts=true&prepStmtCacheSize=500&prepStmtCacheSqlLimit=2048&useUnicode=true&characterEncoding=UTF-8";

        Connection conn = null;
        try {

            Class.forName("org.mariadb.jdbc.Driver");

            conn = DriverManager.getConnection(url,"root","123456789");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

}
