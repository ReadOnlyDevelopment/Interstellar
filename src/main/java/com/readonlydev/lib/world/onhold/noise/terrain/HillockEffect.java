//package com.readonlydev.lib.world.onhold.noise.terrain;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//
//class HillockEffect extends HeightEffect {
//
//	public float height = Integer.MAX_VALUE;
//	public float wavelength = 0;
//	public float minimumSimplex = Integer.MAX_VALUE;
//	public int octave;
//
//	@Override
//	public final float added(ExoplanetWorld exoWorld, float x, float y) {
//		float noise = exoWorld.simplexInstance(octave).noise2f(x / wavelength, y / wavelength);
//		if (noise < minimumSimplex) {
//			noise = 0;
//		} else {
//			noise = (noise - minimumSimplex) / (1f - minimumSimplex);
//		}
//		return noise * height;
//	}
//}
