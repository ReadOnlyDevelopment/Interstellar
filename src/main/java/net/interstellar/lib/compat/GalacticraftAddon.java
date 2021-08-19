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

package net.interstellar.lib.compat;

import java.util.List;
import java.util.Optional;

import net.interstellar.lib.version.Version;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public enum GalacticraftAddon {

	BLANKPLANET("blankplanet", "Blank Planet", RequiredLib.MJRLEGENDSLIB),
	EXTRAPLANETS("extraplanets", "Extra Planets", RequiredLib.MJRLEGENDSLIB),
	GALAXYSPACE("galaxyspace", "Galaxy Space", RequiredLib.ASMODEUSCORE),
	MOREPLANETSEXTRA("moreplanetsextras", "More Extra Planets", RequiredLib.MOREPLANETS),
	MOREPLANETS("moreplanets", "More Planets", RequiredLib.STEVEKUNGSLIB),
	PLANETPROGRESSION("planetprogression", "Planet Progression", RequiredLib.MJRLEGENDSLIB),
	ZOLLERNGALAXY("zollerngalaxy", "Zollern Galaxy", RequiredLib.NONE);

	private final String					modId;
	private final String					displayName;
	private final RequiredLib				requiredLib;
	private final boolean					isLoaded;
	private final List<ModContainer>		list	= Loader.instance().getModList();
	private final Optional<ModContainer>	modContainer;

	GalacticraftAddon(String modId, String displayName, RequiredLib requiredLib) {
		this.modId = modId;
		this.displayName = displayName;
		this.requiredLib = requiredLib;
		this.isLoaded = list.stream().anyMatch(modContainer -> modContainer.getModId().equals(this.modId));
		this.modContainer = list.stream().filter(m -> m.getModId().equals(this.modId)).findFirst();
	}

	public String modId() {

		return modId;
	}

	public String displayName() {
		return displayName;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public Version getVersion() {
		if (modContainer.isPresent()) {
			return new Version(modContainer.get().getVersion());
		}
		return null;
	}

	public RequiredLib getLibMod() {
		return requiredLib;
	}

	public String getLibModId() {
		return requiredLib.modId();
	}

	public boolean isLibModLoaded() {
		return requiredLib.equals(RequiredLib.NONE) ? false : requiredLib.isLoaded();
	}

	public Version getLibModVersion() {
		if (requiredLib.getLibMod().isPresent()) {
			return new Version(requiredLib.getLibMod().get().getVersion());
		}
		return null;
	}

	private enum RequiredLib {

		NONE("none"),
		ASMODEUSCORE("asmodeuscore"),
		MJRLEGENDSLIB("mjrlegendslib"),
		STEVEKUNGSLIB("stevekung's_lib"),
		// Only used for MorePlanetsExtraMod
		MOREPLANETS("moreplanets");

		private final String					modId;
		private final boolean					isLoaded;
		private final List<ModContainer>		list	= Loader.instance().getModList();
		private final Optional<ModContainer>	modContainer;

		RequiredLib(String modId) {
			this.modId = modId;
			this.isLoaded = list.stream().anyMatch(modContainer -> modContainer.getModId().equals(this.modId));
			this.modContainer = list.stream().filter(m -> m.getModId().equals(this.modId)).findFirst();
		}

		public String modId() {
			return modId;
		}

		public boolean isLoaded() {
			return isLoaded;
		}

		public Optional<ModContainer> getLibMod() {
			return modContainer;
		}
	}
}
