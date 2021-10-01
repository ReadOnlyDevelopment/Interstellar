package com.readonlydev.lib.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Functionality annotated with ForRemoval will no longer be supported and should not be used anymore in new code.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR })
public @interface ForRemoval {

	/**
	 * Version which will most likely remove this feature.
	 *
	 * @return The deadline version or N/A if this isn't known yet
	 */
	String deadline() default "N/A";

}
