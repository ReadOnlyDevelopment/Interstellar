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
import net.interstellar.api.client.IGuiInput;
import net.interstellar.lib.client.base.Tooltips;
import net.minecraft.nbt.NBTTagCompound;

public abstract class SElementButton extends SElement implements IGuiDraw, IGuiInput, IGuiDrawTooltip {

	protected Tooltips tooltips;

	protected boolean enabled = true;

	public SElementButton(String elementID, int xPos, int yPos, int xSize, int ySize) {
		super(elementID);
		this.tooltips = new Tooltips();
		this.xPosOffset = xPos;
		this.yPosOffset = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	@Override
	public boolean onClick(int mx, int my, int mouseButton) {
		if (isEnabledOutsideOfGui()) {
			return this.gui.isInBox(mx, my, getXPosActual(), getYPosActual(), getXSize(), getYSize());
		}
		return this.gui.isInBoxAndGUI(mx, my, getXPosActual(), getYPosActual(), getXSize(), getYSize());
	}

	@Override
	public boolean onScroll(int mx, int my, int scroll) {
		return false;
	}

	@Override
	public boolean onKey(char c, int keyCode) {
		return false;
	}

	public void setEnabled(boolean opt) {
		this.enabled = opt;
	}

	public boolean getEnabled() {
		return this.enabled;
	}

	@Override
	public void add(String localizedToolTip) {
		this.tooltips.add(localizedToolTip);
	}

	@Override
	public void set(List<String> toolTips) {
		this.tooltips.set(toolTips);
	}

	@Override
	public void draw(int mx, int my) {
		if (!this.enabled) {
			return;
		}
		boolean inBoxGui = false;
		if (isEnabledOutsideOfGui()) {
			if (this.gui.isInBox(mx, my, getXPosActual(), getYPosActual(), getXSize(), getYSize())) {
				inBoxGui = true;
			}
		} else if (this.gui.isInBoxAndGUI(mx, my, getXPosActual(), getYPosActual(), getXSize(), getYSize())) {
			inBoxGui = true;
		}
		if (inBoxGui) {
			// int mxn = mx - this.gui.getGuiLeft();
			// int myn = my - this.gui.getGuiTop() - 4;
			this.gui.drawHoverText(this.tooltips.get(), mx, my);
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
