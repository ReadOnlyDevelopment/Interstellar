/*
 * (C) 2014-2018 Team CoFH / CoFH / Cult of the Full Hub
 * http://www.teamcofh.com
 */
package com.readonlydev.lib.energy;

import com.readonlydev.api.energy.IEnergyContainerItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Reference implementation of {@link IEnergyContainerItem}. Use/extend this or
 * implement your own.
 *
 * @author King Lemming
 */
public class ItemEnergyContainer extends Item implements IEnergyContainerItem {

	public static final String ENERGY = "Energy";

	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;

	public ItemEnergyContainer() {

	}

	public ItemEnergyContainer(int capacity) {

		this(capacity, capacity, capacity);
	}

	public ItemEnergyContainer(int capacity, int maxTransfer) {

		this(capacity, maxTransfer, maxTransfer);
	}

	public ItemEnergyContainer(int capacity, int maxReceive, int maxExtract) {

		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}

	public ItemEnergyContainer setCapacity(int capacity) {

		this.capacity = capacity;
		return this;
	}

	public ItemEnergyContainer setMaxTransfer(int maxTransfer) {

		setMaxReceive(maxTransfer);
		setMaxExtract(maxTransfer);
		return this;
	}

	public ItemEnergyContainer setMaxReceive(int maxReceive) {

		this.maxReceive = maxReceive;
		return this;
	}

	public ItemEnergyContainer setMaxExtract(int maxExtract) {

		this.maxExtract = maxExtract;
		return this;
	}

	/* IEnergyContainerItem */
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {

		if (!container.hasTagCompound()) {
			container.setTagCompound(new NBTTagCompound());
		}
		int stored = Math.min(container.getTagCompound().getInteger(ENERGY), getMaxEnergyStored(container));
		int energyReceived = Math.min(capacity - stored, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			stored += energyReceived;
			container.getTagCompound().setInteger(ENERGY, stored);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

		if (container.getTagCompound() == null || !container.getTagCompound().hasKey(ENERGY)) {
			return 0;
		}
		int stored = Math.min(container.getTagCompound().getInteger(ENERGY), getMaxEnergyStored(container));
		int energyExtracted = Math.min(stored, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			stored -= energyExtracted;
			container.getTagCompound().setInteger(ENERGY, stored);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {

		if (container.getTagCompound() == null || !container.getTagCompound().hasKey(ENERGY)) {
			return 0;
		}
		return Math.min(container.getTagCompound().getInteger(ENERGY), getMaxEnergyStored(container));
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {

		return capacity;
	}

}
