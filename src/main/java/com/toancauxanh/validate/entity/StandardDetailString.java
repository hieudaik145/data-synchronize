package com.toancauxanh.validate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class StandardDetailString {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String attribute;

    @Column(columnDefinition = "LONGTEXT")
    private String attributeValue;
}
