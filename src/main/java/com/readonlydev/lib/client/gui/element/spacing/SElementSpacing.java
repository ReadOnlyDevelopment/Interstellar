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

package com.readonlydev.lib.client.gui.element.spacing;

import com.readonlydev.lib.client.gui.element.SElement;

import net.minecraft.nbt.NBTTagCompound;

public class SElementSpacing extends SElement {
  public SElementSpacing(String elementID, int xSize, int ySize) {
    super(elementID);
    this.xSize = xSize;
    this.ySize = ySize;
  }
  
  public void init() {}
  
  public void read(NBTTagCompound nbt) {}
  
  public NBTTagCompound write(NBTTagCompound nbt) {
    return nbt;
  }
}
