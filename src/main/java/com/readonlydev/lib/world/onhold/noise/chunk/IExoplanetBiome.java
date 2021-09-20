//package com.readonlydev.lib.world.onhold.noise.chunk;
//
//import java.util.Collection;
//import java.util.Random;
//
//import com.readonlydev.lib.world.onhold.deco.TerrainDecoration;
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//import com.readonlydev.lib.world.onhold.noise.chunk.ExoplanetBiome.BeachType;
//import com.readonlydev.lib.world.onhold.noise.chunk.ExoplanetBiome.RiverType;
//import com.readonlydev.lib.world.onhold.surface.SurfaceBase;
//
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.ChunkPos;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.chunk.ChunkPrimer;
//
//public interface IExoplanetBiome {
//
//	Biome getBiome();
//
//	int getBiomeId();
//
//	RiverType getRiverType();
//
//	BeachType getBeachType();
//
//	IExoplanetBiome getRiverBiome();
//
//	IExoplanetBiome getBeachBiome();
//
//	Biome preferredBeach();
//
//	TerrainBase getTerrainBase();
//
//	SurfaceBase getSurfaceBase();
//
//	void rReplace(final ChunkPrimer primer, final BlockPos blockPos, final int x, final int y, final int depth, final ExoplanetWorld exoWorld, final float[] noise, final float river, final Biome[] base);
//
//	void rReplace(ChunkPrimer primer, int i, int j, int x, int y, int depth, ExoplanetWorld exoWorld, float[] noise, float river, Biome[] base);
//
//	float rNoise(ExoplanetWorld exoWorld, int x, int y, float border, float river);
//
//	double waterLakeMult();
//
//	double lavaLakeMult();
//
//	float lakePressure(ExoplanetWorld exoWorld, int x, int y, float border, float lakeInterval, float largeBendSize, float mediumBendSize, float smallBendSize);
//
//	void initDecorations();
//
//	Collection<TerrainDecoration> getDecorations();
//
//	default void add(TerrainDecoration decoration, boolean allowed) {
//		if (allowed) {
//			Collection<TerrainDecoration> decos = this.getDecorations();
//			decos.add(decoration);
//		}
//	}
//
//	default void addDecoration(TerrainDecoration decoration) {
//		this.add(decoration, true);
//	}
//
//	default void rDecorate(final ExoplanetWorld exoWorld, final Random rand, final ChunkPos chunkPos, final float river, final boolean hasVillage) {
//		this.getDecorations().stream().filter(deco -> deco.preGenerate(river)).forEach(deco -> deco.generate(this, exoWorld, rand, chunkPos, river, hasVillage));
//
//		this.getBiome().decorate(exoWorld.world(), rand, new BlockPos(chunkPos.x * 16, 0, chunkPos.z * 16));
//	}
//
//	TerrainBase initTerrainBase();
//
//	SurfaceBase initSurfaceBase();
//}
