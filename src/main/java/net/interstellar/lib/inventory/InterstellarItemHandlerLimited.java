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

package net.interstellar.lib.inventory;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public abstract class InterstellarItemHandlerLimited extends ItemStackHandler {

	private boolean changedSinceLastCheck = false;

	private boolean extract = true;

	private boolean insert = true;

	public InterstellarItemHandlerLimited() {
		super(1);
	}

	public InterstellarItemHandlerLimited(int size) {
		super(size);
	}

	public InterstellarItemHandlerLimited(NonNullList<ItemStack> stacks) {
		super(stacks);
	}

	public abstract boolean isValidInput(ItemStack paramItemStack);

	public int getFreeSlots() {
		int fs = 0;
		for (ItemStack itemStack : this.stacks) {
			if (itemStack == ItemStack.EMPTY) {
				fs++;
			}
		}
		return fs;
	}

	@Override
	public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
		this.changedSinceLastCheck = true;
		super.setStackInSlot(slot, stack);
	}

	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		if (!this.insert) {
			return stack;
		}
		this.changedSinceLastCheck = true;
		if (isValidInput(stack)) {
			return super.insertItem(slot, stack, simulate);
		}
		return stack;
	}

	@Override
	@Nonnull
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (!this.extract) {
			return ItemStack.EMPTY;
		}
		this.changedSinceLastCheck = true;
		return super.extractItem(slot, amount, simulate);
	}

	public boolean hasChanged() {
		if (this.changedSinceLastCheck) {
			this.changedSinceLastCheck = false;
			return true;
		}
		return false;
	}

	@Override
	protected void onContentsChanged(int slot) {
		this.changedSinceLastCheck = true;
	}

	public void setIO(boolean insert, boolean extract) {
		this.extract = extract;
		this.insert = insert;
	}

	public ItemStack iInsert(int slot, @Nonnull ItemStack stack, boolean simulate) {
		return super.insertItem(slot, stack, simulate);
	}

	@Nonnull
	public ItemStack iExtract(int slot, int amount, boolean simulate) {
		return super.extractItem(slot, amount, simulate);
	}
}
