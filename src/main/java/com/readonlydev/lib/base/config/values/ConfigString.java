package com.readonlydev.lib.base.config.values;

import com.readonlydev.lib.base.config.impl.Category;
import com.readonlydev.lib.base.config.impl.Comment;
import com.readonlydev.lib.base.config.impl.ValidValues;

public class ConfigString extends ConfigValue {

	private String valueString;
	private ValidValues validValues;
	private boolean needsValidation;

	public ConfigString(Category category, String key, String comment, ValidValues validValues) {
		super(Type.STRING, key, category, comment);
		this.valueString = validValues.getDefault();
		this.validValues = validValues;
		this.needsValidation = true;
		this.setComment(Comment.of(format(validValues)));
	}

	public ConfigString(Category category, String key, String comment, String defaultValue) {
		super(Type.STRING, key, category, comment);
		this.valueString = defaultValue;
		this.needsValidation = false;
		this.formatDescription(defaultValue);
	}

	public String get() {
		return this.valueString;
	}

	public void set(String value) {
		this.valueString = value;
	}

	public String[] getValidValues() {
		return this.validValues.get();
	}

	public String[] getValidValuesDisplay() {
		return this.validValues.getDisplayValues();
	}

	public boolean needsValidation() {
		return this.needsValidation;
	}

	private String format(ValidValues validValues) {
		StringBuilder b = new StringBuilder();
		b.append(this.comment() + "\n");
		b.append("Valid Values: \n");
		for (String value : validValues.get()) {
			b.append("   '" + value + "'\n");
		}
		return b.toString();
	}
}
