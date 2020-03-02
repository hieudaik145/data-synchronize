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

public class OracleSQLDAO implements DatabaseStrategy {

    /**
     * Syntax of Oracle get list info tables from input info database
     * 
     * @param infoDatabase
     * @param conn
     * @return List info Table of Database
     */
    @Override
    public List<InfoTableDto> getInfoTables(InfoDatabaseDto infoDatabase) {

        String sql = "SELECT OWNER, TABLE_NAME FROM all_tables WHERE owner = ? AND TABLE_NAME = ?";

        List<InfoTableDto> infoTables = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionUtils.getConnection(infoDatabase);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, infoDatabase.getSchemaName());
            pstmt.setString(2, "MIS_HOKHAU");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                InfoTableDto infoTable = new InfoTableDto();

                infoTable.setTableSchema(rs.getString("OWNER"));
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
     * Syntax of Oracle database get list info columns from input info Table
     * 
     * @param infoTable
     * @param conn
     * @return List info columns of Table
     */
    @Override
    public List<InfoColumnDto> getInfoColumns(InfoTableDto infoTable, Connection conn) {

        String sql = "SELECT OWNER, TABLE_NAME, COLUMN_NAME FROM all_tab_columns WHERE owner= ? and TABLE_NAME = ?";

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
                infoColumn.setTableSchema(rs.getString("OWNER"));
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
