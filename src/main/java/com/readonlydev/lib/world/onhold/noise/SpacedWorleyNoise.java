//package com.readonlydev.lib.world.onhold.noise;
//
//import java.awt.Point;
//import java.awt.geom.Point2D;
//import java.util.Map;
//import java.util.Random;
//
//class SpacedWorleyNoise implements WorleyNoise {
//
//	private static final int totalPoints = 100;
//	private static final int pointsPerTorus = 25;
//	private static final double minDistanceSq = 0.005d;
//
//	private final Map<Point, Point2D.Double[]> cache = new LimitedArrayCacheMap<>(256);
//	private final Point2D.Double[] allPoints;
//	private final long xSeed;
//	private final long ySeed;
//
//	public SpacedWorleyNoise(long xSeed) {
//		this.xSeed = xSeed;
//		this.ySeed = new Random(xSeed).nextLong();
//		this.allPoints = new Point2D.Double[totalPoints];
//		this.setPoints();
//	}
//
//	private static double minimalToroidalDistanceSquared(Point2D.Double point, Point2D.Double[] existing, int count) {
//		double result = 1.0;
//		for (int i = 0; i < count; i++) {
//			double distance = toroidalDistanceSquared(point, existing[i]);
//			if (distance < result) {
//				result = distance;
//			}
//		}
//		return result;
//	}
//
//	private static double toroidalDistanceSquared(Point2D.Double first, Point2D.Double second) {
//		double xDist = Math.abs(first.x - second.x);
//		if (xDist > 0.5) {
//			xDist = 1.0 - xDist;
//		}
//		double yDist = Math.abs(first.y - second.y);
//		if (yDist > 0.5) {
//			yDist = 1.0 - yDist;
//		}
//		return (xDist * xDist) + (yDist * yDist);
//	}
//
//	@Override
//	public VoronoiResult eval2D(double x, double y) {
//		x = x / 5.0;
//		y = y / 5.0;
//
//		int xInt = (x > 0.0 ? (int) x : (int) x - 1);
//		int yInt = (y > 0.0 ? (int) y : (int) y - 1);
//
//		VoronoiResult result = new VoronoiResult();
//
//		Point square = new Point(xInt, yInt);
//		result.evaluate(this.areaPoints(square), x, y);
//
//		double distance = y - yInt;
//		if (distance != result.getNextDistance()) {
//			result.evaluate(this.areaPoints(new Point(xInt, yInt - 1)), x, y);
//		}
//		distance = x - xInt;
//		if (distance != result.getNextDistance()) {
//			result.evaluate(this.areaPoints(new Point(xInt - 1, yInt)), x, y);
//		}
//		distance = yInt - y + 1.0;
//		if (distance != result.getNextDistance()) {
//			result.evaluate(this.areaPoints(new Point(xInt, yInt + 1)), x, y);
//		}
//		distance = xInt - x + 1.0;
//		if (distance != result.getNextDistance()) {
//			result.evaluate(this.areaPoints(new Point(xInt + 1, yInt)), x, y);
//		}
//		distance = Math.min(y - yInt, x - xInt);
//		if (distance != result.getNextDistance()) {
//			result.evaluate(this.areaPoints(new Point(xInt - 1, yInt - 1)), x, y);
//		}
//		distance = Math.min(yInt - y + 1.0, x - xInt);
//		if (distance != result.getNextDistance()) {
//			result.evaluate(this.areaPoints(new Point(xInt - 1, yInt + 1)), x, y);
//		}
//		distance = Math.min(yInt - y + 1.0, xInt - x + 1.0);
//		if (distance != result.getNextDistance()) {
//			result.evaluate(this.areaPoints(new Point(xInt + 1, yInt + 1)), x, y);
//		}
//		distance = Math.min(y - yInt, xInt - x + 1.0);
//		if (distance != result.getNextDistance()) {
//			result.evaluate(this.areaPoints(new Point(xInt + 1, yInt - 1)), x, y);
//		}
//		return result;
//	}
//
//	private Point2D.Double[] areaPoints(Point area) {
//		Point2D.Double[] ret;
//		return ((ret = this.cache.get(area)) != null) ? ret : this.generatedAreaPoints(area);
//	}
//
//	private Point2D.Double[] generatedAreaPoints(Point area) {
//		Random random = new Random(area.hashCode());
//		boolean[] used = new boolean[totalPoints];
//		Point2D.Double[] result = new Point2D.Double[pointsPerTorus];
//		int index = 0;
//		for (int i = 0; i < pointsPerTorus; i++) {
//			int advance = random.nextInt(totalPoints);
//			for (int j = 0; j < advance; j++) {
//				while (used[index]) {
//					index++;
//					if (index >= totalPoints) {
//						index = 0;
//					}
//				}
//			}
//			result[i] = new Point2D.Double(this.allPoints[index].getX() + area.getX(), this.allPoints[index].getY() + area.getY());
//			used[i] = true;
//		}
//		this.cache.put(area, result);
//		return result;
//	}
//
//	private void setPoints() {
//		Random xRandom = new Random(this.xSeed);
//		Random yRandom = new Random(this.ySeed);
//		for (int i = 0; i < totalPoints;) {
//			Point2D.Double newPoint = new Point2D.Double(xRandom.nextDouble(), yRandom.nextDouble());
//			if (minimalToroidalDistanceSquared(newPoint, allPoints, i) >= minDistanceSq) {
//				allPoints[i++] = newPoint;
//			}
//		}
//	}
//}