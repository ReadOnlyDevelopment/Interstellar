package com.readonlydev.lib.world.biome;

import java.util.Random;

import com.readonlydev.api.world.biome.IBiomeExoplanet;
import com.readonlydev.lib.celestial.objects.Exoplanet;
import com.readonlydev.lib.world.biome.DataBuilder.BiomeData;

import lombok.Getter;
import micdoodle8.mods.galacticraft.api.world.BiomeGenBaseGC;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public abstract class BiomeExoplanet extends BiomeGenBaseGC implements IBiomeExoplanet {

	@Getter
	private final BiomeData biomeData;
	private Exoplanet exoplanet;

	public BiomeExoplanet(BiomeData data) {
		super(data, true);
		this.biomeData = data;
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
	}

	protected BiomeExoplanet setPlanet(Exoplanet exoplanet) {
		this.exoplanet = exoplanet;
		return this;
	}

	@Override
	public Exoplanet getBiomePlanet() {
		return exoplanet;
	}

	public abstract void generateSurface(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal);

	@Override
	public final void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
		generateSurface(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
	}

	public void addTypes() {
	}

	@Override
	public BiomeExoplanet get() {
		return this;
	}
}
