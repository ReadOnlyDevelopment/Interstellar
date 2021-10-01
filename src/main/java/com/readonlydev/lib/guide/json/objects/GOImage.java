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
import com.readonlydev.api.json.IJsonSerializer;
import com.readonlydev.lib.client.gui.element.container.SElementContainerBase;
import com.readonlydev.lib.client.gui.element.image.SElementImageScaled;
import com.readonlydev.lib.client.lib.TextureLoc;

import net.minecraft.util.ResourceLocation;

public class GOImage extends GuideObject {

	public static final GOImage	BLANK	= new GOImage();
	public int					xSize;
	public int					ySize;
	public String				location;
	public int					xStart;
	public int					yStart;
	public int					xEnd;
	public int					yEnd;

	@Override
	public String getType() {
		return "image";
	}

	@Override
	public String toString() {
		String str = "{" + this.xSize + ":" + this.ySize + ":" + this.location + "}";
		return str;
	}

	@Override
	public void initElement(String baseAddress, SElementContainerBase cont) {
		TextureLoc tex = new TextureLoc(new ResourceLocation(this.location), this.xStart, this.yStart, this.xEnd, this.yEnd);
		SElementImageScaled e = new SElementImageScaled(baseAddress + "." + cont.getTotalElements() + getType(), tex, 0, 0, this.xSize, this.ySize);
		cont.addElement(e);
	}

	public static class GOImageSerializer implements IJsonSerializer<GOImage> {

		@Override
		public GOImage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			GOImage r = new GOImage();
			JsonObject jobj = json.getAsJsonObject();
			JsonElement xs = jobj.get("xsize");
			if (xs != null) {
				r.xSize = xs.getAsInt();
			}
			JsonElement ys = jobj.get("ysize");
			if (ys != null) {
				r.ySize = ys.getAsInt();
			}
			JsonElement loc = jobj.get("location");
			if (loc != null) {
				r.location = loc.getAsString();
			}
			JsonElement xl = jobj.get("xstart");
			if (xl != null) {
				r.ySize = xl.getAsInt();
			}
			JsonElement yl = jobj.get("ystart");
			if (yl != null) {
				r.ySize = yl.getAsInt();
			}
			JsonElement xe = jobj.get("xend");
			if (xe != null) {
				r.ySize = xe.getAsInt();
			}
			JsonElement ye = jobj.get("yend");
			if (ye != null) {
				r.ySize = ye.getAsInt();
			}
			return r;
		}

		@Override
		public JsonElement serialize(GOImage src, Type typeOfSrc, JsonSerializationContext context) {

			JsonObject r = new JsonObject();
			r.addProperty("type", src.getType());
			r.addProperty("xsize", Integer.valueOf(src.xSize));
			r.addProperty("ysize", Integer.valueOf(src.ySize));
			r.addProperty("location", src.location);
			r.addProperty("xstart", Integer.valueOf(src.xStart));
			r.addProperty("ystart", Integer.valueOf(src.yStart));
			r.addProperty("xend", Integer.valueOf(src.xEnd));
			r.addProperty("yend", Integer.valueOf(src.yEnd));
			return r;
		}
	}
}
