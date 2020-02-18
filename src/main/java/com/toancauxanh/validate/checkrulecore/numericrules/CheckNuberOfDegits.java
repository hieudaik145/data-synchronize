package com.toancauxanh.validate.checkrulecore.numericrules;

import javax.lang.model.element.Modifier;

import com.toancauxanh.validate.checkrulecore.CheckRuleCore;
import com.toancauxanh.validate.entity.Standard;
import com.squareup.javapoet.MethodSpec;

public class CheckNuberOfDegits implements CheckRuleCore {

    @Override
    public MethodSpec generateMethodSpec(Standard standard) {

        int numberDegit = (int) standard.getStandardDetails().get(0).getAttributeValue();

        MethodSpec methodIsValidate = MethodSpec.methodBuilder("isValidate").addModifiers(Modifier.PRIVATE)
                .returns(boolean.class).addParameter(Object.class, "valueCheck").beginControlFlow("try")
                .addStatement("int value = (int) valueCheck")
                .addStatement("return String.valueOf(value).length() == " + numberDegit)
                .nextControlFlow("catch ($T e)", Exception.class).addStatement("return false").endControlFlow().build();

        return methodIsValidate;
    }
}
