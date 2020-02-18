package com.toancauxanh.validate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class StandardDetailNumeric {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String attribute;

    private int attributeValue;
}
