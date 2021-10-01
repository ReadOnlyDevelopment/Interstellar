package com.readonlydev.lib.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class InjectionUtil {
	/**
	 * Returns <code>null</code>.
	 * <p>
	 * This is used in the initialisers of <code>static final</code> fields instead of using <code>null</code> directly.
	 *
	 * @param <T> The field's type.
	 *
	 * @return null
	 */
	@SuppressWarnings({ "ConstantConditions", "SameReturnValue" })
	public static <T> T Null() {
		return null;
	}
}
