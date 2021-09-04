package com.readonlydev.lib.world.noise;

public class CircularSearchCreator {

	private boolean active = false;
	private int size;
	private float center;

	public int[] pattern(float maxRadius, int requestedSize) {
		if (active) {
			throw new RuntimeException();
		}
		active = true;
		size = requestedSize;
		center = (size - 1f) / 2f;
		int[] result = new int[size * size];
		boolean[] found = new boolean[size * size];
		int nextResult = 0;
		int smallerHalfSize = size / 2;
		int largerHalfSize = (size + 1) / 2;
		for (float radius = 0; radius < maxRadius; radius = radius + 0.01f) {
			for (int y = 0; y < largerHalfSize; y++) {
				for (int x = smallerHalfSize; x < size; x++) {
					int index = x * size + y;
					if (found[index]) {
						continue;
					}
					float distance = distanceFromCenter(x, y);
					if (distance > radius) {
						continue;
					}
					result[nextResult++] = index;
					found[index] = true;
				}
			}
			for (int x = size - 1; x >= smallerHalfSize; x--) {
				for (int y = largerHalfSize; y < size; y++) {
					int index = x * size + y;
					if (found[index]) {
						continue;
					}
					float distance = distanceFromCenter(x, y);
					if (distance > radius) {
						continue;
					}
					result[nextResult++] = index;
					found[index] = true;
				}
			}
			for (int y = size - 1; y >= largerHalfSize - 1; y--) {
				for (int x = smallerHalfSize - 1; x > -1; x--) {
					int index = x * size + y;
					if (found[index]) {
						continue;
					}
					float distance = distanceFromCenter(x, y);
					if (distance > radius) {
						continue;
					}
					result[nextResult++] = index;
					found[index] = true;
				}
			}
			for (int x = 0; x < smallerHalfSize; x++) {
				for (int y = largerHalfSize - 1; y > -1; y--) {
					int index = x * size + y;
					if (found[index]) {
						continue;
					}
					float distance = distanceFromCenter(x, y);
					if (distance > radius) {
						continue;
					}
					result[nextResult++] = index;
					found[index] = true;
				}
			}
		}
		active = false;
		if (nextResult < result.length) {
			int[] newResult = new int[nextResult];
			System.arraycopy(result, 0, newResult, 0, nextResult);
			result = newResult;
		}
		return result;
	}

	private float distanceFromCenter(int x, int y) {
		return (float) Math.sqrt(Math.pow((x - this.center), 2) + Math.pow((y - this.center), 2));
	}
}
