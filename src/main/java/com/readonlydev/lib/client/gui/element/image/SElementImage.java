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

package com.readonlydev.lib.client.gui.element.image;

import com.readonlydev.api.client.IGuiDraw;
import com.readonlydev.lib.client.gui.element.SElement;
import com.readonlydev.lib.client.lib.TexUtils;
import com.readonlydev.lib.client.lib.TextureLoc;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;

public class SElementImage extends SElement implements IGuiDraw {

	protected TextureLoc			image;
	protected SElement.XAlignment	xAlignment;
	protected SElement.YAlignment	yAlignment;

	public SElementImage(String elementID, TextureLoc image, int x, int y) {
		super(elementID);
		this.image = image;
		this.xPosOffset = x;
		this.yPosOffset = y;
		this.xAlignment = SElement.XAlignment.LEFT;
		this.yAlignment = SElement.YAlignment.TOP;
		this.xSize = image.width;
		this.ySize = image.height;
	}

	public SElementImage setYAxisAlignment(SElement.YAlignment alignment) {
		this.yAlignment = alignment;
		return this;
	}

	public SElementImage setXAxisAlignment(SElement.XAlignment alignment) {
		this.xAlignment = alignment;
		return this;
	}

	@Override
	public void init() {
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		// int xOffset = this.xAlignment.getOffset(this.image.width);
		// int yOffset = this.yAlignment.getOffset(this.image.height);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		TexUtils.renderTexLocation(this.image, this.gui, this.gui.getGuiLeft() + this.xPosOffset, this.gui.getGuiTop() + this.yPosOffset);
	}

	@Override
	public void drawForeground(int mx, int my, float partialTicks) {
	}

	public void setImage(TextureLoc tex) {
		this.image = tex;
		setXSize(this.image.width);
		setYSize(this.image.height);
	}

	@Override
	public int getXSize() {
		this.xSize = this.image.width;
		return this.image.width;
	}

	@Override
	public int getYSize() {
		this.ySize = this.image.height;
		return this.image.height;
	}

	@Override
	public void read(NBTTagCompound nbt) {
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt) {
		return nbt;
	}
}
