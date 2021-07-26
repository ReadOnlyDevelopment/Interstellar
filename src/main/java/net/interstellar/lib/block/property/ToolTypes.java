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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import net.minecraft.util.IStringSerializable;

public final class ToolTypes {

	private static final Pattern				VALID_NAME		= Pattern.compile("[^a-z_]");
	private static final Map<String, ToolType>	TOOLTYPE_MAP	= new ConcurrentHashMap<>();

	private static final ToolType	NULL	= create("null");
	public static final ToolType	AXE		= create("axe");
	public static final ToolType	HOE		= create("hoe");
	public static final ToolType	PICKAXE	= create("pickaxe");
	public static final ToolType	SHOVEL	= create("shovel");

	private static ToolType create(final String name) {
		return TOOLTYPE_MAP.computeIfAbsent(name, k -> {
			if (VALID_NAME.matcher(name).find()) {
				throw new IllegalArgumentException("ToolType.get() called with invalid name: " + name);
			}
			return new ToolType(name);
		});
	}

	public static ToolType get(final String name) {
		return TOOLTYPE_MAP.containsKey(name) ? TOOLTYPE_MAP.get(name) : ToolTypes.NULL;
	}

	public static final class ToolType implements IStringSerializable {
		private final String toolName;

		ToolType(String toolName) {
			this.toolName = toolName;
		}

		@Override
		public String getName() {
			return toolName;
		}
	}
}
