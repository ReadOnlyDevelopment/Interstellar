package com.readonlydev.api.celestial;

import com.readonlydev.lib.celestial.data.Mass;
import com.readonlydev.lib.celestial.data.Radius;
import com.readonlydev.lib.celestial.data.UnitType;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody.ScalableDistance;
import net.minecraft.world.WorldProvider;

public interface IPropertySerializeable {

	ScalableDistance getDistanceFromStar();

	double getMassValue();

	double getRadiusValue();

	Class<? extends WorldProvider> getWorldProvider();

	default Mass getMass() {
		return new Mass(getMassValue(), UnitType.EARTH);
	}

	default Radius getRadius() {
		return new Radius(getRadiusValue(), UnitType.EARTH);
	}

}
