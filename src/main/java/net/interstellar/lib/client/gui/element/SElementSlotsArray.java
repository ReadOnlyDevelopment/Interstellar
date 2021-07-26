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

import net.interstellar.api.client.IGuiDraw;
import net.interstellar.lib.client.lib.TextureLoc;
import net.interstellar.lib.inventory.InterstellarSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public class SElementSlotsArray extends SElement implements IGuiDraw {

	protected TextureLoc	defaultSlot;
	protected GuiContainer	guiCont;

	public SElementSlotsArray(String elementID, TextureLoc defaultSlot) {
		super(elementID);
		this.defaultSlot = defaultSlot;
	}

	@Override
	public void init() {
		if (this.gui.getGui() instanceof GuiContainer) {
			this.guiCont = (GuiContainer) this.gui.getGui();
		}
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.defaultSlot.texture);
		TextureLoc currBound = this.defaultSlot;
		for (Slot slot : this.guiCont.inventorySlots.inventorySlots) {
			if (slot instanceof InterstellarSlot) {
				InterstellarSlot vs = (InterstellarSlot) slot;
				TextureLoc ts = vs.getTexture();
				if (ts != null && currBound != ts) {
					currBound = ts;
					Minecraft.getMinecraft().getTextureManager().bindTexture(currBound.texture);
				}
				this.guiCont.drawTexturedModalRect(this.gui.getGuiLeft() + slot.xPos - 1, this.gui.getGuiTop() + slot.yPos - 1, currBound.startX, currBound.startY, currBound.width, currBound.height);
				continue;
			}
			if (currBound != this.defaultSlot) {
				currBound = this.defaultSlot;
				Minecraft.getMinecraft().getTextureManager().bindTexture(currBound.texture);
			}
			this.guiCont.drawTexturedModalRect(this.gui.getGuiLeft() + slot.xPos - 1, this.gui.getGuiTop() + slot.yPos - 1, this.defaultSlot.startX, this.defaultSlot.startY, this.defaultSlot.width, this.defaultSlot.height);
		}
	}

	@Override
	public void drawForeground(int mx, int my, float partialTicks) {
	}

	@Override
	public int getXSize() {
		int sx = Integer.MAX_VALUE;
		int lx = 0;
		for (Slot slot : this.guiCont.inventorySlots.inventorySlots) {
			if (slot.xPos < sx) {
				sx = slot.xPos;
				continue;
			}
			if (slot.xPos > lx) {
				lx = slot.xPos;
			}
		}
		return lx - sx;
	}

	@Override
	public int getYSize() {
		int sy = Integer.MAX_VALUE;
		int ly = 0;
		for (Slot slot : this.guiCont.inventorySlots.inventorySlots) {
			if (slot.yPos < sy) {
				sy = slot.yPos;
				continue;
			}
			if (slot.yPos > ly) {
				ly = slot.yPos;
			}
		}
		return ly - sy;
	}

	@Override
	public int getXPosActual() {
		int sx = Integer.MAX_VALUE;
		for (Slot s : this.guiCont.inventorySlots.inventorySlots) {
			if (s.yPos < sx) {
				sx = s.xPos;
			}
		}
		return this.gui.getGuiLeft() + sx;
	}

	@Override
	public int getYPosActual() {
		int sy = Integer.MAX_VALUE;
		for (Slot s : this.guiCont.inventorySlots.inventorySlots) {
			if (s.yPos < sy) {
				sy = s.yPos;
			}
		}
		return this.gui.getGuiLeft() + sy;
	}

	@Override
	public int getXPosOffset() {
		return 0;
	}

	@Override
	public int getYPosOffset() {
		return 0;
	}

	@Override
	public void setXPosOffset(int xPos) {
	}

	@Override
	public void setYPosOffset(int yPos) {
	}

	@Override
	public void read(NBTTagCompound nbt) {
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt) {
		return nbt;
	}
}
