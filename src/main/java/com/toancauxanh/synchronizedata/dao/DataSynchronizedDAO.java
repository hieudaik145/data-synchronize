package com.toancauxanh.synchronizedata.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.toancauxanh.common.dao.ConnectionUtils;
import com.toancauxanh.database.entity.InfoColumnDto;
import com.toancauxanh.database.entity.InfoDatabaseDto;
import com.toancauxanh.database.entity.InfoTableDto;

public class DataSynchronizedDAO {

    /**
     * Method batch insert data to database staging
     * 
     * @param datas       Value insert to database
     * @param queryString String
     * @param infoTable   InfoTable
     * @param conn        Connection
     */
    public void batchInsertToDataStaging(List<Map<String, Object>> datas, InfoTableDto infoTable) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        String queryInsertString = buildStringQueryInsertStaging(infoTable);

        try {
            conn = ConnectionUtils.getConnectionStaging();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(queryInsertString);

            for (Map<String, Object> map : datas) {
                int i = 1;
                for (InfoColumnDto infoColumn : infoTable.getInfoColumns()) {
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
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method connection database and get result set from query string and
     * connection
     * 
     * @param queryString
     * @param conn
     * @return
     */
    public List<Map<String, Object>> getAllDataOfTable(InfoTableDto infoTableDto, InfoDatabaseDto infoDatabaseDto) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM " + infoTableDto.getTableName();

        List<Map<String, Object>> tableDatas = new ArrayList<>();

        try {
            conn = ConnectionUtils.getConnection(infoDatabaseDto);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            Map<String, Object> rowData = null;

            while (rs.next()) {

                rowData = new HashMap<>();

                for (InfoColumnDto infoColumnDto : infoTableDto.getInfoColumns()) {

                    String nameColumn = infoColumnDto.getColumnName();
                    rowData.put(nameColumn, rs.getObject(nameColumn));
                }
                tableDatas.add(rowData);
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
        return tableDatas;
    }
    
    /**
     * Method connection database and get result set from query string and
     * connection
     * 
     * @param queryString
     * @param conn
     * @return
     */
    public List<Map<String, Object>> getDataWithCondition(InfoTableDto infoTableDto, InfoDatabaseDto infoDatabaseDto, Date tuNgay, Date denNgay) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder("SELECT * FROM " + infoTableDto.getTableName());
        
        if(!Objects.isNull(tuNgay) && !Objects.isNull(denNgay)) {
            sql.append("where ngaytao >= " + tuNgay).append(" and  ngaytao <=" + denNgay);
        } else if (!Objects.isNull(tuNgay) && Objects.isNull(denNgay)) {
            sql.append("where ngaytao >= " + tuNgay);
        } else {
            sql.append("where ngaytao <= " + denNgay);
        }

        List<Map<String, Object>> tableDatas = new ArrayList<>();

        try {
            conn = ConnectionUtils.getConnection(infoDatabaseDto);
            pstmt = conn.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();

            Map<String, Object> rowData = null;

            while (rs.next()) {

                rowData = new HashMap<>();

                for (InfoColumnDto infoColumnDto : infoTableDto.getInfoColumns()) {

                    String nameColumn = infoColumnDto.getColumnName();
                    rowData.put(nameColumn, rs.getObject(nameColumn));
                }
                tableDatas.add(rowData);
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
        return tableDatas;
    }

    /**
     * Build query string insert to database staging
     * 
     * @param infoTable
     * @return stringQuery
     */
    private String buildStringQueryInsertStaging(InfoTableDto infoTable) {

        // Using string builder to create query insert string
        StringBuilder queryString = new StringBuilder();

        queryString.append("INSERT INTO " + infoTable.getTableName() + "(");

        StringBuilder paramString = new StringBuilder();
        StringBuilder askString = new StringBuilder();

        for (InfoColumnDto infoColumn : infoTable.getInfoColumns()) {

            if (infoTable.getInfoColumns().lastIndexOf(infoColumn) == infoTable.getInfoColumns().size() - 1) {
                paramString.append(infoColumn.getColumnName() + ")");
                askString.append("?)");
            } else {
                paramString.append(infoColumn.getColumnName() + ", ");
                askString.append("?, ");
            }
        }
        queryString.append(paramString.toString());
        queryString.append("VALUES(");
        queryString.append(askString.toString());
        return queryString.toString();
    }
}
