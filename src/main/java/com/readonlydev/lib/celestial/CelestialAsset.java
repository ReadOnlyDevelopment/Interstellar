package com.readonlydev.lib.celestial;

import java.util.HashMap;
import java.util.Map;

import com.readonlydev.lib.base.InterstellarMod;

import net.minecraft.util.ResourceLocation;

public class CelestialAsset {

	private final InterstellarMod mod;
	private Map<String, ResourceLocation> textures = new HashMap<>();

	public CelestialAsset(InterstellarMod mod) {
		this.mod = mod;
	}

	public void addTexture(String name, String systemName) {
		systemName = systemName.toLowerCase();
		String path = "textures/celestialbodies/" + systemName + "/" + name + ".png";
		textures.put(name, new ResourceLocation(mod.getModId(), path));
	}

	public void addRealisticTexture(String name, String systemName) {
		systemName = systemName.toLowerCase();
		String path = "textures/celestialbodies/" + systemName + "/realism/" + name + ".png";
		textures.put(name + "_realistic", new ResourceLocation(mod.getModId(), path));
	}

	public ResourceLocation getCelestial(String name) {
		name = name.toLowerCase();
		if (!textures.containsKey(name)) {
			mod.getLogger().error("Cannot assign texture " + name + " as it does not exist");
			return null;
		} else {
			return textures.get(name);
		}
	}

	public ResourceLocation getRealistic(String name) {
		name = name.toLowerCase() + "_realistic";
		if (!textures.containsKey(name)) {
			mod.getLogger().error("Cannot assign texture " + name + " as it does not exist");
			return null;
		} else {
			return textures.get(name);
		}
	}
}
