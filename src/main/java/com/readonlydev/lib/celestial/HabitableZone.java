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

package com.readonlydev.lib.celestial;

import java.text.DecimalFormat;

import com.readonlydev.lib.celestial.objects.ExoStar;

public final class HabitableZone {

	double teff = 5780.0;
	double lum = 1.0;
	private final double sRecentV = 1.776;
	private final double caRecentV = 2.136e-4;
	private final double cbRecentV = 2.533e-8;
	private final double ccRecentV = -1.332e-11;
	private final double cdRecentV = -3.097e-15;
	private final double sRunawayG = 1.107;
	private final double caRunawayG = 1.332e-4;
	private final double cbRunawayG = 1.580e-8;
	private final double ccRunawayG = -8.308e-12;
	private final double cdRunawayG = -1.931e-15;
	private final double SEsRunawayG = 1.188;
	private final double SEcaRunawayG = 1.433e-4;
	private final double SEcbRunawayG = 1.707e-8;
	private final double SEccRunawayG = -8.968e-12;
	private final double SEcdRunawayG = -2.084e-15;
	private final double SubsRunawayG = 0.990;
	private final double SubcaRunawayG = 1.209e-4;
	private final double SubcbRunawayG = 1.404e-8;
	private final double SubccRunawayG = -7.418e-12;
	private final double SubcdRunawayG = -1.713e-15;
	private final double sMaximumG = 0.356;
	private final double caMaximumG = 6.171e-5;
	private final double cbMaximumG = 1.698e-9;
	private final double ccMaximumG = -3.198e-12;
	private final double cdMaximumG = -5.575e-16;
	private final double sEarlyMars = 0.3207;
	private final double caEarlyMars = 5.5471e-5;
	private final double cbEarlyMars = 1.5265e-9;
	private final double ccEarlyMars = -2.874e-12;
	private final double cdEarlyMars = -5.011e-16;
	double recentV = 0;
	double RunawayG = 0;
	double MaximumG = 0;
	double EarlyMars = 0;
	double SERunawayG = 0;
	double SubRunawayG = 0;
	double recentVdis = 0;
	double RunawayGdis = 0;
	double MaximumGdis = 0;
	double EarlyMarsdis = 0;
	double SERunawayGdis = 0;
	double SubRunawayGdis = 0;

	private double conservativeRunawayGreenhouse_InnerLimit;
	private double conservativeMaximumGreenhouse_OuterLimit;
	private double optimisticRecentVenus_InnerLimit;
	private double optimisticEarlyMars_OuterLimit;

	private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

	public HabitableZone(ExoStar star) {
		this.calculate(star.getTemperature().getValue(), star.getLuminosity());
		this.conservativeRunawayGreenhouse_InnerLimit = RunawayGdis;
		this.conservativeMaximumGreenhouse_OuterLimit = MaximumGdis;
		this.optimisticRecentVenus_InnerLimit = recentVdis;
		this.optimisticEarlyMars_OuterLimit = EarlyMarsdis;
	}

	public float getConservativeInnerLimit() {
		return Float.valueOf(decimalFormat.format(conservativeRunawayGreenhouse_InnerLimit));
	}

	public float getConservativeOuterLimit() {
		return Float.valueOf(decimalFormat.format(conservativeMaximumGreenhouse_OuterLimit));
	}

	public float getOptimisticInnerLimit() {
		return Float.valueOf(decimalFormat.format(optimisticRecentVenus_InnerLimit));
	}

	public float getOptimisticOuterLimit() {
		return Float.valueOf(decimalFormat.format(optimisticEarlyMars_OuterLimit));
	}

	private void calculate(double teff, double luminosoty) {
		recentV = sRecentV + caRecentV * (teff - 5780) + cbRecentV * Math.pow(teff - 5780, 2) + ccRecentV * Math.pow(teff - 5780, 3) + cdRecentV * Math.pow(teff - 5780, 4);
		recentV = ((Math.round((recentV - Math.floor(recentV)) * 1000)) / 1000) + Math.floor(recentV);
		RunawayG = sRunawayG + caRunawayG * (teff - 5780) + cbRunawayG * Math.pow(teff - 5780, 2) + ccRunawayG * Math.pow(teff - 5780, 3) + cdRunawayG * Math.pow(teff - 5780, 4);
		RunawayG = ((Math.round((RunawayG - Math.floor(RunawayG)) * 10000)) / 10000) + Math.floor(RunawayG);
		MaximumG = sMaximumG + caMaximumG * (teff - 5780) + cbMaximumG * Math.pow(teff - 5780, 2) + ccMaximumG * Math.pow(teff - 5780, 3) + cdMaximumG * Math.pow(teff - 5780, 4);
		MaximumG = ((Math.round((MaximumG - Math.floor(MaximumG)) * 1000)) / 1000) + Math.floor(MaximumG);
		EarlyMars = sEarlyMars + caEarlyMars * (teff - 5780) + cbEarlyMars * Math.pow(teff - 5780, 2) + ccEarlyMars * Math.pow(teff - 5780, 3) + cdEarlyMars * Math.pow(teff - 5780, 4);
		EarlyMars = ((Math.round((EarlyMars - Math.floor(EarlyMars)) * 1000)) / 1000) + Math.floor(EarlyMars);

		SERunawayG = SEsRunawayG + SEcaRunawayG * (teff - 5780) + SEcbRunawayG * Math.pow(teff - 5780, 2) + SEccRunawayG * Math.pow(teff - 5780, 3) + SEcdRunawayG * Math.pow(teff - 5780, 4);
		SERunawayG = ((Math.round((SERunawayG - Math.floor(SERunawayG)) * 10000)) / 10000) + Math.floor(SERunawayG);

		SubRunawayG = SubsRunawayG + SubcaRunawayG * (teff - 5780) + SubcbRunawayG * Math.pow(teff - 5780, 2) + SubccRunawayG * Math.pow(teff - 5780, 3) + SubcdRunawayG * Math.pow(teff - 5780, 4);
		SubRunawayG = ((Math.round((SubRunawayG - Math.floor(SubRunawayG)) * 10000)) / 10000) + Math.floor(SubRunawayG);

		recentVdis = Math.sqrt(lum / recentV);
		recentVdis = ((Math.round((recentVdis - Math.floor(recentVdis)) * 1000)) / 1000) + Math.floor(recentVdis);
		RunawayGdis = Math.sqrt(lum / RunawayG);
		RunawayGdis = ((Math.round((RunawayGdis - Math.floor(RunawayGdis)) * 1000)) / 1000) + Math.floor(RunawayGdis);
		MaximumGdis = Math.sqrt(lum / MaximumG);
		MaximumGdis = ((Math.round((MaximumGdis - Math.floor(MaximumGdis)) * 1000)) / 1000) + Math.floor(MaximumGdis);
		EarlyMarsdis = Math.sqrt(lum / EarlyMars);
		EarlyMarsdis = ((Math.round((EarlyMarsdis - Math.floor(EarlyMarsdis)) * 1000)) / 1000) + Math.floor(EarlyMarsdis);

		SERunawayGdis = Math.sqrt(lum / SERunawayG);
		SERunawayGdis = ((Math.round((SERunawayGdis - Math.floor(SERunawayGdis)) * 1000)) / 1000) + Math.floor(SERunawayGdis);

		SubRunawayGdis = Math.sqrt(lum / SubRunawayG);
		SubRunawayGdis = ((Math.round((SubRunawayGdis - Math.floor(SubRunawayGdis)) * 1000)) / 1000) + Math.floor(SubRunawayGdis);
	}
}
