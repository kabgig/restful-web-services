package com.kabgig.rest.websevices.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"field1","field2"})
public class SomeBean {
    private String field1;
    private String field2;
    private String field3;

    public SomeBean(String value1, String value2, String value3) {
        this.field1 = value1;
        this.field2 = value2;
        this.field3 = value3;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public String getField3() {
        return field3;
    }

    @Override
    public String toString() {
        return "SomeBean{" +
                "value1='" + field1 + '\'' +
                ", value2='" + field2 + '\'' +
                ", value3='" + field3 + '\'' +
                '}';
    }
}
