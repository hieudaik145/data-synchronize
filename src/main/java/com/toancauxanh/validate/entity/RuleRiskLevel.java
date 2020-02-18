package com.toancauxanh.validate.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class RuleRiskLevel {

    private int id;

    private String riskName;

    private String riskDescription;

    private List<Rule> rules;

    private Date createTimestamp;

    private Date updateTimestamp;
}
