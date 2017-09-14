package com.ekarth.model;

import com.ekarth.model.annotations.PrimaryKey;
import com.sun.xml.internal.txw2.annotation.XmlNamespace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Property {
    @PrimaryKey
    int propertyId;
    String name;
    List<String> propertyValues;


}
