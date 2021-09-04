package com.readonlydev.lib.celestial.data;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;

public class DimensionTypeBuilder {

	private int id;
	private String name;
	private String suffix;
	private Class<? extends WorldProvider> clazz;

	public DimensionTypeBuilder dimId(int id) {
		this.id = id;
		return this;
	}

	public DimensionTypeBuilder dimName(String name) {
		this.name = name;
		return this;
	}

	public DimensionTypeBuilder dimSuffix(String suffix) {
		this.suffix = suffix;
		return this;
	}

	public DimensionTypeBuilder providerClass(Class<? extends WorldProvider> clazz) {
		this.clazz = clazz;
		return this;
	}

	public DimensionType createType() {
		return DimensionType.register(this.name, this.suffix, this.id, this.clazz, false);
	}
}
