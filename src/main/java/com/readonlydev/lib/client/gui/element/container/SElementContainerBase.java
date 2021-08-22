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

package com.readonlydev.lib.client.gui.element.container;

import java.util.LinkedList;
import java.util.List;

import com.readonlydev.api.client.IGuiDraw;
import com.readonlydev.api.client.IGuiDrawTooltip;
import com.readonlydev.api.client.IGuiInput;
import com.readonlydev.api.client.IGuiInputEvent;
import com.readonlydev.api.client.IGuiUpdate;
import com.readonlydev.lib.client.base.Tooltips;
import com.readonlydev.lib.client.gui.InterstellarGui;
import com.readonlydev.lib.client.gui.element.SElement;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;

public abstract class SElementContainerBase extends SElement implements IGuiInput, IGuiInputEvent, IGuiDraw, IGuiDrawTooltip, IGuiUpdate, InterstellarGui {

	protected LinkedList<SElement>	elements	= new LinkedList<>();
	protected Tooltips				tooltips;

	public SElementContainerBase(String elementID) {
		super(elementID);
	}

	public void addElement(SElement ele) {
		if (ele == null) {
			return;
		}
		ele.setGui(this);
		if (onElementAdded(ele)) {
			this.elements.addLast(ele);
		}
	}

	public int getTotalElements() {
		return this.elements.size();
	}

	@Override
	public final void init() {
		preInit();
		addElements();
		postInit();
	}

	protected abstract boolean onElementAdded(SElement paramSElement);

	protected abstract void preInit();

	protected abstract void postInit();

	public abstract void addElements();

	@Override
	public Gui getGui() {
		return this.gui.getGui();
	}

	@Override
	public int getGuiLeft() {
		return this.gui.getGuiLeft() + this.xPosOffset;
	}

	@Override
	public int getGuiTop() {
		return this.gui.getGuiTop() + this.yPosOffset;
	}

	@Override
	public int getGuiSizeX() {
		return this.xSize;
	}

	@Override
	public int getGuiSizeY() {
		return this.ySize;
	}

	@Override
	public FontRenderer getFontRenderer() {
		return this.gui.getFontRenderer();
	}

	@Override
	public void drawHoverText(List<String> list, int x, int y) {
		this.gui.drawHoverText(list, x, y);
	}

	@Override
	public boolean isInGUI(int x, int y) {
		return this.gui.isInBoxAndGUI(x, y, getXPosActual(), getYPosActual(), getXSize(), getYSize());
	}

	@Override
	public void update() {
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiUpdate) {
				((IGuiUpdate) ele).update();
			}
		}
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
		boolean isInBox = false;
		if (isInBox(mx, my, getXPosActual(), getYPosActual(), getXSize(), getYSize())) {
			isInBox = true;
		}
		for (SElement ele : this.elements) {
			if ((ele.isEnabledOutsideOfGui() || isInBox) && ele instanceof IGuiDrawTooltip) {
				((IGuiDrawTooltip) ele).draw(mx, my);
			}
		}
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiDraw && ((IGuiDraw) ele).isBackgroundVisible()) {
				((IGuiDraw) ele).drawBackground(mx, my, partialTicks);
			}
		}
	}

	@Override
	public void drawForeground(int mx, int my, float partialTicks) {
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiDraw && ((IGuiDraw) ele).isForegroundVisible()) {
				((IGuiDraw) ele).drawForeground(mx, my, partialTicks);
			}
		}
	}

	@Override
	public void clickEvent(SElement ele, int mx, int my, int mouseButton) {
	}

	@Override
	public void scrollEvent(SElement ele, int mx, int my, int scroll) {
	}

	@Override
	public void keyEvent(SElement ele, char character, int keyCode) {
	}

	@Override
	public boolean onClick(int mx, int my, int mouseButton) {
		boolean any = false;
		boolean isInBox = false;
		if (isInBox(mx, my, getXPosActual(), getYPosActual(), getXSize(), getYSize())) {
			isInBox = true;
		}
		for (SElement ele : this.elements) {
			if ((ele.isEnabledOutsideOfGui() || isInBox) && ele instanceof IGuiInput && ((IGuiInput) ele).onClick(mx, my, mouseButton)) {
				clickEvent(ele, mx, my, mouseButton);
				any = true;
			}
		}
		return any;
	}

	@Override
	public boolean onScroll(int mx, int my, int scroll) {
		boolean any = false;
		boolean isInBox = false;
		if (isInBox(mx, my, getXPosActual(), getYPosActual(), getXSize(), getYSize())) {
			isInBox = true;
		}
		for (SElement ele : this.elements) {
			if ((ele.isEnabledOutsideOfGui() || isInBox) && ele instanceof IGuiInput && ((IGuiInput) ele).onScroll(mx, my, scroll)) {
				scrollEvent(ele, mx, my, scroll);
				any = true;
			}
		}
		return any;
	}

	@Override
	public boolean onKey(char c, int keyCode) {
		boolean any = false;
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiInput && ((IGuiInput) ele).onKey(c, keyCode)) {
				keyEvent(ele, c, keyCode);
				any = true;
			}
		}
		return any;
	}

	@Override
	public final void read(NBTTagCompound nbt) {
		NBTTagCompound nt = nbt.getCompoundTag(this.elementID);
		for (SElement vlElement : this.elements) {
			vlElement.read(nt);
		}
		readOtherFromNBT(nbt);
	}

	public void readOtherFromNBT(NBTTagCompound nbt) {
	}

	@Override
	public final NBTTagCompound write(NBTTagCompound nbt) {
		NBTTagCompound nt = new NBTTagCompound();
		for (SElement vlElement : this.elements) {
			vlElement.write(nt);
		}
		writeOtherToNBT(nbt);
		if (nt.getSize() > 0) {
			nbt.setTag(this.elementID, nt);
		}
		return nbt;
	}

	public NBTTagCompound writeOtherToNBT(NBTTagCompound nbt) {
		return nbt;
	}
}
