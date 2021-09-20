//package com.readonlydev.lib.world.onhold.deco;
//
//import java.util.Random;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//import com.readonlydev.lib.world.onhold.noise.chunk.IExoplanetBiome;
//
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.ChunkPos;
//
//public abstract class TerrainDecoration {
//
//	private boolean checkRiver;
//	private float minRiver;
//	private float maxRiver;
//
//	public TerrainDecoration() {
//		this.checkRiver = false;
//		this.minRiver = -2f;
//		this.setMaxRiver(2f);
//	}
//
//	/**
//	 * Performs pre-generation checks to determine if the decoration is allowed to generate.
//	 */
//	public boolean preGenerate(float river) {
//		return !this.checkRiver || (river <= this.maxRiver) && (river >= this.minRiver);
//	}
//
//	public abstract void generate(final IExoplanetBiome biome, final ExoplanetWorld exoWorld, final Random rand, final ChunkPos chunkPos, final float river, final boolean hasVillage);
//
//	public boolean isCheckRiver() {
//		return checkRiver;
//	}
//
//	public TerrainDecoration setCheckRiver(boolean checkRiver) {
//		this.checkRiver = checkRiver;
//		return this;
//	}
//
//	public float getMinRiver() {
//		return minRiver;
//	}
//
//	public TerrainDecoration setMinRiver(float minRiver) {
//		this.minRiver = minRiver;
//		return this;
//	}
//
//	public float getMaxRiver() {
//		return maxRiver;
//	}
//
//	public TerrainDecoration setMaxRiver(float maxRiver) {
//		this.maxRiver = maxRiver;
//		return this;
//	}
//
//	static BlockPos getOffsetPos(final ChunkPos pos) {
//		return new BlockPos(pos.x * 16 + 8, 0, pos.z * 16 + 8);
//	}
//
//	public static int getRangedRandom(Random rand, int min, int max) {
//		return min + rand.nextInt(max - min + 1);
//	}
//}
