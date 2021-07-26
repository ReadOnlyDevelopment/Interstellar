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

package net.interstellar.lib.network.packet;

import io.netty.buffer.ByteBuf;
import net.interstellar.lib.guide.client.GuiGuide;
import net.interstellar.lib.guide.item.ItemSGuide;
import net.interstellar.lib.network.StellarPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSaveGuide extends StellarPacket<PacketSaveGuide> {

	public NBTTagCompound nbt;

	@SideOnly(Side.CLIENT)
	public PacketSaveGuide(GuiGuide gui, EntityPlayer player) {
		this.nbt = new NBTTagCompound();
		gui.write(this.nbt);
	}

	public PacketSaveGuide() {
		this.nbt = new NBTTagCompound();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.nbt = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, this.nbt);
	}

	@Override
	public IMessage onMessage(PacketSaveGuide message, MessageContext ctx) {
		if (ctx.getServerHandler() != null) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
				EntityPlayerMP player = (ctx.getServerHandler()).player;
				ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);
				if (!(held.getItem() instanceof ItemSGuide)) {
					held = player.getHeldItem(EnumHand.OFF_HAND);
				}
				if (!(held.getItem() instanceof ItemSGuide)) {
					return;
				}
				NBTTagCompound nbt = held.getTagCompound();
				if (nbt == null) {
					held.setTagCompound(new NBTTagCompound());
					nbt = held.getTagCompound();
				}
				nbt.setTag("guide_data", message.nbt);
			});
		}
		return null;
	}
}
