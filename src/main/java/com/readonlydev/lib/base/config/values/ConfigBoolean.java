package com.readonlydev.lib.base.config.values;

import com.readonlydev.lib.base.config.impl.Category;

public class ConfigBoolean extends ConfigValue {

	public boolean valueBoolean;

	public ConfigBoolean(Category category, String key, String comment, boolean defaultValue) {
		super(Type.BOOLEAN, key, category, comment);
		this.valueBoolean = defaultValue;
		this.formatDescription(String.valueOf(defaultValue));
	}

	public boolean get() {
		return this.valueBoolean;
	}

	public void set(boolean value) {
		this.valueBoolean = value;
	}
}
