package com.toancauxanh.validate.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
public class RuleStatus {

    @Id
    private int id;

    private String statusName;

    private String statusDescription;

    private List<Rule> rules;

    private Date createTimestamp;

    private Date updateTimestamp;
}
