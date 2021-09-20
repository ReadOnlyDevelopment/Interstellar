package com.readonlydev.lib.world.gen.layer;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayerExoplanet extends GenLayer {

	public GenLayerExoplanet(long baseSeed) {
		super(baseSeed);
	}

	public static GenLayer[] createWorld(long l, CelestialBody exoplanet) {
		GenLayer biomes = new GenlayerExoplanetBiomes(l, exoplanet);
		biomes = new GenLayerZoom(1000L, biomes);
		biomes = new GenLayerZoom(1001L, biomes);
		biomes = new GenLayerZoom(1002L, biomes);
		biomes = new GenLayerZoom(1003L, biomes);
		biomes = new GenLayerZoom(1004L, biomes);
		biomes = new GenLayerZoom(1005L, biomes);
		GenLayer genLayerVeronoiZoom = new GenLayerVoronoiZoom(5L, biomes);
		biomes.initWorldGenSeed(l);
		genLayerVeronoiZoom.initWorldGenSeed(l);

		return new GenLayer[] { biomes, genLayerVeronoiZoom };
	}
}
