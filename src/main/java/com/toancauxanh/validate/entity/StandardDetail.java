package com.toancauxanh.validate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class StandardDetail {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String attribute;

    @Transient
    private Object attributeValue;

}
