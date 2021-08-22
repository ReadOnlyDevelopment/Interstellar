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

package com.readonlydev.lib.guide.client;

import com.readonlydev.api.mod.IIDProvider;
import com.readonlydev.lib.guide.client.element.Guide;

public abstract class GuideEntry implements IIDProvider, Comparable<GuideEntry> {

	Class<? extends Guide>	guideClass;
	protected int			modIndex	= 0;

	public GuideEntry(Class<? extends Guide> guideClass) {
		this.guideClass = guideClass;
	}

	public Guide getGuide() {
		try {
			return this.guideClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int compareTo(GuideEntry entry) {
		return getModId().compareToIgnoreCase(entry.getModId());
	}
}
