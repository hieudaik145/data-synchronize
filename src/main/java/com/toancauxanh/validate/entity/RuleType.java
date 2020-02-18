package com.toancauxanh.validate.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class RuleType {

    private int id;

    private String typeName;

    private String typeDescription;

    private List<Rule> rules;

    private Date createTimestamp;

    private Date updateTimestamp;

}
