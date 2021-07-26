/*
 * Library License
 *
 * Copyright (c) 2021 ReadOnly Development
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.interstellar.lib.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class InterstellarNetwork {

	public final SimpleNetworkWrapper dispatcher;

	public short packetID = 0;

	public InterstellarNetwork(String channel) {
		this.dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(channel);
	}

	public final void registerPacket(Class packetAndHandler, Side side) {
		this.packetID = (short) (this.packetID + 1);
		this.dispatcher.registerMessage(packetAndHandler, packetAndHandler, this.packetID, side);
	}

	public final void registerPacket(Class handler, Class message, Side side) {
		this.packetID = (short) (this.packetID + 1);
		this.dispatcher.registerMessage(handler, message, this.packetID, side);
	}

	public final void sendTo(IMessage message, EntityPlayerMP player) {
		this.dispatcher.sendTo(message, player);
	}

	public final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
		this.dispatcher.sendToAllAround(message, point);
	}

	public final void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
		sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
	}

	public final void sendToAllAround(IMessage message, EntityPlayer player, double range) {
		sendToAllAround(message, (player.getEntityWorld()).provider.getDimension(), player.posX, player.posY, player.posZ, range);
	}

	public final void sendToAllInDimension(IMessage message, int dimensionId) {
		this.dispatcher.sendToDimension(message, dimensionId);
	}

	public final void sendToServer(IMessage message) {
		this.dispatcher.sendToServer(message);
	}
}
