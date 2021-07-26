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

package net.interstellar.lib.block.property;

import lombok.Builder;
import lombok.Getter;
import net.interstellar.lib.block.property.ToolTypes.ToolType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

@Builder
@Getter
public final class Properties {

	private Material	material;
	private SoundType	soundType;
	private ToolType	harvestTool;
	private boolean		requiresToolType;
	private float		hardness;
	private float		resistance;
	private int			harvestLevel;
}
