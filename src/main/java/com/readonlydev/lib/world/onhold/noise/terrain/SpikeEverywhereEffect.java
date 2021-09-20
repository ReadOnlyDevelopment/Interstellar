//package com.readonlydev.lib.world.onhold.noise.terrain;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//
//class SpikeEverywhereEffect extends HeightEffect {
//
//	public float wavelength = 0;
//	public float minimumSimplex = Integer.MAX_VALUE;
//	public int octave;
//	public float power = 1.6f;
//	public HeightEffect spiked;
//
//	@Override
//	public final float added(ExoplanetWorld exoWorld, float x, float y) {
//		float noise = exoWorld.simplexInstance(octave).noise2f(x / wavelength, y / wavelength);
//		noise = Math.abs(noise);
//		noise = TerrainBase.blendedHillHeight(noise, minimumSimplex);
//		noise = TerrainBase.unsignedPower(noise, power);
//		return noise * spiked.added(exoWorld, x, y);
//	}
//}