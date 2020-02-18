package com.toancauxanh.database.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;


@Data
@Entity
public class InfoDatabase {
    
    @Id
    @GeneratedValue
    private int id;

    private String databaseName;
    
    private String host;
    
    private String port;
    
    private String user;
    
    private String password;
    
    @Enumerated(EnumType.STRING)
    private DatabaseType typeDB;
    
    @Transient
    private List<InfoTable> infoTables;

}
