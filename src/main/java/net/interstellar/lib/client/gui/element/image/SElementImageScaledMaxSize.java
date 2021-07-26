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

package net.interstellar.lib.client.gui.element.image;

import net.interstellar.lib.client.lib.TextureLoc;

public class SElementImageScaledMaxSize extends SElementImageScaled {
	int maxSizeX;

	int maxSizeY;

	public SElementImageScaledMaxSize(String elementID, TextureLoc image, int x, int y, int maxSizeX, int maxSizeY) {
		super(elementID, image, x, y, image.width, image.height);
		setMaxSize(maxSizeX, maxSizeY);
	}

	public void setMaxSize(int maxSizeX, int maxSizeY) {
		this.maxSizeX = maxSizeX;
		this.maxSizeY = maxSizeY;
		float ra = maxSizeX / this.image.width;
		float rb = maxSizeY / this.image.height;
		float rc = Math.min(ra, rb);
		this.xSize = (int) (this.image.width * rc);
		this.ySize = (int) (this.image.height * rc);
	}

	public void setImage(TextureLoc tex) {
		this.image = tex;
	}
}
