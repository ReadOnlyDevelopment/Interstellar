package com.readonlydev.lib.base.config.values;

import com.readonlydev.lib.base.config.impl.Category;

public class ConfigArrayString extends ConfigValue {

	private String[] defaultValues;

	public ConfigArrayString(Category category, String key, String comment, String... values) {
		super(Type.STRING_ARRAY, key, category, comment);
		this.defaultValues = values;

	}

	public String[] get() {
		return this.defaultValues;
	}

	public void set(String[] values) {
		this.defaultValues = values;
	}
}
