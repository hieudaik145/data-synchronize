package com.toancauxanh.database.service;

import java.sql.Connection;

import com.toancauxanh.common.dao.ConnectionDAO;
import com.toancauxanh.database.entity.InfoDatabase;

public class InfoDatabaseService {

    public Connection getConnection(InfoDatabase infoDatabase) {

        ConnectionDAO connectionDAO = new ConnectionDAO();

        return connectionDAO.getConnection(infoDatabase);
    }

}
