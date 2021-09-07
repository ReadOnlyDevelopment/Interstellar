package com.readonlydev.lib.base.config.values;

import com.readonlydev.lib.base.config.impl.Category;
import com.readonlydev.lib.base.config.impl.Range;

public class ConfigDouble extends ConfigValue {

	private Range<Double> range;
	private double valueDouble;
	private boolean hasRange;

	public ConfigDouble(Category category, String key, String comment, double defaultValue) {
		super(Type.DOUBLE, key, category, comment);
		this.valueDouble = defaultValue;
		this.hasRange = false;
		this.formatDescription(String.valueOf(defaultValue));
	}

	public ConfigDouble(Category category, String key, String comment, double defaultValue, double minValueDouble,
			double maxValueDouble) {
		super(Type.DOUBLE, key, category, comment);
		this.range = Range.of(minValueDouble, minValueDouble);
		this.hasRange = true;
		this.valueDouble = defaultValue;

		this.formatDescription(Range.of(minValueDouble, minValueDouble).commentAddl(defaultValue));
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

	public double get() {
		return this.valueDouble;
	}

	public void set(double value) {
		this.valueDouble = value;
	}
}
