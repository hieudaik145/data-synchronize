package com.toancauxanh.common.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.toancauxanh.database.entity.InfoDatabase;

public class ConnectionDAO {

    public Connection getConnection(InfoDatabase infoDatabase) {

        String url = infoDatabase.getTypeDB().getJdbc() + infoDatabase.getHost() + ":" + infoDatabase.getPort() + "/"
                + infoDatabase.getDatabaseName() + "?&user=" + infoDatabase.getUser() + "&password="
                + infoDatabase.getPassword()
                + "&cachePrepStmts=true&prepStmtCacheSize=500&prepStmtCacheSqlLimit=2048&useUnicode=true&characterEncoding=UTF-8";

        Connection conn = null;
        try {

            Class.forName(infoDatabase.getTypeDB().getDriver());

            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;

    }

    public Connection getConnectionStaging() {

        String url = "jdbc:mariadb://localhost/td-kdldc?user=root&password=123456789&cachePrepStmts=true&prepStmtCacheSize=500&prepStmtCacheSqlLimit=2048&useUnicode=true&characterEncoding=UTF-8";

        Connection conn = null;
        try {

            Class.forName("org.mariadb.jdbc.Driver");

            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

}
