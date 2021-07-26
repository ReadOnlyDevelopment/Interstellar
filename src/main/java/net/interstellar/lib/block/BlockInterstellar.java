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

package net.interstellar.lib.block;

import net.interstellar.api.block.IState;
import net.interstellar.lib.block.property.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public abstract class BlockInterstellar extends Block implements IState {

	private boolean requiresToolTypeToHarvest;

	public BlockInterstellar(Material material) {
		super(material);
	}

	protected BlockInterstellar setProperties(Properties properties) {
		setHardness(properties.getHardness());
		setResistance(properties.getResistance());
		setSoundType(properties.getSoundType());
		setHarvestLevel(properties.getHarvestTool().getName(), properties.getHarvestLevel());
		this.requiresToolTypeToHarvest = properties.isRequiresToolType();
		return this;
	}

	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
		if (!requiresToolTypeToHarvest) {
			return false;
		}
		return super.canHarvestBlock(world, pos, player);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();
	}

	public IBlockState getStateFromItemStack(ItemStack itemStack) {
		return getStateFromMeta(itemStack.getItem().getMetadata(itemStack.getMetadata()));
	}

}
