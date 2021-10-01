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

package com.readonlydev.lib.celestial.enums;

import com.readonlydev.lib.celestial.data.Temperature;
import com.readonlydev.lib.celestial.objects.Exoplanet;
import com.readonlydev.lib.system.data.ImmutableDataPair;

/**
 * Thermal Planetary Habitability Classification<br>
 * <br>
 * The proposed thermal planetary habitability classification provides a simple classification scheme based on temperature for terrestrial exoplanets<br>
 * <br>
 * See <a href="http://phl.upr.edu/library/notes/athermalplanetaryhabitabilityclassificationforexoplanets">Publication</a> <br>
 * <br>
 * All Data is taken from the Planetary Habitability Labratory from the University of Puerto Rico at Arecibo
 *
 * @author ROMVoid
 */
public enum HabitabilityClassification {

	//@formatter:off
	HP
	(
		ImmutableDataPair.of("hP", "Hypopsychroplanet"),
		ImmutableDataPair.of(Integer.MIN_VALUE, -50)
	),
	
	P
	(
		ImmutableDataPair.of("P", "Psychroplanet"),
		ImmutableDataPair.of(-49, 0)
	),
	
	M
	(
		ImmutableDataPair.of("M", "Mesoplanet"),
		ImmutableDataPair.of(1, 49)
	),
	
	T
	(
		ImmutableDataPair.of("T", "Theroplanet"),
		ImmutableDataPair.of(50, 100)
	),
	
	HT
	(
		ImmutableDataPair.of("hT", "hyperthermoplanet"),
		ImmutableDataPair.of(101, Integer.MAX_VALUE)
	),
	
	UNKNOWN
	(
			ImmutableDataPair.of("Unknown", "Unknown"),
			ImmutableDataPair.of(null, null)
	);
	//@formatter:on

	private ImmutableDataPair<String, String> descriptor;
	private ImmutableDataPair<Integer, Integer> temperateRange;

	HabitabilityClassification(ImmutableDataPair<String, String> descriptor, ImmutableDataPair<Integer, Integer> temperateRange) {
		this.descriptor = descriptor;
		this.temperateRange = temperateRange;
	}

	public double getTempLow() {
		return temperateRange.getFirst();
	}

	public double getTempHigh() {
		return temperateRange.getSecond();
	}

	public String getClassShortName() {
		return descriptor.getFirst();
	}

	public String getName() {
		return descriptor.getSecond();
	}

	public static HabitabilityClassification getTPHClassification(Exoplanet exoplanet) {
		final Temperature temp = exoplanet.getTemperature();
		if (temp.isLessThan(-50)) {
			return HP;
		} else if (temp.isBetween(-49, 0)) {
			return P;
		} else if (temp.isBetween(1, 49)) {
			return M;
		} else if (temp.isBetween(50, 100)) {
			return T;
		} else if (temp.isMoreThan(101)) {
			return HT;
		} else {
			return UNKNOWN;
		}
	}
}
