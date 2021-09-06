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

package com.readonlydev.lib.celestial.objects;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.readonlydev.api.celestial.IExoplanet;
import com.readonlydev.api.celestial.IMoon;
import com.readonlydev.api.celestial.IStar;
import com.readonlydev.lib.celestial.Physics;
import com.readonlydev.lib.celestial.data.Mass;
import com.readonlydev.lib.celestial.data.Radius;
import com.readonlydev.lib.celestial.data.Temperature;
import com.readonlydev.lib.celestial.enums.HabitabilityClassification;
import com.readonlydev.lib.celestial.enums.PlanetType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.world.AtmosphereInfo;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;

@Data
@EqualsAndHashCode(callSuper = false)
public class Exoplanet extends Planet implements IExoplanet {

	private Mass mass;
	private Radius radius;
	private Temperature temperature;
	private ExoStar hostStar;
	private long daylength;
	private final List<IMoon> moons = new ArrayList<>();

	private Exoplanet(Exoplanet.Builder builder) {
		super(builder.planetName);
		this.setParentSolarSystem(builder.parentSolarSystem);
		this.setRelativeSize(builder.relativeSize);
		this.setRelativeDistanceFromCenter(builder.distance);
		this.setRelativeOrbitTime(builder.relativeOrbitTime);
		this.setPhaseShift(builder.phaseShift);
		this.setTierRequired(builder.tierRequired);
		this.setBodyIcon(builder.icon);
		this.setAtmosphere(builder.getAtmosphereInfo());
		builder.gasses.forEach(e -> this.atmosphereComponent(e));
		this.setDimensionInfo(builder.dimensionID, builder.providerClass);
		this.setBiomeInfo(builder.biomes);
		this.mass = builder.mass;
		this.radius = builder.radius;
		this.temperature = builder.temperature;
		this.daylength = builder.daylength;
		this.hostStar = builder.parentSolarSystem.getMainStar();
	}

	@Override
	public Mass getMass() {
		return mass;
	}

	@Override
	public Radius getRadius() {
		return radius;
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
	public PlanetType getPlanetType() {
		return PlanetType.getPlanetType(this);
	}

	@Override
	public HabitabilityClassification getHabitabilityClassification() {
		return HabitabilityClassification.getTPHClassification(this);
	}

	@Override
	public double getSurfaceGravity() {
		getMass().toMassUnit(Mass.Unit.ABSOLUTE);
		double mass = getMass().getValue();
		getMass().toMassUnit(Mass.Unit.EARTH);
		getRadius().toRadiusUnit(Radius.Unit.ABSOLUTE);
		double radius = getRadius().getValue();
		getRadius().toRadiusUnit(Radius.Unit.EARTH);
		return (Physics.GRAVITATIONAL_CONSTANT * mass) / Math.pow(radius, 2.0);
	}

	@Override
	public double getShwartzchildRadius() {
		return 0;
	}

	@Override
	public long getDayLength() {
		return daylength;
	}

	public static class Builder {

		private String planetName;
		private float relativeSize;
		private ScalableDistance distance;
		private float relativeOrbitTime;
		private float phaseShift;
		private int dimensionID;
		private Class<? extends WorldProvider> providerClass;
		private long daylength = 24000L;
		private int tierRequired = -1;
		private boolean enableRain = false;
		private boolean isCorrosive = false;
		private float temp = 0.0F;
		private float windLevel = 0.0F;
		private float density = 0.0F;
		private Biome[] biomes;
		private ResourceLocation icon;
		private ExoStarSystem parentSolarSystem;
		private List<EnumAtmosphericGas> gasses;
		private Mass mass;
		private Radius radius;
		private Temperature temperature;

		public Builder name(String planetName) {
			this.planetName = planetName;
			return this;
		}

		public Builder daylength(long daylength) {
			this.daylength = daylength;
			return this;
		}

		public Builder mass(double mass) {
			this.mass = new Mass(mass, Mass.Unit.EARTH);
			return this;
		}

		public Builder radius(double radius) {
			this.radius = new Radius(radius, Radius.Unit.EARTH);
			return this;
		}

		public Builder size(float relativeSize) {
			this.relativeSize = relativeSize;
			return this;
		}

		public Builder distanceFromStar(double distance) {
			this.distance = new ScalableDistance((float) distance, (float) distance);
			return this;
		}

		public Builder orbitTime(float relativeOrbitTime) {
			this.relativeOrbitTime = relativeOrbitTime;
			return this;
		}

		public Builder phaseShift(float phaseShift) {
			this.phaseShift = phaseShift;
			return this;
		}

		public Builder dimensionId(int dimensionID) {
			this.dimensionID = dimensionID;
			return this;
		}

		public Builder worldProvider(Class<? extends WorldProvider> providerClass) {
			this.providerClass = providerClass;
			return this;
		}

		public Builder tier(int tierRequired) {
			this.tierRequired = tierRequired;
			return this;
		}

		public Builder enableRain() {
			this.enableRain = true;
			return this;
		}

		public Builder enableCorrosion() {
			this.isCorrosive = true;
			return this;
		}

		public Builder temperature(double temp) {
			this.temperature = new Temperature(temp);
			return this;
		}

		public Builder windLevel(double windLevel) {
			this.windLevel = (float) windLevel;
			return this;
		}

		public Builder density(double density) {
			this.density = (float) density;
			return this;
		}

		private AtmosphereInfo getAtmosphereInfo() {
			return new AtmosphereInfo(null, this.enableRain, this.isCorrosive, this.temp, this.windLevel, this.density);
		}

		public Builder biomes(Biome... biomes) {
			this.biomes = biomes;
			return this;
		}

		public Builder icon(ResourceLocation celestialBodyIcon) {
			this.icon = celestialBodyIcon;
			return this;
		}

		public Builder solarsystem(ExoStarSystem parentSolarSystem) {
			this.parentSolarSystem = parentSolarSystem;
			return this;
		}

		public Builder atmosphereGasses(EnumAtmosphericGas... gasses) {
			this.gasses = Lists.newArrayList(gasses);
			return this;
		}

		public Exoplanet build() {
			return new Exoplanet(this);
		}
	}
}
