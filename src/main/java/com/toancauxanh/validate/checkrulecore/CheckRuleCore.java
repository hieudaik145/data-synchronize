package com.toancauxanh.validate.checkrulecore;

import com.squareup.javapoet.MethodSpec;
import com.toancauxanh.validate.entity.Standard;

public interface CheckRuleCore {

    MethodSpec generateMethodSpec(Standard standard);
}
