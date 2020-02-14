package com.toancauxanh.database.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toancauxanh.database.entity.InfoColumn;
import com.toancauxanh.database.entity.InfoDatabase;
import com.toancauxanh.database.entity.InfoTable;

public class MySQLDAO implements DatabaseDAOStragery {

    /**
     * Syntax of MYSQL database get list info tables from input info database
     * 
     * @param infoDatabase
     * @param conn
     * @return List info Table of Database
     */
    @Override
    public List<InfoTable> getInfoTables(InfoDatabase infoDatabase, Connection conn) {

        String sql = "SELECT TABLE_SCHEMA, TABLE_NAME FROM information_schema.TABLES where TABLE_SCHEMA = ?";

        List<InfoTable> infoTables = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, infoDatabase.getDatabaseName());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                InfoTable infoTable = new InfoTable();

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
    public List<InfoColumn> getInfoColumns(InfoTable infoTable, Connection conn) {

        String sql = "SELECT TABLE_NAME, TABLE_SCHEMA, COLUMN_NAME, DATA_TYPE FROM information_schema.`COLUMNS` c WHERE TABLE_SCHEMA  = ? and TABLE_NAME = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<InfoColumn> infoColumns = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, infoTable.getTableSchema());
            pstmt.setString(2, infoTable.getTableName());

            rs = pstmt.executeQuery();
            while (rs.next()) {

                InfoColumn infoColumn = new InfoColumn();

                infoColumn.setTableName(rs.getString("TABLE_NAME"));
                infoColumn.setTableSchema(rs.getString("TABLE_SCHEMA"));
                infoColumn.setColumnName(rs.getString("COLUMN_NAME"));
                infoColumn.setDataType(rs.getString("DATA_TYPE"));
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
