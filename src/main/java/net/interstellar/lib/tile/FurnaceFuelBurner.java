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

package net.interstellar.lib.tile;

import lombok.Getter;
import net.interstellar.api.tile.IFuelConsumer;
import net.interstellar.lib.annotation.SyncVariable;
import net.interstellar.lib.utils.EnumUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeEventFactory;

public class FurnaceFuelBurner implements IFuelConsumer {

	private final BurnCondition	burnCondition;
	@Getter
	private int					timeRemaining;
	@Getter
	private int					currentItemMaxTime;

	public FurnaceFuelBurner(BurnCondition burnCondition) {
		this.burnCondition = burnCondition;
	}

	@Override
	public boolean feedFuel(ItemStack stack) {
		int value = ForgeEventFactory.getItemBurnTime(stack);
		if (value > 0) {
			timeRemaining = currentItemMaxTime = value;
			return true;
		}
		return false;
	}

	@Override
	public boolean hasFuel() {
		return timeRemaining > 0;
	}

	@Override
	public boolean tickFuel(boolean isProcessing) {
		if (burnCondition.shouldTick(this, isProcessing)) {
			--timeRemaining;
			return true;
		}
		return false;
	}

	private static FurnaceFuelBurner readFromNBT(NBTTagCompound tags) {
		BurnCondition condition = EnumUtils.fromString(BurnCondition.class, tags.getString("Condition")).orElse(BurnCondition.DEFAULT);

		FurnaceFuelBurner result = new FurnaceFuelBurner(condition);
		result.timeRemaining = tags.getInteger("Time");
		result.currentItemMaxTime = tags.getInteger("MaxTime");

		return result;
	}

	private static void writeToNBT(NBTTagCompound tags, FurnaceFuelBurner burner) {
		if (burner.burnCondition != BurnCondition.DEFAULT) {
			tags.setString("Condition", burner.burnCondition.name());
		}
		tags.setInteger("Time", burner.timeRemaining);
		tags.setInteger("MaxTime", burner.currentItemMaxTime);
	}

	static {
		SyncVariable.Helper.registerSerializer(FurnaceFuelBurner.class, FurnaceFuelBurner::readFromNBT, FurnaceFuelBurner::writeToNBT);
	}

	public enum BurnCondition {
		STANDARD {
			@Override
			boolean shouldTick(FurnaceFuelBurner burner, boolean processing) {
				return burner.timeRemaining > 0;
			}
		},
		ONLY_WHEN_PROCESSING {
			@Override
			boolean shouldTick(FurnaceFuelBurner burner, boolean processing) {
				return processing;
			}
		},
		ALWAYS {
			@Override
			boolean shouldTick(FurnaceFuelBurner burner, boolean processing) {
				return true;
			}
		};

		abstract boolean shouldTick(FurnaceFuelBurner burner, boolean processing);

		private static final BurnCondition DEFAULT = STANDARD;
	}
}
