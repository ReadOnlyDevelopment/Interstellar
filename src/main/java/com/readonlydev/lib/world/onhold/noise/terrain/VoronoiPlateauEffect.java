//package com.readonlydev.lib.world.onhold.noise.terrain;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//
//class VoronoiPlateauEffect extends HeightEffect {
//
//	public float pointWavelength = 200;
//	public float minimumDivisor = 0;
//	public float adjustmentRadius = 3f;
//
//	@Override
//	public float added(ExoplanetWorld exoWorld, float x, float y) {
//        Point2D.Float evaluateAt = new Point2D.Float(x / pointWavelength, y / pointWavelength);
//        VoronoiResult points = exoWorld.worleyInstance(1).eval2D(evaluateAt.x, evaluateAt.y);
//        float raise = (float) (points.interiorValue());
//        Point2D.Float adjustAt = points.toLength(evaluateAt, adjustmentRadius);
//        float multiplier = 1.3f;
//        float noZeros = 0.1f;
//        float adjustment = (float) exoWorld.worleyInstance(2).eval2D(adjustAt.x, adjustAt.y).interiorValue() * multiplier + noZeros;
//        float reAdjustment = (float) exoWorld.worleyInstance(3).eval2D(adjustAt.x, adjustAt.y).interiorValue() * multiplier + noZeros;
//        adjustment = TerrainBase.bayesianAdjustment(adjustment, reAdjustment);
//        raise = TerrainBase.bayesianAdjustment(raise, adjustment);
//        return raise;
//		return 0;
//	}
//
//}