//package com.readonlydev.lib.world.onhold.noise.terrain;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//
//class VoronoiBasinEffect extends HeightEffect {
//
//	public float pointWavelength = 200;
//	public float minimumDivisor = 0;
//	public float adjustmentRadius = 1f;
//
//	@Override
//	public float added(ExoplanetWorld exoWorld, float x, float y) {
//        Point2D.Float evaluateAt = new Point2D.Float(x / pointWavelength, y / pointWavelength);
//        VoronoiResult points = exoWorld.worleyInstance(1).eval2D(evaluateAt.x, evaluateAt.y);
//        float raise = (float) (points.borderValue());
//        // now we're going to get an adjustment value which will be the same
//        // for all points on a given vector from the voronoi basin center
//        Point2D.Float adjustAt = points.toLength(evaluateAt, adjustmentRadius);
//        float noZeros = 0.25f;
//        float adjustment = (float) exoWorld.worleyInstance(2).eval2D(adjustAt.x, adjustAt.y).interiorValue() + noZeros;
//        float reAdjustment = (float) exoWorld.worleyInstance(3).eval2D(adjustAt.x, adjustAt.y).interiorValue() + noZeros;
//        // 0 to 1 which is currently undesirable so increase to average closer to 1
//        adjustment = TerrainBase.bayesianAdjustment(adjustment, reAdjustment);
//        // invert adjustment for Bryce
//        raise = TerrainBase.bayesianAdjustment(raise, 1f / (adjustment * adjustment));
//        return raise;
//		return 0;
//	}
//
//}