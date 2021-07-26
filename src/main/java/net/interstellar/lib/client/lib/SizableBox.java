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

/**
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

package net.interstellar.lib.client.lib;

import net.minecraft.util.ResourceLocation;

public class SizableBox {

	private final ResourceLocation	texture;
	private TextureLoc[][]			textureLocations;
	private int						unitXSize;
	private int						unitYSize;
	private boolean					middleRepeatable;

	public SizableBox(ResourceLocation tex, int unitXSize, int unitYSize) {
		this.texture = tex;
		this.textureLocations = new TextureLoc[3][3];
		this.unitXSize = unitXSize;
		this.unitYSize = unitYSize;
	}

	public SizableBox(ResourceLocation tex, int unitXSize, int unitYSize, int texWidth, int texHeight) {
		this.texture = tex;
		this.textureLocations = new TextureLoc[3][3];
		this.unitXSize = unitXSize;
		this.unitYSize = unitYSize;
	}

	public void setTextureLoc(BoxLocation loc, TextureLoc tex) {
		this.textureLocations[loc.y][loc.x] = tex;
	}

	public TextureLoc getTextureLoc(BoxLocation loc) {
		return this.textureLocations[loc.y][loc.x];
	}

	public ResourceLocation getTexture() {
		return this.texture;
	}

	public int getUnitX() {
		return this.unitXSize;
	}

	public int getUnitY() {
		return this.unitYSize;
	}

	public boolean getMiddleRepeatable() {
		return this.middleRepeatable;
	}

	public enum BoxLocation {
		TOP_LEFT(0, 0),
		TOP_MID(0, 1),
		TOP_RIGHT(0, 2),
		MID_LEFT(1, 0),
		MID(1, 1),
		MID_RIGHT(1, 2),
		BOT_LEFT(2, 0),
		BOT_MID(2, 1),
		BOT_RIGHT(2, 2);

		public int x;

		public int y;

		BoxLocation(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
