package com.toancauxanh.validate.validation.service;

import com.toancauxanh.validate.validation.composite.CompositeResult;
import com.toancauxanh.validate.validation.rules.CompositeRule;
import com.toancauxanh.validate.validation.rules.FieldValidate;

public class FieldValidateService {

    public CompositeResult applyFieldValidate(FieldValidate fieldValidate) {

        CompositeRule rule = new CompositeRule(fieldValidate.getRules());

        return rule.validate(fieldValidate.getValue());

    }

}
