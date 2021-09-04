package com.readonlydev.lib.world.deco;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import com.readonlydev.lib.world.ExoplanetWorld;
import com.readonlydev.lib.world.biome.IExoplanetBiome;

public abstract class TerrainDecoration {

	private boolean checkRiver;
	private float minRiver; // Minimum river value required to generate.
	private float maxRiver; // Maximum river value required to generate.

	public TerrainDecoration() {
		this.checkRiver = false;
		this.minRiver = -2f;
		this.setMaxRiver(2f);
	}

	/**
	 * Performs pre-generation checks to determine if the deco is allowed to
	 * generate.
	 */
	// TODO: [1.12] This check is only relevant for Decos added to
	// DecoCollectionDesertRiver and only used in desert biomes. This functionality
	// can be extracted and pushed down to the desert biome classes to simplify the
	// call to IRealisticBiome#rDecorate.
	public boolean preGenerate(float river) {
		return !this.checkRiver || !(river > this.maxRiver) && !(river < this.minRiver);
	}

	public abstract void generate(final IExoplanetBiome biome, final ExoplanetWorld exoWorld, final Random rand,
			final ChunkPos chunkPos, final float river, final boolean hasVillage);

	public boolean isCheckRiver() {

		return checkRiver;
	}

	public TerrainDecoration setCheckRiver(boolean checkRiver) {

		this.checkRiver = checkRiver;
		return this;
	}

	public float getMinRiver() {

		return minRiver;
	}

	public TerrainDecoration setMinRiver(float minRiver) {

		this.minRiver = minRiver;
		return this;
	}

	public float getMaxRiver() {

		return maxRiver;
	}

	public TerrainDecoration setMaxRiver(float maxRiver) {

		this.maxRiver = maxRiver;
		return this;
	}

	static BlockPos getOffsetPos(final ChunkPos pos) {
		return new BlockPos(pos.x * 16 + 8, 0, pos.z * 16 + 8);
	}

	public static int getRangedRandom(Random rand, int min, int max) {
		return min + rand.nextInt(max - min + 1);
	}
}
