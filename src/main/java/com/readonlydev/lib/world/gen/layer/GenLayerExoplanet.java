package com.readonlydev.lib.world.gen.layer;

import com.readonlydev.lib.celestial.objects.Exoplanet;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeAdaptive;
import micdoodle8.mods.miccore.IntCache;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class GenLayerExoplanet extends GenLayer {

	private final Exoplanet planet;

	public GenLayerExoplanet(long baseSeed, Exoplanet planet) {
		super(baseSeed);
		this.planet = planet;
	}

	public static GenLayer[] createWorld(long l, Exoplanet exoplanet) {
		GenLayer biomes = new GenLayerExoplanet(l, exoplanet);
		biomes = new GenLayerZoom(1000L, biomes);
		biomes = new GenLayerZoom(1001L, biomes);
		biomes = new GenLayerZoom(1002L, biomes);
		biomes = new GenLayerZoom(1003L, biomes);
		biomes = new GenLayerZoom(1004L, biomes);
		GenLayer genLayerVeronoiZoom = new GenLayerVoronoiZoom(5L, biomes);
		biomes.initWorldGenSeed(l);
		genLayerVeronoiZoom.initWorldGenSeed(l);

		return new GenLayer[] { biomes, genLayerVeronoiZoom };
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {
		final Biome[] biomes = BiomeAdaptive.getBiomesListFor(planet).toArray(new Biome[0]);
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
