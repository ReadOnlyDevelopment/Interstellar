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

import java.util.List;

import javax.annotation.Nullable;

import com.readonlydev.api.block.IModelProvider;
import com.readonlydev.lib.utils.Keys;
import com.readonlydev.lib.utils.TextUtil;
import com.readonlydev.lib.utils.Keys.Key;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemInterstellar extends Item implements IModelProvider {

	public EnumRarity rarity = EnumRarity.COMMON;

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flag) {
		addTooltipInfo(stack, worldIn, tooltip, flag);
		if (hasShiftTooltipInfo()) {
			if (!Keys.isKeyPressed(Key.SHIFT)) {
				tooltip.add(TextUtil.SHIFTFORINFO);
			}
			if (Keys.isKeyPressed(Key.SHIFT)) {
				addShiftTooltipInfo(stack, worldIn, tooltip, flag);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean hasShiftTooltipInfo() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void addShiftTooltipInfo(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
	}

	@SideOnly(Side.CLIENT)
	public void addTooltipInfo(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
	}

	public void setRarity(EnumRarity rarity, int meta) {
		this.rarity = rarity;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		if (stack != ItemStack.EMPTY) {
			return getRarityFromMeta(stack.getMetadata());
		}
		return EnumRarity.COMMON;
	}

	public EnumRarity getRarityFromMeta(int meta) {
		return this.rarity;
	}

}
