package com.toancauxanh.validate.checkrulecore.stringrules;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.StringUtils;

import com.toancauxanh.validate.checkrulecore.CheckRuleCore;
import com.toancauxanh.validate.entity.Standard;
import com.squareup.javapoet.MethodSpec;

public class CheckEmpty implements CheckRuleCore {

    @Override
    public MethodSpec generateMethodSpec(Standard standard) {
        MethodSpec methodSpec = MethodSpec.methodBuilder("isValidate").addModifiers(Modifier.PRIVATE)
                .returns(boolean.class).addParameter(String.class, "valueCheck").beginControlFlow("try")
                .addStatement("return !$T.isEmpty(value)", StringUtils.class)
                .nextControlFlow("catch ($T e)", Exception.class).addStatement("return false").endControlFlow().build();

        return methodSpec;
    }
}
