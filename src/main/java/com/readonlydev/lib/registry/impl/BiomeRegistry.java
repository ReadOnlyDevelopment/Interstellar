package com.readonlydev.lib.registry.impl;

import com.readonlydev.lib.registry.IEntryClass;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class BiomeRegistry implements IEntryClass<Biome> {

	@Override
	public Class<? extends IForgeRegistryEntry<Biome>> getEntry() {
		return Biome.class;
	}
}
