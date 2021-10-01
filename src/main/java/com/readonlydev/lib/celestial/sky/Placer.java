package com.readonlydev.lib.celestial.sky;

import java.util.LinkedHashMap;
import java.util.Map;

import com.readonlydev.lib.celestial.objects.ExoStarSystem;
import com.readonlydev.lib.celestial.objects.Exoplanet;

public final class Placer {
	private Map<Exoplanet, Float> placementMap = new LinkedHashMap<>();

	public Placer(ExoStarSystem starSystem) {
		for (Exoplanet planet : starSystem.getPlanetList()) {
			placementMap.put(planet, planet.getRelativeDistanceFromCenter().unScaledDistance);
		}
	}

	static enum PlanetLetter {
		B,
		C,
		D,
		E,
		F,
		G,
		H;

		static int fromPlanet(Exoplanet exoplanet) {
			for (PlanetLetter letter : PlanetLetter.values()) {
				if (exoplanet.getCelestialName().endsWith(letter.name())) {
					return letter.ordinal();
				}
			}
			return -1;
		}
	}
}
