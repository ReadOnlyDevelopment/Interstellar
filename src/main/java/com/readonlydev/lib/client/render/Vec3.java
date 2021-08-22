package com.readonlydev.lib.client.render;

import javax.vecmath.Matrix3f;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Vec3 {
	/** X coordinate of Vec3D */
	public double xCoord;
	/** Y coordinate of Vec3D */
	public double yCoord;
	/** Z coordinate of Vec3D */
	public double zCoord;

	/**
	 * Static method for creating a new Vec3D given the three x,y,z values. This is
	 * only called from the other static method which creates and places it in the
	 * list.
	 */
	public static Vec3 createVectorHelper(double xCoord, double yCoord, double zCoord) {
		return new Vec3(xCoord, yCoord, zCoord);
	}

	public Vec3(Vec3d vec) {
		this.xCoord = vec.x;
		this.yCoord = vec.y;
		this.zCoord = vec.z;
	}

	public Vec3(double xCoord, double yCoord, double zCoord) {
		if (xCoord == -0.0D) {
			xCoord = 0.0D;
		}

		if (yCoord == -0.0D) {
			yCoord = 0.0D;
		}

		if (zCoord == -0.0D) {
			zCoord = 0.0D;
		}

		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
	}

	/**
	 * Sets the x,y,z components of the vector as specified.
	 */
	public Vec3 setComponents(double xCoord, double yCoord, double zCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		return this;
	}

	public Vec3 set(Vec3 other) {
		return setComponents(other.xCoord, other.yCoord, other.zCoord);
	}

	/**
	 * Returns a new vector with the result of the specified vector minus this.
	 */
	public Vec3 subtract(Vec3 other) {
		/**
		 * Static method for creating a new Vec3D given the three x,y,z values. This is
		 * only called from the other static method which creates and places it in the
		 * list.
		 */
		return createVectorHelper(this.xCoord - other.xCoord, this.yCoord - other.yCoord, this.zCoord - other.zCoord);
	}

	public Vec3 subtract(double x, double y, double z) {
		return new Vec3(xCoord - x, yCoord - y, zCoord - z);
	}

	/**
	 * Normalizes the vector to a length of 1 (except if it is the zero vector)
	 */
	public Vec3 normalize() {
		double d0 = MathHelper.sqrt(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
		return d0 < 1.0E-4D ? createVectorHelper(0.0D, 0.0D, 0.0D)
				: createVectorHelper(this.xCoord / d0, this.yCoord / d0, this.zCoord / d0);
	}

	public double dotProduct(Vec3 vec3) {
		return this.xCoord * vec3.xCoord + this.yCoord * vec3.yCoord + this.zCoord * vec3.zCoord;
	}

	/**
	 * Returns a new vector with the result of this vector x the specified vector.
	 */
	public Vec3 crossProduct(Vec3 vec3) {
		/**
		 * Static method for creating a new Vec3D given the three x,y,z values. This is
		 * only called from the other static method which creates and places it in the
		 * list.
		 */
		return createVectorHelper(this.yCoord * vec3.zCoord - this.zCoord * vec3.yCoord,
				this.zCoord * vec3.xCoord - this.xCoord * vec3.zCoord,
				this.xCoord * vec3.yCoord - this.yCoord * vec3.xCoord);
	}

	/**
	 * Adds the specified x,y,z vector components to this vector and returns the
	 * resulting vector. Does not change this vector.
	 */
	public Vec3 addVector(double xCoord, double yCoord, double zCoord) {
		/**
		 * Static method for creating a new Vec3D given the three x,y,z values. This is
		 * only called from the other static method which creates and places it in the
		 * list.
		 */
		return createVectorHelper(this.xCoord + xCoord, this.yCoord + yCoord, this.zCoord + zCoord);
	}

	public Vec3 add(Vec3 other) {
		return new Vec3(xCoord + other.xCoord, yCoord + other.yCoord, zCoord + other.zCoord);
	}

	/**
	 * Euclidean distance between this and the specified vector, returned as double.
	 */
	public double distanceTo(Vec3 vec3) {
		double d0 = vec3.xCoord - this.xCoord;
		double d1 = vec3.yCoord - this.yCoord;
		double d2 = vec3.zCoord - this.zCoord;
		return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
	}

	/**
	 * The square of the Euclidean distance between this and the specified vector.
	 */
	public double squareDistanceTo(Vec3 vec3) {
		double d0 = vec3.xCoord - this.xCoord;
		double d1 = vec3.yCoord - this.yCoord;
		double d2 = vec3.zCoord - this.zCoord;
		return d0 * d0 + d1 * d1 + d2 * d2;
	}

	/**
	 * The square of the Euclidean distance between this and the vector of x,y,z
	 * components passed in.
	 */
	public double squareDistanceTo(double xCoord, double yCoord, double zCoord) {
		double d3 = xCoord - this.xCoord;
		double d4 = yCoord - this.yCoord;
		double d5 = zCoord - this.zCoord;
		return d3 * d3 + d4 * d4 + d5 * d5;
	}

	/**
	 * Returns the length of the vector.
	 */
	public double lengthVector() {
		return MathHelper.sqrt(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
	}

	public double lengthSquared() {
		return this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord;
	}

	/**
	 * Returns a new vector with x value equal to the second parameter, along the
	 * line between this vector and the passed in vector, or null if not possible.
	 */
	public Vec3 getIntermediateWithXValue(Vec3 vec3, double line) {
		double d1 = vec3.xCoord - this.xCoord;
		double d2 = vec3.yCoord - this.yCoord;
		double d3 = vec3.zCoord - this.zCoord;

		if (d1 * d1 < 1.0000000116860974E-7D) {
			return null;
		} else {
			double d4 = (line - this.xCoord) / d1;
			return d4 >= 0.0D && d4 <= 1.0D
					? createVectorHelper(this.xCoord + d1 * d4, this.yCoord + d2 * d4, this.zCoord + d3 * d4)
					: null;
		}
	}

	/**
	 * Returns a new vector with y value equal to the second parameter, along the
	 * line between this vector and the passed in vector, or null if not possible.
	 */
	public Vec3 getIntermediateWithYValue(Vec3 vec3, double line) {
		double d1 = vec3.xCoord - this.xCoord;
		double d2 = vec3.yCoord - this.yCoord;
		double d3 = vec3.zCoord - this.zCoord;

		if (d2 * d2 < 1.0000000116860974E-7D) {
			return null;
		} else {
			double d4 = (line - this.yCoord) / d2;
			return d4 >= 0.0D && d4 <= 1.0D
					? createVectorHelper(this.xCoord + d1 * d4, this.yCoord + d2 * d4, this.zCoord + d3 * d4)
					: null;
		}
	}

	/**
	 * Returns a new vector with z value equal to the second parameter, along the
	 * line between this vector and the passed in vector, or null if not possible.
	 */
	public Vec3 getIntermediateWithZValue(Vec3 vec3, double line) {
		double d1 = vec3.xCoord - this.xCoord;
		double d2 = vec3.yCoord - this.yCoord;
		double d3 = vec3.zCoord - this.zCoord;

		if (d3 * d3 < 1.0000000116860974E-7D) {
			return null;
		} else {
			double d4 = (line - this.zCoord) / d3;
			return d4 >= 0.0D && d4 <= 1.0D
					? createVectorHelper(this.xCoord + d1 * d4, this.yCoord + d2 * d4, this.zCoord + d3 * d4)
					: null;
		}
	}

	@Override
	public String toString() {
		return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
	}

	/**
	 * Rotates the vector around the x axis by the specified angle.
	 */
	public void rotateAroundX(float val) {
		float f1 = MathHelper.cos(val);
		float f2 = MathHelper.sin(val);
		double d0 = this.xCoord;
		double d1 = this.yCoord * f1 + this.zCoord * f2;
		double d2 = this.zCoord * f1 - this.yCoord * f2;
		this.setComponents(d0, d1, d2);
	}

	/**
	 * Rotates the vector around the y axis by the specified angle.
	 */
	public void rotateAroundY(float val) {
		float f1 = MathHelper.cos(val);
		float f2 = MathHelper.sin(val);
		double d0 = this.xCoord * f1 + this.zCoord * f2;
		double d1 = this.yCoord;
		double d2 = this.zCoord * f1 - this.xCoord * f2;
		this.setComponents(d0, d1, d2);
	}

	/**
	 * Rotates the vector around the z axis by the specified angle.
	 */
	public void rotateAroundZ(float val) {
		float f1 = MathHelper.cos(val);
		float f2 = MathHelper.sin(val);
		double d0 = this.xCoord * f1 + this.yCoord * f2;
		double d1 = this.yCoord * f1 - this.xCoord * f2;
		double d2 = this.zCoord;
		this.setComponents(d0, d1, d2);
	}

	public Vec3 interpolate(Vec3 other, double inter) {
		return Vec3.createVectorHelper(this.xCoord + (other.xCoord - this.xCoord) * inter,
				this.yCoord + (other.yCoord - this.yCoord) * inter, this.zCoord + (other.zCoord - this.zCoord) * inter);
	}

	public Vec3 mult(float mult) {
		return Vec3.createVectorHelper(this.xCoord * mult, this.yCoord * mult, this.zCoord * mult);
	}

	public Vec3 multd(double mult) {
		return Vec3.createVectorHelper(this.xCoord * mult, this.yCoord * mult, this.zCoord * mult);
	}

	public Vec3 negate() {
		return new Vec3(-xCoord, -yCoord, -zCoord);
	}

	public Matrix3f outerProduct(Vec3 other) {
		Matrix3f mat = new Matrix3f((float) (xCoord * other.xCoord), (float) (xCoord * other.yCoord),
				(float) (xCoord * other.zCoord), (float) (yCoord * other.xCoord), (float) (yCoord * other.yCoord),
				(float) (yCoord * other.zCoord), (float) (zCoord * other.xCoord), (float) (zCoord * other.yCoord),
				(float) (zCoord * other.zCoord));
		return mat;
	}

	public Vec3 matTransform(Matrix3f mat) {
		double x, y, z;
		x = mat.m00 * xCoord + mat.m01 * yCoord + mat.m02 * zCoord;
		y = mat.m10 * xCoord + mat.m11 * yCoord + mat.m12 * zCoord;
		z = mat.m20 * xCoord + mat.m21 * yCoord + mat.m22 * zCoord;
		return new Vec3(x, y, z);
	}

	public Vec3 copy() {
		return new Vec3(xCoord, yCoord, zCoord);
	}

	public Vec3d toVec3d() {
		return new Vec3d(xCoord, yCoord, zCoord);
	}

	public Vec3 max(double d) {
		return new Vec3(Math.max(xCoord, d), Math.max(yCoord, d), Math.max(zCoord, d));
	}

	public Vec3 min(double d) {
		return new Vec3(Math.min(xCoord, d), Math.min(yCoord, d), Math.min(zCoord, d));
	}
}
