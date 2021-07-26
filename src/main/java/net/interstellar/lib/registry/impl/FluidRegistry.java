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

package net.interstellar.lib.registry.impl;

import java.util.function.Consumer;

import net.interstellar.lib.registry.InterstellarRegistry;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidBlock;

public abstract class FluidRegistry {

	protected static String modid;

	public abstract void register(InterstellarRegistry registry);

	protected static <T extends Block & IFluidBlock> Fluid create(String name, boolean hasFlowIcon, boolean hasOverlay, Consumer<Fluid> fluidPropertyApplier) {
		String texturePrefix = modid + ":" + "blocks/fluids/";
		ResourceLocation still = new ResourceLocation(texturePrefix + name + "_still");
		ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(texturePrefix + name + "_flow") : still;
		ResourceLocation overlay = hasOverlay ? new ResourceLocation(texturePrefix + name + "_overlay") : null;
		Fluid fluid = new Fluid(name, still, flowing, overlay);
		fluidPropertyApplier.accept(fluid);
		return fluid;
	}
}
