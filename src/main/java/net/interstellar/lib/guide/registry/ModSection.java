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

package net.interstellar.lib.guide.registry;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import net.interstellar.api.mod.IIDProvider;

public abstract class ModSection implements IIDProvider {

	protected Map<String, TileDataProvider> DATA = new HashMap<>();

	public void registerTileDataProvider(@Nonnull TileDataProvider tdp) {
		if (!hasTileDataProvider(tdp.getID())) {
			this.DATA.put(tdp.getID(), tdp);
		}
	}

	public boolean hasTileDataProvider(@Nonnull String tdpID) {
		return this.DATA.containsKey(tdpID);
	}

	public TileDataProvider getProvider(@Nonnull String tdpID) {
		return this.DATA.get(tdpID);
	}
}
