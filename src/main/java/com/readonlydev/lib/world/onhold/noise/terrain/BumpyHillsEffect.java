//package com.readonlydev.lib.world.onhold.noise.terrain;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//
//class BumpyHillsEffect extends HeightEffect {
//
//	public float hillHeight = Integer.MAX_VALUE;
//	public float hillWavelength = 0;
//	public float spikeHeight = Integer.MAX_VALUE;
//	public float spikeWavelength = 0;
//
//	public int hillOctave = 0;
//	public int spikeOctave = 2;
//
//	@Override
//	public final float added(ExoplanetWorld exoWorld, float x, float y) {
//		float noise = exoWorld.simplexInstance(hillOctave).noise2f(x / hillWavelength, y / hillWavelength);
//		noise = TerrainBase.blendedHillHeight(noise);
//		float spikeNoise = exoWorld.simplexInstance(spikeOctave).noise2f(x / spikeWavelength, y / spikeWavelength);
//		spikeNoise = TerrainBase.blendedHillHeight(spikeNoise * noise);
//		return noise * hillHeight + spikeNoise * spikeHeight;
//	}
//}
