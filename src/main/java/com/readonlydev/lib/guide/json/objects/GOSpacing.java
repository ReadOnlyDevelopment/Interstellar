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

package com.readonlydev.lib.guide.json.objects;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.readonlydev.lib.client.gui.element.container.SElementContainerBase;
import com.readonlydev.lib.client.gui.element.spacing.SElementSpacing;
import com.readonlydev.lib.utils.json.IJsonSerializer;

public class GOSpacing extends GuideObject {

	public static final GOSpacing	BLANK	= new GOSpacing();
	public int						xSize;
	public int						ySize;

	@Override
	public String getType() {
		return "spacing";
	}

	@Override
	public void initElement(String baseAddress, SElementContainerBase cont) {
		SElementSpacing e = new SElementSpacing(baseAddress + "." + cont.getTotalElements() + getType(), this.xSize, this.ySize);
		cont.addElement(e);
	}

	public static class GOSpacingSerializer implements IJsonSerializer<GOSpacing> {
		@Override
		public GOSpacing deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			GOSpacing r = new GOSpacing();
			JsonObject jobj = json.getAsJsonObject();
			JsonElement cw = jobj.get("xsize");
			if (cw != null) {
				r.xSize = cw.getAsInt();
			}
			JsonElement ch = jobj.get("ysize");
			if (ch != null) {
				r.ySize = ch.getAsInt();
			}
			return r;
		}

		@Override
		public JsonElement serialize(GOSpacing src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject r = new JsonObject();
			r.addProperty("type", src.getType());
			r.addProperty("xsize", Integer.valueOf(src.xSize));
			r.addProperty("ysize", Integer.valueOf(src.ySize));
			return r;
		}
	}
}
