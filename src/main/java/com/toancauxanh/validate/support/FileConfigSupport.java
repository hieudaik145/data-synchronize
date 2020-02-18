package com.toancauxanh.validate.support;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.toancauxanh.validate.entity.Column;
import com.toancauxanh.validate.entity.Rule;
import com.toancauxanh.validate.entity.RuleAction;
import com.toancauxanh.validate.entity.RuleCategories;
import com.toancauxanh.validate.entity.RuleRiskLevel;
import com.toancauxanh.validate.entity.RuleStatus;
import com.toancauxanh.validate.entity.RuleType;
import com.toancauxanh.validate.entity.Standard;
import com.toancauxanh.validate.entity.StandardDetail;
import com.toancauxanh.validate.entity.StandardType;
import com.toancauxanh.validate.entity.Table;
import com.toancauxanh.validate.service.TableConfigService;

public class FileConfigSupport {

    private static final String PATHFILETABLECONFIG = "./src/tableconfig.json";

    /**
     * Write file configuration for check rule.
     * 
     * @param tables
     */
    public void writeFileConfigJson(List<Table> tables) {

        JSONArray jsonArray = new JSONArray();

        for (Table table : tables) {
            JSONObject tableObj = new JSONObject();
            tableObj.put("id", table.getId());
            tableObj.put("tableName", table.getTableName());
            tableObj.put("tableDescription", table.getTableDescription());

            JSONArray columnsArray = new JSONArray();

            for (Column column : table.getColumns()) {

                JSONObject columnObj = new JSONObject();

                columnObj.put("id", column.getId());
                columnObj.put("colName", column.getColName());
                columnObj.put("colDataType", column.getColDataType());

                JSONArray ruleArray = new JSONArray();

                for (Rule rule : column.getRules()) {

                    JSONObject ruleObj = new JSONObject();
                    ruleObj.put("id", rule.getId());
                    ruleObj.put("ruleName", rule.getRuleName());
                    ruleObj.put("ruleDescription", rule.getRuleDescription());

                    if (rule.getRuleAction() != null) {
                        JSONObject ruleAction = new JSONObject();
                        ruleAction.put("id", rule.getRuleAction().getId());
                        ruleAction.put("actionName", rule.getRuleAction().getActionName());
                        ruleAction.put("actionDescription", rule.getRuleAction().getActionDescription());
                        ruleObj.put("ruleAction", ruleAction);
                    }

                    if (rule.getRuleStatus() != null) {
                        JSONObject ruleStatus = new JSONObject();
                        ruleStatus.put("id", rule.getRuleStatus().getId());
                        ruleStatus.put("statusName", rule.getRuleStatus().getStatusName());
                        ruleStatus.put("statusDescription", rule.getRuleStatus().getStatusDescription());
                        ruleObj.put("ruleStatus", ruleStatus);
                    }

                    if (rule.getRuleRiskLevel() != null) {
                        JSONObject ruleRiskLevels = new JSONObject();
                        ruleRiskLevels.put("id", rule.getRuleRiskLevel().getId());
                        ruleRiskLevels.put("riskName", rule.getRuleRiskLevel().getRiskName());
                        ruleRiskLevels.put("riskDescription", rule.getRuleRiskLevel().getRiskDescription());
                        ruleObj.put("ruleRiskLevels", ruleRiskLevels);
                    }

                    if (rule.getRuleType() != null) {
                        JSONObject ruleTypes = new JSONObject();
                        ruleTypes.put("id", rule.getRuleType().getId());
                        ruleTypes.put("typeName", rule.getRuleType().getTypeName());
                        ruleTypes.put("typeDescription", rule.getRuleType().getTypeDescription());
                        ruleObj.put("ruleTypes", ruleTypes);
                    }

                    if (rule.getRuleCategories() != null) {
                        JSONObject ruleCategories = new JSONObject();
                        ruleCategories.put("id", rule.getRuleCategories().getId());
                        ruleCategories.put("catName", rule.getRuleCategories().getCatName());
                        ruleCategories.put("catDescription", rule.getRuleCategories().getCatDescription());
                        ruleObj.put("ruleCategories", ruleCategories);
                    }

                    JSONArray standardArray = new JSONArray();

                    for (Standard standard : rule.getStandards()) {

                        JSONObject standardObj = new JSONObject();
                        standardObj.put("id", standard.getId());
                        standardObj.put("standardName", standard.getStandardName());
                        standardObj.put("standardDescription", standard.getStandardDescription());

                        JSONObject standardTypeObj = new JSONObject();
                        standardTypeObj.put("id", standard.getStandardType().getId());
                        standardTypeObj.put("stdTypeName", standard.getStandardType().getStdTypeName());
                        standardTypeObj.put("stdTypeDescription", standard.getStandardType().getStdTypeDescription());
                        standardObj.put("standardType", standardTypeObj);

                        JSONArray standardDetailArray = new JSONArray();

                        if (standard.getStandardDetails() != null) {
                            for (StandardDetail standardDetail : standard.getStandardDetails()) {

                                JSONObject standardDetailObject = new JSONObject();
                                standardDetailObject.put("id", standardDetail.getId());
                                standardDetailObject.put("attribute", standardDetail.getAttribute());
                                standardDetailObject.put("attributeValue", standardDetail.getAttributeValue());

                                standardDetailArray.add(standardDetailObject);
                            }
                        }

                        standardObj.put("standardDetails", standardDetailArray);

                        standardArray.add(standardObj);
                    }

                    ruleObj.put("standards", standardArray);
                    ruleArray.add(ruleObj);

                }
                columnObj.put("rules", ruleArray);
                columnsArray.add(columnObj);
            }

            tableObj.put("columns", columnsArray);

            jsonArray.add(tableObj);
        }

        try {
            FileWriter file = new FileWriter(PATHFILETABLECONFIG);
            file.write(jsonArray.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(jsonArray);
    }

    public List<Table> readFileTableConfig() {

        List<Table> tables = new ArrayList<Table>();

        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(PATHFILETABLECONFIG)) {

            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            Iterator<Object> iterator = jsonArray.iterator();

            while (iterator.hasNext()) {
                JSONObject jsonObj = (JSONObject) iterator.next();
                Table table = new Table();
                table.setId((int) (long) jsonObj.get("id"));
                table.setTableName((String) jsonObj.get("tableName"));
                table.setTableDescription((String) jsonObj.get("tableDescription"));

                JSONArray arrayColumn = (JSONArray) jsonObj.get("columns");

                Iterator<Object> iteratorCol = arrayColumn.iterator();

                List<Column> columns = new ArrayList<Column>();

                while (iteratorCol.hasNext()) {
                    JSONObject columnObj = (JSONObject) iteratorCol.next();
                    Column column = new Column();
                    column.setId((int) (long) columnObj.get("id"));
                    column.setColName((String) columnObj.get("colName"));
                    column.setColDataType((String) columnObj.get("colDataType"));

                    JSONArray arrayRules = (JSONArray) columnObj.get("rules");
                    Iterator<Object> iteratorRule = arrayRules.iterator();
                    List<Rule> rules = new ArrayList<Rule>();
                    while (iteratorRule.hasNext()) {

                        JSONObject ruleObj = (JSONObject) iteratorRule.next();
                        Rule rule = new Rule();
                        rule.setId((int) (long) ruleObj.get("id"));
                        rule.setRuleName((String) ruleObj.get("ruleName"));
                        rule.setRuleDescription((String) ruleObj.get("ruleDescription"));

                        JSONObject jsonAction = (JSONObject) ruleObj.get("ruleAction");
                        if (!Objects.isNull(jsonAction)) {
                            RuleAction ruleAction = new RuleAction();
                            ruleAction.setId((int) (long) jsonAction.get("id"));
                            ruleAction.setActionName((String) jsonAction.get("actionName"));
                            ruleAction.setActionDescription((String) jsonAction.get("actionDescription"));
                            rule.setRuleAction(ruleAction);
                        }

                        JSONObject jsonStatus = (JSONObject) ruleObj.get("ruleStatus");
                        if (!Objects.isNull(jsonStatus)) {
                            RuleStatus ruleStatus = new RuleStatus();
                            ruleStatus.setId((int) (long) jsonStatus.get("id"));
                            ruleStatus.setStatusName((String) jsonStatus.get("statusName"));
                            ruleStatus.setStatusDescription((String) jsonStatus.get("statusDescription"));
                            rule.setRuleStatus(ruleStatus);
                        }

                        JSONObject jsonRiskLevel = (JSONObject) ruleObj.get("ruleRiskLevels");
                        if (!Objects.isNull(jsonRiskLevel)) {
                            RuleRiskLevel ruleRiskLevel = new RuleRiskLevel();
                            ruleRiskLevel.setId((int) (long) jsonRiskLevel.get("id"));
                            ruleRiskLevel.setRiskName((String) jsonRiskLevel.get("riskName"));
                            ruleRiskLevel.setRiskDescription((String) jsonRiskLevel.get("riskDescription"));
                            rule.setRuleRiskLevel(ruleRiskLevel);
                        }

                        JSONObject jsonType = (JSONObject) ruleObj.get("ruleTypes");
                        if (!Objects.isNull(jsonType)) {
                            RuleType ruleType = new RuleType();
                            ruleType.setId((int) (long) jsonType.get("id"));
                            ruleType.setTypeName((String) jsonType.get("typeName"));
                            ruleType.setTypeDescription((String) jsonType.get("typeDescription"));
                            rule.setRuleType(ruleType);
                        }

                        JSONObject jsonCategories = (JSONObject) ruleObj.get("ruleCategories");
                        if (!Objects.isNull(jsonCategories)) {
                            RuleCategories ruleCategories = new RuleCategories();
                            ruleCategories.setId((int) (long) jsonCategories.get("id"));
                            ruleCategories.setCatName((String) jsonCategories.get("catName"));
                            ruleCategories.setCatDescription((String) jsonCategories.get("catDescription"));
                            rule.setRuleCategories(ruleCategories);
                        }

                        JSONArray arrayStandard = (JSONArray) ruleObj.get("standards");
                        Iterator<Object> iteratorStandard = arrayStandard.iterator();
                        List<Standard> listStandards = new ArrayList<>();

                        while (iteratorStandard.hasNext()) {

                            JSONObject standardObject = (JSONObject) iteratorStandard.next();

                            Standard standard = new Standard();
                            standard.setId((int)(long) standardObject.get("id"));
                            standard.setStandardName((String) standardObject.get("standardName"));
                            standard.setStandardDescription((String) standardObject.get("standardDescription"));

                            JSONObject standardTypeObject = (JSONObject) standardObject.get("standardType");
                            StandardType standardType = new StandardType();
                            standardType.setId((int)(long) standardTypeObject.get("id"));
                            standardType.setStdTypeName((String) standardTypeObject.get("stdTypeName"));
                            standardType.setStdTypeDescription((String) standardTypeObject.get("stdTypeDescription"));
                            standard.setStandardType(standardType);

                            JSONArray arrayDetailStandards = (JSONArray) standardObject.get("standardDetails");
                            Iterator<Object> iteratorDetailStandard = arrayStandard.iterator();
                            List<StandardDetail> listStandardsDetail = new ArrayList<>();

                            while (iteratorDetailStandard.hasNext()) {

                                JSONObject standardDetailObject = (JSONObject) iteratorDetailStandard.next();

                                StandardDetail standardDetail = new StandardDetail();
                                standardDetail.setId((int)(long) standardDetailObject.get("id"));
                                standardDetail.setAttribute((String) standardDetailObject.get("attribute"));
                                standardDetail.setAttributeValue(standardDetailObject.get("attributeValue"));

                                listStandardsDetail.add(standardDetail);
                            }

                            standard.setStandardDetails(listStandardsDetail);
                            listStandards.add(standard);

                        }
                        rule.setStandards(listStandards);
                        rules.add(rule);
                    }
                    column.setRules(rules);
                    columns.add(column);
                }
                table.setColumns(columns);
                tables.add(table);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tables;
    }

    public static void main(String[] args) {

        List<Table> tables = new ArrayList<Table>();

        TableConfigService service = new TableConfigService();

        tables.add(service.getTableConfig());

        FileConfigSupport support = new FileConfigSupport();

        support.writeFileConfigJson(tables);

        List<Table> tables2 = support.readFileTableConfig();

        for (Table temp : tables2) {
            System.out.println(temp);
        }
        System.out.println("success");

    }

}
