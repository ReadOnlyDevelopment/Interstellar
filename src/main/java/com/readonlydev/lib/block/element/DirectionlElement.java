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

package com.readonlydev.lib.block.element;

import com.readonlydev.api.block.element.IBlockElement;
import com.readonlydev.api.block.element.IElement;
import com.readonlydev.lib.utils.EntityUtil;
import com.readonlydev.lib.utils.FacingUtil;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DirectionlElement implements IBlockElement {
	public static final PropertyDirection	HORIZONTAL	= PropertyDirection.create("direction", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyDirection	ALL			= PropertyDirection.create("direction");

	private IPlacement			placement	= IPlacement.PLACER;
	private PropertyDirection	property	= HORIZONTAL;

	/**
	 * Instantiates a new {@link DirectionlElement}.
	 *
	 * @param property  the property
	 * @param placement the placement
	 */
	public DirectionlElement(PropertyDirection property, IPlacement placement) {
		this.property = property;
		this.placement = placement;
	}

	/**
	 * Instantiates a new {@link DirectionlElement} with {@link #HORIZONTAL} property and {@link IPlacement#PLACER} by default.
	 */
	public DirectionlElement() {
		this(HORIZONTAL, IPlacement.PLACER);
	}

	/**
	 * Instantiates a new {@link DirectionlElement} with specified property and {@link IPlacement#PLACER} by default.
	 *
	 * @param property the property
	 */
	public DirectionlElement(PropertyDirection property) {
		this(property, IPlacement.PLACER);
	}

	/**
	 * Instantiates a new {@link DirectionlElement} with specified placement and {@link #HORIZONTAL} property by default.
	 *
	 * @param placement the placement
	 */
	public DirectionlElement(IPlacement placement) {
		this(HORIZONTAL, placement);
	}

	/**
	 * Gets the property direction to use for this {@link DirectionlElement}.
	 *
	 * @return the property direction
	 */
	@Override
	public PropertyDirection getProperty() {
		return property;
	}

	/**
	 * Sets the default value to use for the {@link IBlockState}.
	 *
	 * @param block the block
	 * @param state the state
	 * @return the i block state
	 */
	@Override
	public IBlockState setDefaultState(Block block, IBlockState state) {
		return state.withProperty(getProperty(), EnumFacing.SOUTH);
	}

	public IBlockState placedState(IBlockState state, EnumFacing facing, EntityLivingBase placer) {
		return state.withProperty(getProperty(), placement.getPlacement(state, facing, placer));
	}

	/**
	 * Automatically gets the right {@link IBlockState} based on the <code>placer</code> facing.
	 *
	 * @param block  the block
	 * @param world  the world
	 * @param pos    the pos
	 * @param facing the facing
	 * @param hitX   the hit x
	 * @param hitY   the hit y
	 * @param hitZ   the hit z
	 * @param meta   the meta
	 * @param placer the placer
	 * @return the i block state
	 */
	@Override
	public IBlockState getStateForPlacement(Block block, World world, BlockPos pos, IBlockState state, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return placedState(state, facing, placer);
	}

	/**
	 * Gets the {@link IBlockState} from <code>meta</code>.
	 *
	 * @param block the block
	 * @param meta  the meta
	 * @return the state from meta
	 */
	@Override
	public IBlockState getStateFromMeta(Block block, IBlockState state, int meta) {
		EnumFacing facing = null;
		if (getProperty() == HORIZONTAL) {
			facing = EnumFacing.getHorizontal(meta & 3);
		} else {
			facing = EnumFacing.getFront(meta & 7);
		}
		return state.withProperty(getProperty(), facing);
	}

	/**
	 * Gets the metadata from the {@link IBlockState}.
	 *
	 * @param block the block
	 * @param state the state
	 * @return the meta from state
	 */
	@Override
	public int getMetaFromState(Block block, IBlockState state) {
		if (getProperty() == HORIZONTAL) {
			return getDirection(state).getHorizontalIndex();
		} else {
			return getDirection(state).getIndex();
		}
	}

	/**
	 * Gets the {@link EnumFacing direction} for the {@link Block} at world coords.
	 *
	 * @param world the world
	 * @param pos   the pos
	 * @return the EnumFacing, null if the block is not {@link DirectionlElement}
	 */
	public static EnumFacing getDirection(IBlockAccess world, BlockPos pos) {
		return world != null && pos != null ? getDirection(world.getBlockState(pos)) : EnumFacing.SOUTH;
	}

	/**
	 * Gets the {@link EnumFacing direction} for the {@link IBlockState}
	 *
	 * @param state the state
	 * @return the EnumFacing, null if the block is not {@link DirectionlElement}
	 */
	public static EnumFacing getDirection(IBlockState state) {
		DirectionlElement dc = IElement.getElement(DirectionlElement.class, state.getBlock());
		if (dc == null) {
			return EnumFacing.SOUTH;
		}

		PropertyDirection property = dc.getProperty();
		if (property == null || !state.getProperties().containsKey(property)) {
			return EnumFacing.SOUTH;
		}

		return state.getValue(property);
	}

	/**
	 * Gets the {@link PropertyDirection} used by the block.
	 *
	 * @param block the block
	 * @return the property
	 */
	public static PropertyDirection getProperty(Block block) {
		DirectionlElement dc = IElement.getElement(DirectionlElement.class, block);
		return dc != null ? dc.getProperty() : null;
	}

	/**
	 * Rotates the {@link IBlockState} by 90 degrees counter-clockwise.
	 *
	 * @param state the state
	 * @return the i block state
	 */
	public static IBlockState rotate(IBlockState state) {
		return rotate(state, 1);
	}

	/**
	 * Rotates the {@link IBlockState} by a factor of 90 degrees counter-clockwise.
	 *
	 * @param state the state
	 * @param angle the angle
	 * @return the i block state
	 */
	public static IBlockState rotate(IBlockState state, int angle) {
		int a = -angle & 3;
		if (a == 0) {
			return state;
		}

		PropertyDirection property = DirectionlElement.getProperty(state.getBlock());
		if (property == null || !state.getProperties().containsKey(property)) {
			return state;
		}

		return state.withProperty(property, FacingUtil.rotateFacing(state.getValue(property), a));
	}

	public static IBlockState getPlacedState(IBlockState state, EnumFacing facing, EntityLivingBase placer) {
		DirectionlElement dc = IElement.getElement(DirectionlElement.class, state.getBlock());
		return dc != null ? dc.placedState(state, facing, placer) : state;
	}

	public static interface IPlacement {
		/** Direction is determined by the side of the block clicked */
		public static final IPlacement	BLOCKSIDE	= (state, side, placer) -> side;
		/** Direction is determined by the facing of the entity placing the block. */
		public static final IPlacement	PLACER		= (state, side, placer) -> {
														EnumFacing facing = EntityUtil.getEntityFacing(placer, DirectionlElement.getProperty(state.getBlock()) == ALL);
														return placer.isSneaking() ? facing : facing.getOpposite();
													};

		public EnumFacing getPlacement(IBlockState state, EnumFacing side, EntityLivingBase placer);
	}
}
