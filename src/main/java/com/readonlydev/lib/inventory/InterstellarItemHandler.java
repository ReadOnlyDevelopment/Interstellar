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

package com.readonlydev.lib.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class InterstellarItemHandler extends ItemStackHandler {

	public InterstellarItemHandler() {
		super(1);
	}

	public InterstellarItemHandler(int size) {
		super(size);
	}

	public InterstellarItemHandler(NonNullList<ItemStack> stacks) {
		super(stacks);
	}

	public int getFreeSlots() {
		int fs = 0;
		for (ItemStack itemStack : this.stacks) {
			if (itemStack == ItemStack.EMPTY) {
				fs++;
			}
		}
		return fs;
	}
}
