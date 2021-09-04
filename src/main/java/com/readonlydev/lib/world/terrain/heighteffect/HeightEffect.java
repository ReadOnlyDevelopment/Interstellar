package com.readonlydev.lib.world.terrain.heighteffect;

import com.readonlydev.lib.world.ExoplanetWorld;

/**
 * @author Zeno410
 */
public abstract class HeightEffect {

	public abstract float added(ExoplanetWorld exoWorld, float x, float y);

	public HeightEffect plus(HeightEffect added) {

		return new SummedHeightEffect(this, added);
	}
}
