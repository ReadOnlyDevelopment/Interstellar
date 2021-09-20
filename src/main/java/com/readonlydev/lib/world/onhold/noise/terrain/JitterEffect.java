//package com.readonlydev.lib.world.onhold.noise.terrain;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//import com.readonlydev.lib.world.onhold.noise.ISimplexData2D;
//import com.readonlydev.lib.world.onhold.noise.SimplexData2D;
//
//class JitterEffect extends HeightEffect {
//
//	public float amplitude = Integer.MAX_VALUE;
//	public float wavelength = 0;
//	public HeightEffect jittered;
//
//	public JitterEffect() {
//	}
//
//	public JitterEffect(float amplitude, float wavelength, HeightEffect toJitter) {
//		this.amplitude = amplitude;
//		this.wavelength = wavelength;
//		this.jittered = toJitter;
//	}
//
//	@Override
//	public final float added(ExoplanetWorld exoWorld, float x, float y) {
//		ISimplexData2D jitterData = SimplexData2D.newDisk();
//		exoWorld.simplexInstance(1).multiEval2D(x / wavelength, y / wavelength, jitterData);
//		int pX = (int) Math.round(x + jitterData.getDeltaX() * amplitude);
//		int pY = (int) Math.round(y + jitterData.getDeltaY() * amplitude);
//		return jittered.added(exoWorld, pX, pY);
//	}
//
//}
