package com.toancauxanh.database.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toancauxanh.common.dao.ConnectionUtils;
import com.toancauxanh.database.entity.InfoColumnDto;
import com.toancauxanh.database.entity.InfoDatabaseDto;
import com.toancauxanh.database.entity.InfoTableDto;

public class MySQLDAO implements DatabaseStrategy {

    /**
     * Syntax of MYSQL database get list info tables from input info database
     * 
     * @param infoDatabase
     * @param conn
     * @return List info Table of Database
     */
    public List<InfoTableDto> getInfoTables(InfoDatabaseDto infoDatabase) {

        String sql = "SELECT TABLE_SCHEMA, TABLE_NAME FROM information_schema.TABLES where TABLE_SCHEMA = ?";

        List<InfoTableDto> infoTables = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionUtils.getConnection(infoDatabase);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, infoDatabase.getDatabaseName());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                InfoTableDto infoTable = new InfoTableDto();

                infoTable.setTableSchema(rs.getString("TABLE_SCHEMA"));
                infoTable.setTableName(rs.getString("TABLE_NAME"));

                infoTable.setInfoColumns(getInfoColumns(infoTable, conn));

                infoTables.add(infoTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return infoTables;
    }

    /**
     * Syntax of MYSQL database get list info columns from input info Table
     * 
     * @param infoTable
     * @param conn
     * @return List info columns of Table
     */
    @Override
    public List<InfoColumnDto> getInfoColumns(InfoTableDto infoTable, Connection conn) {

        String sql = "SELECT TABLE_NAME, TABLE_SCHEMA, COLUMN_NAME, COLUMN_TYPE FROM information_schema.`COLUMNS` c WHERE TABLE_SCHEMA  = ? and TABLE_NAME = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<InfoColumnDto> infoColumns = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, infoTable.getTableSchema());
            pstmt.setString(2, infoTable.getTableName());

            rs = pstmt.executeQuery();
            while (rs.next()) {

                InfoColumnDto infoColumn = new InfoColumnDto();

                infoColumn.setTableName(rs.getString("TABLE_NAME"));
                infoColumn.setTableSchema(rs.getString("TABLE_SCHEMA"));
                infoColumn.setColumnName(rs.getString("COLUMN_NAME"));
                infoColumns.add(infoColumn);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return infoColumns;
    }

}
