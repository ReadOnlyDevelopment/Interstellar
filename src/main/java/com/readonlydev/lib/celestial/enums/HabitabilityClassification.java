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

import com.readonlydev.api.celestial.IExoplanet;
import com.readonlydev.lib.celestial.data.Temperature;
import com.readonlydev.lib.utils.data.ImmutableDataPair;

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

	private ImmutableDataPair<String, String>	descriptor;
	private ImmutableDataPair<Integer, Integer>	temperateRange;

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

	public static HabitabilityClassification getTPHClassification(IExoplanet exoplanet) {
		HabitabilityClassification clazz = null;
		switch (applyData(exoplanet)) {
			case 1:
				clazz = HP;
				break;
			case 2:
				clazz = P;
				break;
			case 3:
				clazz = M;
				break;
			case 4:
				clazz = T;
				break;
			case 5:
				clazz = HT;
				break;
			case 0:
				clazz = UNKNOWN;
				break;
		}
		return clazz;
	}

	private static int applyData(IExoplanet exoplanet) {
		final Temperature temp = exoplanet.getTemperature();
		if (temp.isLessThan(-50)) {
			return 1;
		} else if (temp.isBetween(-49, 0)) {
			return 2;
		} else if (temp.isBetween(1, 49)) {
			return 3;
		} else if (temp.isBetween(50, 100)) {
			return 4;
		} else if (temp.isMoreThan(101)) {
			return 5;
		} else {
			return 0;
		}
	}
}
