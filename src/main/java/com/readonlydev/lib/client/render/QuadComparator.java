package com.readonlydev.lib.client.render;

import java.util.Comparator;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class QuadComparator implements Comparator<Object> {

	private float x;
	private float y;
	private float z;
	private int[] buffer;

	public QuadComparator(int[] buffer, float x, float y, float z) {
		this.buffer = buffer;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int compare(Integer int1, Integer int2) {
		float f = Float.intBitsToFloat(this.buffer[int1]) - this.x;
		float f1 = Float.intBitsToFloat(this.buffer[int1.intValue() + 1]) - this.y;
		float f2 = Float.intBitsToFloat(this.buffer[int1.intValue() + 2]) - this.z;
		float f3 = Float.intBitsToFloat(this.buffer[int1.intValue() + 8]) - this.x;
		float f4 = Float.intBitsToFloat(this.buffer[int1.intValue() + 9]) - this.y;
		float f5 = Float.intBitsToFloat(this.buffer[int1.intValue() + 10]) - this.z;
		float f6 = Float.intBitsToFloat(this.buffer[int1.intValue() + 16]) - this.x;
		float f7 = Float.intBitsToFloat(this.buffer[int1.intValue() + 17]) - this.y;
		float f8 = Float.intBitsToFloat(this.buffer[int1.intValue() + 18]) - this.z;
		float f9 = Float.intBitsToFloat(this.buffer[int1.intValue() + 24]) - this.x;
		float f10 = Float.intBitsToFloat(this.buffer[int1.intValue() + 25]) - this.y;
		float f11 = Float.intBitsToFloat(this.buffer[int1.intValue() + 26]) - this.z;
		float f12 = Float.intBitsToFloat(this.buffer[int2]) - this.x;
		float f13 = Float.intBitsToFloat(this.buffer[int2.intValue() + 1]) - this.y;
		float f14 = Float.intBitsToFloat(this.buffer[int2.intValue() + 2]) - this.z;
		float f15 = Float.intBitsToFloat(this.buffer[int2.intValue() + 8]) - this.x;
		float f16 = Float.intBitsToFloat(this.buffer[int2.intValue() + 9]) - this.y;
		float f17 = Float.intBitsToFloat(this.buffer[int2.intValue() + 10]) - this.z;
		float f18 = Float.intBitsToFloat(this.buffer[int2.intValue() + 16]) - this.x;
		float f19 = Float.intBitsToFloat(this.buffer[int2.intValue() + 17]) - this.y;
		float f20 = Float.intBitsToFloat(this.buffer[int2.intValue() + 18]) - this.z;
		float f21 = Float.intBitsToFloat(this.buffer[int2.intValue() + 24]) - this.x;
		float f22 = Float.intBitsToFloat(this.buffer[int2.intValue() + 25]) - this.y;
		float f23 = Float.intBitsToFloat(this.buffer[int2.intValue() + 26]) - this.z;
		float f24 = (f + f3 + f6 + f9) * 0.25F;
		float f25 = (f1 + f4 + f7 + f10) * 0.25F;
		float f26 = (f2 + f5 + f8 + f11) * 0.25F;
		float f27 = (f12 + f15 + f18 + f21) * 0.25F;
		float f28 = (f13 + f16 + f19 + f22) * 0.25F;
		float f29 = (f14 + f17 + f20 + f23) * 0.25F;
		float f30 = f24 * f24 + f25 * f25 + f26 * f26;
		float f31 = f27 * f27 + f28 * f28 + f29 * f29;
		return Float.compare(f31, f30);
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		return this.compare((Integer) obj1, (Integer) obj2);
	}
}
