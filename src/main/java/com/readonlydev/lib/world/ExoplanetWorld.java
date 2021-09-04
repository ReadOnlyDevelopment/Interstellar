package com.readonlydev.lib.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.readonlydev.lib.Interstellar;
import com.readonlydev.lib.LibInfo;
import com.readonlydev.lib.world.gen.ExoChunkGenSettings;
import com.readonlydev.lib.world.noise.OpenSimplexNoise;
import com.readonlydev.lib.world.noise.SimplexNoise;
import com.readonlydev.lib.world.noise.SpacedWorleyNoise;
import com.readonlydev.lib.world.noise.WorleyNoise;

import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = LibInfo.ID)
public final class ExoplanetWorld {

	public static final float ACTUAL_RIVER_PROPORTION = 150f / 1600f;
	public static final float RIVER_FLATTENING_ADDEND = ACTUAL_RIVER_PROPORTION / (1f - ACTUAL_RIVER_PROPORTION);
	private static final double RIVER_LARGE_BEND_SIZE_BASE = 140D;
	private static final double RIVER_SMALL_BEND_SIZE_BASE = 30D;
	private static final double RIVER_SEPARATION_BASE = 975D;
	private static final double RIVER_VALLEY_LEVEL_BASE = 140D / 450D;
	private static final float LAKE_FREQUENCY_BASE = 649.0f;
	private static final float LAKE_SHORE_LEVEL_BASE = 0.035f;
	private static final float LAKE_DEPRESSION_LEVEL = 0.15f;
	private static final float LAKE_BEND_SIZE_LARGE = 80;
	private static final float LAKE_BEND_SIZE_MEDIUM = 30;
	private static final float LAKE_BEND_SIZE_SMALL = 12;
	private static final int SIMPLEX_INSTANCE_COUNT = 10;
	private static final int WORLEY_INSTANCE_COUNT = 5;

	private static final Map<World, ExoplanetWorld> INSTANCE_CACHE = new HashMap<>();

	private final World world;
	private final ExoChunkGenSettings generatorSettings;
	private final SimplexNoise[] simplexNoiseInstances = new SimplexNoise[SIMPLEX_INSTANCE_COUNT];
	private final WorleyNoise[] worleyNoiseInstances = new WorleyNoise[WORLEY_INSTANCE_COUNT];
	// this field is mutable, but it can only be set once from #setRandom when the
	// ChunkGeneratorRTG is initialised
	private Random generatorRandom = null;

	private ExoplanetWorld(World world) {

		this.world = world;
		this.generatorSettings = new ExoChunkGenSettings.Factory().build();

		for (int i = 0; i < SIMPLEX_INSTANCE_COUNT; i++) {
			this.simplexNoiseInstances[i] = new OpenSimplexNoise(world.getSeed() + i);
		}
		for (int i = 0; i < WORLEY_INSTANCE_COUNT; i++) {
			this.worleyNoiseInstances[i] = new SpacedWorleyNoise(world.getSeed() + i);
		}
	}

	public static ExoplanetWorld getInstance(World world) {
		if (!INSTANCE_CACHE.containsKey(world)) {
			INSTANCE_CACHE.put(world, new ExoplanetWorld(world));
		}
		return INSTANCE_CACHE.get(world);
	}

	public void setRandom(Random random) {
		if (this.generatorRandom == null) {
			this.generatorRandom = random;
		}
	}

	public World world() {
		return this.world;
	}

	public ExoChunkGenSettings getGeneratorSettings() {
		return this.generatorSettings;
	}

	public long seed() {
		return this.world().getSeed();
	}

	public Random rand() {
		return this.generatorRandom;
	}

	public long getChunkSeed(final int chunkX, final int chunkZ) {
		final long seed = world().getSeed();
		final Random rand = new Random(seed);
		return (chunkX * (rand.nextLong() / 2L * 2L + 1L)) + (chunkZ * (rand.nextLong() / 2L * 2L + 1L)) ^ seed;
	}

	public SimplexNoise simplexInstance(int index) {
		if (index >= this.simplexNoiseInstances.length) {
			index = 0;
		}
		return this.simplexNoiseInstances[index];
	}

	public WorleyNoise worleyInstance(int index) {
		if (index >= this.worleyNoiseInstances.length) {
			index = 0;
		}
		return this.worleyNoiseInstances[index];
	}

	public double getRiverLargeBendSize() {
		return RIVER_LARGE_BEND_SIZE_BASE * generatorSettings.riverBendMult;
	}

	public double getRiverSmallBendSize() {
		return RIVER_SMALL_BEND_SIZE_BASE * generatorSettings.riverBendMult;
	}

	public double getRiverSeparation() {
		return RIVER_SEPARATION_BASE / generatorSettings.riverFrequency;
	}

	public double getRiverValleyLevel() {
		return RIVER_VALLEY_LEVEL_BASE * generatorSettings.riverSizeMult * generatorSettings.riverFrequency;
	}

	public float getLakeFrequency() {
		return LAKE_FREQUENCY_BASE * generatorSettings.RTGlakeFreqMult;
	}

	public float getLakeShoreLevel() {
		return LAKE_SHORE_LEVEL_BASE * generatorSettings.RTGlakeFreqMult * generatorSettings.RTGlakeSizeMult;
	}

	public float getLakeDepressionLevel() {
		return LAKE_DEPRESSION_LEVEL * generatorSettings.RTGlakeFreqMult * generatorSettings.RTGlakeSizeMult;
	}

	public float getLakeBendSizeLarge() {
		return LAKE_BEND_SIZE_LARGE * generatorSettings.RTGlakeShoreBend;
	}

	public float getLakeBendSizeMedium() {
		return LAKE_BEND_SIZE_MEDIUM * generatorSettings.RTGlakeShoreBend;
	}

	public float getLakeBendSizeSmall() {
		return LAKE_BEND_SIZE_SMALL * generatorSettings.RTGlakeShoreBend;
	}

	@SubscribeEvent
	public static void onWorldLoad(WorldEvent.Load event) {

		World world = event.getWorld();
		if (!world.isRemote) {
			Interstellar.log.debug(
					"WorldEvent.Load: WorldType: {}, DimID: {}, DimType: {}, BiomeProvider: {}, IChunkGenerator: {}",
					world.getWorldType().getClass().getSimpleName(), world.provider.getDimension(),
					world.provider.getDimensionType(), world.provider.getBiomeProvider().getClass().getName(),
					((ChunkProviderServer) world.getChunkProvider()).chunkGenerator.getClass().getName());
			if (world.provider.getDimension() == 0) {
				Interstellar.log.info("World Seed: " + world.getSeed());
			}
		}
	}

	@SubscribeEvent
	public static void onWorldUnload(WorldEvent.Unload event) {

		final World world = event.getWorld();
		if (!world.isRemote) {
			Interstellar.log.debug(
					"WorldEvent.Unload: WorldType: {}, DimID: {}, DimType: {}, BiomeProvider: {}, IChunkGenerator: {}",
					world.getWorldType().getClass().getSimpleName(), world.provider.getDimension(),
					world.provider.getDimensionType(), world.provider.getBiomeProvider().getClass().getName(),
					((ChunkProviderServer) world.getChunkProvider()).chunkGenerator.getClass().getName());
		}
		INSTANCE_CACHE.remove(world);
	}
}
