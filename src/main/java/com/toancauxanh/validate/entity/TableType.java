package com.toancauxanh.validate.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class TableType {

    private int id;

    private String typeName;

    private String typeDescription;

    private List<Table> tables;

    private Date createTimestamp;

    private Date updateTimestamp;

}
