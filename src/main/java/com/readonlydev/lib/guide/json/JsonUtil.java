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

package com.readonlydev.lib.guide.json;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

public class JsonUtil {

	public static final String	OD			= "OD";
	public static final String	OD_ALL		= "OD_ALL";
	public static final String	OD_PREFIX	= "OD_PREFIX";

	public static String getOre(String id, String type) {
		if (!StringUtils.isNullOrEmpty(id)) {
			String[] split = id.split("[:@]+");
			if (split.length > 1 && split[0].equals(type)) {
				return split[1];
			}
		}
		return null;
	}

	public static ItemStack getFromString(String id) {
		if (!StringUtils.isNullOrEmpty(id)) {
			String[] split = id.split("[:@]+");
			int meta = 0;
			if (split.length > 2) {
				try {
					meta = Integer.parseInt(split[2]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			if (split.length < 2) {
				return ItemStack.EMPTY;
			}
			ResourceLocation res = new ResourceLocation(split[0], split[1]);
			Item itm = (Item) Item.REGISTRY.getObject(res);
			if (itm != null) {
				return new ItemStack(itm, 1, meta);
			}
			Block blk = (Block) Block.REGISTRY.getObject(res);
			if (blk != null || blk != Blocks.AIR) {
				return new ItemStack(blk, 1, meta);
			}
		}
		return ItemStack.EMPTY;
	}
}
