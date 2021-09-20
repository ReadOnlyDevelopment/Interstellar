package com.readonlydev.api.celestial;

import com.readonlydev.lib.celestial.data.Mass;
import com.readonlydev.lib.celestial.data.Radius;

public interface ISystemBody {

	public Mass getMass();

	public Radius getRadius();

	public double getShwartzchildRadius();
}
