package com.toancauxanh.validate.validation.config;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.janino.SimpleCompiler;

import com.toancauxanh.validate.entity.Column;
import com.toancauxanh.validate.entity.Rule;
import com.toancauxanh.validate.entity.Standard;
import com.toancauxanh.validate.entity.Table;
import com.toancauxanh.validate.gencode.GenSourceFileValidatorService;
import com.toancauxanh.validate.gencode.PathSupport;
import com.toancauxanh.validate.support.FileConfigSupport;
import com.toancauxanh.validate.support.FileSupport;
import com.toancauxanh.validate.validation.component.Validator;
import com.toancauxanh.validate.validation.rules.FieldValidate;
import com.toancauxanh.validate.validation.rules.RowValidate;


public class ConfigBuilder {

    private static final String DOT_JAVA = ".java";

    GenSourceFileValidatorService genSourceService = new GenSourceFileValidatorService();

    FileSupport fileSupport = new FileSupport();

    /**
     * Create Row Validate
     * 
     * @param object
     * @return RowValidate
     */
    public RowValidate createRowValidate(Object object) {

        Table table = findTableByObject(object);

        RowValidate rowValidate = new RowValidate();

        List<FieldValidate> fieldVaildates = new ArrayList<>();

        for (Column column : table.getColumns()) {

            List<Validator> validators = new ArrayList<>();

            for (Rule rule : column.getRules()) {

                for (Standard standard : rule.getStandards()) {
                    try {

                        if (!fileSupport.existFile(PathSupport.getPathFileGenCode(),
                                standard.getStandardName() + DOT_JAVA)) {
                            genSourceService.genFileJava(standard);
                        }

                        Object act = invokeNewInstanceObject(standard.getStandardName());
                        validators.add((Validator) act);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            fieldVaildates.add(new FieldValidate.Builder().setFieldName(column.getColName())
                    .setType(column.getColDataType()).setValue(getValueProperty(object, column.getColName()))
                    .addRules(validators).createFieldValidate());
        }
        rowValidate.setFieldValidates(fieldVaildates);
        rowValidate.setId((int) getValueProperty(object, "id"));
        rowValidate.setTable(table.getTableName());

        return rowValidate;

    }

    /**
     * Find table config corresponding from file config table
     * 
     * @param object
     * @return Table
     */
    private Table findTableByObject(Object object) {

        FileConfigSupport fileConfigSupport = new FileConfigSupport();

//        fileConfigSupport.writeFileConfigJson(Arrays.asList(TableService.createListTable()));

        List<Table> tables = fileConfigSupport.readFileTableConfig();

        for (Table table : tables) {

            if (StringUtils.equalsIgnoreCase(table.getTableName(), object.getClass().getSimpleName())) {
                return table;
            }
        }

        return null;
    }

    /**
     * Method get perpropety of object
     * 
     * @param object
     * @param nameProperty
     * @return
     */
    private Object getValueProperty(Object object, String nameProperty) {
        Object obj = null;
        try {
            obj = PropertyUtils.getProperty(object, nameProperty);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return obj;
    }

    /**
     * Method new Instance object from file .java
     * 
     * @param nameClass
     * @return
     */
    private Object invokeNewInstanceObject(String nameClass) {

        Object object = null;

        try {
            SimpleCompiler simpleCompiler = new SimpleCompiler(
                    "./src/main/java/com/filegen/validators/" + nameClass + ".java");

            ClassLoader classLoader = simpleCompiler.getClassLoader();

            Class<?> clazz = classLoader.loadClass("com.filegen.validators." + nameClass);

            object = clazz.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

}
