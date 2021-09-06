package com.readonlydev.lib.celestial.objects;

import com.readonlydev.api.celestial.IHabitableZone;
import com.readonlydev.api.celestial.IStar;
import com.readonlydev.lib.celestial.Physics;
import com.readonlydev.lib.celestial.data.Mass;
import com.readonlydev.lib.celestial.data.Radius;
import com.readonlydev.lib.celestial.data.Temperature;
import com.readonlydev.lib.utils.factory.CelestialFactory;

import lombok.Data;
import lombok.EqualsAndHashCode;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import net.minecraft.util.ResourceLocation;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExoStar extends Star implements IStar {

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
	private IHabitableZone habitableZone;

	private ExoStar(Builder builder) {
		super(builder.starName);
		this.setRelativeSize(builder.size);
		this.setTierRequired(-1);
		this.setBodyIcon(builder.icon);
		this.habitableZone = builder.zone;
		this.mass = builder.mass;
		this.radius = builder.radius;
		this.temperature = builder.temperature;
		this.spectralClassifcation = builder.spectralClassifcation;
	}

	public void setSolarSystem(ExoStarSystem system) {
		this.setParentSolarSystem(system);
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
	public Temperature getSurfaceTemperature() {
		return temperature;
	}

	@Override
	public String getSpectralClassifcation() {
		return spectralClassifcation;
	}

	@Override
	public IHabitableZone getHabitableZone() {
		return habitableZone;
	}

	@Override
	public double getLuminosity() {
		getRadius().toRadiusUnit(Radius.Unit.ABSOLUTE);
		double radius = getRadius().getValue();
		getRadius().toRadiusUnit(Radius.Unit.SOLAR);
		return Math.pow(radius * sRadi * 1000, 2) * (4 * Math.PI * sBlzConst * Math.pow(temperature.getValue(), 4));
	}

	@Override
	public double getSurfaceGravity() {
		getRadius().toRadiusUnit(Radius.Unit.ABSOLUTE);
		double radius = getRadius().getValue();
		getRadius().toRadiusUnit(Radius.Unit.SOLAR);
		return Math.pow(radius * sRadi * 1000, 2) * (4 * Math.PI * sBlzConst * Math.pow(temperature.getValue(), 4));
	}

	@Override
	public double getShwartzchildRadius() {
		return (2.0 * gConst * getMass().getValue() * sMass / (spdLight * spdLight)) / (1000.0 * sRadi);
	}

	public static CelestialFactory<ExoStar> factory() {
		return new ExoStar.Builder();
	}

	static final class Builder implements CelestialFactory<ExoStar> {
		private String starName;
		private float size = 1.0F;
		private ResourceLocation icon;
		private IHabitableZone zone;
		private Mass mass;
		private Radius radius;
		private Temperature temperature;
		private String spectralClassifcation;

		public Builder name(String starName) {
			this.starName = starName;
			return this;
		}

		public Builder mass(double solarMasses) {
			this.mass = new Mass(solarMasses, Mass.Unit.SOLAR);
			return this;
		}

		public Builder radius(double solarRadius) {
			this.radius = new Radius(solarRadius, Radius.Unit.SOLAR);
			return this;
		}

		public Builder temperature(double surfaceTemperature) {
			this.temperature = new Temperature(surfaceTemperature);
			return this;
		}

		public Builder spectralClass(String clazz) {
			this.spectralClassifcation = clazz;
			return this;
		}

		public Builder size(float size) {
			this.size = size;
			return this;
		}

		public Builder icon(ResourceLocation icon) {
			this.icon = icon;
			return this;
		}

		public Builder habitableZone(IHabitableZone zone) {
			this.zone = zone;
			return this;
		}

		@Override
		public ExoStar build() {
			return new ExoStar(this);
		}
	}
}
