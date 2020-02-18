package com.toancauxanh.synchronizedata.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.toancauxanh.database.entity.InfoColumn;
import com.toancauxanh.database.entity.InfoTable;

public class DataSynchronizedDAO {

    /**
     * Method batch insert data to database staging
     * 
     * @param datas Value insert to database
     * @param queryString String
     * @param infoTable InfoTable
     * @param conn Connection
     */
    public int batchInsertDataSourceToDataStagin(List<Map<String, Object>> datas, String queryString,
            InfoTable infoTable, Connection conn) {

        PreparedStatement pstmt = null;

        try {
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(queryString);

            for (Map<String, Object> map : datas) {

                int i = 1;
                for (InfoColumn infoColumn : infoTable.getInfoColumns()) {

                    pstmt.setObject(i, map.get(infoColumn.getColumnName()));
                    i++;
                }

                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return 0;
    }

    /**
     * Method connection database and get result set from query string and
     * connection
     * 
     * @param queryString
     * @param conn
     * @return
     * @throws SQLException
     */
    public ResultSet getResultSetFromDataSource(String queryString, Connection conn) {
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.createStatement();

            rs = statement.executeQuery(queryString);

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
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rs;
    }

}
