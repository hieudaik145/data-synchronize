package com.toancauxanh.validate.entity;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
public class ColumnsType {

    private int id;

    private String colTypeName;

    private String colTypeDescription;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;

}
