package com.readonlydev.lib.world.biome;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.readonlydev.api.InterstellarAPI;
import com.readonlydev.lib.world.ExoplanetWorld;
import com.readonlydev.lib.world.biome.DataBuilder.BiomeData;
import com.readonlydev.lib.world.deco.TerrainDecoration;
import com.readonlydev.lib.world.noise.ISimplexData2D;
import com.readonlydev.lib.world.noise.SimplexData2D;
import com.readonlydev.lib.world.noise.VoronoiResult;
import com.readonlydev.lib.world.surface.SurfaceBase;
import com.readonlydev.lib.world.surface.SurfaceRiverOasis;
import com.readonlydev.lib.world.terrain.TerrainBase;

import lombok.Getter;
import micdoodle8.mods.galacticraft.api.world.BiomeGenBaseGC;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.common.BiomeDictionary;

public abstract class ExoplanetBiome extends BiomeGenBaseGC implements IExoplanetBiome {

	private final int exoBiomeId;
	private final RiverType riverType;
	private final BeachType beachType;
	private final TerrainBase terrain;
	private final SurfaceBase surface;
	private final SurfaceBase surfaceRiver;
	@Getter
	private final BiomeData biomeData;

	private Collection<TerrainDecoration> decos;

	protected boolean allow_rivers = false;
	protected boolean allow_scenic_lakes = false;

	public ExoplanetBiome(BiomeData data) {
		super(data, true);
		this.biomeData = data;
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.exoBiomeId = Biome.getIdForBiome(this);
		this.riverType = RiverType.NORMAL;
		this.beachType = BeachType.NORMAL;
		this.terrain = initTerrainBase();
		this.surface = initSurfaceBase();
		this.surfaceRiver = new SurfaceRiverOasis();
		this.decos = new ArrayList<>();
		initDecorations();
	}

	public abstract void generateSurface(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z,
			double noiseVal);

	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
		generateSurface(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
	}

	@Override
	public final Biome getBiome() {
		return this;
	}

	@Override
	public RiverType getRiverType() {
		return riverType;
	}

	@Override
	public BeachType getBeachType() {
		return beachType;
	}

	@Override
	public Biome preferredBeach() {
		return this.beachType.getBiome();
	}

	@Override
	public IExoplanetBiome getRiverBiome() {
		return this.riverType.getRTGBiome();
	}

	@Override
	public IExoplanetBiome getBeachBiome() {
		return InterstellarAPI.getBiome(Biome.getIdForBiome(this.preferredBeach()));
	}

	@Override
	public Collection<TerrainDecoration> getDecorations() {
		return this.decos;
	}

	@Override
	public int getBiomeId() {
		return this.exoBiomeId;
	}

	@Override
	public float rNoise(ExoplanetWorld exoWorld, int x, int y, float border, float river) {

		// we now have both lakes and rivers lowering land
		if (!this.allow_rivers) {
			float borderForRiver = border * 2;
			if (borderForRiver > 1f) {
				borderForRiver = 1;
			}
			river = 1f - (1f - borderForRiver) * (1f - river);
			return terrain.generateNoise(exoWorld, x, y, border, river);
		}

		float lakeStrength = lakePressure(exoWorld, x, y, border, exoWorld.getLakeFrequency(),
				exoWorld.getLakeBendSizeLarge(), exoWorld.getLakeBendSizeMedium(), exoWorld.getLakeBendSizeSmall());
		float lakeFlattening = lakeFlattening(lakeStrength, exoWorld.getLakeShoreLevel(),
				exoWorld.getLakeDepressionLevel());

		// combine rivers and lakes
		if (river < 1 && lakeFlattening < 1) {
			river = (1f - river) / river + (1f - lakeFlattening) / lakeFlattening;
			river = 1f / (river + 1f);
		} else if (lakeFlattening < river) {
			river = lakeFlattening;
		}

		// smooth the edges on the top
		river = 1f - river;
		river = river * (river / (river + 0.05f) * 1.05f);
		river = 1f - river;

		// make the water areas flat for water features
		float riverFlattening = river * (1f + ExoplanetWorld.RIVER_FLATTENING_ADDEND)
				- ExoplanetWorld.RIVER_FLATTENING_ADDEND;
		if (riverFlattening < 0) {
			riverFlattening = 0;
		}

		// flatten terrain to set up for the water features
		float terrainNoise = terrain.generateNoise(exoWorld, x, y, border, riverFlattening);
		// place water features
		return this.erodedNoise(exoWorld, x, y, river, border, terrainNoise);
	}

	public float erodedNoise(ExoplanetWorld exoWorld, int x, int y, float river, float border, float biomeHeight) {
		float r;
		// river of actualRiverProportions now maps to 1;
		float riverFlattening = 1f - river;
		riverFlattening = riverFlattening - (1 - ExoplanetWorld.ACTUAL_RIVER_PROPORTION);
		// return biomeHeight if no river effect
		if (riverFlattening < 0) {
			return biomeHeight;
		}
		// what was 1 set back to 1;
		riverFlattening /= ExoplanetWorld.ACTUAL_RIVER_PROPORTION;

		// back to usual meanings: 1 = no river 0 = river
		r = 1f - riverFlattening;

		if (r < 1f && biomeHeight > 55f) {
			float irregularity = exoWorld.simplexInstance(0).noise2f(x / 12f, y / 12f) * 2f
					+ exoWorld.simplexInstance(0).noise2f(x / 8f, y / 8f);
			// less on the bottom and more on the sides
			irregularity = irregularity * (1 + r);
			return biomeHeight * r + (55f + irregularity) * 1.0f * (1f - r);
		} else {
			return biomeHeight;
		}
	}

	@Override
	public float lakePressure(ExoplanetWorld exoWorld, int x, int y, float border, float lakeInterval,
			float largeBendSize, float mediumBendSize, float smallBendSize) {

		if (!this.allow_scenic_lakes) {
			return 1f;
		}

		double pX = x;
		double pY = y;
		ISimplexData2D jitterData = SimplexData2D.newDisk();

		exoWorld.simplexInstance(1).multiEval2D(x / 240.0d, y / 240.0d, jitterData);
		pX += jitterData.getDeltaX() * largeBendSize;
		pY += jitterData.getDeltaY() * largeBendSize;

		exoWorld.simplexInstance(0).multiEval2D(x / 80.0d, y / 80.0d, jitterData);
		pX += jitterData.getDeltaX() * mediumBendSize;
		pY += jitterData.getDeltaY() * mediumBendSize;

		exoWorld.simplexInstance(4).multiEval2D(x / 30.0d, y / 30.0d, jitterData);
		pX += jitterData.getDeltaX() * smallBendSize;
		pY += jitterData.getDeltaY() * smallBendSize;

		VoronoiResult lakeResults = exoWorld.worleyInstance(0).eval2D(pX / lakeInterval, pY / lakeInterval);
		return (float) (1.0d - lakeResults.interiorValue());
	}

	public float lakeFlattening(float pressure, float shoreLevel, float topLevel) {
		// adjusts the lake pressure to the river numbers. The lake shoreLevel is mapped
		// to become equivalent to actualRiverProportion
		if (pressure > topLevel) {
			return 1;
		}
		if (pressure < shoreLevel) {
			return pressure / shoreLevel * ExoplanetWorld.ACTUAL_RIVER_PROPORTION;
		}
		// proportion between top and shore becomes proportion between 1 and actual
		// river
		float proportion = (pressure - shoreLevel) / (topLevel - shoreLevel);
		return ExoplanetWorld.ACTUAL_RIVER_PROPORTION + proportion * (1f - ExoplanetWorld.ACTUAL_RIVER_PROPORTION);
	}

	@Override
	public void rReplace(ChunkPrimer primer, BlockPos blockPos, int x, int y, int depth, ExoplanetWorld exoWorld,
			float[] noise, float river, Biome[] base) {
		rReplace(primer, blockPos.getX(), blockPos.getZ(), x, y, depth, exoWorld, noise, river, base);
	}

	@Override
	public void rReplace(ChunkPrimer primer, int i, int j, int x, int y, int depth, ExoplanetWorld exoWorld,
			float[] noise, float river, Biome[] base) {
		float riverRegion = !this.allow_rivers ? 0f : river;
		this.surface.paintTerrain(primer, i, j, x, y, depth, exoWorld, noise, riverRegion, base);
	}

	protected void rReplaceWithRiver(ChunkPrimer primer, int i, int j, int x, int y, int depth, ExoplanetWorld exoWorld,
			float[] noise, float river, Biome[] base) {
		float riverRegion = !this.allow_rivers ? 0f : river;
		this.surface.paintTerrain(primer, i, j, x, y, depth, exoWorld, noise, riverRegion, base);
	}

	@Override
	public TerrainBase getTerrainBase() {
		return this.terrain;
	}

	@Override
	public SurfaceBase getSurfaceBase() {
		return this.surface;
	}

	protected BeachType determineBeachType() {

		if (getBiome().getDefaultTemperature() <= 0.05f
				|| BiomeDictionary.hasType(getBiome(), BiomeDictionary.Type.SNOWY)) {
			return BeachType.COLD;
		}

		float height = getBiome().getBaseHeight() + getBiome().getHeightVariation() * 2f;
		if (height > 1.5f || isTaigaBiome(getBiome())) {
			return BeachType.STONE;
		}

		return BeachType.NORMAL;
	}

	private boolean isTaigaBiome(Biome biome) {
		return BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD)
				&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.CONIFEROUS)
				&& BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST);
	}

	public enum BeachType {
		NORMAL, STONE, COLD;

		private IExoplanetBiome rtgBiome;
		private boolean locked = false;

		public Biome getBiome() {
			return this == STONE ? Biomes.STONE_BEACH : this == COLD ? Biomes.COLD_BEACH : Biomes.BEACH;
		}

		public IExoplanetBiome getExoBiome() {
			return rtgBiome;
		}

		public IExoplanetBiome setExoBiome(IExoplanetBiome rtgBiome) {
			if (!locked) {
				this.rtgBiome = rtgBiome;
				this.locked = true;
			}
			return rtgBiome;
		}

		public BeachType getTypeFromBiome(Biome beachBiome) {
			return beachBiome == Biomes.STONE_BEACH ? STONE : beachBiome == Biomes.COLD_BEACH ? COLD : NORMAL;
		}
	}

	public enum RiverType {
		NORMAL, FROZEN;

		private IExoplanetBiome rtgBiome;
		private boolean locked = false;

		public static RiverType getTypeFromBiome(Biome riverBiome) {
			return riverBiome == Biomes.FROZEN_RIVER ? FROZEN : NORMAL;
		}

		public Biome getBiome() {
			return this == NORMAL ? Biomes.RIVER : Biomes.FROZEN_RIVER;
		}

		public IExoplanetBiome getRTGBiome() {
			return rtgBiome;
		}

		public IExoplanetBiome setRTGBiome(IExoplanetBiome rtgBiome) {
			if (!locked) {
				this.rtgBiome = rtgBiome;
				this.locked = true;
			}
			return rtgBiome;
		}
	}

	@Override
	public SurfaceBase initSurfaceBase() {
		return null;
	}

	@Override
	public TerrainBase initTerrainBase() {
		return null;
	}

	@Override
	public double waterLakeMult() {
		return 0;
	}

	@Override
	public double lavaLakeMult() {
		return 0;
	}

	@Override
	public void initDecorations() {

	}

	public void addTypes() {

	}
}
