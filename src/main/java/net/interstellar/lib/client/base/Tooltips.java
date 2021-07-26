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

package net.interstellar.lib.client.base;

import java.util.ArrayList;
import java.util.List;

public class Tooltips {

	protected List<String> localizedTooltips;

	public Tooltips() {
		this.localizedTooltips = new ArrayList<>();
	}

	public Tooltips(List<String> toolTips) {
		if (toolTips == null) {
			this.localizedTooltips = new ArrayList<>();
		} else {
			this.localizedTooltips = toolTips;
		}
	}

	public void add(String localizedToolTip) {
		if (this.localizedTooltips == null) {
			this.localizedTooltips = new ArrayList<>();
		}
		this.localizedTooltips.add(localizedToolTip);
	}

	public void set(List<String> tooltips) {
		this.localizedTooltips = tooltips;
	}

	public List<String> get() {
		return this.localizedTooltips;
	}
}
