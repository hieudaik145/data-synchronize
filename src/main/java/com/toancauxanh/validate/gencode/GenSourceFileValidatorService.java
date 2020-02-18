package com.toancauxanh.validate.gencode;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.lang.model.element.Modifier;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.toancauxanh.validate.checkrulecore.CheckRuleCore;
import com.toancauxanh.validate.entity.Standard;
import com.toancauxanh.validate.entity.StandardDetail;
import com.toancauxanh.validate.entity.StandardType;
import com.toancauxanh.validate.validation.component.Result;
import com.toancauxanh.validate.validation.component.Validator;
import com.toancauxanh.validate.validation.composite.SimpleResult;

public class GenSourceFileValidatorService {

    // =========================================================================
    // CONSTANTS
    // ==========
    private static final String FAILED = "FAILED";
    private static final String VALIDATE = "validate";

    // =========================================================================
    // Execute Method
    // ========
    /**
     * Method generate FieldSpec
     * 
     * @return FieldSpec
     */
    private FieldSpec genFieldSpec() {

        // create field final
        return FieldSpec.builder(Result.class, FAILED, Modifier.PRIVATE, Modifier.FINAL).build();
    }

    /**
     * Method generate MethodSpec
     * 
     * @param standard Standard
     * @return list MethodSpec List<MethodSpec>
     */
    private List<MethodSpec> genMethodSpec(Standard standard) {

        // create list methodSpec return
        List<MethodSpec> methodSpecs = new ArrayList<>();

        // create method constructor
        MethodSpec methodConstructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
                .addStatement("super()")
                .addStatement("FAILED = new $T(\"" + standard.getStandardDescription() + "\", false)",
                        SimpleResult.class)
                .build();

        // add methodSpec to list methodSpec
        methodSpecs.add(methodConstructor);

        // call method new instance object check rule core
        CheckRuleCore checkCore = createObjectCheckRule(standard);

        // add methodSpec to list methodSpec
        methodSpecs.add(checkCore.generateMethodSpec(standard));

        // create method override
        MethodSpec methodtOverride = MethodSpec.methodBuilder(VALIDATE)
                .addAnnotation(GenAnnotationCommon.OVERRIDE_ANNOTATION).addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "value").returns(Result.class).beginControlFlow("try")
                .addStatement("return isValidate(value) ? Result.OK : FAILED")
                .nextControlFlow("catch ($T e)", Exception.class)
                .addStatement("return new SimpleResult(\"Sai kiểu dữ liệu không thể thực hiện check\",false)")
                .endControlFlow().build();

        // add methodSpec to list methodSpec
        methodSpecs.add(methodtOverride);

        return methodSpecs;
    }

    /**
     * This method generate TypeSpec perform add all component of class java
     * 
     * @param rule Rule
     * @return typeSpec TypeSpec
     */
    private TypeSpec getTypeSpec(Standard standard) {

        // perform add all method, field, constructor and all component of class java
        TypeSpec typeSpec = TypeSpec.classBuilder(standard.getStandardName()).addSuperinterface(Validator.class)
                .addModifiers(Modifier.PUBLIC).addField(genFieldSpec()).addMethods(genMethodSpec(standard)).build();

        return typeSpec;
    }

    /**
     * This method new Instance object CheckRuleCore using
     * 
     * @param standard
     * @return CheckRuleCore
     */
    private CheckRuleCore createObjectCheckRule(Standard standard) {

        StringBuilder namePackage = new StringBuilder("com.checkrulecore.");

        StandardType standardType = standard.getStandardType();

        if (standardType.getStdTypeName().equals("String")) {
            namePackage.append("stringrules");
        } else if (standardType.getStdTypeName().equals("Numeric")) {
            namePackage.append("numericrules");
        } else {
            namePackage.append("daterules");
        }

        namePackage.append("." + standard.getMethodHash());
        CheckRuleCore checkRuleCore = null;
        try {
            Class<?> act = Class.forName(namePackage.toString());
            checkRuleCore = (CheckRuleCore) act.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return checkRuleCore;

    }

    /**
     * Method perform generate file java and specify address save file
     * 
     * @param Standard
     */
    public void genFileJava(Standard standard) {

        // create Java file set package and class
        JavaFile javaFile = JavaFile.builder(PathSupport.PACKAGE_NAME, getTypeSpec(standard)).build();
        try {
            // call save to Path
            javaFile.writeTo(PathSupport.getOutputPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ParseException {

        Standard standard = new Standard();

        standard.setStandardName("CheckAllIsUpperCaseValidator");

        standard.setMethodHash("CheckIsUppercase");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        StandardType standardType = new StandardType();
        standardType.setStdTypeName("String");
        standard.setStandardType(standardType);

        StandardDetail standardDetail = new StandardDetail();

        standardDetail.setAttributeValue("acc");

//        StandardDetail detail2 = new StandardDetail();
//        detail2.setAttributeValue(sdf.parse("2019/10/01"));

        standard.setStandardDetails(Arrays.asList(standardDetail));

        standard.setStandardDescription("Number phair nam trong range");
        GenSourceFileValidatorService genCodeService = new GenSourceFileValidatorService();

        genCodeService.genFileJava(standard);
    }
}
