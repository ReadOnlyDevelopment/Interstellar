package com.readonlydev.lib.base.config.values;

import com.readonlydev.lib.base.config.impl.Category;
import com.readonlydev.lib.base.config.impl.Range;

public class ConfigInteger extends ConfigValue {

	public int valueInt;
	private Range<Integer> range;
	private boolean hasRange;

	public ConfigInteger(Category category, String key, String comment, int defaultValue) {
		super(Type.INTEGER, key, category, comment);
		this.valueInt = defaultValue;
		this.hasRange = false;
		this.formatDescription(String.valueOf(defaultValue));
	}

	public ConfigInteger(Category category, String key, String comment, int defaultValue, Range<Integer> range) {
		super(Type.INTEGER, key, category, comment);
		this.valueInt = defaultValue;
		this.hasRange = true;
		this.range = range;
		this.formatDescription(range.commentAddl(defaultValue));
	}

	public ConfigInteger(Category category, String key, String comment, int defaultValue, int minValueInt,
			int maxValueInt) {
		super(Type.INTEGER, key, category, comment);
		this.range = Range.of(minValueInt, maxValueInt);
		this.hasRange = true;
		this.valueInt = defaultValue;

		this.formatDescription(Range.of(minValueInt, maxValueInt).commentAddl(defaultValue));
	}

	public boolean hasRange() {
		return this.hasRange;
	}

	public int min() {
		return this.range.min();
	}

	public int max() {
		return this.range.max();
	}

	public int get() {
		return this.valueInt;
	}

	public void set(int value) {
		this.valueInt = value;
	}
}
