package com.toancauxanh.validate.checkrulecore.numericrules;

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.StringUtils;

import com.toancauxanh.validate.checkrulecore.CheckRuleCore;
import com.toancauxanh.validate.entity.Standard;
import com.squareup.javapoet.MethodSpec;

public class CheckSumValue implements CheckRuleCore {

    @Override
    public MethodSpec generateMethodSpec(Standard standard) {

        List<Integer> temp = standard.getStandardDetails().stream().map(x -> {
            return (int) x.getAttributeValue();
        }).collect(Collectors.toList());

        String chuoi = StringUtils.join(temp, "+");

        MethodSpec methodIsValidate = MethodSpec.methodBuilder("isValidate").addModifiers(Modifier.PRIVATE)
                .returns(boolean.class).addParameter(Object.class, "valueCheck").beginControlFlow("try")
                .addStatement("int value = (int) valueCheck").addStatement("return value == " + chuoi)
                .nextControlFlow("catch ($T e)", Exception.class).addStatement("return false").endControlFlow().build();

        return methodIsValidate;
    }
}
