package com.readonlydev.api.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public interface ISurfaceGenerate {

	void surfaceGenerate(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal);

}
