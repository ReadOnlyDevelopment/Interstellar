package com.readonlydev.lib.celestial.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.Lists;
import com.readonlydev.api.celestial.ICelestialObject;
import com.readonlydev.lib.celestial.data.Mass;
import com.readonlydev.lib.celestial.data.Radius;
import com.readonlydev.lib.celestial.data.Temperature;
import com.readonlydev.lib.celestial.misc.CelestialFactory;

import lombok.Data;
import lombok.EqualsAndHashCode;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.util.ResourceLocation;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExoStarSystem extends SolarSystem implements ICelestialObject {

	private ExoStar mainStar;
	private List<Exoplanet> planetList = new ArrayList<>();

	private ExoStarSystem(Builder builder) {
		super(builder.systemName, builder.galaxy);
		this.setMapPosition(builder.mapPosition);
		this.mainStar = ExoStar.factory(this).mass(builder.mass).radius(builder.radius).temp(builder.temp).spectralType(builder.specialType).build();
		this.setMainStar(getMainStar());
	}

	public boolean addPlanet(Exoplanet exoplanet) {
		return planetList.add(exoplanet);
	}

	@Override
	public String getCelestialName() {
		return super.getName().toUpperCase(Locale.ENGLISH);
	}

	public Exoplanet[] getPlanets() {
		return planetList.toArray(new Exoplanet[planetList.size()]);
	}

	@Override
	public ExoStar getMainStar() {
		return mainStar;
	}

	public ResourceLocation[] getPlanetListIcons() {
		List<ResourceLocation> iconList = Lists.newArrayList();
		for (Exoplanet exoplanet : this.getPlanetList()) {
			iconList.add(exoplanet.getBodyIcon());
		}
		return iconList.toArray(new ResourceLocation[getPlanetList().size()]);
	}

	public static ExoStarSystem.Builder factory(String name) {
		return new ExoStarSystem.Builder(name);
	}

	public static final class Builder extends CelestialFactory<ExoStarSystem> {
		private String systemName;
		private String galaxy = "milky_way";
		private Vector3 mapPosition;
		private Mass mass;
		private Radius radius;
		private Temperature temp;
		private String specialType;

		private Builder(String name) {
			this.systemName = name;
		}

		public Builder position(Vector3 vector3) {
			this.mapPosition = vector3;
			return this;
		}

		public Builder mainStarProperties(Mass mass, Radius radius, Temperature temp, String specialType) {
			this.mass = mass;
			this.radius = radius;
			this.temp = temp;
			this.specialType = specialType;
			return this;
		}

		@Override
		public ExoStarSystem build() {
			return new ExoStarSystem(this);
		}
	}
}
