package com.toancauxanh.validate.service;

import java.util.Arrays;

import com.toancauxanh.validate.entity.Column;
import com.toancauxanh.validate.entity.Rule;
import com.toancauxanh.validate.entity.Standard;
import com.toancauxanh.validate.entity.StandardDetail;
import com.toancauxanh.validate.entity.StandardType;
import com.toancauxanh.validate.entity.Table;


public class TableConfigService {

    public Table getTableConfig() {

        StandardType standardTypeString = new StandardType();
        standardTypeString.setStdTypeName("String");
        standardTypeString.setStdTypeDescription("Description type of String");

        StandardType standardTypeNumber = new StandardType();
        standardTypeNumber.setStdTypeName("String");

        StandardType standardTypeDate = new StandardType();
        standardTypeDate.setStdTypeName("Date");

        Table table = new Table();

        table.setTableName("Bao Luc Gia Dinh");

        Column colTenQuanHuyen = new Column();
        colTenQuanHuyen.setColName("Tên Quận Huyện");

        Rule rule = new Rule();
        rule.setRuleName("ruleCheckTenQuanHuyen");

        // rule max length ten quan huyen
        Standard standardMaxlength = new Standard();
        standardMaxlength.setStandardType(standardTypeString);
        standardMaxlength.setStandardName("CheckMaxLengthTenQuanHuyen");

        StandardDetail detailMaxlengthTenQuanHuyen = new StandardDetail();
        detailMaxlengthTenQuanHuyen.setAttribute("maxLength");
        detailMaxlengthTenQuanHuyen.setAttributeValue(255);

        standardMaxlength.setStandardDetails(Arrays.asList(detailMaxlengthTenQuanHuyen));
        standardMaxlength.setStandardDescription("Tên quận huyện phải đảm bảo nhỏ hơn"
                + standardMaxlength.getStandardDetails().get(0).getAttributeValue() + " ký tự");
        //

        // rule max length ten quan huyen
        Standard standardMinLength = new Standard();
        standardMinLength.setStandardType(standardTypeString);
        standardMinLength.setStandardName("CheckMinLengthTenQuanHuyen");

        StandardDetail detailMinLengthTenQuanHuyen = new StandardDetail();
        detailMinLengthTenQuanHuyen.setAttribute("minLength");
        detailMinLengthTenQuanHuyen.setAttributeValue(5);

        standardMinLength.setStandardDetails(Arrays.asList(detailMinLengthTenQuanHuyen));
        standardMaxlength.setStandardDescription("Tên quận huyện phải đảm bảo lớn hơn "
                + standardMaxlength.getStandardDetails().get(0).getAttributeValue() + " ký tự");

        Standard checkNull = new Standard();
        
        checkNull.setStandardType(standardTypeString);
        checkNull.setStandardName("CheckNullTenQuanHuyen");
        checkNull.setStandardDescription("Tên quận huyện không được null");

        rule.setStandards(Arrays.asList(standardMaxlength, standardMinLength, checkNull));

        colTenQuanHuyen.setRules(Arrays.asList(rule));

        table.setColumns(Arrays.asList(colTenQuanHuyen));

        return table;

    }
}
