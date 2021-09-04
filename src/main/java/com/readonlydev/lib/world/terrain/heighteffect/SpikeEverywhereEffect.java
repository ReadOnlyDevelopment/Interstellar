package com.readonlydev.lib.world.terrain.heighteffect;

import com.readonlydev.lib.world.ExoplanetWorld;
import com.readonlydev.lib.world.terrain.TerrainBase;


/**
 * This creates a spiky multiplier going from 0 to 1
 *
 * @author Zeno410
 */
public class SpikeEverywhereEffect extends HeightEffect {

    // not going to bother to set up a creator shell to make sure everything is set
    // set defaults to absurd values to crash if they're not set
    // a trio of parameters frequently used together
    ;
    public float wavelength = 0;
    public float minimumSimplex = Integer.MAX_VALUE;// normal range is -1 to 1;
    //usually numbers above 0 are often preferred to avoid dead basins
    public int octave;
    public float power = 1.6f;// usually a range of 1 to 2
    public HeightEffect spiked;

    @Override
    public final float added(ExoplanetWorld exoWorld, float x, float y) {

        float noise = exoWorld.simplexInstance(octave).noise2f(x / wavelength, y / wavelength);
        noise = Math.abs(noise);
        noise = TerrainBase.blendedHillHeight(noise, minimumSimplex);
        noise = TerrainBase.unsignedPower(noise, power);
        return noise * spiked.added(exoWorld, x, y);
    }
}