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

package net.interstellar.lib.celestial.objects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import net.interstellar.api.celestial.IExoplanet;
import net.interstellar.api.celestial.IMoon;
import net.interstellar.api.celestial.IStar;
import net.interstellar.lib.celestial.data.Size;
import net.interstellar.lib.celestial.data.Temperature;
import net.interstellar.lib.celestial.enums.EnumPlanetType;
import net.interstellar.lib.celestial.enums.EnumTPHClass;

@Builder(builderClassName = "Builder", builderMethodName = "create")
@Getter
public class Exoplanet implements IExoplanet {

	private String		designatedName;
	private Size		massAndRadius;
	private Temperature	temperature;
	private float		distance;

	@lombok.Builder.Default
	private IStar				hostStar	= null;
	private final List<IMoon>	moons		= new ArrayList<>();

	@Override
	public Size getMassAndRadius() {
		return massAndRadius;
	}

	@Override
	public Temperature getTemperature() {
		return temperature;
	}

	@Override
	public IStar getHostStar() {
		return hostStar;
	}

	public boolean addMoon(IMoon... moon) {
		return moons.addAll(moons);
	}

	@Override
	public List<IMoon> getMoonsList() {
		return moons;
	}

	@Override
	public IMoon[] getMoons() {
		return moons.toArray(new IMoon[0]);
	}

	@Override
	public EnumPlanetType getPlanetType() {
		return EnumPlanetType.getPlanetType(this);
	}

	@Override
	public EnumTPHClass getHabitabilityClassification() {
		return null;
	}

	static class Builder {

		public Builder distance(double distance) {
			this.distance = BigDecimal.valueOf(distance).floatValue();
			return this;
		}
	}
}
