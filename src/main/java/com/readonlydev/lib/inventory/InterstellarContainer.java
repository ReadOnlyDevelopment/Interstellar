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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class InterstellarContainer extends Container {

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	public void addInventorySlots(InventoryPlayer invPlayer, int invOffsetX, int invOffsetY) {
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 9; k++) {
				addSlotToContainer(new Slot(invPlayer, k + i * 9 + 9, invOffsetX + k * 18, invOffsetY + i * 18));
			}
		}
		int hotbar = 58;
		for (int j = 0; j < 9; j++) {
			addSlotToContainer(new Slot(invPlayer, j, invOffsetX + j * 18, hotbar + invOffsetY));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot lSlot = this.inventorySlots.get(index);
		if (lSlot != null && lSlot.getHasStack()) {
			ItemStack stack2 = lSlot.getStack();
			stack = stack2.copy();
			if (lSlot instanceof InterstellarSlotOutput) {
				if (!mergeItemStack(stack2, this.inventorySlots.size() - 36, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
				lSlot.onSlotChange(stack2, stack);
			} else if (lSlot instanceof InterstellarSlot) {
				if (!mergeItemStack(stack2, this.inventorySlots.size() - 36, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (lSlot instanceof Slot && !mergeItemStack(stack2, 0, 1, false)) {
				return ItemStack.EMPTY;
			}
			if (stack2 != ItemStack.EMPTY && stack2.getCount() == 0) {
				lSlot.putStack(ItemStack.EMPTY);
			} else {
				lSlot.onSlotChanged();
			}
			if (stack2 != ItemStack.EMPTY && stack != ItemStack.EMPTY && stack2.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}
			lSlot.onTake(playerIn, stack2);
		}
		return stack;
	}
}
