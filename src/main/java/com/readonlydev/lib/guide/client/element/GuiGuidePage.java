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

package com.readonlydev.lib.guide.client.element;

import com.readonlydev.lib.client.gui.element.SElementIndex;
import com.readonlydev.lib.client.gui.element.container.list.SContListVFixedX;

public abstract class GuiGuidePage extends SContListVFixedX {

	public SElementIndex index;

	public GuiGuidePage(String elementID, int xSize) {
		super(elementID, xSize);
	}

	protected void preInit() {
		setXPosOffset(4);
		setYPosOffset(4);
	}
}
