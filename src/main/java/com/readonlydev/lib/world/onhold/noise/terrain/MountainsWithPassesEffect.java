//package com.readonlydev.lib.world.onhold.noise.terrain;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//
//class MountainsWithPassesEffect extends HeightEffect {
//
//	public float mountainHeight = Integer.MAX_VALUE;
//	public float mountainWavelength = 0;
//	public float spikeHeight = Integer.MAX_VALUE;
//	public float spikeWavelength = 0;
//	public int hillOctave = 0;
//	public int spikeOctave = 2;
//	private float adjustedBottom = TerrainBase.blendedHillHeight(0, .2f);
//
//	@Override
//	public final float added(ExoplanetWorld exoWorld, float x, float y) {
//		float noise = exoWorld.simplexInstance(hillOctave).noise2f(x / mountainWavelength, y / mountainWavelength);
//		noise = Math.abs(noise);
//		noise = TerrainBase.blendedHillHeight(noise, 0.2f);
//		noise = 1f - (1f - noise) / (1f - adjustedBottom);
//		float spikeNoise = TerrainBase.blendedHillHeight(noise, 0.1f);
//		spikeNoise *= spikeNoise;
//		spikeNoise = TerrainBase.blendedHillHeight(spikeNoise * noise);
//		if (noise > 1.01) {
//			throw new RuntimeException();
//		}
//		if (spikeNoise > 1.01) {
//			throw new RuntimeException();
//		}
//		return noise * mountainHeight + spikeNoise * spikeHeight;
//	}
//}