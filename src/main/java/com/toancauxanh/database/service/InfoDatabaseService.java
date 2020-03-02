package com.toancauxanh.database.service;

import java.sql.Connection;

import com.toancauxanh.common.dao.ConnectionUtils;
import com.toancauxanh.database.entity.InfoDatabaseDto;

public class InfoDatabaseService {

    public Connection getConnection(InfoDatabaseDto infoDatabase) {

        ConnectionUtils connectionDAO = new ConnectionUtils();

        return connectionDAO.getConnection(infoDatabase);
    }

}
