package com.toancauxanh.validate.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
public class DataDefinition {

    private int id;

    private String decription;

    private String sampleData;

    private List<DataDefinition> dataDefinitions;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;
}
