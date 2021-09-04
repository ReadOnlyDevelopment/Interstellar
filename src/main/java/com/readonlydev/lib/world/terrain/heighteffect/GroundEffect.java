package com.readonlydev.lib.world.terrain.heighteffect;

import com.readonlydev.lib.world.ExoplanetWorld;
import com.readonlydev.lib.world.terrain.TerrainBase;

/**
 * @author Zeno410
 */
public class GroundEffect extends HeightEffect {

	// the standard ground effect
	private final float amplitude;

	public GroundEffect(float amplitude) {

		this.amplitude = amplitude;
	}

	@Override
	public final float added(ExoplanetWorld exoWorld, float x, float y) {
		return TerrainBase.groundNoise(x, y, amplitude, exoWorld);
	}

}
