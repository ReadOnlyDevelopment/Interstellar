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

package com.readonlydev.lib.client.gui.element.container.dynamic;

import com.readonlydev.lib.client.gui.element.SElement;
import com.readonlydev.lib.client.gui.element.container.SContainerDynamic;

public abstract class SContFixedXDynY extends SContainerDynamic {
  public SContFixedXDynY(String elementID, int xSize) {
    super(elementID);
    this.xSize = xSize;
  }
  
  protected boolean onElementAdded(SElement ele) {
    return true;
  }
  
  public void postInitPositioning() {}
  
  public void postPositioningContainerResize() {
    for (SElement e : this.elements) {
      if (getYPosActualLargest() < e.getYPosActualLargest())
        setYSize(e.getYPosOffsetLargest()); 
    } 
  }
}