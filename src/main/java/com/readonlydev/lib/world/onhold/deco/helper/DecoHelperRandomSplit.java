//package com.readonlydev.lib.world.onhold.deco.helper;
//
//import java.util.Random;
//
//import net.minecraft.util.math.ChunkPos;
//
//import com.readonlydev.lib.world.onhold.deco.TerrainDecoration;
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//import com.readonlydev.lib.world.onhold.noise.chunk.IExoplanetBiome;
//
//class DecoHelperRandomSplit extends TerrainDecoration {
//
//	public TerrainDecoration[] decos;
//	public int[] chances;
//
//	public DecoHelperRandomSplit() {
//
//		super();
//
//		this.decos = new TerrainDecoration[] {};
//		this.chances = new int[] {};
//	}
//
//	@Override
//	public void generate(final IExoplanetBiome biome, final ExoplanetWorld exoWorld, final Random rand,
//			final ChunkPos chunkPos, final float river, final boolean hasVillage) {
//
//		if (this.decos.length < 1 || this.chances.length < 1 || this.decos.length != this.chances.length) {
//			throw new RuntimeException("DecoHelperRandomSplit is confused.");
//		}
//
//		int totalChances = 0;
//		for (int i = 0; i < this.decos.length; i++) {
//			totalChances += chances[i];
//		}
//		int chosen = rand.nextInt(totalChances);
//
//		for (int i = 0; i < this.decos.length; i++) {
//
//			if (chosen < this.chances[i]) {
//
//				this.decos[i].generate(biome, exoWorld, rand, chunkPos, river, hasVillage);
//			}
//			chosen -= chances[i];
//		}
//	}
//
//	public TerrainDecoration[] getDecos() {
//
//		return decos;
//	}
//
//	public DecoHelperRandomSplit setDecos(TerrainDecoration[] decos) {
//
//		this.decos = decos;
//		return this;
//	}
//
//	public int[] getChances() {
//
//		return chances;
//	}
//
//	public DecoHelperRandomSplit setChances(int[] chances) {
//
//		this.chances = chances;
//		return this;
//	}
//}
