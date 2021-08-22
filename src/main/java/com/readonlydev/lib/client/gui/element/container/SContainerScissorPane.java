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

import com.readonlydev.api.client.IGuiDraw;
import com.readonlydev.lib.client.gui.element.SElement;
import com.readonlydev.lib.client.render.RenderUtil;
import com.readonlydev.lib.math.MathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;

public abstract class SContainerScissorPane extends SContainerFixedSize {

	protected int	xScroll		= 0;
	protected int	yScroll		= 0;
	protected int	xScrollMin	= 0;
	protected int	yScrollMin	= 0;
	protected int	xScrollMax	= 0;
	protected int	yScrollMax	= 0;
	protected int	zoom		= 10;

	public SContainerScissorPane(String elementID, int xSize, int ySize) {
		super(elementID, xSize, ySize);
	}

	public SContainerScissorPane(String elementID) {
		this(elementID, 0, 0);
	}

	public void resetScroll() {
		this.xScroll = 0;
		this.yScroll = 0;
	}

	public void setYScroll(int amt) {
		this.yScroll = MathUtil.clamp(amt, this.yScrollMin, this.yScrollMax);
	}

	public void setXScroll(int amt) {
		this.xScroll = MathUtil.clamp(amt, this.xScrollMin, this.xScrollMax);
	}

	public void addYScroll(int amt) {
		this.yScroll = MathUtil.clamp(this.yScroll + amt, this.yScrollMin, this.yScrollMax);
	}

	public void addXScroll(int amt) {
		this.xScroll = MathUtil.clamp(this.xScroll + amt, this.xScrollMin, this.xScrollMax);
	}

	public void setScrollMin(int xScrollMin, int yScrollMin) {
		this.xScrollMin = xScrollMin;
		this.yScrollMin = yScrollMin;
	}

	public void setScrollMax(int xScrollMax, int yScrollMax) {
		this.xScrollMax = xScrollMax;
		this.yScrollMax = yScrollMax;
	}

	public float getZoomDiv() {
		return 10.0F;
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		float zoomSize = this.zoom / getZoomDiv();
		GlStateManager.pushMatrix();
		RenderUtil.guiScissorStart(Minecraft.getMinecraft(), this.gui.getGuiLeft() + this.xPosOffset + 1, this.gui.getGuiTop() + this.yPosOffset + 1, this.xSize - 2, this.ySize - 2);
		GlStateManager.scale(zoomSize, zoomSize, 1.0F);
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiDraw && ((IGuiDraw) ele).isBackgroundVisible()) {
				((IGuiDraw) ele).drawBackground(mx, my, partialTicks);
			}
		}
		RenderUtil.guiScissorEnd(Minecraft.getMinecraft(), this.gui.getGuiLeft() + this.xPosOffset + 1, this.gui.getGuiTop() + this.yPosOffset + 1, this.xSize - 2, this.ySize - 2);
		GlStateManager.popMatrix();
	}

	@Override
	public void drawForeground(int mx, int my, float partialTicks) {
		float zoomSize = this.zoom / getZoomDiv();
		GlStateManager.pushMatrix();
		RenderUtil.guiScissorStart(Minecraft.getMinecraft(), this.gui.getGuiLeft() + this.xPosOffset + 1, this.gui.getGuiTop() + this.yPosOffset + 1, this.xSize - 2, this.ySize - 2);
		GlStateManager.scale(zoomSize, zoomSize, 1.0F);
		for (SElement ele : this.elements) {
			if (ele instanceof IGuiDraw && ((IGuiDraw) ele).isForegroundVisible()) {
				((IGuiDraw) ele).drawForeground(mx, my, partialTicks);
			}
		}
		RenderUtil.guiScissorEnd(Minecraft.getMinecraft(), this.gui.getGuiLeft() + this.xPosOffset + 1, this.gui.getGuiTop() + this.yPosOffset + 1, this.xSize - 2, this.ySize - 2);
		GlStateManager.popMatrix();
	}

	@Override
	public boolean onClick(int mx, int my, int mouseButton) {
		return super.onClick(mx, my, mouseButton);
	}

	@Override
	public boolean onScroll(int mx, int my, int scroll) {
		addYScroll(-scroll * 12);
		return super.onScroll(mx, my, scroll);
	}

	@Override
	public int getGuiLeft() {
		return (int) ((this.gui.getGuiLeft() + this.xPosOffset) + this.xScroll * this.zoom / getZoomDiv());
	}

	@Override
	public int getGuiTop() {
		return (int) ((this.gui.getGuiTop() + this.yPosOffset) + this.yScroll * this.zoom / getZoomDiv());
	}

	@Override
	public void readOtherFromNBT(NBTTagCompound nbt) {
		this.xScroll = nbt.getInteger("x_scroll");
		this.yScroll = nbt.getInteger("y_scroll");
	}

	@Override
	public NBTTagCompound writeOtherToNBT(NBTTagCompound nbt) {
		nbt.setInteger("x_scroll", this.xScroll);
		nbt.setInteger("y_scroll", this.yScroll);
		return nbt;
	}
}
