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

import com.readonlydev.lib.client.lib.TexUtils;
import com.readonlydev.lib.client.lib.TextureLoc;

import net.minecraft.client.renderer.GlStateManager;

public class SElementImageScaled extends SElementImage {

	public SElementImageScaled(String elementID, TextureLoc image, int x, int y, int xSize, int ySize) {
		super(elementID, image, x, y);
		this.xSize = xSize;
		this.ySize = ySize;
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		int xOffset = this.xAlignment.getOffset(getXSize());
		int yOffset = this.yAlignment.getOffset(getYSize());
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		TexUtils.renderTexLocationScaled(this.image, this.gui, this.gui.getGuiLeft() + this.xPosOffset + xOffset, this.gui.getGuiTop() + this.yPosOffset + yOffset, getXSize(), getYSize());
	}

	@Override
	public int getXSize() {
		return this.xSize;
	}

	@Override
	public int getYSize() {
		return this.ySize;
	}
}
