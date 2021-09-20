package com.readonlydev.lib.world.gen.layer;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeAdaptive;
import micdoodle8.mods.miccore.IntCache;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;

public class GenlayerExoplanetBiomes extends GenLayer {

	private final Biome[] biomes;

	public GenlayerExoplanetBiomes(long seed, CelestialBody exoplanet) {
		super(seed);
		biomes = BiomeAdaptive.getBiomesListFor(exoplanet).toArray(new Biome[0]);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {
		int[] dest = IntCache.getIntCache(width * depth);
		for (int k = 0; k < depth; ++k) {
			for (int i = 0; i < width; ++i) {
				initChunkSeed(x + i, z + k);
				dest[i + k * width] = Biome.getIdForBiome(biomes[nextInt(biomes.length)]);
			}
		}
		return dest;
	}
}
