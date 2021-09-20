//package com.readonlydev.lib.world.onhold.noise.terrain;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//
//class HeightVariation extends HeightEffect {
//
//	public float height = Integer.MAX_VALUE;
//	public float wavelength = 0;
//	public int octave = -1;
//
//	@Override
//	public final float added(ExoplanetWorld exoWorld, float x, float y) {
//		return exoWorld.simplexInstance(octave).noise2f(x / wavelength, y / wavelength) * height;
//	}
//
//}
