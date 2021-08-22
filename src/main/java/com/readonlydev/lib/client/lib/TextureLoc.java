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

package com.readonlydev.lib.client.lib;

import net.minecraft.util.ResourceLocation;

public class TextureLoc {

	public final int				startX;
	public final int				startY;
	public final int				endX;
	public final int				endY;
	public final int				width;
	public final int				height;
	public final boolean			repeatable;
	public final ResourceLocation	texture;
	private int						texWidth;
	private int						texHeight;

	public TextureLoc(ResourceLocation tex, int startX, int startY, int endX, int endY) {
		this(tex, startX, startY, endX, endY, true);
		this.texWidth = 256;
		this.texHeight = 256;
	}

	public TextureLoc(ResourceLocation tex, int startX, int startY, int endX, int endY, boolean repeatable) {
		this.texture = tex;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.width = this.endX - this.startX;
		this.height = this.endY - this.startY;
		this.repeatable = repeatable;
		this.texWidth = 256;
		this.texHeight = 256;
	}

	public void setTexSize(int w, int h) {
		this.texWidth = w;
		this.texHeight = h;
	}

	public int getTexW() {
		return this.texWidth;
	}

	public int getTexH() {
		return this.texHeight;
	}
}
