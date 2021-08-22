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

package com.readonlydev.lib.utils;

import java.util.Collection;

import com.google.common.collect.ImmutableList;
import com.readonlydev.lib.math.MathUtil;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryUtil {
	public static boolean canItemsStack(ItemStack a, ItemStack b) {
		if (a.isEmpty() || !a.isItemEqual(b) || a.hasTagCompound() != b.hasTagCompound()) {
			return false;
		}
		// noinspection ConstantConditions
		return (!a.hasTagCompound() || a.getTagCompound().equals(b.getTagCompound())) && a.areCapsCompatible(b);
	}

	public static ItemStack mergeItem(IInventory inventory, int slotStart, int slotEndExclusive, ItemStack stack) {
		if (inventory == null || stack.isEmpty()) {
			return stack;
		}

		for (int i = slotStart; i < slotEndExclusive && !stack.isEmpty(); ++i) {
			ItemStack inSlot = inventory.getStackInSlot(i);
			if (canItemsStack(inSlot, stack)) {
				int amountCanFit = MathUtil.min(inSlot.getMaxStackSize() - inSlot.getCount(), stack.getCount(), inventory.getInventoryStackLimit());
				inSlot.grow(amountCanFit);
				stack.shrink(amountCanFit);
				inventory.setInventorySlotContents(i, inSlot);
			}
		}

		for (int i = slotStart; i < slotEndExclusive && !stack.isEmpty(); ++i) {
			if (inventory.getStackInSlot(i).isEmpty()) {
				int amountCanFit = MathUtil.min(stack.getCount(), inventory.getInventoryStackLimit());
				ItemStack toInsert = stack.copy();
				toInsert.setCount(amountCanFit);
				stack.shrink(amountCanFit);
				inventory.setInventorySlotContents(i, toInsert);
			}
		}

		return stack;
	}

	public static Collection<ItemStack> mergeItems(IInventory inventory, int slotStart, int slotEndExclusive, Collection<ItemStack> stacks) {
		if (inventory == null && stacks.isEmpty()) {
			return ImmutableList.of();
		}

		ImmutableList.Builder<ItemStack> leftovers = ImmutableList.builder();

		for (ItemStack stack : stacks) {
			stack = mergeItem(inventory, slotStart, slotEndExclusive, stack);

			// Failed to merge?
			if (!stack.isEmpty()) {
				leftovers.add(stack);
			}
		}

		return leftovers.build();
	}
}
