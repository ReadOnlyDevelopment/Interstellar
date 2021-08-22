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

package com.readonlydev.lib.guide.registry;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class ModSectionRegistry {
	private static ModSectionRegistry instance;

	public static ModSectionRegistry getInstance() {
		if (instance == null) {
			instance = new ModSectionRegistry();
		}
		return instance;
	}

	private Map<String, ModSection> DATA = new HashMap<>();

	public void registerSection(@Nonnull ModSection mod) {
		if (!hasSection(mod.getModId())) {
			this.DATA.put(mod.getModId(), mod);
		}
	}

	public boolean hasSection(@Nonnull String modID) {
		return this.DATA.containsKey(modID);
	}

	public ModSection getModSection(@Nonnull String modID) {
		return this.DATA.get(modID);
	}
}
