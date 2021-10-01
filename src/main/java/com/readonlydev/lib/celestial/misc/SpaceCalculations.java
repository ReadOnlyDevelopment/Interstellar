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

package com.readonlydev.lib.celestial.misc;

import java.util.Random;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.IChildBody;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Star;

public class SpaceCalculations {

	public static final long	yearFactor		= 8640000L;
	public static final long	monthFactor		= 192000L;
	public static final String	nameSeparator	= "\\";
	// try doing this in AUs
	public static final double moonDistanceFactor = 0.00015;
	// earth<-->sun = 1 -> 149598023 => 39
	public static final double planetDistanceFactor = 1.0;
	// sun<-->ra = 1069,17 (raw value from map coords). proportional value from
	// pixels: 12,8
	public static final double systemDistanceFactor = 12.3 / 1069.17;

	public static final float	maxTemperature	= 5.0F;
	public static final float	maxSolarLevel	= 10.0F;

	public static final double AUlength = 149597870700.0;

	public static final double maxSpeed = 299792458.0D; // this used to be an arbitary value, but
	// the actual speed of light makes for a
	// good maxSpeed

	/**
	 * Should calculate a thermal level depending on that body's distance from the star in it's system
	 *
	 * @param body
	 * @return
	 */
	public static float getThermalLevel(CelestialBody body) {
		if (body instanceof Star) {
			return maxTemperature;
		}
		body = getParentPlanet(body);
		float dist = body.getRelativeDistanceFromCenter().unScaledDistance;
		float temperature = (-4 * dist) + 4;
		if (temperature < -maxTemperature) {
			temperature = -maxTemperature;
		} else if (temperature > maxTemperature) {
			temperature = maxTemperature;
		}

		return temperature;
	}

	@Deprecated
	public static CelestialBody getParentPlanet(CelestialBody body) {
		if (body == null) {
			return null;
		}
		if (body instanceof Moon) {
			return ((Moon) body).getParentPlanet();
		}
		if (body instanceof IChildBody) {
			return ((IChildBody) body).getParentPlanet();
		}

		return body;
	}

	// a.value = Number(Math.pow(7.49558e-6*parseFloat(M.value)*Math.pow(parseFloat(P.value),2),1/3)).toPrecision(5)

	/**
	 * Obtains Schwartzchild radius for an object of certain mass. If the radius is lower than this value, then the object is a black hole.
	 *
	 * @param M Mass in solar masses.
	 * @return Radius in solar radii.
	 */
	public static double schwartzchildRadius(double M) {
		double r = (2.0 * Physics.GRAVITATIONAL_CONSTANT * M * Physics.SUN_MASS) / (Physics.SPEED_OF_LIGHT * Physics.SPEED_OF_LIGHT);

		return r / (1000.0 * Physics.SUN_RADIUS);
	}

	/**
	 * Obtain the luminosity using the mass-luminosity relation for main sequence stars. Formula used depends on if the mass is lower than 0.43, lower than 2, and lower than 20 or not. See http://en.wikipedia.org/wiki/Mass%E2%80%93luminosity_relation for the different formulae using Cassisi (2005) and Duric (2004).
	 * 
	 * @param mass Mass in solar units.
	 * @return Luminosity in solar units.
	 */
	public static double getLuminosityFromMassLuminosityRelation(double mass) {
		if (mass < 0.43) {
			return 0.23 * Math.pow(mass, 2.3);
		}
		if (mass < 2) {
			return Math.pow(mass, 4);
		}
		if (mass > 20) {
			return (1.5 * Math.pow(20, 3.5)) * Math.pow(mass / 20, 2); // Wikipedia says 1 as exponent here, but seems
		}
		// excesive
		return 1.5 * Math.pow(mass, 3.5);
	}

	/**
	 * Obtains star radius.
	 *
	 * @param luminosity  Luminosity in solar units.
	 * @param temperature Temperature in K.
	 * @return Radius in solar radii.
	 */
	public static double getStarRadius(double luminosity, double temperature) {
		return Math.sqrt((luminosity * Physics.SUN_LUMINOSITY) / (4.0 * Math.PI * Physics.STEFAN_BOLTZMANN_CONSTANT * Math.pow(temperature, 4.0))) / (Physics.SUN_RADIUS * 1000.0);
	}

	/**
	 * @param a1 - The first angle.
	 * @param a2 - The second angle.
	 * @param p  - A float between 0.0 and 1.0 that determines the progress between the two angles.
	 * @return a rotation angle that is between two other rotation angles. 'a1' and 'a2' are the angles between which to interpolate. Example: angle1 = 30, angle2 = 50, progress = 0.5, return = 40
	 */
	public static float interpolateRotation(float a1, float a2, float p) {
		float angle = a2 - a1;
		angle = angle < -180F ? angle += 360F : angle;
		return a1 + (p * (angle = angle >= 180F ? angle -= 360F : angle));
	}

	public static int getRandomNumberInRange(Random rand, int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		int v = rand.nextInt((max - min) + 1) + min;
		return (isBetween(v, min, max, true)) ? v : 0;
	}

	public static <T extends Number> boolean isBetween(T i, T min, T max, boolean inclusive) {
		if (i instanceof Integer) {
			if (inclusive == true) {
				return i.intValue() >= min.intValue() && i.intValue() <= max.intValue();
			} else {
				return i.intValue() > min.intValue() && i.intValue() < max.intValue();
			}
		} else if (i instanceof Double) {
			if (inclusive == true) {
				return i.doubleValue() >= min.doubleValue() && i.doubleValue() <= max.doubleValue();
			} else {
				return i.doubleValue() > min.doubleValue() && i.doubleValue() < max.doubleValue();
			}
		} else if (i instanceof Float) {
			if (inclusive == true) {
				return i.floatValue() >= min.floatValue() && i.floatValue() <= max.floatValue();
			} else {
				return i.floatValue() > min.floatValue() && i.floatValue() < max.floatValue();
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static <T extends Number> boolean isMoreThan(T i, T max, boolean inclusive) {
		if (i instanceof Integer) {
			return inclusive ? i.intValue() >= max.intValue() : i.intValue() > max.intValue();
		} else if (i instanceof Double) {
			return inclusive ? i.doubleValue() >= max.doubleValue() : i.doubleValue() > max.doubleValue();
		} else if (i instanceof Float) {
			return inclusive ? i.floatValue() >= max.floatValue() : i.floatValue() > max.floatValue();
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static <T extends Number> boolean isLessThan(T i, T min, boolean inclusive) {
		if (i instanceof Integer) {
			return inclusive ? i.intValue() <= min.intValue() : i.intValue() < min.intValue();
		} else if (i instanceof Double) {
			return inclusive ? i.doubleValue() <= min.doubleValue() : i.doubleValue() < min.doubleValue();
		} else if (i instanceof Float) {
			return inclusive ? i.floatValue() <= min.floatValue() : i.floatValue() < min.floatValue();
		} else {
			throw new IllegalArgumentException();
		}
	}
}
