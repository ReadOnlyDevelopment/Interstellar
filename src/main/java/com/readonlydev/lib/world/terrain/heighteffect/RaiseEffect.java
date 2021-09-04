package com.readonlydev.lib.world.terrain.heighteffect;

import com.readonlydev.lib.world.ExoplanetWorld;


/**
 * // just adds a constant increase
 *
 * @author Zeno410
 */
public class RaiseEffect extends HeightEffect {

    // just adds a number
    public final float height;

    public RaiseEffect(float height) {

        this.height = height;
    }

    @Override
    public final float added(ExoplanetWorld exoWorld, float x, float y) {

        return height;
    }
}
