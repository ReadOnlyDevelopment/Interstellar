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

package net.interstellar.lib.client.gui.element;

import javax.annotation.Nonnull;

import net.interstellar.api.client.IGuiData;
import net.interstellar.api.client.IGuiPos;
import net.interstellar.lib.client.gui.InterstellarGui;

public abstract class SElement implements IGuiPos, IGuiData {

	protected InterstellarGui		gui;
	protected String	elementID;
	protected boolean	init		= false;
	protected int		xPosOffset	= 0;
	protected int		yPosOffset	= 0;
	protected int		xSize		= 0;
	protected int		ySize		= 0;

	public SElement(String elementID) {
		this.elementID = elementID;
	}

	public void setGui(InterstellarGui gui) {
		this.gui = gui;
		if (!this.init) {
			this.init = true;
			init();
		}
	}

	public abstract void init();

	public String getID() {
		return this.elementID;
	}

	@Override
	public int getXSize() {
		return this.xSize;
	}

	@Override
	public int getYSize() {
		return this.ySize;
	}

	@Override
	public void setXSize(int xSize) {
		this.xSize = xSize;
		this.gui.onElementResize(this);
	}

	@Override
	public void setYSize(int ySize) {
		this.ySize = ySize;
		this.gui.onElementResize(this);
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
	public int getXPosOffset() {
		return this.xPosOffset;
	}

	@Override
	public int getYPosOffset() {
		return this.yPosOffset;
	}

	@Override
	public void setXPosOffset(int xPos) {
		this.xPosOffset = xPos;
		this.gui.onElementResize(this);
	}

	@Override
	public void setYPosOffset(int yPos) {
		this.yPosOffset = yPos;
		this.gui.onElementResize(this);
	}

	@Nonnull
	public enum XAlignment {
		LEFT("left"),
		CENTER("center"),
		RIGHT("right");

		final String name;

		XAlignment(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		@Nonnull
		public static XAlignment getFromName(@Nonnull String name) {
			if (name == "left") {
				return LEFT;
			}
			if (name == "center") {
				return CENTER;
			}
			return RIGHT;
		}

		public int getOffset(int objectWidth) {
			switch (this) {
				case LEFT:
					return 0;
				case CENTER:
					return -(objectWidth / 2);
				case RIGHT:
					return -objectWidth;
			}
			return 0;
		}

		@Nonnull
		public static XAlignment fromString(@Nonnull String str) {
			if (str.equalsIgnoreCase("center")) {
				return CENTER;
			}
			if (str.equalsIgnoreCase("right")) {
				return RIGHT;
			}
			return LEFT;
		}
	}

	public enum YAlignment {
		TOP("top"),
		CENTER("center"),
		BOTTOM("bottom");

		final String name;

		YAlignment(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public int getOffset(int objectHeight) {
			switch (this) {
				case TOP:
					return 0;
				case CENTER:
					return -(objectHeight / 2);
				case BOTTOM:
					return -objectHeight;
			}
			return 0;
		}

		public static YAlignment fromString(String str) {
			if (str.equalsIgnoreCase("center")) {
				return CENTER;
			}
			if (str.equalsIgnoreCase("bottom")) {
				return BOTTOM;
			}
			return TOP;
		}
	}

	public enum Justified {
		LEFT,
		CENTER,
		RIGHT;

		public int getOffset(int objectWidth) {
			switch (this) {
				case LEFT:
					return 0;
				case CENTER:
					return -(objectWidth / 2);
				case RIGHT:
					return -objectWidth;
			}
			return 0;
		}
	}
}
