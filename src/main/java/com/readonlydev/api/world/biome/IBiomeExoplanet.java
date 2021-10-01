package com.readonlydev.api.world.biome;

import com.google.common.base.Supplier;
import com.readonlydev.lib.celestial.objects.Exoplanet;
import com.readonlydev.lib.world.biome.BiomeExoplanet;

public interface IBiomeExoplanet extends Supplier<BiomeExoplanet> {

	Exoplanet getBiomePlanet();

}
