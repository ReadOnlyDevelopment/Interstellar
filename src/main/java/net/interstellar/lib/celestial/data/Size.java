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

import net.interstellar.lib.celestial.SpaceCalculations;
import net.interstellar.lib.utils.data.ImmutableDataPair;

public class Size {

	private final ImmutableDataPair<Double, Double> sizeDataPair;

	private Size(final double mass, final double radius) {
		super();
		this.sizeDataPair = ImmutableDataPair.of(mass, radius);
	}

	public double getMass() {
		return sizeDataPair.getFirst();
	}

	public double getRadius() {
		return sizeDataPair.getSecond();
	}

	public boolean isMassBetween(double min, double max) {
		return SpaceCalculations.isBetween(getMass(), min, max, false);
	}

	public boolean isRadiusBetween(double min, double max) {
		return SpaceCalculations.isBetween(getRadius(), min, max, true);
	}

}
