package com.toancauxanh.validate.validation.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.toancauxanh.validate.validation.component.Result;
import com.toancauxanh.validate.validation.component.Validator;
import com.toancauxanh.validate.validation.composite.CompositeResult;

public class CompositeRule extends ArrayList<Validator> implements Validator {

    /**
     * 
     */
    private static final long serialVersionUID = -8414932387703832604L;

    public CompositeRule(Collection<Validator> rules) {
        addAll(rules);
    }

    public CompositeRule(Validator... rules) {
        addAll(Arrays.asList(rules));
    }

    @Override
    public CompositeResult validate(Object value) {

        CompositeResult result = new CompositeResult(size());

        for (Validator rule : this) {

            Result tempResult = rule.validate(value);

            List<Result> list = new ArrayList<>();

            if (!tempResult.isOk()) {
                list.add(tempResult);
            }

            result.add(list);

        }
        return result;
    }

}