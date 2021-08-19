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

import net.interstellar.lib.block.property.ToolTypes.ToolType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public final class Properties {

	private Material	material;
	private SoundType	soundType;
	private ToolType	harvestTool;
	private boolean		requiresToolType;
	private float		hardness;
	private float		resistance;
	private int			harvestLevel;

	private Properties(Builder builder) {
		this.material = builder.material;
		this.soundType = builder.soundType;
		this.harvestTool = builder.harvestTool;
		this.requiresToolType = builder.requiresToolType;
		this.hardness = builder.hardness;
		this.resistance = builder.resistance;
		this.harvestLevel = builder.harvestLevel;
	}

	public Material getMaterial() {
		return material;
	}

	public SoundType getSoundType() {
		return soundType;
	}

	public ToolType getHarvestTool() {
		return harvestTool;
	}

	public boolean isRequiresToolType() {
		return requiresToolType;
	}

	public float getHardness() {
		return hardness;
	}

	public float getResistance() {
		return resistance;
	}

	public int getHarvestLevel() {
		return harvestLevel;
	}

	/**
	 * Creates builder to build {@link Properties}.
	 *
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Properties}.
	 */
	public static final class Builder {
		private Material	material;
		private SoundType	soundType;
		private ToolType	harvestTool;
		private boolean		requiresToolType;
		private float		hardness;
		private float		resistance;
		private int			harvestLevel;

		private Builder() {
		}

		public Builder withMaterial(Material material) {
			this.material = material;
			return this;
		}

		public Builder withSoundType(SoundType soundType) {
			this.soundType = soundType;
			return this;
		}

		public Builder withHarvestTool(ToolType harvestTool) {
			this.harvestTool = harvestTool;
			return this;
		}

		public Builder withRequiresToolType(boolean requiresToolType) {
			this.requiresToolType = requiresToolType;
			return this;
		}

		public Builder withHardness(float hardness) {
			this.hardness = hardness;
			return this;
		}

		public Builder withResistance(float resistance) {
			this.resistance = resistance;
			return this;
		}

		public Builder withHarvestLevel(int harvestLevel) {
			this.harvestLevel = harvestLevel;
			return this;
		}

		public Properties build() {
			return new Properties(this);
		}
	}
}
