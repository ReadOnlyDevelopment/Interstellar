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

import com.readonlydev.lib.celestial.SpaceCalculations;

public class Temperature {

	public static enum Unit {
		FAHRENHEIT("°F"),
		CELSIUS("°C"),
		KELVIN("K");

		private final String symbol;

		Unit(String symbol) {
			this.symbol = symbol;
		}

		String getSymbol() {
			return symbol;
		}
	}

	private final double	kelvinValue;
	private Unit			unit;

	public Temperature(double value) {
		this(value, Temperature.Unit.KELVIN);
	}

	public Temperature(double value, Temperature.Unit unit) {
		this.unit = unit;
		kelvinValue = toKelvin(value);
	}

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

	public double getValue() {
		return fromKelvin(kelvinValue);
	}

	public Unit getUnit() {
		return unit;
	}

	public void toUnit(Unit unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return getValue() + " " + unit.getSymbol();
	}

	public boolean isBetween(int min, int max) {
		return isBetween(min, max, true);
	}

	public boolean isBetween(int min, int max, boolean inclusive) {
		return SpaceCalculations.isBetween(getValue(), min, max, inclusive);
	}

	public boolean isLessThan(int min) {
		return isLessThan(min, true);
	}

	public boolean isLessThan(int min, boolean inclusive) {
		return SpaceCalculations.isLessThan(getValue(), min, inclusive);
	}

	public boolean isMoreThan(int max) {
		return isMoreThan(max, true);
	}

	public boolean isMoreThan(int max, boolean inclusive) {
		return SpaceCalculations.isMoreThan(getValue(), max, inclusive);
	}
}
