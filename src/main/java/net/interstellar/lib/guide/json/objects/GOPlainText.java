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

package net.interstellar.lib.guide.json.objects;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import net.interstellar.lib.client.gui.element.SElement;
import net.interstellar.lib.client.gui.element.SElementText;
import net.interstellar.lib.client.gui.element.container.SElementContainerBase;
import net.interstellar.lib.utils.json.IJsonSerializer;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;

public class GOPlainText extends GuideObject {

	public static final GOPlainText	BLANK	= new GOPlainText();
	public String					mcStyleCodes;
	public String					text;
	public String					valign;
	public String					halign;
	public int						r;
	public int						g;
	public int						b;

	@Override
	public String getType() {
		return "ptext";
	}

	@Override
	public void initElement(String baseAddress, SElementContainerBase cont) {

		SElementText e = new SElementText(baseAddress + "." + cont.getTotalElements() + getType(), 0, 0, this.text);
		e.setStyleCodes(getStyleCodes());
		e.setColor(this.r, this.g, this.b);
		if (!StringUtils.isNullOrEmpty(this.halign)) {
			e.setXAxisAlignment(SElement.XAlignment.fromString(this.halign));
		}
		if (!StringUtils.isNullOrEmpty(this.valign)) {
			e.setYAxisAlignment(SElement.YAlignment.fromString(this.valign));
		}
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

	public static class GOPlainTextSerializer implements IJsonSerializer<GOPlainText> {
		@Override
		public GOPlainText deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			GOPlainText r = new GOPlainText();
			JsonObject jobj = json.getAsJsonObject();
			JsonElement sc = jobj.get("mcstyles");
			if (sc != null) {
				r.mcStyleCodes = sc.getAsString();
			}
			JsonElement uid = jobj.get("text");
			if (uid != null) {
				r.text = uid.getAsString();
			}
			JsonElement va = jobj.get("valign");
			if (va != null) {
				r.valign = va.getAsString();
			}
			JsonElement ha = jobj.get("halign");
			if (ha != null) {
				r.halign = ha.getAsString();
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
		public JsonElement serialize(GOPlainText src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject r = new JsonObject();
			r.addProperty("type", src.getType());
			r.addProperty("mcstyles", src.mcStyleCodes);
			r.addProperty("text", src.text);
			r.addProperty("valign", src.valign);
			r.addProperty("halign", src.halign);
			r.addProperty("r", Integer.valueOf(src.r));
			r.addProperty("g", Integer.valueOf(src.g));
			r.addProperty("b", Integer.valueOf(src.b));
			return r;
		}
	}
}
