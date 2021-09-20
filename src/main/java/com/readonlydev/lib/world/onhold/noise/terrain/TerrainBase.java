//package com.readonlydev.lib.world.onhold.noise.terrain;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//import com.readonlydev.lib.world.onhold.noise.ISimplexData2D;
//import com.readonlydev.lib.world.onhold.noise.SimplexData2D;
//import com.readonlydev.lib.world.onhold.noise.SimplexNoise;
//import com.readonlydev.lib.world.onhold.noise.WorleyNoise;
//
//import net.minecraft.block.BlockSnow;
//import net.minecraft.init.Blocks;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.chunk.ChunkPrimer;
//
//abstract class TerrainBase {
//
//	private static final float minimumOceanFloor = 20.01f;
//	private static final float minimumDuneHeight = 21f;
//	protected final float minDuneHeight;
//	protected final float groundNoiseAmplitudeHills;
//	protected final float groundVariation;
//	protected final float rollingHillsMaxHeight;
//	protected float base;
//	protected float groundNoise;
//
//	public TerrainBase() {
//		this(68f);
//	}
//
//	public TerrainBase(float base) {
//		this.base = base;
//		this.minDuneHeight = minimumDuneHeight;
//		this.groundVariation = 2f;
//		this.groundNoise = this.base;
//		this.groundNoiseAmplitudeHills = 6f;
//		this.rollingHillsMaxHeight = 80f;
//	}
//
//	public static float blendedHillHeight(float simplex) {
//		float result = simplex + 1;
//		result = result * result * result + 10;
//		result = (float) Math.pow(result, .33333333333333);
//		result = result / 0.46631f;
//		result = result - 4.62021f;
//		return result;
//	}
//
//	public static float blendedHillHeight(float simplex, float turnAt) {
//		float adjusted = (1f - (1f - simplex) / (1f - turnAt));
//		return blendedHillHeight(adjusted);
//	}
//
//	public static float above(float limited, float limit) {
//		if (limited > limit) {
//			return limited - limit;
//		}
//		return 0f;
//	}
//
//	public static float unsignedPower(float number, float power) {
//		if (number > 0) {
//			return (float) Math.pow(number, power);
//		}
//		return (-1f) * (float) Math.pow((-1f) * number, power);
//	}
//
//	public static float hills(float x, float y, float hillStrength, ExoplanetWorld exoWorld) {
//		float m = exoWorld.simplexInstance(0).noise2f(x / 150f, y / 150f);
//		m = blendedHillHeight(m, 0.2f);
//
//		float sm = exoWorld.simplexInstance(2).noise2f(x / 55, y / 55);
//
//		sm = blendedHillHeight(sm, 0.2f);
//
//		sm *= sm * m;
//		m += sm / 3f;
//
//		return m * hillStrength;
//	}
//
//	public static float groundNoise(int x, int y, float amplitude, ExoplanetWorld exoWorld) {
//		float h = blendedHillHeight(exoWorld.simplexInstance(0).noise2f(x / 49f, y / 49f), 0.2f) * amplitude;
//		h += blendedHillHeight(exoWorld.simplexInstance(1).noise2f(x / 23f, y / 23f), 0.2f) * amplitude / 2f;
//		h += blendedHillHeight(exoWorld.simplexInstance(2).noise2f(x / 11f, y / 11f), 0.2f) * amplitude / 4f;
//		return h;
//	}
//
//	public static float groundNoise(float x, float y, float amplitude, ExoplanetWorld exoWorld) {
//		float h = blendedHillHeight(exoWorld.simplexInstance(0).noise2f(x / 49f, y / 49f), 0.2f) * amplitude;
//		h += blendedHillHeight(exoWorld.simplexInstance(1).noise2f(x / 23f, y / 23f), 0.2f) * amplitude / 2f;
//		h += blendedHillHeight(exoWorld.simplexInstance(2).noise2f(x / 11f, y / 11f), 0.2f) * amplitude / 4f;
//		return h;
//	}
//
//	public static float getTerrainBase() {
//		return 68f;
//	}
//
//	public static float getTerrainBase(float river) {
//		return 62f + 6f * river;
//	}
//
//	public static float mountainCap(float m) {
//		if (m > 160) {
//			m = 160 + (m - 160) * .75f;
//			if (m > 180) {
//				m = 180 + (m - 180f) * .75f;
//			}
//		}
//		return m;
//	}
//
//	public static float riverized(float height, float river) {
//		if (height < 62.45f) {
//			return height;
//		}
//		float adjustment = (height - 62.45f) / 10f + .6f;
//		river = bayesianAdjustment(river, adjustment);
//		return 62.45f + (height - 62.45f) * river;
//	}
//
//	public static float terrainBeach(int x, int y, ExoplanetWorld exoWorld, float river, float baseHeight) {
//		return riverized(baseHeight + TerrainBase.groundNoise(x, y, 4f, exoWorld), river);
//	}
//
//	public static float terrainBryce(int x, int y, ExoplanetWorld exoWorld, float river, float height) {
//		SimplexNoise simplex = exoWorld.simplexInstance(0);
//		float sn = simplex.noise2f(x / 2f, y / 2f) * 0.5f + 0.5f;
//		sn += simplex.noise2f(x, y) * 0.2 + 0.2;
//		sn += simplex.noise2f(x / 4f, y / 4f) * 4f + 4f;
//		sn += simplex.noise2f(x / 8f, y / 8f) * 2f + 2f;
//		float n = height / sn * 2;
//		n += simplex.noise2f(x / 64f, y / 64f) * 4f;
//		n = (sn < 6) ? n : 0f;
//		return riverized(getTerrainBase() + n, river);
//	}
//
//	public static float terrainCanyon(int x, int y, ExoplanetWorld exoWorld, float river, float[] height, float border, float strength, int heightLength, boolean booRiver) {
//		SimplexNoise simplex = exoWorld.simplexInstance(0);
//		float r = simplex.noise2f(x / 100f, y / 100f) * 50f * river;
//		r = r < -7.4f ? -7.4f : r > 7.4f ? 7.4f : r;
//		float b = (17f + r) * river;
//
//		float hn = simplex.noise2f(x / 12f, y / 12f) * 0.5f;
//		float sb = 0f;
//		if (b > 0f) {
//			sb = b;
//			sb = sb > 7f ? 7f : sb;
//			sb = hn * sb * river;
//		}
//		b += sb;
//
//		float cTotal = 0f;
//		float cTemp;
//		for (int i = 0; i < heightLength; i += 2) {
//			cTemp = 0;
//			if (b > height[i] && border > 0.6f + (height[i] * 0.015f) + hn * 0.2f) {
//				cTemp = b > height[i] + height[i + 1] ? height[i + 1] : b - height[i];
//				cTemp *= strength;
//			}
//			cTotal += cTemp;
//		}
//		float bn = 0f;
//		if (booRiver) {
//			if (b < 5f) {
//				bn = 5f - b;
//				for (int i = 0; i < 3; i++) {
//					bn *= bn / 4.5f;
//				}
//			}
//		} else if (b < 5f) {
//			bn = (simplex.noise2f(x / 7f, y / 7f) * 1.3f + simplex.noise2f(x / 15f, y / 15f) * 2f) * (5f - b) * 0.2f;
//		}
//		b += cTotal - bn;
//
//		return getTerrainBase(river) + b;
//	}
//
//	public static float terrainFlatLakes(int x, int y, ExoplanetWorld exoWorld, float river, float baseHeight) {
//		/*
//		 * float h = simplex.noise2f(x / 300f, y / 300f) * 40f * river; h = h > hMax ? hMax : h; h += simplex.noise2f(x / 50f, y / 50f) * (12f - h) * 0.4f; h += simplex.noise2f(x / 15f, y
//		 * / 15f) * (12f - h) * 0.15f;
//		 */
//
//		float ruggedNoise = exoWorld.simplexInstance(1).noise2f(x / VariableRuggednessEffect.STANDARD_RUGGEDNESS_WAVELENGTH, y / VariableRuggednessEffect.STANDARD_RUGGEDNESS_WAVELENGTH);
//
//		ruggedNoise = blendedHillHeight(ruggedNoise);
//		float h = groundNoise(x, y, 2f * (ruggedNoise + 1f), exoWorld);
//		return riverized(baseHeight + h, river);
//	}
//
//	public static float terrainForest(int x, int y, ExoplanetWorld exoWorld, float river, float baseHeight) {
//		SimplexNoise simplex = exoWorld.simplexInstance(0);
//
//		double h = simplex.noise2d(x / 100d, y / 100d) * 8d;
//		h += simplex.noise2d(x / 30d, y / 30d) * 4d;
//		h += simplex.noise2d(x / 15d, y / 15d) * 2d;
//		h += simplex.noise2d(x / 7d, y / 7d);
//
//		return riverized(baseHeight + 20f + (float) h, river);
//	}
//
//	public static float terrainGrasslandFlats(int x, int y, ExoplanetWorld exoWorld, float river, float mPitch, float baseHeight) {
//		SimplexNoise simplex = exoWorld.simplexInstance(0);
//		float h = simplex.noise2f(x / 100f, y / 100f) * 7;
//		h += simplex.noise2f(x / 20f, y / 20f) * 2;
//
//		float m = simplex.noise2f(x / 180f, y / 180f) * 35f * river;
//		m *= m / mPitch;
//
//		float sm = blendedHillHeight(simplex.noise2f(x / 30f, y / 30f)) * 8f;
//		sm *= m / 20f > 3.75f ? 3.75f : m / 20f;
//		m += sm;
//
//		return riverized(baseHeight + h + m, river);
//	}
//
//	public static float terrainGrasslandHills(int x, int y, ExoplanetWorld exoWorld, float river, float vWidth, float vHeight, float hWidth, float hHeight, float bHeight) {
//		float h = exoWorld.simplexInstance(0).noise2f(x / vWidth, y / vWidth);
//		h = blendedHillHeight(h, 0.3f);
//
//		float m = exoWorld.simplexInstance(1).noise2f(x / hWidth, y / hWidth);
//		m = blendedHillHeight(m, 0.3f) * h;
//		m *= m;
//
//		h *= vHeight * river;
//		m *= hHeight * river;
//
//		h += TerrainBase.groundNoise(x, y, 4f, exoWorld);
//
//		return riverized(bHeight + h, river) + m;
//	}
//
//	public static float terrainGrasslandMountains(int x, int y, ExoplanetWorld exoWorld, float river, float hFactor, float mFactor, float baseHeight) {
//		SimplexNoise simplex0 = exoWorld.simplexInstance(0);
//		float h = simplex0.noise2f(x / 100f, y / 100f) * hFactor;
//		h += simplex0.noise2f(x / 20f, y / 20f) * 2;
//
//		float m = simplex0.noise2f(x / 230f, y / 230f) * mFactor * river;
//		m *= m / 35f;
//		m = m > 70f ? 70f + (m - 70f) / 2.5f : m;
//
//		float c = exoWorld.simplexInstance(4).noise3f(x / 30f, y / 30f, 1f) * (m * 0.30f);
//
//		float sm = simplex0.noise2f(x / 30f, y / 30f) * 8f + simplex0.noise2f(x / 8f, y / 8f);
//		sm *= m / 20f > 2.5f ? 2.5f : m / 20f;
//		m += sm;
//
//		m += c;
//
//		return riverized(baseHeight + h + m, river);
//	}
//
//	public static float terrainHighland(float x, float y, ExoplanetWorld exoWorld, float river, float start, float width, float height, float baseAdjust) {
//		float h = exoWorld.simplexInstance(0).noise2f(x / width, y / width) * height * river;
//		h = h < start ? start + ((h - start) / 4.5f) : h;
//		if (h < 0f) {
//			h = 0;
//		}
//		if (h > 0f) {
//			float st = h * 1.5f > 15f ? 15f : h * 1.5f;
//			h += exoWorld.simplexInstance(4).noise3f(x / 70f, y / 70f, 1f) * st;
//			h = h * river;
//		}
//		h += blendedHillHeight(exoWorld.simplexInstance(0).noise2f(x / 20f, y / 20f), 0f) * 4f;
//		h += blendedHillHeight(exoWorld.simplexInstance(0).noise2f(x / 12f, y / 12f), 0f) * 2f;
//		h += blendedHillHeight(exoWorld.simplexInstance(0).noise2f(x / 5f, y / 5f), 0f) * 1f;
//		if (h < 0) {
//			h = h / 2f;
//		}
//		if (h < -3) {
//			h = (h + 3f) / 2f - 3f;
//		}
//		return getTerrainBase(river) + (h + baseAdjust) * river;
//	}
//
//	public static float terrainLonelyMountain(int x, int y, ExoplanetWorld exoWorld, float river, float strength, float width, float terrainHeight) {
//		SimplexNoise simplex0 = exoWorld.simplexInstance(0);
//		float h = blendedHillHeight(simplex0.noise2f(x / 20f, y / 20f), 0) * 3;
//		h += blendedHillHeight(simplex0.noise2f(x / 7f, y / 7f), 0) * 1.3f;
//
//		float m = simplex0.noise2f(x / width, y / width) * strength * river;
//		m *= m / 35f;
//		m = m > 70f ? 70f + (m - 70f) / 2.5f : m;
//
//		float st = m * 0.7f;
//		st = st > 20f ? 20f : st;
//		float c = exoWorld.simplexInstance(4).noise3f(x / 30f, y / 30f, 1f) * (5f + st);
//
//		float sm = simplex0.noise2f(x / 30f, y / 30f) * 8f + simplex0.noise2f(x / 8f, y / 8f);
//		sm *= (m + 10f) / 20f > 2.5f ? 2.5f : (m + 10f) / 20f;
//		m += sm;
//
//		m += c;
//		if (m > 90) {
//			m = 90f + (m - 90f) * .75f;
//			if (m > 110) {
//				m = 110f + (m - 110f) * .75f;
//			}
//		}
//		return riverized(terrainHeight + h + m, river);
//	}
//
//	public static float terrainMarsh(int x, int y, ExoplanetWorld exoWorld, float baseHeight, float river) {
//		SimplexNoise simplex = exoWorld.simplexInstance(0);
//		float h = simplex.noise2f(x / 130f, y / 130f) * 20f;
//
//		h += simplex.noise2f(x / 12f, y / 12f) * 2f;
//		h += simplex.noise2f(x / 18f, y / 18f) * 4f;
//
//		h = h < 8f ? 0f : h - 8f;
//		if (h == 0f) {
//			h += simplex.noise2f(x / 20f, y / 20f) + simplex.noise2f(x / 5f, y / 5f);
//			h *= 2f;
//		}
//		return riverized(baseHeight + h, river);
//	}
//
//	public static float terrainOcean(int x, int y, ExoplanetWorld exoWorld, float river, float averageFloor) {
//		SimplexNoise simplex = exoWorld.simplexInstance(0);
//		float h = simplex.noise2f(x / 300f, y / 300f) * 8f * river;
//
//		h += simplex.noise2f(x / 50f, y / 50f) * 2f;
//		h += simplex.noise2f(x / 15f, y / 15f) * 1f;
//
//		float floNoise = averageFloor + h;
//		floNoise = floNoise < minimumOceanFloor ? minimumOceanFloor : floNoise;
//
//		return floNoise;
//	}
//
//	public static float terrainOceanCanyon(int x, int y, ExoplanetWorld exoWorld, float river, float[] height, float border, float strength, int heightLength, boolean booRiver) {
//		SimplexNoise simplex = exoWorld.simplexInstance(0);
//		river *= 1.3f;
//		river = river > 1f ? 1f : river;
//		float r = simplex.noise2f(x / 100f, y / 100f) * 50f;
//		r = r < -7.4f ? -7.4f : r > 7.4f ? 7.4f : r;
//		float b = (17f + r) * river;
//
//		float hn = simplex.noise2f(x / 12f, y / 12f) * 0.5f;
//		float sb = 0f;
//		if (b > 0f) {
//			sb = b;
//			sb = sb > 7f ? 7f : sb;
//			sb = hn * sb;
//		}
//		b += sb;
//
//		float cTotal = 0f;
//		float cTemp;
//		for (int i = 0; i < heightLength; i += 2) {
//			cTemp = 0;
//			if (b > height[i] && border > 0.6f + (height[i] * 0.015f) + hn * 0.2f) {
//				cTemp = b > height[i] + height[i + 1] ? height[i + 1] : b - height[i];
//				cTemp *= strength;
//			}
//			cTotal += cTemp;
//		}
//		float bn = 0f;
//		if (booRiver) {
//			if (b < 5f) {
//				bn = 5f - b;
//				for (int i = 0; i < 3; i++) {
//					bn *= bn / 4.5f;
//				}
//			}
//		} else if (b < 5f) {
//			bn = (simplex.noise2f(x / 7f, y / 7f) * 1.3f + simplex.noise2f(x / 15f, y / 15f) * 2f) * (5f - b) * 0.2f;
//		}
//		b += cTotal - bn;
//
//		float floNoise = 30f + b;
//		floNoise = floNoise < minimumOceanFloor ? minimumOceanFloor : floNoise;
//
//		return floNoise;
//	}
//
//	public static float terrainPlains(int x, int y, ExoplanetWorld exoWorld, float river, float stPitch, float stFactor, float hPitch, float hDivisor, float baseHeight) {
//		SimplexNoise simplex = exoWorld.simplexInstance(0);
//		float floNoise;
//		float st = (simplex.noise2f(x / stPitch, y / stPitch) + 0.38f) * stFactor * river;
//		st = st < 0.2f ? 0.2f : st;
//
//		float h = simplex.noise2f(x / hPitch, y / hPitch) * st * 2f;
//		h = h > 0f ? -h : h;
//		h += st;
//		h *= h / hDivisor;
//		h += st;
//
//		floNoise = riverized(baseHeight + h, river);
//		return floNoise;
//	}
//
//	public static float terrainPlateau(float x, float y, ExoplanetWorld exoWorld, float river, float[] height, float border, float strength, int heightLength, float selectorWaveLength, boolean isM) {
//		SimplexNoise simplex = exoWorld.simplexInstance(0);
//		river = river > 1f ? 1f : river;
//		float border2 = border * 4 - 2.5f;
//		border2 = border2 > 1f ? 1f : (border2 < 0f) ? 0f : border2;
//		float b = simplex.noise2f(x / 40f, y / 40f) * 1.5f;
//
//		float sn = simplex.noise2f(x / selectorWaveLength, y / selectorWaveLength) * 0.5f + 0.5f;
//		sn *= border2;
//		sn *= river;
//		sn += simplex.noise2f(x / 4f, y / 4f) * 0.01f + 0.01f;
//		sn += simplex.noise2f(x / 2f, y / 2f) * 0.01f + 0.01f;
//		float n, hn, stepUp;
//		for (int i = 0; i < heightLength; i += 2) {
//			n = (sn - height[i + 1]) / (1 - height[i + 1]);
//			n = n * strength;
//			n = (n < 0f) ? 0f : (n > 1f) ? 1f : n;
//			hn = height[i] * 0.5f * ((sn * 2f) - 0.4f);
//			hn = (hn < 0) ? 0f : hn;
//			stepUp = 0f;
//			if (sn > height[i + 1]) {
//				stepUp += (height[i] * n);
//				if (isM) {
//					stepUp += simplex.noise2f(x / 20f, y / 20f) * 3f * n;
//					stepUp += simplex.noise2f(x / 12f, y / 12f) * 2f * n;
//					stepUp += simplex.noise2f(x / 5f, y / 5f) * 1f * n;
//				}
//			}
//			if (i == 0 && stepUp < hn) {
//				b += hn;
//			}
//			stepUp = (stepUp < 0) ? 0f : stepUp;
//			b += stepUp;
//		}
//		if (isM) {
//			b += simplex.noise2f(x / 12, y / 12) * sn;
//		}
//		b /= border;
//
//		return riverized(getTerrainBase(), river) + b;
//	}
//
//	public static float terrainPolar(float x, float y, ExoplanetWorld exoWorld, float river, float stPitch, float stFactor, float hPitch, float hDivisor, float baseHeight) {
//		SimplexNoise simplex = exoWorld.simplexInstance(0);
//		float floNoise;
//		float st = (simplex.noise2f(x / stPitch, y / stPitch) + 0.38f) * stFactor * river;
//		st = st < 0.1f ? 0.1f : st;
//
//		float h = simplex.noise2f(x / hPitch, y / hPitch) * st * 2f;
//		h = h > 0f ? -h : h;
//		h += st;
//		h *= h / hDivisor;
//		h += st;
//
//		floNoise = riverized(baseHeight + h, river);
//		return floNoise;
//	}
//
//	public static float terrainRollingHills(int x, int y, ExoplanetWorld exoWorld, float river, float hillStrength, float addedHeight, float groundNoiseAmplitudeHills, float lift) {
//		float groundNoise = groundNoise(x, y, groundNoiseAmplitudeHills, exoWorld);
//		float m = hills(x, y, hillStrength, exoWorld);
//		float floNoise = addedHeight + groundNoise + m;
//		return riverized(floNoise + lift, river);
//	}
//
//	public static float terrainRollingHills(int x, int y, ExoplanetWorld exoWorld, float river, float hillStrength, float groundNoiseAmplitudeHills, float baseHeight) {
//		float groundNoise = groundNoise(x, y, groundNoiseAmplitudeHills, exoWorld);
//		float m = hills(x, y, hillStrength, exoWorld);
//		float floNoise = groundNoise + m;
//		return riverized(floNoise + baseHeight, river);
//	}
//
//	public static float terrainVolcano(int x, int y, ExoplanetWorld exoWorld, float border, float baseHeight) {
//		SimplexNoise simplex = exoWorld.simplexInstance(0);
//		WorleyNoise worleyNoise = exoWorld.worleyInstance(0);
//
////
//
//		return 0;
//	}
//
//	public static float getRiverStrength(final BlockPos blockPos, final ExoplanetWorld exoWorld) {
//		final int worldX = blockPos.getX();
//		final int worldZ = blockPos.getZ();
//		double pX = worldX;
//		double pZ = worldZ;
//		ISimplexData2D jitterData = SimplexData2D.newDisk();
//
//		exoWorld.simplexInstance(1).multiEval2D(worldX / 240.0, worldZ / 240.0, jitterData);
//		pX += jitterData.getDeltaX() * exoWorld.getRiverLargeBendSize();
//		pZ += jitterData.getDeltaY() * exoWorld.getRiverLargeBendSize();
//
//		exoWorld.simplexInstance(2).multiEval2D(worldX / 80.0, worldZ / 80.0, jitterData);
//		pX += jitterData.getDeltaX() * exoWorld.getRiverSmallBendSize();
//		pZ += jitterData.getDeltaY() * exoWorld.getRiverSmallBendSize();
//
//		pX /= exoWorld.getRiverSeparation();
//		pZ /= exoWorld.getRiverSeparation();
//
//		return 0;
//	}
//
//	public static float calcCliff(int x, int z, float[] noise) {
//		float cliff = 0f;
//		if (x > 0) {
//			cliff = Math.max(cliff, Math.abs(noise[x * 16 + z] - noise[(x - 1) * 16 + z]));
//		}
//		if (z > 0) {
//			cliff = Math.max(cliff, Math.abs(noise[x * 16 + z] - noise[x * 16 + z - 1]));
//		}
//		if (x < 15) {
//			cliff = Math.max(cliff, Math.abs(noise[x * 16 + z] - noise[(x + 1) * 16 + z]));
//		}
//		if (z < 15) {
//			cliff = Math.max(cliff, Math.abs(noise[x * 16 + z] - noise[x * 16 + z + 1]));
//		}
//		return cliff;
//	}
//
//	public static void calcSnowHeight(int x, int y, int z, ChunkPrimer primer, float[] noise) {
//		if (y < 254) {
//			byte h = (byte) ((noise[x * 16 + z] - ((int) noise[x * 16 + z])) * 8);
//			if (h > 7) {
//				primer.setBlockState(x, y + 2, z, Blocks.SNOW_LAYER.getDefaultState());
//				primer.setBlockState(x, y + 1, z, Blocks.SNOW_LAYER.getDefaultState().withProperty(BlockSnow.LAYERS, 7));
//			} else if (h > 0) {
//				primer.setBlockState(x, y + 1, z, Blocks.SNOW_LAYER.getDefaultState().withProperty(BlockSnow.LAYERS, (int) h));
//			}
//		}
//	}
//
//	public static float bayesianAdjustment(float probability, float multiplier) {
//		if (probability >= 1) {
//			return probability;
//		}
//		if (probability <= 0) {
//			return probability;
//		}
//		float newConfidence = probability * multiplier / (1f - probability);
//		return newConfidence / (1f + newConfidence);
//	}
//
//	public abstract float generateNoise(ExoplanetWorld exoWorld, int x, int y, float border, float river);
//}
