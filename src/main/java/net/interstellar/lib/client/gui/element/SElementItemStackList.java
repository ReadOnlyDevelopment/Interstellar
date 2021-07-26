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

package net.interstellar.lib.client.gui.element;

import java.util.List;

import net.interstellar.api.client.IGuiDraw;
import net.interstellar.api.client.IGuiDrawTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SElementItemStackList extends SElement implements IGuiDraw, IGuiDrawTooltip {

	protected List<ItemStack> stacks;

	protected float currentPartialTick = 0.0F;

	protected int ticksPerItem = 20;

	protected int currentItem = 0;

	protected boolean draw = true;

	public SElementItemStackList(String elementID, int x, int y, List<ItemStack> stacks) {
		super(elementID);
		this.xPosOffset = x;
		this.yPosOffset = y;
		this.stacks = stacks;
		this.xSize = 16;
		this.ySize = 16;
	}

	public void setDrawToolTip(boolean opt) {
		this.draw = opt;
	}

	public SElementItemStackList setTicksPerItem(int t) {
		this.ticksPerItem = t;
		return this;
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		this.currentPartialTick += partialTicks;
		if (this.currentPartialTick >= this.ticksPerItem) {
			this.currentPartialTick = 0.0F;
			this.currentItem++;
			if (this.currentItem >= this.stacks.size()) {
				this.currentItem = 0;
			}
		}
		if (this.stacks == null && this.stacks.size() <= 0) {
			return;
		}
		if (this.stacks.size() > this.currentItem) {
			RenderHelper.enableGUIStandardItemLighting();
			Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(this.stacks.get(this.currentItem), getXPosActual(), getYPosActual());
			RenderHelper.disableStandardItemLighting();
		}
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
			this.gui.drawHoverText(this.stacks.get(this.currentItem).getTooltip((Minecraft.getMinecraft()).player, (Minecraft.getMinecraft()).gameSettings.advancedItemTooltips ? (ITooltipFlag) ITooltipFlag.TooltipFlags.ADVANCED : (ITooltipFlag) ITooltipFlag.TooltipFlags.NORMAL), mx, my);
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
