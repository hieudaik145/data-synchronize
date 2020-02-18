package com.toancauxanh.validate.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
public class RuleAction {

    @Id
    private int id;

    private String actionName;

    private String actionDescription;

    @Transient
    private List<Rule> rules;

    private Date createTimestamp;

    private Date updateTimestamp;
}
