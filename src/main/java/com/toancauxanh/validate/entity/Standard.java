package com.toancauxanh.validate.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Standard {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String standardName;

    @NotNull
    private String standardDescription;

    @OneToOne
    private StandardType standardType;

    @NotNull
    private String methodHash;

    @OneToMany
    private List<StandardDetail> standardDetails;

    @OneToMany
    private List<StandardDetailDate> standardDetailDates;
    
    @OneToMany
    private List<StandardDetailNumeric> standardDetailNumerics;
    
    @OneToMany
    private List<StandardDetailString> standardDetailStrings;
    
    private Date createTimestamp;

    private Date updateTimestamp;

}
