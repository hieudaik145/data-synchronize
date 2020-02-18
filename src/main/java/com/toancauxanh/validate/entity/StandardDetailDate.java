package com.toancauxanh.validate.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class StandardDetailDate {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String attribute;

    private Date attributeValue;
}
