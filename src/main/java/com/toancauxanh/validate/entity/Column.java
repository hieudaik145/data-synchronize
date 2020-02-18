package com.toancauxanh.validate.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity(name = "ds_column")
public class Column {

    @Id
    private int id;

    @NotNull
    private String colName;

    @NotNull
    private String colDataType;

    private boolean isPrimaryKey;

    private boolean isForeignKey;

    private boolean isDentity;

    private boolean isNull;

    @OneToMany
    private List<Rule> rules;

}
