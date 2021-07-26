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

package net.interstellar.lib.tile;

import net.interstellar.lib.annotation.SyncVariable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class TileInterstellarEntity extends TileEntity {

	public final void sendUpdate() {
		if (world != null && !world.isRemote) {
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 3);
			markDirty();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tags) {
		super.readFromNBT(tags);
		readSyncVars(tags);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tags) {
		super.writeToNBT(tags);
		writeSyncVars(tags, SyncVariable.Type.WRITE);
		return tags;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tags = getUpdateTag();
		return new SPacketUpdateTileEntity(pos, 1, tags);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tags = super.getUpdateTag();
		writeSyncVars(tags, SyncVariable.Type.PACKET);
		return tags;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		readSyncVars(packet.getNbtCompound());
	}

	protected void readSyncVars(NBTTagCompound tags) {
		SyncVariable.Helper.readSyncVars(this, tags);
	}

	protected NBTTagCompound writeSyncVars(NBTTagCompound tags, SyncVariable.Type syncType) {
		return SyncVariable.Helper.writeSyncVars(this, tags, syncType);
	}
}
