//package com.readonlydev.lib.world.onhold.noise.chunk;
//
//import java.util.Arrays;
//
//import com.readonlydev.api.InterstellarAPI;
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//
//import net.minecraft.init.Biomes;
//import net.minecraft.world.biome.Biome;
//import net.minecraftforge.common.BiomeDictionary;
//import net.minecraftforge.common.BiomeDictionary.Type;
//import net.minecraftforge.fml.common.registry.ForgeRegistries;
//
//public final class BiomeAnalyzer {
//
//	private static final int NO_BIOME = -1;
//	private static final int MAX_BIOMES = 256;
//	private boolean[] riverBiome = new boolean[MAX_BIOMES];
//	private boolean[] oceanBiome = new boolean[MAX_BIOMES];
//	private boolean[] swampBiome = new boolean[MAX_BIOMES];
//	private boolean[] beachBiome = new boolean[MAX_BIOMES];
//	private boolean[] landBiome = new boolean[MAX_BIOMES];
//	private int[] preferredBeach;
//
//	private IExoplanetBiome scenicLakeBiome = InterstellarAPI.getBiome(Biomes.RIVER);
//	private SmoothingSearchStatus beachSearch;
//	private SmoothingSearchStatus landSearch;
//	private SmoothingSearchStatus oceanSearch;
//
//	public BiomeAnalyzer() {
//		initBiomes();
//		setupBeachesForBiomes();
//		setSearches();
//	}
//
//	public int[] xyinverted() {
//		int[] result = new int[MAX_BIOMES];
//		for (int i = 0; i < 16; i++) {
//			for (int j = 0; j < 16; j++) {
//				result[i * 16 + j] = j * 16 + i;
//			}
//		}
//		for (int i = 0; i < MAX_BIOMES; i++) {
//			if (result[result[i]] != i) {
//				throw new RuntimeException("" + i + " " + result[i] + " " + result[result[i]]);
//			}
//		}
//		return result;
//	}
//
//	private void initBiomes() {
//		ForgeRegistries.BIOMES.getValuesCollection().forEach(biome -> {
//			int id = Biome.getIdForBiome(biome);
//			if (BiomeDictionary.hasType(biome, Type.OCEAN)) {
//				oceanBiome[id] = true;
//			} else if (BiomeDictionary.hasType(biome, Type.RIVER)) {
//				riverBiome[id] = true;
//			} else if (BiomeDictionary.hasType(biome, Type.SWAMP)) {
//				swampBiome[id] = true;
//			} else if (BiomeDictionary.hasType(biome, Type.BEACH)) {
//				beachBiome[id] = true;
//			} else {
//				landBiome[id] = true;
//			}
//		});
//	}
//
//	private void setupBeachesForBiomes() {
//		preferredBeach = new int[MAX_BIOMES];
//		for (int i = 0; i < preferredBeach.length; i++) {
//			Biome biome = Biome.getBiome(i);
//			if (biome == null) {
//				continue;
//			}
//			IExoplanetBiome exoplanetBiome = InterstellarAPI.INTERSTELLAR_BIOME_MAP.getValueAt(i);
//			if (exoplanetBiome == null) {
//				continue;
//			}
//			preferredBeach[i] = exoplanetBiome.getBeachBiome().getBiomeId();
//		}
//	}
//
//	public void newRepair(final Biome[] genLayerBiomes, final int[] biomeNeighborhood, final ChunkLandscape landscape) {
//		final IExoplanetBiome[] jitteredBiomes = landscape.biome;
//		final float[] noise = landscape.noise;
//		final float[] riverStrength = landscape.river;
//
//		IExoplanetBiome realisticBiome;
//		int realisticBiomeId;
//		for (int i = 0; i < MAX_BIOMES; i++) {
//			realisticBiome = InterstellarAPI.getBiome(genLayerBiomes[i]);
//			realisticBiomeId = realisticBiome.getBiomeId();
//
//			boolean canBeRiver = riverStrength[i] > 0.7;
//			if ((noise[i] > 61.5) || (!canBeRiver || oceanBiome[realisticBiomeId] || swampBiome[realisticBiomeId])) {
//				jitteredBiomes[i] = realisticBiome;
//			} else {
//				jitteredBiomes[i] = realisticBiome.getRiverBiome();
//			}
//		}
//		beachSearch.setNotHunted();
//		beachSearch.setAbsent();
//		float beachTop = 64.5f;
//		for (int i = 0; i < MAX_BIOMES; i++) {
//			if (beachSearch.isAbsent()) {
//				break;
//			}
//			float beachBottom = 61.5f;
//			if (noise[i] < beachBottom || noise[i] > riverAdjusted(beachTop, riverStrength[i])) {
//				continue;
//			}
//			int biomeID = Biome.getIdForBiome(jitteredBiomes[i].getBiome());
//			if (swampBiome[biomeID]) {
//				continue;
//			}
//			if (beachSearch.isNotHunted()) {
//				beachSearch.hunt(biomeNeighborhood);
//				landSearch.hunt(biomeNeighborhood);
//			}
//			int foundBiome = beachSearch.biomes[i];
//			if (foundBiome != NO_BIOME) {
//				int nearestLandBiome = landSearch.biomes[i];
//				if (nearestLandBiome > -1) {
//					foundBiome = preferredBeach[nearestLandBiome];
//				}
//				jitteredBiomes[i] = InterstellarAPI.getBiome(foundBiome);
//			}
//		}
//		landSearch.setAbsent();
//		landSearch.setNotHunted();
//		for (int i = 0; i < MAX_BIOMES; i++) {
//			if (landSearch.isAbsent() && beachSearch.isAbsent()) {
//				break;
//			}
//			if (noise[i] < riverAdjusted(beachTop, riverStrength[i])) {
//				continue;
//			}
//			int biomeID = Biome.getIdForBiome(jitteredBiomes[i].getBiome());
//			if (landBiome[biomeID]) {
//				continue;
//			}
//			if (swampBiome[biomeID]) {
//				continue;
//			}
//			if (landSearch.isNotHunted()) {
//				landSearch.hunt(biomeNeighborhood);
//			}
//			int foundBiome = landSearch.biomes[i];
//			if (foundBiome == NO_BIOME) {
//				if (beachSearch.isNotHunted()) {
//					beachSearch.hunt(biomeNeighborhood);
//				}
//				foundBiome = beachSearch.biomes[i];
//			}
//			if (foundBiome != NO_BIOME) {
//				jitteredBiomes[i] = InterstellarAPI.getBiome(foundBiome);
//			}
//		}
//		oceanSearch.setAbsent();
//		oceanSearch.setNotHunted();
//		for (int i = 0; i < MAX_BIOMES; i++) {
//			if (oceanSearch.isAbsent()) {
//				break;
//			}
//			float oceanTop = 61.5f;
//			if (noise[i] > oceanTop) {
//				continue;
//			}
//			int biomeID = Biome.getIdForBiome(jitteredBiomes[i].getBiome());
//			if (oceanBiome[biomeID]) {
//				continue;
//			}
//			if (swampBiome[biomeID]) {
//				continue;
//			}
//			if (riverBiome[biomeID]) {
//				continue;
//			}
//			if (oceanSearch.isNotHunted()) {
//				oceanSearch.hunt(biomeNeighborhood);
//			}
//			int foundBiome = oceanSearch.biomes[i];
//			if (foundBiome != NO_BIOME) {
//				jitteredBiomes[i] = InterstellarAPI.getBiome(foundBiome);
//			}
//		}
//		for (int i = 0; i < MAX_BIOMES; i++) {
//			int biomeID = Biome.getIdForBiome(jitteredBiomes[i].getBiome());
//			if (noise[i] <= 61.5 && !riverBiome[biomeID]) {
//				if (!oceanBiome[biomeID] && !swampBiome[biomeID] && !beachBiome[biomeID]) {
//					jitteredBiomes[i] = scenicLakeBiome;
//				}
//			}
//		}
//	}
//
//	private void setSearches() {
//		beachSearch = new SmoothingSearchStatus(this.beachBiome);
//		landSearch = new SmoothingSearchStatus(this.landBiome);
//		oceanSearch = new SmoothingSearchStatus(this.oceanBiome);
//	}
//
//	private float riverAdjusted(float top, float river) {
//		if (river >= 1) {
//			return top;
//		}
//		float erodedRiver = river / ExoplanetWorld.ACTUAL_RIVER_PROPORTION;
//		if (erodedRiver <= 1f) {
//			top = top * (1 - erodedRiver) + 62f * erodedRiver;
//		}
//		top = top * (1 - river) + 62f * river;
//		return top;
//	}
//
//	private static final class SmoothingSearchStatus {
//
//		private final int upperLeftFinding = 0;
//		private final int upperRightFinding = 3;
//		private final int lowerLeftFinding = 1;
//		private final int lowerRightFinding = 4;
//		private final int[] quadrantBiome = new int[4];
//		private final float[] quadrantBiomeWeighting = new float[4];
//		public int[] biomes = new int[MAX_BIOMES];
//		private boolean absent = false;
//		private boolean notHunted;
//		private int[] findings = new int[3 * 3];
//		private float[] weightings = new float[3 * 3];
//		private boolean[] desired;
//		private int arraySize;
//		private int[] pattern;
//		private int biomeCount;
//
//		private SmoothingSearchStatus(boolean[] desired) {
//			this.desired = desired;
//		}
//
//		private int size() {
//			return 3;
//		}
//
//		private void hunt(int[] biomeNeighborhood) {
//			clear();
//			int oldArraySize = arraySize;
//			arraySize = (int) Math.sqrt(biomeNeighborhood.length);
//			if (arraySize * arraySize != biomeNeighborhood.length) {
//				throw new RuntimeException("non-square array");
//			}
//			if (arraySize != oldArraySize) {
//				pattern = new CircularSearchCreator().pattern(arraySize / 2f - 1, arraySize);
//			}
//			for (int xOffset = -1; xOffset <= 1; xOffset++) {
//				for (int zOffset = -1; zOffset <= 1; zOffset++) {
//					search(xOffset, zOffset, biomeNeighborhood);
//				}
//			}
//			smoothBiomes();
//		}
//
//		private void search(int xOffset, int zOffset, int[] biomeNeighborhood) {
//			int offset = xOffset * arraySize + zOffset;
//			int location = (xOffset + 1) * size() + zOffset + 1;
//			findings[location] = NO_BIOME;
//			weightings[location] = 2f;
//			for (int i = 0; i < pattern.length; i++) {
//				int biome = biomeNeighborhood[pattern[i] + offset];
//				if (desired[biome]) {
//					findings[location] = biome;
//					weightings[location] = (float) Math.sqrt(pattern.length) - (float) Math.sqrt(i) + 2f;
//					break;
//				}
//			}
//		}
//
//		private void smoothBiomes() {
//			smoothQuadrant(biomeIndex(0, 0), upperLeftFinding);
//			smoothQuadrant(biomeIndex(8, 0), upperRightFinding);
//			smoothQuadrant(biomeIndex(0, 8), lowerLeftFinding);
//			smoothQuadrant(biomeIndex(8, 8), lowerRightFinding);
//		}
//
//		private void smoothQuadrant(int biomesOffset, int findingsOffset) {
//			int upperLeft = findings[upperLeftFinding + findingsOffset];
//			int upperRight = findings[upperRightFinding + findingsOffset];
//			int lowerLeft = findings[lowerLeftFinding + findingsOffset];
//			int lowerRight = findings[lowerRightFinding + findingsOffset];
//			if ((upperLeft == upperRight) && (upperLeft == lowerLeft) && (upperLeft == lowerRight)) {
//				for (int x = 0; x < 8; x++) {
//					for (int z = 0; z < 8; z++) {
//						biomes[biomeIndex(x, z) + biomesOffset] = upperLeft;
//					}
//				}
//				return;
//			}
//			biomeCount = 0;
//			addBiome(upperLeft);
//			addBiome(upperRight);
//			addBiome(lowerLeft);
//			addBiome(lowerRight);
//			for (int x = 0; x < 8; x++) {
//				for (int z = 0; z < 8; z++) {
//					addBiome(lowerRight);
//					for (int i = 0; i < 4; i++) {
//						quadrantBiomeWeighting[i] = 0;
//					}
//					addWeight(upperLeft, weightings[upperLeftFinding + findingsOffset] * (7 - x) * (7 - z));
//					addWeight(upperRight, weightings[upperRightFinding + findingsOffset] * x * (7 - z));
//					addWeight(lowerLeft, weightings[lowerLeftFinding + findingsOffset] * (7 - x) * z);
//					addWeight(lowerRight, weightings[lowerRightFinding + findingsOffset] * x * z);
//					biomes[biomeIndex(x, z) + biomesOffset] = preferredBiome();
//				}
//			}
//		}
//
//		private void addBiome(int biome) {
//			for (int i = 0; i < biomeCount; i++) {
//				if (biome == quadrantBiome[i]) {
//					return;
//				}
//			}
//			quadrantBiome[biomeCount++] = biome;
//		}
//
//		private void addWeight(int biome, float weight) {
//			for (int i = 0; i < biomeCount; i++) {
//				if (biome == quadrantBiome[i]) {
//					quadrantBiomeWeighting[i] += weight;
//					return;
//				}
//			}
//		}
//
//		private int preferredBiome() {
//			float bestWeight = 0;
//			int result = -2;
//			for (int i = 0; i < biomeCount; i++) {
//				if (quadrantBiomeWeighting[i] > bestWeight) {
//					bestWeight = quadrantBiomeWeighting[i];
//					result = quadrantBiome[i];
//				}
//			}
//			return result;
//		}
//
//		private int biomeIndex(int x, int z) {
//			return x * 16 + z;
//		}
//
//		private void clear() {
//			Arrays.fill(findings, -1);
//		}
//
//		private boolean isAbsent() {
//			return absent;
//		}
//
//		private void setAbsent() {
//			this.absent = false;
//		}
//
//		private boolean isNotHunted() {
//			return notHunted;
//		}
//
//		private void setNotHunted() {
//			this.notHunted = true;
//		}
//	}
//}