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
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.readonlydev.api.json.IJsonSerializer;
import com.readonlydev.lib.client.gui.SGuiResources;
import com.readonlydev.lib.client.gui.element.container.SElementContainerBase;
import com.readonlydev.lib.client.gui.element.container.list.SContListH;
import com.readonlydev.lib.client.gui.element.sizablebox.SElementGuiSizableBox;
import com.readonlydev.lib.guide.json.GuideHandler;
import com.readonlydev.lib.utils.ColorUtil;
import com.readonlydev.lib.utils.factory.ConditionalFactory;

import net.minecraftforge.common.crafting.IConditionFactory;

@SuppressWarnings("rawtypes")
public class GOContainerHorizontal extends GuideObject {

	public static final GOContainerHorizontal BLANK = new GOContainerHorizontal();

	@Override
	public String getType() {
		return "hcontainer";
	}

	public boolean				hasBackground	= false;
	public int					r;
	public int					g;
	public int					b;
	public int					a;
	public List<GuideObject>	content;
	public String				conditionFactory;

	@Override
	public String toString() {
		String str = "{";
		int i = 0;
		for (GuideObject guideObject : this.content) {
			str = str + guideObject.toString();
			if (i < this.content.size()) {
				str = str + ",";
			}
		}
		return str + "}";
	}

	@Override
	public void initElement(String baseAddress, SElementContainerBase cont) {
		IConditionFactory cf = ConditionalFactory.getCondition(this.conditionFactory, true);
		if (!cf.parse(null, null).getAsBoolean()) {
			return;
		}
		final String ba = baseAddress + "." + cont.getTotalElements() + getType();
		cont.addElement(new SContListH(ba) {
			@Override
			public void addElements() {
				if (GOContainerHorizontal.this.hasBackground) {
					SElementGuiSizableBox gsb;
					addElement(gsb = new SElementGuiSizableBox(ba + "." + Character.MIN_VALUE + "bg", SGuiResources.S_A_WHITE));
					gsb.setColor(ColorUtil.calcMCColor(GOContainerHorizontal.this.r, GOContainerHorizontal.this.g, GOContainerHorizontal.this.b, GOContainerHorizontal.this.a));
				}
				for (GuideObject go : GOContainerHorizontal.this.content) {
					go.initElement(ba, this);
				}
			}
		});
	}

	public static class GOContainerHorizontalSerializer implements IJsonSerializer<GOContainerHorizontal> {

		@Override
		public GOContainerHorizontal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			GOContainerHorizontal r = new GOContainerHorizontal();
			r.content = new ArrayList<>();
			JsonObject jobj = json.getAsJsonObject();
			JsonArray jarr = jobj.get("content").getAsJsonArray();
			for (JsonElement je : jarr) {
				String type = je.getAsJsonObject().get("type").getAsString();
				Class c = GuideHandler.getTypes().get(type);
				if (c == null) {

				}
				r.content.add(context.deserialize(je, c));
			}
			JsonElement cbg = jobj.get("bgenabled");
			if (cbg != null) {
				r.hasBackground = cbg.getAsBoolean();
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
			JsonElement ca = jobj.get("a");
			if (ca != null) {
				r.a = ca.getAsInt();
			}
			JsonElement conditionFactory = jobj.get("condition");
			if (conditionFactory != null) {
				r.conditionFactory = conditionFactory.getAsString();
			}
			return r;
		}

		@Override
		public JsonElement serialize(GOContainerHorizontal src, Type typeOfSrc, JsonSerializationContext context) {
			JsonArray jarr = new JsonArray();
			for (GuideObject go : src.content) {
				String type = go.getType();
				Class c = GuideHandler.getTypes().get(type);
				if (c == null) {

				}
				jarr.add(context.serialize(go, c));
			}
			JsonObject r = new JsonObject();
			r.addProperty("type", src.getType());
			r.addProperty("bgenabled", src.hasBackground);
			r.addProperty("r", Integer.valueOf(src.r));
			r.addProperty("g", Integer.valueOf(src.g));
			r.addProperty("b", Integer.valueOf(src.b));
			r.addProperty("a", Integer.valueOf(src.a));
			r.addProperty("condition", src.conditionFactory);
			r.add("content", jarr);
			return r;
		}
	}
}
