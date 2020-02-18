package com.toancauxanh.validate.checkrulecore.stringrules;

import java.util.Objects;

import javax.lang.model.element.Modifier;

import com.toancauxanh.validate.checkrulecore.CheckRuleCore;
import com.toancauxanh.validate.entity.Standard;
import com.squareup.javapoet.MethodSpec;

/**
 * @author hieuvv
 * @since GĐ1
 */
public class CheckNull implements CheckRuleCore {

    @Override
    public MethodSpec generateMethodSpec(Standard standard) {

        MethodSpec methodIsValidate = MethodSpec.methodBuilder("isValidate").addModifiers(Modifier.PRIVATE)
                .returns(boolean.class).addParameter(Object.class, "valueCheck").beginControlFlow("try")
                .addStatement("return  $T.isNull(valueCheck)", Objects.class)
                .nextControlFlow("catch ($T e)", Exception.class).addStatement("return false").endControlFlow().build();

        return methodIsValidate;

    }
}
