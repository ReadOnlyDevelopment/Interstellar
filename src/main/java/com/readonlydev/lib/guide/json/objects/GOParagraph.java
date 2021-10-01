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
import com.readonlydev.lib.client.gui.element.text.STextParagraph;
import com.readonlydev.lib.utils.LangUtil;

import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;

public class GOParagraph extends GuideObject {

	public static final GOParagraph	BLANK	= new GOParagraph();
	public String					mcStyleCodes;
	public String					unlocalizedID;
	public int						r;
	public int						g;
	public int						b;

	@Override
	public String getType() {
		return "paragraph";
	}

	@Override
	public String toString() {
		String str = "{" + this.mcStyleCodes + ":" + this.unlocalizedID + ":" + this.r + "" + this.g + "" + this.b + "}";
		return str;
	}

	@Override
	public void initElement(String baseAddress, SElementContainerBase cont) {
		STextParagraph e = new STextParagraph(baseAddress + "." + cont.getTotalElements() + getType(), 0, 0, LangUtil.toLoc(this.unlocalizedID), cont.getXSize() - 8);
		e.setStyleCodes(getStyleCodes());
		e.setColor(this.r, this.g, this.b);
		cont.addElement(e);
	}

	public String getStyleCodes() {
		if (StringUtils.isNullOrEmpty(this.mcStyleCodes)) {
			return "";
		}
		String[] s = this.mcStyleCodes.split(",");
		String formats = "";
		for (String string : s) {
			TextFormatting tf = TextFormatting.getValueByName(string);
			formats = formats + tf;
		}
		return formats;
	}

	public static class GOParagraphSerializer implements IJsonSerializer<GOParagraph> {
		@Override
		public GOParagraph deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			GOParagraph r = new GOParagraph();
			JsonObject jobj = json.getAsJsonObject();
			JsonElement sc = jobj.get("mcstyles");
			if (sc != null) {
				r.mcStyleCodes = sc.getAsString();
			}
			JsonElement uid = jobj.get("unlocalizedid");
			if (uid != null) {
				r.unlocalizedID = uid.getAsString();
			}
			JsonElement cr = jobj.get("r");
			if (cr != null) {
				r.r = cr.getAsInt();
			}
			JsonElement cg = jobj.get("g");
			if (cg != null) {
				r.g = cg.getAsInt();
			}
			JsonElement cb = jobj.get("b");
			if (cb != null) {
				r.b = cb.getAsInt();
			}
			return r;
		}

		@Override
		public JsonElement serialize(GOParagraph src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject r = new JsonObject();
			r.addProperty("type", src.getType());
			r.addProperty("mcstyles", src.mcStyleCodes);
			r.addProperty("unlocalizedid", src.unlocalizedID);
			r.addProperty("r", Integer.valueOf(src.r));
			r.addProperty("g", Integer.valueOf(src.g));
			r.addProperty("b", Integer.valueOf(src.b));
			return r;
		}
	}
}
