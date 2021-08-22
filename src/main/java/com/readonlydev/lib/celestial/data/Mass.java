package com.readonlydev.lib.celestial.data;

import com.readonlydev.lib.celestial.Physics;
import com.readonlydev.lib.celestial.SpaceCalculations;

/**
 * Represents the mass of a given object.<br>
 * The input value but be in km (Kg) and in Scientific Notation
 * 
 * @author rom
 */
public class Mass extends Unit {

	public static enum Unit {
		SOLAR("M☉"),
		EARTH("M⊕"),
		ABSOLUTE("Kg");

		private final String symbol;

		Unit(String symbol) {
			this.symbol = symbol;
		}

		String getSymbol() {
			return symbol;
		}
	}

	private final double	absoluteValue;
	private Mass.Unit		unit;

	public Mass(double value) {
		this(value, Mass.Unit.ABSOLUTE);
	}

	public Mass(double value, Mass.Unit unit) {
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

	public Mass.Unit getMassUnit() {
		return unit;
	}

	public Mass toMassUnit(Mass.Unit unit) {
		this.unit = unit;
		return this;
	}

	@Override
	public String toString() {
		return getValue() + " " + unit.getSymbol();
	}

	/**
	 * Returns the mass in Earth Masses (M⊕) <br>
	 * <br>
	 * To preserve the values percision, perform any calculation <br>
	 * against the returned string value<br>
	 * 
	 * @return a String representation of the value in Earth Masses
	 */
	public String inEarthMasses() {
		return format.format(getValue() / Physics.EARTH_MASS);
	}

	/**
	 * Returns the mass in Solar Masses (M☉) <br>
	 * <br>
	 * To preserve the values percision, perform any calculation <br>
	 * against the returned string value<br>
	 * 
	 * @return a String representation of the value in Solar Masses
	 */
	public String inSolarMasses() {
		return format.format(getValue() / Physics.SUN_MASS);
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
