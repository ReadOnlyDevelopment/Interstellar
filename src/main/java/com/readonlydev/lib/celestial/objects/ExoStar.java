package com.readonlydev.lib.celestial.objects;

import java.util.Locale;

import com.readonlydev.api.celestial.ICelestialObject;
import com.readonlydev.lib.celestial.HabitableZone;
import com.readonlydev.lib.celestial.Physics;
import com.readonlydev.lib.celestial.data.Mass;
import com.readonlydev.lib.celestial.data.Radius;
import com.readonlydev.lib.celestial.data.Temperature;
import com.readonlydev.lib.celestial.data.UnitType;
import com.readonlydev.lib.utils.factory.CelestialFactory;

import lombok.Data;
import lombok.EqualsAndHashCode;
import micdoodle8.mods.galacticraft.api.galaxies.Star;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExoStar extends Star implements ICelestialObject {

	private final double gConst = Physics.GRAVITATIONAL_CONSTANT;
	private final double sMass = Physics.SUN_MASS;
	private final double sLum = Physics.SUN_LUMINOSITY;
	private final double sRadi = Physics.SUN_RADIUS;
	private final double sBlzConst = Physics.STEFAN_BOLTZMANN_CONSTANT;
	private final double spdLight = Physics.SPEED_OF_LIGHT;

	private Mass mass;
	private Radius radius;
	private Temperature temperature;
	private String spectralClassifcation;
	private HabitableZone habitableZone;

	private ExoStar(Builder builder) {
		super(builder.starName);
		this.setTierRequired(-1);
		this.mass = builder.mass;
		this.radius = builder.radius;
		this.temperature = builder.temperature;
		this.spectralClassifcation = builder.spectralClassifcation;
		this.habitableZone = new HabitableZone(this);
	}

	public void setSolarSystem(ExoStarSystem system) {
		this.setParentSolarSystem(system);
	}

	public ExoStarSystem getStarSystem() {
		return (ExoStarSystem) super.getParentSolarSystem();
	}

	@Override
	public String getCelestialName() {
		return super.getName().toUpperCase(Locale.ENGLISH);
	}

	public Mass getMass() {
		return mass;
	}

	public Radius getRadius() {
		return radius;
	}

	public Temperature getSurfaceTemperature() {
		return temperature;
	}

	public String getSpectralClassifcation() {
		return spectralClassifcation;
	}

	public HabitableZone getHabitableZone() {
		return habitableZone;
	}

	public double getLuminosity() {
		getRadius().toRadiusUnit(UnitType.ABSOLUTE);
		double radius = getRadius().getValue();
		getRadius().toRadiusUnit(UnitType.SOLAR);
		return Math.pow(radius * sRadi * 1000, 2) * (4 * Math.PI * sBlzConst * Math.pow(temperature.getValue(), 4));
	}

	public double getSurfaceGravity() {
		getRadius().toRadiusUnit(UnitType.ABSOLUTE);
		double radius = getRadius().getValue();
		getRadius().toRadiusUnit(UnitType.SOLAR);
		return Math.pow(radius * sRadi * 1000, 2) * (4 * Math.PI * sBlzConst * Math.pow(temperature.getValue(), 4));
	}

	public double getShwartzchildRadius() {
		return (2.0 * gConst * getMass().getValue() * sMass / (spdLight * spdLight)) / (1000.0 * sRadi);
	}

	public static ExoStar.Builder factory(ExoStarSystem starSystem) {
		return new ExoStar.Builder(starSystem.getCelestialName());
	}

	public static final class Builder extends CelestialFactory<ExoStar> {
		private String starName;
		private Mass mass;
		private Radius radius;
		private Temperature temperature;
		private String spectralClassifcation;

		private Builder(String name) {
			this.starName = name + "_A";
		}

		public Builder mass(Mass mass) {
			this.mass = mass.toMassUnit(UnitType.SOLAR);
			return this;
		}

		public Builder mass(double solarMasses) {
			this.mass = new Mass(solarMasses, UnitType.SOLAR);
			return this;
		}

		public Builder radius(double solarRadius) {
			this.radius = new Radius(solarRadius, UnitType.SOLAR);
			return this;
		}

		public Builder radius(Radius solarRadius) {
			this.radius = solarRadius.toRadiusUnit(UnitType.SOLAR);
			return this;
		}

		public Builder temp(double surfaceTemperature) {
			this.temperature = new Temperature(surfaceTemperature);
			return this;
		}

		public Builder temp(Temperature surfaceTemperature) {
			this.temperature = surfaceTemperature;
			return this;
		}

		public Builder spectralType(String clazz) {
			this.spectralClassifcation = clazz;
			return this;
		}

		@Override
		public ExoStar build() {
			return new ExoStar(this);
		}
	}
}
