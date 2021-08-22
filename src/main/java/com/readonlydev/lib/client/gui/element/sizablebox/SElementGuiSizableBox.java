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

public class SElementGuiSizableBox extends SElementSizableBox {

	public SElementGuiSizableBox(String elementID, SizableBox bg) {
		super(elementID, bg);
	}

	@Override
	public int getXPosActual() {
		return this.gui.getGuiLeft();
	}

	@Override
	public int getYPosActual() {
		return this.gui.getGuiTop();
	}

	@Override
	public int getRenderSizeX() {
		return this.gui.getGuiSizeX();
	}

	@Override
	public int getRenderSizeY() {
		return this.gui.getGuiSizeY();
	}

	@Override
	public int getXSize() {
		return 0;
	}

	@Override
	public int getYSize() {
		return 0;
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
	public void setXSize(int xSize) {
	}

	@Override
	public void setYSize(int ySize) {
	}
}
