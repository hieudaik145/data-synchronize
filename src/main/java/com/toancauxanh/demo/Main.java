package com.toancauxanh.demo;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.toancauxanh.database.entity.DatabaseType;
import com.toancauxanh.database.entity.InfoDatabaseDto;
import com.toancauxanh.database.service.InfoDatabaseService;
import com.toancauxanh.synchronizedata.service.SynchronizeDataService;

public class Main {

    public static void main(String[] args) {
        
        InfoDatabaseService servive = new InfoDatabaseService();

        InfoDatabaseDto infoDatabase = new InfoDatabaseDto();

        infoDatabase.setHost("localhost");
        infoDatabase.setDatabaseName("MariaDatabase");
        infoDatabase.setPassword("123456789");
        infoDatabase.setUser("root");
        infoDatabase.setPort("3306");
        infoDatabase.setTypeDB(DatabaseType.MARIA_DB);
        
        InfoDatabaseDto databaseMsSQL = new InfoDatabaseDto();
        databaseMsSQL.setHost("localhost");
        databaseMsSQL.setDatabaseName("Database_sqlserver");
        databaseMsSQL.setPassword("Hieudaik145");
        databaseMsSQL.setUser("sa");
        databaseMsSQL.setPort("1433");
        databaseMsSQL.setTypeDB(DatabaseType.MS_SQL_SERVER);
        
        InfoDatabaseDto oracleDB = new InfoDatabaseDto();
        oracleDB.setHost("192.168.1.238");
        oracleDB.setDatabaseName("orcl");
        oracleDB.setPassword("1");
        oracleDB.setUser("c##qa_dng_nam");
        oracleDB.setPort("1521");
        oracleDB.setSchemaName("C##QA_DNG_NAM");
        oracleDB.setTypeDB(DatabaseType.ORACLE);
        
        
        SynchronizeDataService service = new SynchronizeDataService();
        LocalDateTime begin = LocalDateTime.now();
        service.synchronizeListDatabase(Arrays.asList(oracleDB,databaseMsSQL,infoDatabase));
        LocalDateTime end = LocalDateTime.now();
        System.out.println(begin + " -- " + end);


    }
}
