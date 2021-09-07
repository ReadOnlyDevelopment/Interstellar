package com.readonlydev.lib.base.config.values;

import com.readonlydev.lib.base.config.impl.Category;
import com.readonlydev.lib.base.config.impl.Range;

public class ConfigArrayInteger extends ConfigValue {

	private int[] defaultValues;
	private Range<Integer> range;
	private boolean hasRange;

	public ConfigArrayInteger(Category category, String key, String comment, int... values) {
		super(Type.INTEGER_ARRAY, key, category, comment);
		this.defaultValues = values;
		this.hasRange = false;
	}

	public ConfigArrayInteger(Category category, String key, String comment, Range<Integer> range, int[] values) {
		this(category, key, comment, values);
		this.range = range;
		this.hasRange = true;
	}

	public ConfigArrayInteger(Category category, String key, String comment, int minValueInt, int maxValueInt,
			int[] values) {
		this(category, key, comment, values);
		this.range = Range.of(minValueInt, maxValueInt);
		this.hasRange = true;
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

	public int[] get() {
		return this.defaultValues;
	}

	public void set(int... values) {
		this.defaultValues = values;
	}
}
