package com.readonlydev.lib.world.terrain.heighteffect;

import com.readonlydev.lib.world.ExoplanetWorld;

/**
 * @author Zeno410
 */
public class HeightVariation extends HeightEffect {

	// not going to bother to set up a creator shell to make sure everything is set
	// set defaults to absurd values to crash if they're not set
	public float height = Integer.MAX_VALUE;
	public float wavelength = 0;
	public int octave = -1;

	@Override
	public final float added(ExoplanetWorld exoWorld, float x, float y) {
		return exoWorld.simplexInstance(octave).noise2f(x / wavelength, y / wavelength) * height;
	}

}
