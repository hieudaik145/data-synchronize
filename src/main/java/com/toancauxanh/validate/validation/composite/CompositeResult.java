package com.toancauxanh.validate.validation.composite;

import java.util.ArrayList;
import java.util.List;

import com.toancauxanh.validate.validation.component.Result;


public class CompositeResult implements Result {

    private List<Result> results;

    public void add(List<Result> list) {
        if (results == null || results.isEmpty()) {
            results = new ArrayList<>();
        }
        results.addAll(list);
    }

    private Integer appliedCount;

    public CompositeResult(Integer appliedCount) {
        this.appliedCount = appliedCount;
    }

    public CompositeResult() {
        super();
    }

    @Override
    public boolean isOk() {
        return results.isEmpty();
    }

    @Override
    public String getMessage() {
        return toString();
    }

    public Integer failCount() {
        return results.size();
    }

    public Integer passCount() {
        return appliedCount - results.size();
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Integer getAppliedCount() {
        return appliedCount;
    }

    public void setAppliedCount(Integer appliedCount) {
        this.appliedCount = appliedCount;
    }

}
