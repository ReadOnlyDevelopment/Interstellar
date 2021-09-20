package com.readonlydev.api.world.biome;

import com.readonlydev.lib.celestial.objects.Exoplanet;
import com.readonlydev.lib.world.biome.BiomeExoplanet;

public interface IBiomeExoplanet {

	Exoplanet getBiomePlanet();

	BiomeExoplanet getBiome();
}
