/*
 * (C) 2014-2018 Team CoFH / CoFH / Cult of the Full Hub
 * http://www.teamcofh.com
 */
package com.readonlydev.api.energy;

import net.minecraft.util.EnumFacing;

/**
 * Implement this interface on TileEntities which should connect to energy
 * transportation blocks. This is intended for blocks which generate energy but
 * do not accept it; otherwise just use IEnergyHandler.
 *
 * Note that {@linkplain IEnergyHandler} is an extension of this.
 *
 * @author King Lemming
 */
public interface IEnergyConnection {

	/**
	 * Returns TRUE if the TileEntity can connect on a given side.
	 */
	boolean canConnectEnergy(EnumFacing from);

}
