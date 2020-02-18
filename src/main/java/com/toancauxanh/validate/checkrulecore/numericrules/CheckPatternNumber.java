package com.toancauxanh.validate.checkrulecore.numericrules;

import javax.lang.model.element.Modifier;

import com.toancauxanh.validate.checkrulecore.CheckRuleCore;
import com.toancauxanh.validate.entity.Standard;
import com.squareup.javapoet.MethodSpec;

public class CheckPatternNumber implements CheckRuleCore{

    @Override
    public MethodSpec generateMethodSpec(Standard standard) {

        String pattern = (String) standard.getStandardDetails().get(0).getAttributeValue();

        MethodSpec methodSpec = MethodSpec.methodBuilder("isValidate").addModifiers(Modifier.PRIVATE)
                .returns(boolean.class).addParameter(Object.class, "valueCheck").beginControlFlow("try")
                .addStatement("String value = (String) valueCheck")
                .addStatement("return value.matches(\"" + pattern + "\")")
                .nextControlFlow("catch ($T e)", Exception.class).addStatement("return false").endControlFlow().build();

        return methodSpec;
    }
}
