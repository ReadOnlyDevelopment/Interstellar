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

package com.readonlydev.lib.block.fluid;

import javax.annotation.Nullable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

@Builder(toBuilder = true, access = AccessLevel.PUBLIC)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FluidImpl {

	@Builder.Default
	private int					luminosity	= 0;
	@Builder.Default
	private int					density		= 1000;
	@Builder.Default
	private int					temperature	= 300;
	@Builder.Default
	private int					viscosity	= 1000;
	private boolean				isGaseous;
	@Builder.Default
	private EnumRarity			rarity		= EnumRarity.COMMON;
	private ResourceLocation	still;
	private ResourceLocation	flowing;
	@Nullable
	private ResourceLocation	overlay;
	@Builder.Default
	private SoundEvent			fillSound	= SoundEvents.ITEM_BUCKET_FILL;
	@Builder.Default
	private SoundEvent			emptySound	= SoundEvents.ITEM_BUCKET_EMPTY;

}
