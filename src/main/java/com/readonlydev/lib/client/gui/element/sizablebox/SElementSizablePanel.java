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

package com.readonlydev.lib.client.gui.element.sizablebox;

import com.readonlydev.lib.client.gui.element.SElementSizableBox;
import com.readonlydev.lib.client.lib.SizableBox;

public class SElementSizablePanel extends SElementSizableBox {

	public SElementSizablePanel(String elementID, SizableBox sb, int x, int y, int xSize, int ySize) {
		super(elementID, sb);
		this.xPosOffset = x;
		this.yPosOffset = y;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	@Override
	public int getXPosActual() {
		return this.gui.getGuiLeft() + this.xPosOffset;
	}

	@Override
	public int getYPosActual() {
		return this.gui.getGuiTop() + this.yPosOffset;
	}

	@Override
	public int getRenderSizeX() {
		return this.xSize;
	}

	@Override
	public int getRenderSizeY() {
		return this.ySize;
	}
}
