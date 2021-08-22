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

package com.readonlydev.lib.client;

import java.util.HashMap;

import com.readonlydev.api.client.IGuiObjectHandler;
import com.readonlydev.api.client.IGuiObjectReference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class InterstellarGuiHandler implements IGuiHandler {

	private static InterstellarGuiHandler _instance;

	public static InterstellarGuiHandler instance() {
		if (_instance == null) {
			_instance = new InterstellarGuiHandler();
		}
		return _instance;
	}

	private HashMap<IGuiObjectReference, Integer>	gti	= new HashMap<>();
	private HashMap<Integer, IGuiObjectReference>	itg	= new HashMap<>();

	private int nextID = 0;

	private GuiScreen lastScreen;

	public void register(IGuiObjectReference obj) {
		this.gti.put(obj, this.nextID);
		this.itg.put(this.nextID, obj);
		this.nextID++;
	}

	public int getGuiID(IGuiObjectReference obj) {
		if (this.gti.containsKey(obj)) {
			return (this.gti.get(obj));
		}
		return 0;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		IGuiObjectHandler obj = getObject(ID, player, world, x, y, z);
		if (obj != null) {
			return obj.getServerElement(player);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		this.lastScreen = (Minecraft.getMinecraft()).currentScreen;
		IGuiObjectHandler gho = getObject(ID, player, world, x, y, z);
		if (gho != null) {
			return gho.getClientElement(player);
		}
		return null;
	}

	public void returnToLast() {
		if (this.lastScreen != null) {
			Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
		}
	}

	public void setLast(GuiScreen gui) {
		this.lastScreen = gui;
	}

	private IGuiObjectHandler getObject(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (this.itg.containsKey(Integer.valueOf(ID))) {
			IGuiObjectReference obj = this.itg.get(Integer.valueOf(ID));
			if (obj instanceof net.minecraft.block.Block) {
				TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
				if (te instanceof IGuiObjectHandler) {
					IGuiObjectHandler gho = (IGuiObjectHandler) te;
					return gho;
				}
			} else if (obj instanceof Item) {
				if (player.getHeldItemMainhand() == ItemStack.EMPTY) {
					return null;
				}
				Item item = player.getHeldItemMainhand().getItem();
				if (item instanceof IGuiObjectHandler) {
					IGuiObjectHandler gho = (IGuiObjectHandler) item;
					return gho;
				}
			} else if (obj instanceof IGuiObjectHandler) {
				IGuiObjectHandler gho = (IGuiObjectHandler) obj;
				return gho;
			}
		}
		return null;
	}
}
