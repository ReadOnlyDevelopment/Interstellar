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

import net.interstellar.lib.math.MathUtil;

public class GridAlignment {

	protected final int	boxWidth;
	protected final int	boxHeight;
	protected final int	xSpace;
	protected final int	ySpace;
	protected int		alignedLimit;
	protected int		space;
	protected Alignment	aligned;
	protected int		curRow	= 0;
	protected int		curCol	= 0;

	public GridAlignment(int boxWidth, int boxHeight, int xSpace, int ySpace, int alignedLimit, Alignment a) {
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		this.xSpace = xSpace;
		this.ySpace = ySpace;
		this.aligned = a;
		this.alignedLimit = alignedLimit;
	}

	public int getCurrentRow() {
		return this.curRow;
	}

	public int getCurrentCol() {
		return this.curCol;
	}

	public void next() {
		if (this.aligned == Alignment.VERTICAL) {
			if (this.curCol < this.alignedLimit - 1) {
				this.curCol++;
			} else {
				this.curCol = 0;
				this.curRow++;
			}
		} else if (this.curRow < this.alignedLimit - 1) {
			this.curRow++;
		} else {
			this.curRow = 0;
			this.curCol++;
		}
	}

	public int getXOffset(int col) {
		int s = MathUtil.clamp(col, 0, 2147483647) * this.xSpace;
		int x = MathUtil.clamp(col, 0, 2147483647) * this.boxWidth;
		return x + s;
	}

	public int getYOffset(int row) {
		int s = MathUtil.clamp(row, 0, 2147483647) * this.ySpace;
		int y = MathUtil.clamp(row, 0, 2147483647) * this.boxHeight;
		return y + s;
	}

	public int getCurrentXOffset() {
		return getXOffset(this.curCol);
	}

	public int getCurrentYOffset() {
		return getYOffset(this.curRow);
	}

	public enum Alignment {
		HORIZONTAL,
		VERTICAL;
	}
}
