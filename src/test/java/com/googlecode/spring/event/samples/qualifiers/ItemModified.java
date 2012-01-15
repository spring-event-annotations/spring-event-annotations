package com.googlecode.spring.event.samples.qualifiers;

import java.lang.annotation.*;

import com.googlecode.spring.event.annotation.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.CONSTRUCTOR, ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD })
@Documented
public @interface ItemModified {
	String someParam() default "";
}
