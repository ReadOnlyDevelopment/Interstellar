package com.readonlydev.lib.celestial;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.readonlydev.lib.celestial.events.RegisterEvent;
import com.readonlydev.lib.celestial.events.RegisterEvent.ExoplanetRegisterEvent;
import com.readonlydev.lib.celestial.objects.ExoStar;
import com.readonlydev.lib.celestial.objects.ExoStarSystem;
import com.readonlydev.lib.celestial.objects.Exoplanet;

public class CelestialRegistry {

	private static List<Exoplanet> registeredExoplanets = new ArrayList<>();
	private static List<ExoStar> registeredStars = new ArrayList<>();
	private static List<ExoStarSystem> registeredStarSystems = new ArrayList<>();

	private static Map<ExoStarSystem, List<Exoplanet>> systemsMap = new LinkedHashMap<>();

	private static <T> RegisterEvent<?> registerEventFactory(T celestialObject) {
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

	public static void register(Exoplanet exoplanet) {
		if (registeredExoplanets.add(exoplanet)) {
			if (registerEventFactory(exoplanet).call()) {
				systemsMap.get(exoplanet.getParentSolarSystem()).add(exoplanet);
			}
		}
	}

	public static void register(ExoStar star) {
		if (registeredStars.add(star)) {
			registerEventFactory(star).call();
		}
	}

	public static void register(ExoStarSystem starSystem) {
		if (registeredStarSystems.add(starSystem)) {
			if (registerEventFactory(starSystem).call()) {
				systemsMap.put(starSystem, new ArrayList<>());
			}
		}
	}
}
