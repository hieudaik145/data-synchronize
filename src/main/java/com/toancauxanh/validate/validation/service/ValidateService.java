package com.toancauxanh.validate.validation.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.toancauxanh.validate.validation.config.ConfigBuilder;
import com.toancauxanh.validate.validation.rules.ResultError;
import com.toancauxanh.validate.validation.rules.RowValidate;

public class ValidateService {

    RowValidateService rowValidateService = new RowValidateService();

    public void applyValidate(List<Object> list) {

        List<Object> pass = new ArrayList<>();

        List<ResultError> fail = new ArrayList<>();

        LocalDateTime start = LocalDateTime.now();

        for (Object object : list) {

            List<ResultError> resultErrors = rowValidateService.applyRowValidate(callConstantsRowCheck(object));

            if (resultErrors.isEmpty()) {
                pass.add(object);
            } else {
                fail.addAll(resultErrors);
            }
        }
        LocalDateTime end = LocalDateTime.now();

//        for (ResultError resultError : fail) {
//            System.out.println(resultError.toString());
//        }

//        for (Object object : pass) {
//            object.save();
//        }
        System.out.println(start + " - " + end);
        System.out.println("end");

    }

    public RowValidate callConstantsRowCheck(Object object) {

        ConfigBuilder configBuilder = new ConfigBuilder();

        return configBuilder.createRowValidate(object);

    }
}
