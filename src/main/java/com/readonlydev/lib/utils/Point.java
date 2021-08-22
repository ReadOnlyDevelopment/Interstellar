/*
 * Library License
 *
 * Copyright (c) 2021 ReadOnly Development
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.readonlydev.lib.utils;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class Point {

	public double x, y, z;

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point(Point p) {
		x = p.x;
		y = p.y;
		z = p.z;
	}

	public Point(Vec3d v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}

	/**
	 * Sets this point to x, y and z coordinates.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public void set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Moves this {@link Point} according to point.
	 *
	 * @param point the point
	 */
	public void add(Point point) {
		x += point.x;
		y += point.y;
		z += point.z;
	}

	/**
	 * Moves this {@link Point} according to vector.
	 *
	 * @param vec3d the vec3d
	 */
	public void add(Vec3d vec3d) {
		x += vec3d.x;
		y += vec3d.y;
		z += vec3d.z;
	}

	/**
	 * Adds the x, y and z coordinates to this {@link Point}.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public void add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}

	/**
	 * Subtracts the given {@link Point} from this <code>Point</code>.
	 */
	public void subtract(Point v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
	}

	/**
	 * Subtracts the given {@link Point} from this <code>Vec3d</code>.
	 */
	public void subtract(Vec3d v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
	}

	public void subtract(double x, double y, double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
	}

	/**
	 * Gets the squared length of this {@link Point}.
	 *
	 * @return the length squared
	 */
	public double lengthSquared() {
		return x * x + y * y + z * z;
	}

	/**
	 * Gets the squared length of this {@link Point}.
	 *
	 * @return the length squared
	 */
	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Normalizes this {@link Point}.
	 */
	public void normalize() {
		double d = length();
		x /= d;
		y /= d;
		z /= d;
	}

	/**
	 * Calculates the cross product of this {@link Point} with a given <code>Point</code>.
	 *
	 * @param v the vector
	 */
	public void cross(Point v) {
		x = (y * v.z) - (z * v.y);
		y = (z * v.x) - (x * v.z);
		z = (x * v.y) - (y * v.x);
	}

	/**
	 * Calculates the dot product of this {@link Point} with a given {@link Point}.
	 *
	 * @param p the point
	 * @return the dot product
	 */
	public double dot(Point p) {
		return (x * p.x) + (y * p.y) + (z * p.z);
	}

	/**
	 * Scales this {@link Point} by a factor.
	 *
	 * @param factor the factor to scale
	 */
	public void scale(double factor) {
		x *= factor;
		y *= factor;
		z *= factor;
	}

	/**
	 * Inverses the vector.
	 */
	public void negate() {
		x = -x;
		y = -y;
		z = -z;
	}

	/**
	 * Checks if this {@link Point} is inside the {@link AxisAlignedBB}.
	 *
	 * @param aabb the aabb
	 * @return true, if it is inside
	 */
	public boolean isInside(AxisAlignedBB aabb) {
		return x >= aabb.minX && x <= aabb.maxX && y >= aabb.minY && y <= aabb.maxY && z >= aabb.minZ && z <= aabb.maxZ;
	}

	/**
	 * Creates a {@link Vec3d} from this {@link Point} coordinates.
	 *
	 * @return the Vec3d
	 */
	public Vec3d toVec3d() {
		return new Vec3d(x, y, z);
	}

	/**
	 * Checks if this {@link Point} is equal to the specified one.
	 *
	 * @param p the point to check
	 * @return true, if equal, false otherwise
	 */
	public boolean equals(Point p) {
		if (p == null) {
			return false;
		}
		return ((x == p.x) && (y == p.y) && (z == p.z));
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}

	/**
	 * Calculates the squared distance between two {@link Point points}.
	 *
	 * @param p1 fist point
	 * @param p2 second point
	 * @return the distance squared
	 */
	public static double distanceSquared(Point p1, Point p2) {
		double x = p2.x - p1.x;
		double y = p2.y - p1.y;
		double z = p2.z - p1.z;
		return x * x + y * y + z * z;
	}

	/**
	 * Calculates the distance between two {@link Point points}.
	 *
	 * @param p1 fist point
	 * @param p2 second point
	 * @return the distance
	 */
	public static double distance(Point p1, Point p2) {
		double x = p2.x - p1.x;
		double y = p2.y - p1.y;
		double z = p2.z - p1.z;
		return Math.sqrt(x * x + y * y + z * z);
	}
}
