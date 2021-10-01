package com.readonlydev.lib.celestial;

import java.util.ArrayList;
import java.util.List;

import com.readonlydev.lib.celestial.events.RegisterEvent;
import com.readonlydev.lib.celestial.events.RegisterEvent.ExoplanetRegisterEvent;
import com.readonlydev.lib.celestial.objects.ExoStar;
import com.readonlydev.lib.celestial.objects.ExoStarSystem;
import com.readonlydev.lib.celestial.objects.Exoplanet;
import com.readonlydev.lib.exception.CelestialRegistryError;

public class CelestialRegistry {

	private List<Exoplanet> registeredExoplanets = new ArrayList<>();
	private List<ExoStar> registeredStars = new ArrayList<>();
	private List<ExoStarSystem> registeredStarSystems = new ArrayList<>();

	private <T> RegisterEvent<?> registerEventFactory(T celestialObject) {
		RegisterEvent<?> event;
		if (celestialObject instanceof Exoplanet) {
			Exoplanet exoplanet = (Exoplanet) celestialObject;
			event = new ExoplanetRegisterEvent(exoplanet);
		} else if (celestialObject instanceof ExoStar) {
			ExoStar exoStar = (ExoStar) celestialObject;
			event = new RegisterEvent.StarRegisterEvent(exoStar);
		} else if (celestialObject instanceof ExoStarSystem) {
			ExoStarSystem exoStarSystem = (ExoStarSystem) celestialObject;
			event = new RegisterEvent.StarSystemRegisterEvent(exoStarSystem);
		} else {
			throw new IllegalArgumentException();
		}
		return event;
	}

	public boolean register(Exoplanet exoplanet) {
		boolean result = registerEventFactory(exoplanet).call();
		if (!registeredExoplanets.add(exoplanet)) {
			new CelestialRegistryError(exoplanet, "Tried adding an Existing Exoplanet! This is a bug");
		}
		if (!result) {
			new CelestialRegistryError(exoplanet, "An error occured during a ExoplanetRegisterEvent! This is a bug!");
		}
		return result;
	}

	public boolean register(ExoStar star) {
		boolean result = registerEventFactory(star).call();
		if (!registeredStars.add(star)) {
			new CelestialRegistryError(star, "Tried adding an Existing Star! This is a bug");
		}
		if (!result) {
			new CelestialRegistryError(star, "An error occured during a StarRegisterEvent! This is a bug!");
		}
		return result;
	}

	public boolean register(ExoStarSystem starSystem) {
		boolean result = registerEventFactory(starSystem).call();
		if (!registeredStarSystems.add(starSystem)) {
			new CelestialRegistryError(starSystem, "Tried adding an Existing StarSystem! This is a bug");
		}
		if (!result) {
			new CelestialRegistryError(starSystem, "An error occured during a StarSystemRegisterEvent! This is a bug!");
		}
		return result;
	}
}
