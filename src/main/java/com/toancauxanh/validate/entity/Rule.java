package com.toancauxanh.validate.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Rule {

    @Id
    private int id;

    @NotNull
    private String ruleName;

    private String ruleDescription;

    @Transient
    private RuleAction ruleAction;

    @Transient
    private RuleStatus ruleStatus;

    @Transient
    private RuleRiskLevel ruleRiskLevel;

    @Transient
    private RuleType ruleType;

    @Transient
    private RuleCategories ruleCategories;

    @OneToMany
    private List<Standard> standards;

    private Date createTimestamp;

    private Date updateTimestamp;
}
