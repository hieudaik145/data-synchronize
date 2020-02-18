package com.toancauxanh.validate.validation.component;

public interface Result {

    Result OK = new Result() {

        @Override
        public boolean isOk() {
            return true;
        }

        @Override
        public String getMessage() {
            return toString();
        }
    };

    public boolean isOk();

    public String getMessage();

}
