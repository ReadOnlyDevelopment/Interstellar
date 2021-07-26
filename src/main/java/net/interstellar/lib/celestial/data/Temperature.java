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

package net.interstellar.lib.celestial.data;

import lombok.Getter;
import net.interstellar.lib.celestial.SpaceCalculations;

public class Temperature {

	@Getter
	private int temperature;

	public static Temperature of(int temperature) {
		return new Temperature(temperature);
	}

	private Temperature(int temperature) {
		this.temperature = temperature;
	}

	public boolean isBetween(int min, int max) {
		return isBetween(min, max, true);
	}

	public boolean isBetween(int min, int max, boolean inclusive) {
		return SpaceCalculations.isBetween(temperature, min, max, inclusive);
	}

	public boolean isLessThan(int min) {
		return isLessThan(min, true);
	}

	public boolean isLessThan(int min, boolean inclusive) {
		return SpaceCalculations.isLessThan(temperature, min, inclusive);
	}

	public boolean isMoreThan(int max) {
		return isMoreThan(max, true);
	}

	public boolean isMoreThan(int max, boolean inclusive) {
		return SpaceCalculations.isMoreThan(temperature, max, inclusive);
	}
}
