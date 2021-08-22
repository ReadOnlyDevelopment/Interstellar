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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.readonlydev.api.client.IGuiDraw;
import com.readonlydev.api.client.IGuiDrawTooltip;
import com.readonlydev.api.client.IGuiInput;
import com.readonlydev.api.client.IGuiInputEvent;
import com.readonlydev.api.client.IGuiUpdate;
import com.readonlydev.lib.client.base.Tooltips;
import com.readonlydev.lib.client.gui.InterstellarGui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;

public abstract class SElementIndex extends SElement implements IGuiInput, IGuiInputEvent, IGuiDraw, IGuiDrawTooltip, IGuiUpdate, InterstellarGui {

	protected Map<String, SElement>	indices		= new HashMap<>();
	protected Map<String, Integer>	idToNum		= new HashMap<>();
	protected Map<Integer, String>	numToId		= new HashMap<>();
	protected int					indexTotal	= 0;
	protected String				currentIndex;
	protected Tooltips				tooltips;

	public SElementIndex(String elementID) {
		super(elementID);
	}

	@Override
	public final void read(NBTTagCompound nbt) {
		NBTTagCompound tn = nbt.getCompoundTag(this.elementID);
		setIndex(tn.getString("index"));
		getCurrentIndex().read(tn);
	}

	@Override
	public final NBTTagCompound write(NBTTagCompound nbt) {
		NBTTagCompound tn = new NBTTagCompound();
		tn.setString("index", this.currentIndex);
		getCurrentIndex().write(tn);
		nbt.setTag(this.elementID, tn);
		return nbt;
	}

	public void addIndex(SElement index) {
		if (index != null) {
			index.setGui(this);
			this.indices.put(index.elementID, index);
			this.idToNum.put(index.elementID, this.indexTotal);
			this.numToId.put(this.indexTotal, index.elementID);
			this.indexTotal++;
			if (StringUtils.isNullOrEmpty(this.currentIndex)) {
				this.currentIndex = index.elementID;
			}
		}
	}

	public boolean hasIndex(String indexID) {
		if (StringUtils.isNullOrEmpty(indexID)) {
			return false;
		}
		return this.indices.containsKey(indexID);
	}

	public boolean setIndex(String indexID) {
		if (hasIndex(indexID)) {
			this.currentIndex = indexID;
			return true;
		}
		return false;
	}

	public SElement getCurrentIndex() {
		return this.indices.get(this.currentIndex);
	}

	public SElement getIndex(String index) {
		if (!this.indices.containsKey(index)) {
			return null;
		}
		return this.indices.get(index);
	}

	public boolean setIndex(int index) {
		return false;
	}

	public String getNextIndex() {
		int next = this.idToNum.get(this.currentIndex).intValue() + 1;
		if (next < this.indices.size()) {
			return this.numToId.get(Integer.valueOf(next));
		}
		return this.numToId.get(Integer.valueOf(0));
	}

	public String getPrevIndex() {
		int prev = this.idToNum.get(this.currentIndex).intValue() - 1;
		if (prev >= 0) {
			return this.numToId.get(Integer.valueOf(prev));
		}
		return this.numToId.get(Integer.valueOf(this.indices.size() - 1));
	}

	public boolean hasNextIndex() {
		return (this.idToNum.get(this.currentIndex).intValue() < this.indices.size() - 1);
	}

	public boolean hasPrevIndex() {
		return (this.idToNum.get(this.currentIndex).intValue() > 0);
	}

	public boolean isCurrentIndex(String index) {
		return (index != null) ? index.equals(this.currentIndex) : false;
	}

	@Override
	public void update() {
		SElement ci = this.indices.get(this.currentIndex);
		if (ci != null && ci instanceof IGuiUpdate) {
			((IGuiUpdate) ci).update();
		}
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		SElement ci = this.indices.get(this.currentIndex);
		if (ci != null && ci instanceof IGuiDraw) {
			((IGuiDraw) ci).drawBackground(mx, my, partialTicks);
		}
	}

	@Override
	public void drawForeground(int mx, int my, float partialTicks) {
		SElement ci = this.indices.get(this.currentIndex);
		if (ci != null && ci instanceof IGuiDraw) {
			((IGuiDraw) ci).drawForeground(mx, my, partialTicks);
		}
	}

	@Override
	public void clickEvent(SElement ele, int mx, int my, int mouseButton) {
		SElement ci = this.indices.get(this.currentIndex);
		if (ci != null && ci instanceof IGuiInputEvent) {
			((IGuiInputEvent) ci).clickEvent(ele, mx, my, mouseButton);
		}
	}

	@Override
	public void scrollEvent(SElement ele, int mx, int my, int scroll) {
		SElement ci = this.indices.get(this.currentIndex);
		if (ci != null && ci instanceof IGuiInputEvent) {
			((IGuiInputEvent) ci).scrollEvent(ele, mx, my, scroll);
		}
	}

	@Override
	public void keyEvent(SElement ele, char character, int keyCode) {
		SElement ci = this.indices.get(this.currentIndex);
		if (ci != null && ci instanceof IGuiInputEvent) {
			((IGuiInputEvent) ci).keyEvent(ele, character, keyCode);
		}
	}

	@Override
	public boolean onClick(int mx, int my, int mouseButton) {
		SElement ci = this.indices.get(this.currentIndex);
		if (ci != null && ci instanceof IGuiInput) {
			((IGuiInput) ci).onClick(mx, my, mouseButton);
		}
		return false;
	}

	@Override
	public boolean onScroll(int mx, int my, int scroll) {
		SElement ci = this.indices.get(this.currentIndex);
		if (ci != null && ci instanceof IGuiInput) {
			((IGuiInput) ci).onScroll(mx, my, scroll);
		}
		return false;
	}

	@Override
	public boolean onKey(char c, int keyCode) {
		return false;
	}

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
		return this.gui.getGuiSizeX();
	}

	@Override
	public int getGuiSizeY() {
		return this.gui.getGuiSizeY();
	}

	@Override
	public FontRenderer getFontRenderer() {
		return this.gui.getFontRenderer();
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
		SElement ci = this.indices.get(this.currentIndex);
		if (ci != null && ci instanceof IGuiDrawTooltip) {
			((IGuiDrawTooltip) ci).draw(mx, my);
		}
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
	public void onElementResize(@Nonnull SElement ele) {
		if (ele.getXPosActualLargest() > getXPosActualLargest()) {
			this.xSize = ele.getXPosOffsetLargest();
		}
		if (ele.getYPosActualLargest() > getYPosActualLargest()) {
			this.ySize = ele.getXPosOffsetLargest();
		}
		this.gui.onElementResize(ele);
	}

	@Override
	public int getXSize() {
		if (hasIndex(this.currentIndex)) {
			return getCurrentIndex().getXSize();
		}
		return this.gui.getGuiSizeX();
	}

	@Override
	public int getYSize() {
		if (hasIndex(this.currentIndex)) {
			return getCurrentIndex().getYSize();
		}
		return this.gui.getGuiSizeY();
	}

	@Override
	public int getXPosActual() {
		return getGuiLeft();
	}

	@Override
	public int getYPosActual() {
		return getGuiTop();
	}
}
