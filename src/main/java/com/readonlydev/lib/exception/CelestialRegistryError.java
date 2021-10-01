package com.readonlydev.lib.exception;

import com.readonlydev.api.celestial.ICelestialObject;

public class CelestialRegistryError extends Error {

	public CelestialRegistryError(ICelestialObject celestialObject, String msg) {
		super("CelestialRegistryError", "[Issue Regstering: " + celestialObject.getCelestialName() + "] | ", msg);
		log();
	}
}
