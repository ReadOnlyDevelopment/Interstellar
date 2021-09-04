package com.readonlydev.lib.world;

import net.minecraft.world.WorldType;

public final class InterstellarWorldType extends WorldType {

	private static InterstellarWorldType _instance;

	public InterstellarWorldType() {
		super("Interstellar");
	}

	public static InterstellarWorldType getInstance() {
		if (_instance == null) {
			init();
		}
		return _instance;
	}

	public static void init() {
		_instance = new InterstellarWorldType();
	}

	@Override
	public boolean isCustomizable() {
		return false;
	}
}
