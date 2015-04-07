package com.github.spring.event.samples.qualifiers;


import com.github.spring.event.annotation.Qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.CONSTRUCTOR, ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD })
@Documented
public @interface ItemCreated {

}
