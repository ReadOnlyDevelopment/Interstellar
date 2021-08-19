package net.interstellar.lib.celestial.data;

import micdoodle8.mods.galacticraft.api.vector.Vector3;

public class Vec {

	private double x, y, z;

	public static Vec of(double x, double y, double z) {
		return new Vec(x, y, z);
	}

	private Vec(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3 toVector3() {
		return new Vector3(x, y, z);
	}

}
