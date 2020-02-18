package com.toancauxanh.validate.checkrulecore.numericrules;

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.StringUtils;

import com.toancauxanh.validate.checkrulecore.CheckRuleCore;
import com.toancauxanh.validate.entity.Standard;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

public class CheckValueInListNumber implements CheckRuleCore {

    @Override
    public MethodSpec generateMethodSpec(Standard standard) {

        ClassName list = ClassName.get("java.util", "List");

        ClassName string = ClassName.get("java.lang", "Integer");

        ClassName arrays = ClassName.get("java.util", "Arrays");

        TypeName listOfString = ParameterizedTypeName.get(list, string);

        List<Integer> temp = standard.getStandardDetails().stream().map(x -> {
            return (int)x.getAttributeValue() ;
        }).collect(Collectors.toList());

        String chuoi = StringUtils.join(temp, ",");

        MethodSpec methodSpec = MethodSpec.methodBuilder("isValidate").addModifiers(Modifier.PRIVATE)
                .returns(boolean.class).addParameter(Object.class, "valueCheck").beginControlFlow("try")
                .addStatement("int  value = (int) valueCheck")
                .addStatement("$T list = $T.asList(" + chuoi + ")", listOfString, arrays)
                .addStatement("return list.contains(value)").nextControlFlow("catch ($T e)", Exception.class)
                .addStatement("return false").endControlFlow().build();

        return methodSpec;

    }

}
