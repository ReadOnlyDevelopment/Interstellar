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

package com.readonlydev.lib.item;

import com.readonlydev.lib.block.BlockSubtype;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemBlockSubtype extends ItemBlock {
	private final int subtypeCount;

	public ItemBlockSubtype(BlockSubtype block) {
		this(block, block.getSubtypeCount());
	}

	public ItemBlockSubtype(Block block, int subtypeCount) {
		super(block);
		this.subtypeCount = subtypeCount;
		setMaxDamage(0);
		setHasSubtypes(subtypeCount > 1);
	}

	@Override
	public int getMetadata(int damage) {
		return damage & 0xF;
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return super.getTranslationKey() + getMetadata(stack.getItemDamage());
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(tab)) {
			return;
		}
		for (int i = 0; i < this.subtypeCount; ++i) {
			items.add(new ItemStack(this, 1, i));
		}
	}
}
