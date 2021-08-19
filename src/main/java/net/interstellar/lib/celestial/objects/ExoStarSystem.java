package net.interstellar.lib.celestial.objects;

import java.util.List;
import lombok.Data;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import net.interstellar.api.celestial.IExoplanet;
import net.interstellar.api.celestial.ISystem;
import net.interstellar.lib.celestial.data.Vec;

@Data
public class ExoStarSystem extends SolarSystem implements ISystem {

	private ExoStar mainStar;

	private ExoStarSystem(Builder builder) {
		super(builder.systemName, builder.galaxy);
		this.setMainStar(builder.mainStar);
		this.setMapPosition(builder.mapPosition.toVector3());
	}

	public ExoStarSystem(String solarSystem, String parentGalaxy) {
		super(solarSystem, parentGalaxy);
	}

	@Override
	public List<IExoplanet> getPlanetsList() {
		return null;
	}

	@Override
	public IExoplanet[] getPlanets() {
		return null;
	}

	@Override
	public ExoStar getMainStar() {
		return mainStar;
	}

	public static final class Builder {
		private String	systemName;
		private String	galaxy;
		private Vec		mapPosition;
		private ExoStar	mainStar;

		private Builder() {
		}

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

		public ExoStarSystem build() {
			return new ExoStarSystem(this);
		}
	}
}
