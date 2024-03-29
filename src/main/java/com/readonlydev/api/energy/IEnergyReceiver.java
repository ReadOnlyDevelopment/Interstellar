/*
 * (C) 2014-2018 Team CoFH / CoFH / Cult of the Full Hub
 * http://www.teamcofh.com
 */
package com.readonlydev.api.energy;

import net.minecraft.util.EnumFacing;

/**
 * Implement this interface on Tile Entities which should receive energy,
 * generally storing it in one or more internal {@linkplain IEnergyStorage} objects.
 *
 * A reference implementation is provided {@linkplain TileEnergyHandler}.
 *
 * @author King Lemming
 */
public interface IEnergyReceiver extends IEnergyHandler {

	/**
	 * Add energy to an IEnergyReceiver, internal distribution is left entirely to
	 * the IEnergyReceiver.
	 *
	 * @param from       Orientation the energy is received from.
	 * @param maxReceive Maximum amount of energy to receive.
	 * @param simulate   If TRUE, the charge will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated)
	 *         received.
	 */
	int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate);

}
