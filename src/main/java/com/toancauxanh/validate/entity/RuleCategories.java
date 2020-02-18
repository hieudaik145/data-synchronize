package com.toancauxanh.validate.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class RuleCategories {

    private int id;
    
    private String catName;
    
    private String catDescription;
    
    private List<Rule> rules;
    
    private Date createTimestamp;

    private Date updateTimestamp;
}
