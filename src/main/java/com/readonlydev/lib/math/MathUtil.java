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

package com.readonlydev.lib.math;

import java.util.Random;

import lombok.experimental.UtilityClass;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

@UtilityClass
public class MathUtil {

	private static final double DOUBLES_EQUAL_PRECISION = 0.000000001;
	private static final Random RANDOM = new Random();

	public static Random getRandom(BlockPos pos) {
		long blockSeed = (((pos.getY() << 28) + pos.getX() + 30000000) << 28) + pos.getZ() + 30000000;
		return new Random(blockSeed);
	}

	public static AxisAlignedBB boundingBoxByPixels(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		return new AxisAlignedBB(minX / 16f, minY / 16f, minZ / 16f, maxX / 16f, maxY / 16f, maxZ / 16f);
	}

	/**
	 * Distance squared between two {@link Vec3i} (such as
	 * {@link net.minecraft.util.math.BlockPos})
	 */
	public static double distanceSq(Vec3i from, Vec3i to) {
		int dx = to.getX() - from.getX();
		int dy = to.getY() - from.getY();
		int dz = to.getZ() - from.getZ();
		return (dx * dx) + (dy * dy) + (dz * dz);
	}

	/**
	 * Distance squared between two {@link Vec3i} (such as
	 * {@link net.minecraft.util.math.BlockPos}), but ignores the Y-coordinate
	 */
	public static double distanceHorizontalSq(Vec3i from, Vec3i to) {
		int dx = to.getX() - from.getX();
		int dz = to.getZ() - from.getZ();
		return (dx * dx) + (dz * dz);
	}

	/**
	 * Compare if two doubles are equal, using precision constant
	 * {@link #DOUBLES_EQUAL_PRECISION}.
	 */
	public static boolean doublesEqual(double a, double b) {
		return doublesEqual(a, b, DOUBLES_EQUAL_PRECISION);
	}

	/**
	 * Compare if two doubles are equal, within the given level of precision.
	 *
	 * @param precision Should be a small, positive number (like
	 *                  {@link #DOUBLES_EQUAL_PRECISION})
	 */
	public static boolean doublesEqual(double a, double b, double precision) {
		return Math.abs(b - a) < precision;
	}

	/**
	 * Compare if two floats are equal, using precision constant
	 * {@link #DOUBLES_EQUAL_PRECISION}.
	 */
	public static boolean floatsEqual(float a, float b) {
		return floatsEqual(a, b, (float) DOUBLES_EQUAL_PRECISION);
	}

	/**
	 * Compare if two floats are equal, within the given level of precision.
	 *
	 * @param precision Should be a small, positive number (like
	 *                  {@link #DOUBLES_EQUAL_PRECISION})
	 */
	public static boolean floatsEqual(float a, float b, float precision) {
		return Math.abs(b - a) < precision;
	}

	public static double getDistanceXZ(BlockPos pos, BlockPos pos2) {
		double x = (pos2.getX() - pos.getX());
		double z = (pos2.getZ() - pos.getZ());
		return x - z;
	}

	public static int getRandomRange(Random rand, int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}

	public static float percentageToMultiplier(int percentage) {
		float perc = Math.abs(percentage);
		return perc / 100.0F;
	}

	public static int multiplierToPercentage(float multiplier) {
		return (int) (100.0F * multiplier);
	}

	public static byte clamp(byte value, byte min, byte max) {
		if (value < min) {
			return min;
		}
		return (value > max) ? max : value;
	}

	public static short clamp(short value, short min, short max) {
		if (value < min) {
			return min;
		}
		return (value > max) ? max : value;
	}

	public static int clamp(int value, int min, int max) {
		if (value < min) {
			return min;
		}
		return (value > max) ? max : value;
	}

	public static long clamp(long value, long min, long max) {
		if (value < min) {
			return min;
		}
		return (value > max) ? max : value;
	}

	public static float clamp(float value, float min, float max) {
		if (value < min) {
			return min;
		}
		return (value > max) ? max : value;
	}

	public static double clamp(double value, double min, double max) {
		if (value < min) {
			return min;
		}
		return (value > max) ? max : value;
	}

	public static int min(final int a, final int b) {
		return a < b ? a : b;
	}

	public static int min(int a, final int b, final int c) {
		if (b < a) {
			a = b;
		}
		if (c < a) {
			a = c;
		}
		return a;
	}

	public static int min(int a, final int b, final int c, final int d) {
		if (b < a) {
			a = b;
		}
		if (c < a) {
			a = c;
		}
		if (d < a) {
			a = d;
		}
		return a;
	}

	public static int min(int a, final int b, final int c, final int d, int... rest) {
		int min = min(a, b, c, d);
		for (int i : rest) {
			if (i < min) {
				min = i;
			}
		}
		return min;
	}

	public static int max(final int a, final int b) {
		return a > b ? a : b;
	}

	public static int max(int a, final int b, final int c) {
		if (b > a) {
			a = b;
		}
		if (c > a) {
			a = c;
		}
		return a;
	}

	public static int max(int a, final int b, final int c, final int d) {
		if (b > a) {
			a = b;
		}
		if (c > a) {
			a = c;
		}
		if (d > a) {
			a = d;
		}
		return a;
	}

	public static int max(int a, final int b, final int c, final int d, int... rest) {
		int max = max(a, b, c, d);
		for (int i : rest) {
			if (i > max) {
				max = i;
			}
		}
		return max;
	}

	public static double nextGaussian(double mean, double deviation) {
		return (deviation * RANDOM.nextGaussian()) + mean;
	}

	public static double nextGaussian(Random random, double mean, double deviation) {
		return (deviation * random.nextGaussian()) + mean;
	}

	public static int nextInt(int bound) {
		return RANDOM.nextInt(bound);
	}

	public static int nextIntInclusive(int min, int max) {
		return RANDOM.nextInt((max - min) + 1) + min;
	}

	public static int nextIntInclusive(Random random, int min, int max) {
		return random.nextInt((max - min) + 1) + min;
	}

	public static boolean tryPercentage(double percent) {
		return RANDOM.nextDouble() < percent;
	}

	public static boolean tryPercentage(Random random, double percent) {
		return random.nextDouble() < percent;
	}
}
