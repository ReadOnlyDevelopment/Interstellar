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

package net.interstellar.lib.client.gui.element.container.list;

import net.interstellar.lib.client.gui.element.SElement;
import net.interstellar.lib.client.gui.element.container.SContainerDynamic;

public abstract class SContListH extends SContainerDynamic {

	public SContListH(String elementID) {
		super(elementID);
	}

	@Override
	public void postInitPositioning() {
		int xS = 0;
		for (SElement vlElement : this.elements) {
			vlElement.setXPosOffset(xS);
			xS = vlElement.getXPosOffsetLargest();
		}
	}

	@Override
	protected boolean onElementAdded(SElement ele) {
		return true;
	}
}
