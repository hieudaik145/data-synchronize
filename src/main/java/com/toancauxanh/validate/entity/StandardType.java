package com.toancauxanh.validate.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
public class StandardType {

    @Id
    @GeneratedValue
    private int id;

    private String stdTypeName;

    private String stdTypeDescription;

    @Transient
    private List<Standard> standards;

    private Date createTimestamp;

    private Date updateTimestamp;

}
