package com.readonlydev.lib.celestial.data;

import static com.readonlydev.lib.celestial.data.UnitType.ABSOLUTE;

import com.readonlydev.lib.celestial.Physics;
import com.readonlydev.lib.celestial.SpaceCalculations;

public class Radius extends Unit {

	private final double absoluteValue;
	private UnitType unit;

	public Radius(double value) {
		this(value, ABSOLUTE);
	}

	public Radius(double value, UnitType unit) {
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
			val = value * Physics.EARTH_RADIUS;
			break;
		case SOLAR:
			val = value * Physics.SUN_RADIUS;
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
			val = value / Physics.EARTH_RADIUS;
			break;
		case SOLAR:
			val = value / Physics.SUN_RADIUS;
		default:
			throw new IllegalArgumentException();
		}
		return val;
	}

	public double getValue() {
		return fromAbsolute(absoluteValue);
	}

	@Override
	public UnitType getUnitType() {
		return unit;
	}

	public Radius toRadiusUnit(UnitType unit) {
		this.unit = unit;
		return this;
	}

	@Override
	public String toString() {
		return getUnitType() == ABSOLUTE ? getValue() + " km" : getValue() + " R" + unit.getSymbol();
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
