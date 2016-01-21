package com.github.spring.event.samples.qualifiers;


import com.github.spring.event.annotation.Qualifier;

import java.lang.annotation.*;


@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.CONSTRUCTOR, ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD })
@Documented
public @interface ItemModified {
	String someParam() default "";
}
