//package com.readonlydev.lib.world.onhold.noise;
//
//import java.awt.geom.Point2D;
//
//class VoronoiResult {
//
//    private double shortestDistance = 32000000.0;
//    private double nextDistance = 32000000.0;
//    private double closestX = 32000000.0;
//    private double closestZ = 32000000.0;
//
//    public final double getShortestDistance() {
//        return this.shortestDistance;
//    }
//
//    public final double getNextDistance() {
//        return this.nextDistance;
//    }
//
//    public final double borderValue() {
//        return shortestDistance / nextDistance;
//    }
//
//    public final double interiorValue() {
//        return (nextDistance - shortestDistance) / nextDistance;
//    }
//
//    public final Point2D.Float toLength(Point2D.Float toMap, float radius) {
//        double distance = toMap.distance(this.closestX, this.closestZ);
//        double xDist = toMap.getX() - this.closestX;
//        double zDist = toMap.getY() - this.closestZ;
//        xDist *= radius / distance;
//        zDist *= radius / distance;
//        return new Point2D.Float((float) (this.closestX + xDist), (float) (this.closestZ + zDist));
//    }
//
//    void evaluate(Point2D.Double[] points, double x, double z) {
//        for (Point2D.Double point : points) {
//            double distance = point.distanceSq(x, z);
//            if (distance < this.shortestDistance) {
//                this.nextDistance = this.shortestDistance;
//                this.shortestDistance = distance;
//                this.closestX = point.getX();
//                this.closestZ = point.getY();
//            }
//            else if (distance < this.nextDistance) {
//                this.nextDistance = distance;
//            }
//        }
//    }
//}
