/*
 * Library License
 *
 * Copyright (c) 2021 ReadOnly Development
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.readonlydev.lib.celestial.data;

import com.readonlydev.lib.celestial.misc.SpaceCalculations;

public class Temperature {

	public static enum Scale {

		/** The fahrenheit. */
		FAHRENHEIT("°F"),

		/**
		 * <p>
		 * A temperature scale where the difference between the reference temperatures<br>
		 * of the freezing and boiling points of water is divided into 100 degrees. The<br>
		 * freezing point is taken as 0 °C and the boiling point as 100 °C.<br>
		 * <b>(Symbol: °C)</b>
		 * <p>
		 * 
		 * <pre>
		 * The Celsius scale is widely known as the centigrade
		 * scale because it is divided into 100 degrees. named
		 * for the astronomer Anders Celsius, who  established
		 * the scale in 1742. William T. Kelvin used it as the
		 * basis of his absolute temperature scale, now  known
		 * as the Kelvin temperature scale
		 * </pre>
		 */
		CELSIUS("°C"),

		/**
		 * <b>Kelvin</b>: <br>
		 * A temperature scale having an absolute zero below which temperatures do not exist.<br>
		 * Absolute zero, or 0°K, is the temperature at which molecular energy is a minimum, and it <br>
		 * corresponds to a temperature of −273.15° on the Celsius temperature scale.<br>
		 * <b>(Symbol: K)</b>
		 * <p>
		 *
		 * <pre>
		 * Unlike Fahrenheit and Celsius, kelvin is not referred
		 * to or written as a degree. And is the primary unit of
		 * temperature measurement for physical sciences, but is
		 * often used in conjunction with Celsius, which has the
		 * same magnitude.
		 * </pre>
		 * 
		 * @ <a href="https://www.nist.gov/si-redefinition/kelvin-introduction">https://www.nist.gov/si-redefinition/kelvin-introduction</a>
		 * <a href="https://en.wikipedia.org/wiki/Kelvin">https://en.wikipedia.org/wiki/Kelvin</a>
		 */
		KELVIN("K");

		/** The symbol. */
		private final String symbol;

		/**
		 * Instantiates a new unit.
		 *
		 * @param symbol the symbol
		 */
		Scale(String symbol) {
			this.symbol = symbol;
		}

		/**
		 * Gets the symbol.
		 *
		 * @return the symbol
		 */
		String getSymbol() {
			return symbol;
		}
	}

	/** The kelvin value. */
	private final double kelvinValue;

	/** The unit. */
	private Scale unit;

	/**
	 * Defines a {@link Temperature} value using the provided value.<br>
	 * Automatically converts the value to its value in {@link Temperature.Scale#KELVIN}
	 *
	 * @param value the temperature value
	 */
	public Temperature(double value) {
		this(value, Temperature.Scale.KELVIN);
	}

	/**
	 * Instantiates a new temperature.
	 *
	 * @param value the value
	 * @param unit the unit
	 */
	public Temperature(double value, Temperature.Scale unit) {
		this.unit = unit;
		kelvinValue = toKelvin(value);
	}

	/**
	 * To kelvin.
	 *
	 * @param value the value
	 *
	 * @return the double
	 */
	protected double toKelvin(double value) {
		double val;
		switch (unit) {
		case KELVIN:
			val = value;
			break;
		case CELSIUS:
			val = value + 273.15;
			break;
		case FAHRENHEIT:
			val = (value + 459.67) * 5.0 / 9.0;
			break;
		default:
			throw new IllegalArgumentException();
		}
		return val;
	}

	/**
	 * From kelvin.
	 *
	 * @param value the value
	 *
	 * @return the double
	 */
	protected double fromKelvin(double value) {
		double convertedValue;
		switch (unit) {
		case KELVIN:
			convertedValue = value;
			break;
		case CELSIUS:
			convertedValue = value - 273.15;
			break;
		case FAHRENHEIT:
			convertedValue = value * 9.0 / 5.0 - 459.67;
			break;
		default:
			throw new IllegalArgumentException();
		}
		return convertedValue;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public double getValue() {
		return fromKelvin(kelvinValue);
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public Scale getUnit() {
		return unit;
	}

	/**
	 * To unit.
	 *
	 * @param unit the unit
	 */
	public void toUnit(Scale unit) {
		this.unit = unit;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return getValue() + " " + unit.getSymbol();
	}

	/**
	 * Checks if is between.
	 *
	 * @param min the min
	 * @param max the max
	 *
	 * @return true, if is between
	 */
	public boolean isBetween(int min, int max) {
		return isBetween(min, max, true);
	}

	/**
	 * Checks if is between.
	 *
	 * @param min the min
	 * @param max the max
	 * @param inclusive the inclusive
	 *
	 * @return true, if is between
	 */
	public boolean isBetween(int min, int max, boolean inclusive) {
		return SpaceCalculations.isBetween(getValue(), min, max, inclusive);
	}

	/**
	 * Checks if is less than.
	 *
	 * @param min the min
	 *
	 * @return true, if is less than
	 */
	public boolean isLessThan(int min) {
		return isLessThan(min, true);
	}

	/**
	 * Checks if is less than.
	 *
	 * @param min the min
	 * @param inclusive the inclusive
	 *
	 * @return true, if is less than
	 */
	public boolean isLessThan(int min, boolean inclusive) {
		return SpaceCalculations.isLessThan(getValue(), min, inclusive);
	}

	/**
	 * Checks if is more than.
	 *
	 * @param max the max
	 *
	 * @return true, if is more than
	 */
	public boolean isMoreThan(int max) {
		return isMoreThan(max, true);
	}

	/**
	 * Checks if is more than.
	 *
	 * @param max the max
	 * @param inclusive the inclusive
	 *
	 * @return true, if is more than
	 */
	public boolean isMoreThan(int max, boolean inclusive) {
		return SpaceCalculations.isMoreThan(getValue(), max, inclusive);
	}
}
