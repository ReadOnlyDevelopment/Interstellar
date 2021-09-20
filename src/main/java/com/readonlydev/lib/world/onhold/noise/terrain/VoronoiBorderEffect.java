//package com.readonlydev.lib.world.onhold.noise.terrain;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//
//class VoronoiBorderEffect extends HeightEffect {
//
//	public float pointWavelength = 0;
//	public float floor = Float.MAX_VALUE;
//	public float minimumDivisor = 0;
//
//	@Override
//	public float added(ExoplanetWorld exoWorld, float x, float y) {
//		VoronoiResult points = exoWorld.worleyInstance(1).eval2D(x / pointWavelength, y / pointWavelength);
//		float raise = (float) (points.interiorValue());
//		raise = 1.0f - raise;
//		// raise = TerrainBase.blendedHillHeight(raise, floor);
//		return raise;
//		return 0;
//	}
//
//}