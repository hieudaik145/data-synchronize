package com.toancauxanh.validate.validation.service;

import java.util.ArrayList;
import java.util.List;

import com.toancauxanh.validate.validation.component.Result;
import com.toancauxanh.validate.validation.composite.CompositeResult;
import com.toancauxanh.validate.validation.rules.FieldValidate;
import com.toancauxanh.validate.validation.rules.ResultError;
import com.toancauxanh.validate.validation.rules.RowValidate;


public class RowValidateService {

    public List<ResultError> applyRowValidate(RowValidate rowValidate) {

        FieldValidateService fieldValidateService = new FieldValidateService();

        List<ResultError> errors = new ArrayList<>();

        for (FieldValidate fieldValidate : rowValidate.getFieldValidates()) {

            CompositeResult tempResult = fieldValidateService.applyFieldValidate(fieldValidate);

            if (!tempResult.isOk()) {
                for (Result result : tempResult.getResults()) {

                    ResultError resultError = new ResultError();

                    resultError.setId(rowValidate.getId());
                    resultError.setFieldName(fieldValidate.getFieldName());
                    resultError.setMessage(result.getMessage());
                    resultError.setType(fieldValidate.getType());
                    resultError.setValue(fieldValidate.getValue());
                    resultError.setTable(rowValidate.getTable());

                    errors.add(resultError);

                }

            }

        }

        return errors;

    }
}
