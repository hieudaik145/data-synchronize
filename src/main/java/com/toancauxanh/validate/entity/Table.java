package com.toancauxanh.validate.entity;

import java.util.Date;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity(name = "ds_table")
@Data
public class Table {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String tableName;

    private String tableDescription;

    @OneToMany
    private List<Column> columns;

    @OneToMany
    private List<Rule> rules;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;

}
