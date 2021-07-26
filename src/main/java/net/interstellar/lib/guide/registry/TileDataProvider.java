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

public class TileDataProvider {
  protected final String id;
  
  protected Map<String, TileData> DATA = new HashMap<>();
  
  public TileDataProvider(String id) {
    this.id = id;
  }
  
  public String getID() {
    return this.id;
  }
  
  public void registerData(@Nonnull TileData td) {
    if (!hasData(td.getID()))
      this.DATA.put(td.getID(), td); 
  }
  
  public boolean hasData(@Nonnull String dataID) {
    return this.DATA.containsKey(dataID);
  }
  
  public TileData getData(@Nonnull String dataID) {
    return this.DATA.get(dataID);
  }
}
