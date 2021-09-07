package com.readonlydev.lib.base.config.values;

import com.readonlydev.lib.base.config.impl.Category;
import com.readonlydev.lib.base.config.impl.Range;

public class ConfigArrayDouble extends ConfigValue {

	private double[] defaultValues;
	private Range<Double> range;
	private boolean hasRange;

	public ConfigArrayDouble(Category category, String key, String comment, double... values) {
		super(Type.DOUBLE_ARRAY, key, category, comment);
		this.defaultValues = values;
		this.hasRange = false;
	}

	public ConfigArrayDouble(Category category, String key, String comment, Range<Double> range, double... values) {
		this(category, key, comment, values);
		this.range = range;
		this.hasRange = true;
	}

	public ConfigArrayDouble(Category category, String key, String comment, double minValueInt, double maxValueInt,
			double... values) {
		this(category, key, comment, values);
		this.range = Range.of(minValueInt, maxValueInt);
		this.hasRange = true;
	}

	public boolean hasRange() {
		return this.hasRange;
	}

	public double min() {
		return this.range.min();
	}

	public double max() {
		return this.range.max();
	}

	public double[] get() {
		return this.defaultValues;
	}

	public void set(double... values) {
		this.defaultValues = values;
	}
}
