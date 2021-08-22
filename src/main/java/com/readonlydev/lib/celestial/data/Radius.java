package com.readonlydev.lib.celestial.data;

import com.readonlydev.lib.celestial.Physics;
import com.readonlydev.lib.celestial.SpaceCalculations;

public class Radius {

	public static final Radius	EARTH	= new Radius(Physics.EARTH_MASS, Radius.Unit.EARTH);
	public static final Radius	SOLAR	= new Radius(Physics.SUN_MASS, Radius.Unit.SOLAR);

	public static enum Unit {
		SOLAR("R☉"),
		EARTH("R⊕"),
		ABSOLUTE("km");

		private final String symbol;

		Unit(String symbol) {
			this.symbol = symbol;
		}

		String getSymbol() {
			return symbol;
		}
	}

	private final double	absoluteValue;
	private Radius.Unit		unit;

	public Radius(double value) {
		this(value, Radius.Unit.ABSOLUTE);
	}

	public Radius(double value, Radius.Unit unit) {
		this.unit = unit;
		absoluteValue = toAbsolute(value);
	}

	protected double toAbsolute(double value) {
		double val;
		switch (unit) {
			case ABSOLUTE:
				val = value;
				break;
			case EARTH:
				val = value * Radius.EARTH.getValue();
				break;
			case SOLAR:
				val = value * Radius.SOLAR.getValue();
			default:
				throw new IllegalArgumentException();
		}
		return val;
	}

	protected double fromAbsolute(double value) {
		double val;
		switch (unit) {
			case ABSOLUTE:
				val = value;
				break;
			case EARTH:
				val = value / Radius.EARTH.getValue();
				break;
			case SOLAR:
				val = value / Radius.SOLAR.getValue();
			default:
				throw new IllegalArgumentException();
		}
		return val;
	}

	public double getValue() {
		return fromAbsolute(absoluteValue);
	}

	public Radius.Unit getRadiusUnit() {
		return unit;
	}

	public Radius toRadiusUnit(Radius.Unit unit) {
		this.unit = unit;
		return this;
	}

	@Override
	public String toString() {
		return getValue() + " " + unit.getSymbol();
	}

	public boolean isBetween(double min, double max) {
		return isBetween(min, max, true);
	}

	public boolean isBetween(double min, double max, boolean inclusive) {
		return SpaceCalculations.isBetween(getValue(), min, max, inclusive);
	}

	public boolean isLessThan(double min) {
		return isLessThan(min, true);
	}

	public boolean isLessThan(double min, boolean inclusive) {
		return SpaceCalculations.isLessThan(getValue(), min, inclusive);
	}

	public boolean isMoreThan(double max) {
		return isMoreThan(max, true);
	}

	public boolean isMoreThan(double max, boolean inclusive) {
		return SpaceCalculations.isMoreThan(getValue(), max, inclusive);
	}
}
