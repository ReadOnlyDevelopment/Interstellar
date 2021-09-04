package com.readonlydev.lib.world.gen.chunk;

import com.readonlydev.lib.world.biome.IExoplanetBiome;

/**
 * @author Zeno410
 */
public class ChunkLandscape {

	public float[] noise = new float[256];
	public IExoplanetBiome[] biome = new IExoplanetBiome[256];
	public float[] river = new float[256];
}
