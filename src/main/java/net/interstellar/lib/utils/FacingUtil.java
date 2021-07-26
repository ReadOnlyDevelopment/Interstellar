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

package net.interstellar.lib.utils;

import net.interstellar.lib.block.element.DirectionlElement;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;

public class FacingUtil {
	/**
	 * Gets the rotation count for the facing.
	 *
	 * @param facing the facing
	 * @return the rotation count
	 */
	public static int getRotationCount(EnumFacing facing) {
		if (facing == null) {
			return 0;
		}

		switch (facing) {
			case EAST:
				return 1;
			case NORTH:
				return 2;
			case WEST:
				return 3;
			case SOUTH:
			default:
				return 0;
		}
	}

	/**
	 * Gets the rotation count for the {@link IBlockState}
	 *
	 * @param state the state
	 * @return the rotation count
	 */
	public static int getRotationCount(IBlockState state) {
		EnumFacing direction = DirectionlElement.getDirection(state);
		return FacingUtil.getRotationCount(direction);
	}

	/**
	 * Rotates facing {@code count} times.
	 *
	 * @param facing the facing
	 * @param count  the count
	 * @return the enum facing
	 */
	public static EnumFacing rotateFacing(EnumFacing facing, int count) {
		if (facing == null) {
			return null;
		}

		while (count-- > 0) {
			facing = facing.rotateAround(EnumFacing.Axis.Y);
		}
		return facing;
	}

	/**
	 * Gets the real side of a rotated block.
	 *
	 * @param state the state
	 * @param side  the side
	 * @return the real side
	 */
	public static EnumFacing getRealSide(IBlockState state, EnumFacing side) {
		if (state == null || side == null) {
			return side;
		}

		EnumFacing direction = DirectionlElement.getDirection(state);
		if (direction == EnumFacing.SOUTH) {
			return side;
		}

		if (direction == EnumFacing.DOWN) {
			return side.rotateAround(Axis.X);
		} else if (direction == EnumFacing.UP) {
			switch (side) {
				case UP:
					return EnumFacing.SOUTH;
				case DOWN:
					return EnumFacing.NORTH;
				case NORTH:
					return EnumFacing.UP;
				case SOUTH:
					return EnumFacing.DOWN;
				default:
					return side;
			}
		}

		int count = FacingUtil.getRotationCount(direction);
		side = FacingUtil.rotateFacing(side, count);

		return side;
	}
}
