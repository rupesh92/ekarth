package com.ekarth.model;

import lombok.AllArgsConstructor;
import lombok.Data;


public class Property {
    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropertyValues() {
        return PropertyValues;
    }

    public void setPropertyValues(String propertyValues) {
        PropertyValues = propertyValues;
    }

    int propertyId;
    String name;
    String PropertyValues;


}
