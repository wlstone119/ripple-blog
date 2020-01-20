package com.ripple.blog.infrastructure.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
public @interface DocDocument {

	String name() default "";

	String desc() default "";

	boolean required() default false;
}
