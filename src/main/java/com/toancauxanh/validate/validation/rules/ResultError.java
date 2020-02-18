package com.toancauxanh.validate.validation.rules;

public class ResultError {

    private String message;
    private int id;
    private String table;
    private String type;
    private String fieldName;
    private Object value;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public ResultError(String message, int id, String table, String type, String fieldName, String value) {
        super();
        this.message = message;
        this.id = id;
        this.table = table;
        this.type = type;
        this.fieldName = fieldName;
        this.value = value;
    }

    public ResultError() {
        super();
    }

    @Override
    public String toString() {
        return "ResultError [ id=" + id + ", Trường Check=" + fieldName + ",  message=" + message + ", value="
                + value.toString() + ", table=" + table + ", type=" + type + "]";
    }

}
