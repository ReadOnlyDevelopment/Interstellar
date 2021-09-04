package com.readonlydev.lib.world.gen.chunk;

import java.util.List;
import java.util.Random;

import com.readonlydev.api.InterstellarAPI;
import com.readonlydev.api.world.IChunkGen;
import com.readonlydev.lib.Interstellar;
import com.readonlydev.lib.world.ExoplanetWorld;
import com.readonlydev.lib.world.biome.BiomeAnalyzer;
import com.readonlydev.lib.world.biome.IExoplanetBiome;
import com.readonlydev.lib.world.gen.ExoChunkGenSettings;
import com.readonlydev.lib.world.noise.ISimplexData2D;
import com.readonlydev.lib.world.noise.LimitedArrayCacheMap;
import com.readonlydev.lib.world.noise.SimplexData2D;
import com.readonlydev.lib.world.terrain.TerrainBase;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkGenerator implements IChunkGen {

	public final ExoplanetWorld exoWorld;
	private final ExoChunkGenSettings settings;
	private final World world;
	private final LimitedArrayCacheMap<ChunkPos, ChunkLandscape> landscapeCache = new LimitedArrayCacheMap<>(1024);// cache
																													// ChunkLandscape
																													// objects
	private final int sampleSize = 8;
	private final int sampleArraySize = sampleSize * 2 + 5;
	private final int[] biomeData = new int[sampleArraySize * sampleArraySize];
	private final float[] weightedBiomes = new float[256];
	private final float[][] weightings = new float[sampleArraySize * sampleArraySize][256];
	// private final MesaBiomeCombiner mesaCombiner = new MesaBiomeCombiner();
	private BiomeAnalyzer analyzer = new BiomeAnalyzer();
	private int[] xyinverted = analyzer.xyinverted();
	private Random rand;
	private Biome[] baseBiomesList;

	public ChunkGenerator(ExoplanetWorld exoWorld) {

		Interstellar.log.debug("Instantiating CPRTG using generator settings: {}",
				exoWorld.world().getWorldInfo().getGeneratorOptions());

		this.world = exoWorld.world();
		this.exoWorld = exoWorld;
		this.settings = exoWorld.getGeneratorSettings();

		this.rand = new Random(exoWorld.seed());
		this.exoWorld.setRandom(this.rand);

		this.baseBiomesList = new Biome[256];

		setWeightings();
	}

	@Override
	public Chunk generateChunk(final int cx, final int cz) {

		final ChunkPos chunkPos = new ChunkPos(cx, cz);
		final BlockPos blockPos = new BlockPos(cx * 16, 0, cz * 16);
		final BiomeProvider biomeProvider = this.world.getBiomeProvider();

		this.rand.setSeed(cx * 341873128712L + cz * 132897987541L);

		final ChunkPrimer primer = new ChunkPrimer();
		final ChunkLandscape landscape = getLandscape(biomeProvider, chunkPos);
		generateTerrain(primer, landscape.noise);

		for (int i = 0; i < 256; i++) {
			this.baseBiomesList[i] = landscape.biome[i].getBiome();
		}

		ISimplexData2D jitterData = SimplexData2D.newDisk();
		IExoplanetBiome[] jitteredBiomes = new IExoplanetBiome[256];
		IExoplanetBiome jitterbiome, actualbiome;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				int x = blockPos.getX() + i;
				int z = blockPos.getZ() + j;
				this.exoWorld.simplexInstance(0).multiEval2D(x, z, jitterData);
				int pX = (int) Math.round(x + jitterData.getDeltaX() * 32);
				int pZ = (int) Math.round(z + jitterData.getDeltaY() * 32);
				actualbiome = landscape.biome[(x & 15) * 16 + (z & 15)];
				jitterbiome = landscape.biome[(pX & 15) * 16 + (pZ & 15)];
				jitteredBiomes[i * 16 + j] = actualbiome;
			}
		}

		replaceBiomeBlocks(cx, cz, primer, jitteredBiomes, this.baseBiomesList, landscape.noise);

		Chunk chunk = new Chunk(this.world, primer, cx, cz);

		byte[] abyte1 = chunk.getBiomeArray();
		for (int i = 0; i < abyte1.length; ++i) {
			byte b = (byte) Biome.getIdForBiome(this.baseBiomesList[this.xyinverted[i]]);
			abyte1[i] = b;
		}
		chunk.setBiomeArray(abyte1);

		chunk.generateSkylightMap();

		return chunk;
	}

	public void generateTerrain(ChunkPrimer primer, float[] noise) {

		int height;
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				height = (int) noise[x * 16 + z];

				for (int y = 0; y < 256; y++) {
					if (y > height) {
						if (y < this.settings.seaLevel) {
							primer.setBlockState(x, y, z, Blocks.WATER.getDefaultState());
						} else {
							primer.setBlockState(x, y, z, Blocks.AIR.getDefaultState());
						}
					} else {
						primer.setBlockState(x, y, z, Blocks.STONE.getDefaultState());
					}
				}
			}
		}
	}

	private void replaceBiomeBlocks(int cx, int cz, ChunkPrimer primer, IExoplanetBiome[] biomes, Biome[] base,
			float[] noise) {

		if (!ForgeEventFactory.onReplaceBiomeBlocks(this, cx, cz, primer, this.world)) {
			return;
		}

		int worldX = cx * 16;
		int worldZ = cz * 16;

		MutableBlockPos mpos = new MutableBlockPos();
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				mpos.setPos(worldX + x, 0, worldZ + z);

				float river = -TerrainBase.getRiverStrength(mpos, exoWorld);
				int depth = -1;
				biomes[x * 16 + z].rReplace(primer, mpos, x, z, depth, exoWorld, noise, river, base);

				if (this.settings.bedrockLayers > 1) {
					for (int bl = 9; bl >= 0; --bl) {
						if (bl <= this.rand.nextInt(this.settings.bedrockLayers)) {
							primer.setBlockState(x, bl, z, Blocks.BEDROCK.getDefaultState());
						}
					}
				} else {
					primer.setBlockState(x, 0, z, Blocks.BEDROCK.getDefaultState());
				}
			}
		}
	}

	@Override
	public void populate(int chunkX, int chunkZ) {

		BlockFalling.fallInstantly = true;

		final BiomeProvider biomeProvider = this.world.getBiomeProvider();
		final ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
		final BlockPos blockPos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
		final BlockPos offsetpos = blockPos.add(8, 0, 8);

		IExoplanetBiome biome = InterstellarAPI.getBiome(biomeProvider.getBiome(blockPos.add(16, 0, 16)));

		this.rand.setSeed(exoWorld.getChunkSeed(chunkX, chunkZ));

		boolean hasVillage = false;

		ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, chunkX, chunkZ, false);

//		if (settings.useWaterLakes && settings.waterLakeChance > 0 && !hasVillage) {
//
//			final long nextchance = rand.nextLong();
//			final int surfacechance = settings.getSurfaceWaterLakeChance(biome.waterLakeMult());
//			final BlockPos pos = offsetpos.add(rand.nextInt(16), 0, rand.nextInt(16));
//
//			if (surfacechance > 0 && nextchance % surfacechance == 0) {
//				if (TerrainGen.populate(this, world, rand, chunkX, chunkZ, hasVillage,
//						PopulateChunkEvent.Populate.EventType.LAKE)) {
//					(new WorldGenPond(Blocks.WATER.getDefaultState())).generate(world, rand, pos.up(rand.nextInt(256)));
//				}
//			} else if (nextchance % settings.waterLakeChance == 0) {
//				if (TerrainGen.populate(this, world, rand, chunkX, chunkZ, hasVillage,
//						PopulateChunkEvent.Populate.EventType.LAKE)) {
//					(new WorldGenLakes(Blocks.WATER)).generate(world, rand, pos.up(rand.nextInt(50) + 4));
//				}
//			}
//		}

//		if (settings.useLavaLakes && settings.lavaLakeChance > 0 && !hasVillage) {
//
//			final long nextchance = rand.nextLong();
//			final int surfacechance = settings.getSurfaceLavaLakeChance(biome.lavaLakeMult());
//			final BlockPos pos = offsetpos.add(rand.nextInt(16), 0, rand.nextInt(16));
//
//			if (surfacechance > 0 && nextchance % surfacechance == 0) {
//				if (TerrainGen.populate(this, world, rand, chunkX, chunkZ, hasVillage,
//						PopulateChunkEvent.Populate.EventType.LAVA)) {
//					(new WorldGenPond(Blocks.LAVA.getDefaultState())).generate(world, rand, pos.up(rand.nextInt(256)));
//				}
//			} else if (nextchance % settings.lavaLakeChance == 0) {
//				if (TerrainGen.populate(this, world, rand, chunkX, chunkZ, hasVillage,
//						PopulateChunkEvent.Populate.EventType.LAVA)) {
//					(new WorldGenLakes(Blocks.LAVA)).generate(world, rand, pos.up(rand.nextInt(50) + 4));
//				}
//			}
//		}

		if (settings.useDungeons) {
			if (TerrainGen.populate(this, world, rand, chunkX, chunkZ, hasVillage,
					PopulateChunkEvent.Populate.EventType.DUNGEON)) {
				for (int i = 0; i < settings.dungeonChance; i++) {
					(new WorldGenDungeons()).generate(world, rand,
							offsetpos.add(rand.nextInt(16), rand.nextInt(256), rand.nextInt(16)));
				}
			}
		}

		float river = -TerrainBase.getRiverStrength(blockPos.add(16, 0, 16), exoWorld);

		if (river > 0.9f) {
			biome.getRiverBiome().rDecorate(this.exoWorld, this.rand, chunkPos, river, hasVillage);
		} else {
			biome.rDecorate(this.exoWorld, this.rand, chunkPos, river, hasVillage);
		}

		if (TerrainGen.populate(this, this.world, this.rand, chunkX, chunkZ, hasVillage,
				PopulateChunkEvent.Populate.EventType.ANIMALS)) {
			WorldEntitySpawner.performWorldGenSpawning(this.world, biome.getBiome(), blockPos.getX() + 8,
					blockPos.getZ() + 8, 16, 16, this.rand);
		}

		if (TerrainGen.populate(this, this.world, this.rand, chunkX, chunkZ, hasVillage,
				PopulateChunkEvent.Populate.EventType.ICE)) {

			for (int x = 0; x < 16; ++x) {
				for (int z = 0; z < 16; ++z) {

					// Ice.
					final BlockPos freezePos = world.getPrecipitationHeight(offsetpos.add(x, 0, z)).down();
					if (this.world.canBlockFreezeWater(freezePos)) {
						this.world.setBlockState(freezePos, Blocks.ICE.getDefaultState(), 2);
					}

					// Snow layers.
					final BlockPos surfacePos = world.getTopSolidOrLiquidBlock(offsetpos.add(x, 0, z));
					if (settings.useSnowLayers) {
						// start at 32 blocks above the surface (should be above any tree leaves), and
						// move down placing
						// snow layers on any leaves, or the surface block, if the temperature permits
						// it.
						for (BlockPos checkPos = surfacePos.up(32); checkPos.getY() >= surfacePos
								.getY(); checkPos = checkPos.down()) {
							if (world.getBlockState(checkPos).getMaterial() == Material.AIR) {
								final float temp = biomeProvider.getBiome(surfacePos).getTemperature(checkPos);
								if (temp <= settings.getClampedSnowLayerTemp()) {
									if (Blocks.SNOW_LAYER.canPlaceBlockAt(world, checkPos)) {
										this.world.setBlockState(checkPos, Blocks.SNOW_LAYER.getDefaultState(), 2);
										// we already know the next check block is not air, so skip ahead.
										checkPos = checkPos.down();
									}
								}
							}
						}
					}
				}
			}
		}

		ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, chunkX, chunkZ, hasVillage);

		BlockFalling.fallInstantly = false;
	}

	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		Biome biomegenbase = this.world.getBiome(pos);
		return biomegenbase.getSpawnableList(creatureType);
	}

	/* Landscape Geneator */

	private void setWeightings() {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				float limit = (float) Math.pow((56f * 56f), 0.7D);
				for (int mapX = 0; mapX < sampleArraySize; mapX++) {
					for (int mapZ = 0; mapZ < sampleArraySize; mapZ++) {
						float xDist = (x - (mapX - sampleSize) * 8);
						float zDist = (z - (mapZ - sampleSize) * 8);
						float distanceSquared = xDist * xDist + zDist * zDist;
						float distance = (float) Math.pow(distanceSquared, 0.7D);
						float weight = 1f - distance / limit;
						if (weight < 0) {
							weight = 0;
						}
						weightings[mapX * sampleArraySize + mapZ][x * 16 + z] = weight;
					}
				}
			}
		}
	}

	public ChunkLandscape getLandscape(final BiomeProvider biomeProvider, final ChunkPos chunkPos) {
		final BlockPos blockPos = new BlockPos(chunkPos.x * 16, 0, chunkPos.z * 16);
		ChunkLandscape landscape = landscapeCache.get(chunkPos);
		if (landscape == null) {
			landscape = generateLandscape(biomeProvider, blockPos);
			landscapeCache.put(chunkPos, landscape);
		}
		return landscape;
	}

	private synchronized ChunkLandscape generateLandscape(BiomeProvider biomeProvider, BlockPos blockPos) {
		final ChunkLandscape landscape = new ChunkLandscape();
		getNewerNoise(biomeProvider, blockPos.getX(), blockPos.getZ(), landscape);
		Biome[] biomes = new Biome[256];
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				biomes[x * 16 + z] = biomeProvider.getBiome(blockPos.add(x, 0, z));
			}
		}
		analyzer.newRepair(biomes, this.biomeData, landscape);
		return landscape;
	}

	private synchronized void getNewerNoise(final BiomeProvider biomeProvider, final int worldX, final int worldZ,
			ChunkLandscape landscape) {

		// get area biome map
		for (int x = -sampleSize; x < sampleSize + 5; x++) {
			for (int z = -sampleSize; z < sampleSize + 5; z++) {
				biomeData[(x + sampleSize) * sampleArraySize + (z + sampleSize)] = Biome
						.getIdForBiome(biomeProvider.getBiome(new BlockPos(worldX + ((x * 8)), 0, worldZ + ((z * 8)))));
			}
		}

		// fill the old smallRender
		MutableBlockPos mpos = new MutableBlockPos(worldX, 0, worldZ);
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				float totalWeight = 0;
				for (int mapX = 0; mapX < sampleArraySize; mapX++) {
					for (int mapZ = 0; mapZ < sampleArraySize; mapZ++) {
						float weight = weightings[mapX * sampleArraySize + mapZ][x * 16 + z];
						if (weight > 0) {
							totalWeight += weight;
							weightedBiomes[biomeData[mapX * sampleArraySize + mapZ]] += weight;
						}
					}
				}

				// normalize biome weights
				for (int biomeIndex = 0; biomeIndex < weightedBiomes.length; biomeIndex++) {
					weightedBiomes[biomeIndex] /= totalWeight;
				}

				// combine mesa biomes
				// mesaCombiner.adjust(weightedBiomes);

				landscape.noise[x * 16 + z] = 0f;

				float river = TerrainBase.getRiverStrength(mpos.setPos(worldX + x, 0, worldZ + z), exoWorld);
				landscape.river[x * 16 + z] = -river;

				for (int i = 0; i < 256; i++) {

					if (weightedBiomes[i] > 0f) {

						landscape.noise[x * 16 + z] += InterstellarAPI.getBiome(i).rNoise(this.exoWorld, worldX + x,
								worldZ + z, weightedBiomes[i], river + 1f) * weightedBiomes[i];

						weightedBiomes[i] = 0f;
					}
				}
			}
		}

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				BlockPos pos = new BlockPos(worldX + (x - 7) * 8 + 4, 0, worldZ + (z - 7) * 8 + 4);
				landscape.biome[x * 16 + z] = InterstellarAPI.getBiome(biomeProvider.getBiome(pos));
			}
		}
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position,
			boolean findUnexplored) {
		return null;
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		return false;
	}
}
