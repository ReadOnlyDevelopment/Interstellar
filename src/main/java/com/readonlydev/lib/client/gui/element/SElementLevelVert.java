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
import com.readonlydev.lib.client.base.Tooltips;
import com.readonlydev.lib.client.lib.TextureLoc;
import com.readonlydev.lib.utils.ColorUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.client.config.GuiUtils;

public class SElementLevelVert extends SElement implements IGuiDraw, IGuiDrawTooltip {

	protected TextureLoc levelOverlay;

	protected int levelColor;

	protected float level_scaled = 0.0F;

	protected Tooltips tooltips;

	public SElementLevelVert(String elementID, int x, int y, TextureLoc levelOverlay) {
		super(elementID);
		this.xPosOffset = x;
		this.yPosOffset = y;
		this.levelOverlay = levelOverlay;
		this.tooltips = new Tooltips();
		this.levelColor = ColorUtil.calcMCColor(255, 0, 0, 255);
	}

	@Override
	public void init() {
	}

	public void updateLevel(float level) {
		if (level < 0.0F) {
			this.level_scaled = 0.0F;
		} else if (level > 1.0F) {
			this.level_scaled = 1.0F;
		} else {
			this.level_scaled = level;
		}
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.gui.getGui();
		Gui.drawRect(this.gui.getGuiLeft() + this.xPosOffset, this.gui.getGuiTop() + this.yPosOffset + (int) (this.levelOverlay.height * (1.0F - this.level_scaled)), this.gui.getGuiLeft() + this.xPosOffset + this.levelOverlay.width, this.gui.getGuiTop() + this.yPosOffset + this.levelOverlay.height, this.levelColor);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.levelOverlay.texture);
		this.gui.getGui().drawTexturedModalRect(this.gui.getGuiLeft() + this.xPosOffset, this.gui.getGuiTop() + this.yPosOffset, this.levelOverlay.startX, this.levelOverlay.startY, this.levelOverlay.width, this.levelOverlay.height);
	}

	public void setColor(int color) {
		this.levelColor = color;
	}

	@Override
	public void drawForeground(int mx, int my, float partialTicks) {
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
		if (this.gui.isInBox(mx, my, this.gui.getGuiLeft() + this.xPosOffset, this.gui.getGuiTop() + this.yPosOffset, this.levelOverlay.width, this.levelOverlay.height)) {
			int mxn = mx - this.gui.getGuiLeft();
			int myn = my - this.gui.getGuiTop();
			GuiUtils.drawHoveringText(this.tooltips.get(), mxn, myn, this.gui.getGuiSizeX(), this.gui.getGuiSizeY(), -1, this.gui.getFontRenderer());
		}
	}

	@Override
	public int getXSize() {
		return this.levelOverlay.width;
	}

	@Override
	public int getYSize() {
		return this.levelOverlay.height;
	}

	@Override
	public void read(NBTTagCompound nbt) {
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt) {
		return nbt;
	}

	protected float calcMultiplier(int capacity, int stored) {
		float c1 = capacity;
		float c2 = stored;
		return c2 / c1;
	}
}
