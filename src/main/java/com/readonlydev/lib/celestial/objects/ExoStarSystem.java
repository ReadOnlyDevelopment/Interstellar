package com.readonlydev.lib.celestial.objects;

import java.util.ArrayList;
import java.util.List;

import com.readonlydev.api.celestial.IExoplanet;
import com.readonlydev.api.celestial.ISystem;
import com.readonlydev.lib.celestial.data.Vec;
import com.readonlydev.lib.utils.factory.CelestialFactory;

import lombok.Data;
import lombok.EqualsAndHashCode;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExoStarSystem extends SolarSystem implements ISystem {

	private ExoStar mainStar;
	private List<IExoplanet> planetList = new ArrayList<>();

	private ExoStarSystem(Builder builder) {
		super(builder.systemName, builder.galaxy);
		this.setMainStar(builder.mainStar);
		this.setMapPosition(builder.mapPosition.toVector3());
		this.mainStar = builder.mainStar;
	}

	public boolean addPlanet(IExoplanet exoplanet) {
		return planetList.add(exoplanet);
	}

	@Override
	public List<IExoplanet> getPlanetsList() {
		return planetList;
	}

	@Override
	public IExoplanet[] getPlanets() {
		return planetList.toArray(new IExoplanet[planetList.size()]);
	}

	@Override
	public ExoStar getMainStar() {
		return mainStar;
	}

	public static CelestialFactory<ExoStarSystem> factory() {
		return new ExoStarSystem.Builder();
	}

	static final class Builder implements CelestialFactory<ExoStarSystem> {
		private String systemName;
		private String galaxy;
		private Vec mapPosition;
		private ExoStar mainStar;

		public Builder name(String systemName) {
			this.systemName = systemName;
			return this;
		}

		public Builder galaxy(String galaxy) {
			this.galaxy = galaxy;
			return this;
		}

		public Builder position(double x, double y, double z) {
			this.mapPosition = Vec.of(x, y, z);
			return this;
		}

		public Builder mainStar(ExoStar mainStar) {
			this.mainStar = mainStar;
			return this;
		}

		@Override
		public ExoStarSystem build() {
			return new ExoStarSystem(this);
		}
	}
}
