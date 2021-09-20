package com.readonlydev.lib.celestial.events;

import com.readonlydev.api.celestial.ICelestialObject;
import com.readonlydev.lib.celestial.objects.ExoStar;
import com.readonlydev.lib.celestial.objects.ExoStarSystem;
import com.readonlydev.lib.celestial.objects.Exoplanet;

import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class RegisterEvent<T extends ICelestialObject> extends Event {

	private ICelestialObject celestialObject;

	public RegisterEvent(T celestialObject) {
		this.celestialObject = celestialObject;
	}

	public boolean call() {
		return MinecraftForge.EVENT_BUS.post(this);
	}

	public ICelestialObject getCelestialObject() {
		return celestialObject;
	}

	public static class ExoplanetRegisterEvent extends RegisterEvent<Exoplanet> {

		public ExoplanetRegisterEvent(Exoplanet celestialObject) {
			super(celestialObject);
			GalaxyRegistry.registerPlanet(celestialObject);
		}
	}

	public static class StarRegisterEvent extends RegisterEvent<ExoStar> {

		public StarRegisterEvent(ExoStar celestialObject) {
			super(celestialObject);
		}
	}

	public static class StarSystemRegisterEvent extends RegisterEvent<ExoStarSystem> {

		public StarSystemRegisterEvent(ExoStarSystem celestialObject) {
			super(celestialObject);
			GalaxyRegistry.registerSolarSystem(celestialObject);
		}
	}
}
