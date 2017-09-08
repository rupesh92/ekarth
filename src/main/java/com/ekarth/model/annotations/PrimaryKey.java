package com.ekarth.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface PrimaryKey {
    boolean isSerial() default true;

}
