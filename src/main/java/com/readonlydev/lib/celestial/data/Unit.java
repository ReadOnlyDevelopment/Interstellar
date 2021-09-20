package com.readonlydev.lib.celestial.data;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public abstract class Unit {

	protected NumberFormat format = new DecimalFormat("###.###########################################");

	public abstract UnitType getUnitType();

}
