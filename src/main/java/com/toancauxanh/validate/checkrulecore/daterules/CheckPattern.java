package com.toancauxanh.validate.checkrulecore.daterules;

import java.util.Date;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.MethodSpec;
import com.toancauxanh.validate.checkrulecore.CheckRuleCore;
import com.toancauxanh.validate.entity.Standard;

public class CheckPattern implements CheckRuleCore{

    @Override
    public MethodSpec generateMethodSpec(Standard standard) {

        String pattern = (String) standard.getStandardDetails().get(0).getAttributeValue();

        MethodSpec methodIsValidate = MethodSpec.methodBuilder("isValidate").addModifiers(Modifier.PRIVATE)
                .returns(boolean.class).addParameter(Object.class, "valueCheck").beginControlFlow("try")
                .addStatement("$T value = (Date) valueCheck",Date.class)
                .addStatement("return value ")
                .nextControlFlow("catch ($T e)", Exception.class).addStatement("return false").endControlFlow().build();

        return methodIsValidate;
    }

}
