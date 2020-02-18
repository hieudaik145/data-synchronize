package com.toancauxanh.validate.checkrulecore.numericrules;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.StringUtils;

import com.toancauxanh.validate.checkrulecore.CheckRuleCore;
import com.toancauxanh.validate.entity.Standard;
import com.squareup.javapoet.MethodSpec;

public class CheckValueRange implements CheckRuleCore {

    @Override
    public MethodSpec generateMethodSpec(Standard standard) {

        int numberMin = (int) standard.getStandardDetails().get(0).getAttributeValue();
        int numberMax = (int) standard.getStandardDetails().get(1).getAttributeValue();

        MethodSpec methodIsValidate = MethodSpec.methodBuilder("isValidate").addModifiers(Modifier.PRIVATE)
                .returns(boolean.class).addParameter(Object.class, "valueCheck").beginControlFlow("try")
                .addStatement("int value = (int) valueCheck")
                .addStatement("return value <= " + numberMin + " && value <= " + numberMax, StringUtils.class)
                .nextControlFlow("catch ($T e)", Exception.class).addStatement("return false").endControlFlow().build();

        return methodIsValidate;

    }
}
