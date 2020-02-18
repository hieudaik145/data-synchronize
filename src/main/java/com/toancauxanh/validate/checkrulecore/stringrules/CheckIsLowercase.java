package com.toancauxanh.validate.checkrulecore.stringrules;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.StringUtils;

import com.toancauxanh.validate.checkrulecore.CheckRuleCore;
import com.toancauxanh.validate.entity.Standard;
import com.squareup.javapoet.MethodSpec;

public class CheckIsLowercase implements CheckRuleCore {

    @Override
    public MethodSpec generateMethodSpec(Standard standard) {

        MethodSpec methodIsValidate = MethodSpec.methodBuilder("isValidate").addModifiers(Modifier.PRIVATE)
                .returns(boolean.class).addParameter(Object.class, "valueCheck").beginControlFlow("try")
                .addStatement("String value = (String) valueCheck")
                .addStatement("return $T.isAllLowerCase(value)", StringUtils.class)
                .nextControlFlow("catch ($T e)", Exception.class).addStatement("return false").endControlFlow().build();

        return methodIsValidate;

    }
}
