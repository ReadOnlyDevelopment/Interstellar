/*
 * (C) 2014-2018 Team CoFH / CoFH / Cult of the Full Hub
 * http://www.teamcofh.com
 */
package com.readonlydev.api.energy;

import net.minecraft.util.EnumFacing;

/**
 * Implement this interface on Tile Entities which should handle energy,
 * generally storing it in one or more internal {@linkplain IEnergyStorage} objects.
 *
 * A reference implementation is provided {@linkplain TileEnergyHandler}.
 *
 * Note that {@linkplain IEnergyReceiver} and {@linkplain IEnergyProvider} are extensions
 * of this.
 *
 * @author King Lemming
 */
public interface IEnergyHandler extends IEnergyConnection {

	/**
	 * Returns the amount of energy currently stored.
	 */
	int getEnergyStored(EnumFacing from);

	/**
	 * Returns the maximum amount of energy that can be stored.
	 */
	int getMaxEnergyStored(EnumFacing from);

}
