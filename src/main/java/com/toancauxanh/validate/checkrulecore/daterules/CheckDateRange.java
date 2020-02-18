package com.toancauxanh.validate.checkrulecore.daterules;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.MethodSpec;
import com.toancauxanh.validate.checkrulecore.CheckRuleCore;
import com.toancauxanh.validate.entity.Standard;

public class CheckDateRange implements CheckRuleCore{

    @Override
    public MethodSpec generateMethodSpec(Standard standard) {

        Date dateTemp = (Date) standard.getStandardDetails().get(0).getAttributeValue();
        Date date2 = (Date) standard.getStandardDetails().get(1).getAttributeValue();
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        MethodSpec methodIsValidate = MethodSpec.methodBuilder("isValidate").addModifiers(Modifier.PRIVATE)
                .returns(boolean.class).addParameter(Object.class, "valueCheck").beginControlFlow("try")
                .addStatement("$T sdformat = new SimpleDateFormat(\"yyyy/MM/dd\")", SimpleDateFormat.class)
                .addStatement("Date d1 = sdformat.parse(\"" + simpleDateFormat.format(dateTemp) + "\")")
                .addStatement("Date d2 = sdformat.parse(\"" + simpleDateFormat.format(date2) + "\")")
                .addStatement("$T value = (Date) valueCheck", Date.class)
                .addStatement("String datetemp = sdformat.format(value)")
                .addStatement("Date dateValue = sdformat.parse(datetemp)")
                .addStatement("return dateValue.compareTo(d1) >= 0 && dateValue.compareTo(d2) <=0").nextControlFlow("catch ($T e)", Exception.class)
                .addStatement("return false").endControlFlow().build();

        return methodIsValidate;
    }

}
