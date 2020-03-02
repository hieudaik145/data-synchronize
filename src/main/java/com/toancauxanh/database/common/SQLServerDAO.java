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

public class SQLServerDAO implements DatabaseStrategy {

    /**
     * Syntax of SQLServer database get list info tables from input info database
     * 
     * @param infoDatabase
     * @param conn
     * @return List info Table of Database
     */
    @Override
    public List<InfoTableDto> getInfoTables(InfoDatabaseDto infoDatabase) {

        String sql = "SELECT TABLE_CATALOG, TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_CATALOG = ?";

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

                infoTable.setTableSchema(rs.getString("TABLE_CATALOG"));
                infoTable.setTableName(rs.getString("TABLE_NAME"));

                // call method get info column from info table
                infoTable.setInfoColumns(getInfoColumns(infoTable, conn));
                // add info table to list info tables
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
     * Syntax of SQLServer database get list info columns from input info Table
     * 
     * @param infoTable
     * @param conn
     * @return List info columns of Table
     */
    @Override
    public List<InfoColumnDto> getInfoColumns(InfoTableDto infoTable, Connection conn) {

        String sql = "SELECT TABLE_CATALOG, TABLE_NAME, COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_CATALOG  = ? and TABLE_NAME = ?";

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
                infoColumn.setTableSchema(rs.getString("TABLE_CATALOG"));
                infoColumn.setTableName(rs.getString("TABLE_NAME"));
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
