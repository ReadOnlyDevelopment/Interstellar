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

package com.readonlydev.lib.client.gui.element;

import java.util.List;

import com.readonlydev.api.client.IGuiDraw;
import com.readonlydev.api.client.IGuiDrawTooltip;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SElementItemStack extends SElement implements IGuiDraw, IGuiDrawTooltip {

	protected ItemStack	stack;
	protected boolean	draw		= true;
	protected boolean	renderStackSize	= false;

	public SElementItemStack(String elementID, int x, int y, ItemStack stack) {
		super(elementID);
		this.stack = stack;
		this.xPosOffset = x;
		this.yPosOffset = y;
		this.xSize = 16;
		this.ySize = 16;
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		RenderHelper.enableGUIStandardItemLighting();
		Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(this.stack, getXPosActual(), getYPosActual());
		if (this.renderStackSize) {
			Minecraft.getMinecraft().getRenderItem().renderItemOverlayIntoGUI(this.gui.getFontRenderer(), this.stack, getXPosActual(), getYPosActual(), "" + this.stack.getCount());
		}
		RenderHelper.disableStandardItemLighting();
	}

	public void renderStackSize(boolean opt) {
		this.renderStackSize = opt;
	}

	public void setDrawToolTip(boolean opt) {
		this.draw = opt;
	}

	@Override
	public void drawForeground(int mx, int my, float partialTicks) {
	}

	@Override
	public int getXSize() {
		return 16;
	}

	@Override
	public int getYSize() {
		return 16;
	}

	@Override
	public void setXSize(int xSize) {
	}

	@Override
	public void setYSize(int ySize) {
	}

	@Override
	public void init() {
	}

	@Override
	public void add(String localizedToolTip) {
	}

	@Override
	public void set(List<String> toolTips) {
	}

	@Override
	public void draw(int mx, int my) {
		if (!this.draw) {
			return;
		}
		if (this.gui.isInBoxAndGUI(mx, my, getXPosActual(), getYPosActual(), getXSize(), getYSize())) {
			this.gui.drawHoverText(this.stack.getTooltip((Minecraft.getMinecraft()).player, (Minecraft.getMinecraft()).gameSettings.advancedItemTooltips ? (ITooltipFlag) ITooltipFlag.TooltipFlags.ADVANCED : (ITooltipFlag) ITooltipFlag.TooltipFlags.NORMAL), mx, my);
		}
	}

	@Override
	public void read(NBTTagCompound nbt) {
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt) {
		return nbt;
	}
}
