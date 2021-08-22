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

package com.readonlydev.lib.guide.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.readonlydev.lib.client.gui.element.SElement;
import com.readonlydev.lib.guide.client.element.GuiGuidePage;
import com.readonlydev.lib.guide.client.element.Guide;
import com.readonlydev.lib.guide.json.objects.GuideObject;
import com.readonlydev.lib.utils.LangUtil;
import com.readonlydev.lib.utils.factory.ConditionalFactory;
import com.readonlydev.lib.utils.json.IJsonSerializer;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

@SuppressWarnings("rawtypes")
public class GuidePage implements Comparable<GuidePage> {

	public static final GuidePage	BLANK		= new GuidePage();
	public static final String		TYPE		= "type";
	public int						pageNumber;
	public List<String>				menuItems	= new ArrayList<>();
	public String					address;
	public String					title;
	public String					conditionFactory;
	List<GuideObject>				content;

	public String getPageAddress() {
		return this.address;
	}

	public String getPageTitle() {
		return this.title;
	}

	public List<ItemStack> getStacks() {
		List<ItemStack> stacks = new ArrayList<>();
		for (String is : this.menuItems) {
			ItemStack stack = JsonUtil.getFromString(is);
			if (stack != ItemStack.EMPTY) {
				stacks.add(stack);
			}
		}
		return stacks;
	}

	public GuiGuidePage addPage(final Guide.GuideTOCPage mainMenu, int xSize) {
		IConditionFactory cf = ConditionalFactory.getCondition(this.conditionFactory, true);
		if (!cf.parse(new JsonContext(this.address), new JsonObject()).getAsBoolean()) {
			return null;
		}
		GuiGuidePage page;
		mainMenu.addPage(page = new GuiGuidePage(this.address, xSize) {
			@Override
			public void addElements() {
				List<SElement> ele = new ArrayList<>();
				for (GuideObject go : GuidePage.this.content) {
					go.initElement(this.elementID, this);
				}
				for (SElement vlElement : ele) {
					addElement(vlElement);
				}
				mainMenu.addMenuButton(GuidePage.this.address, GuidePage.this.getStacks(), LangUtil.toLoc(GuidePage.this.title));
			}
		});
		return page;
	}

	@Override
	public String toString() {
		String str = "{" + this.address + ":" + this.title + ":";
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
	public int compareTo(GuidePage arg0) {
		if (arg0 == null) {
			return -1;
		}
		if (this.pageNumber > arg0.pageNumber) {
			return 1;
		}
		if (this.pageNumber == arg0.pageNumber) {
			return 0;
		}
		return -1;
	}

	public static class GuidePageSerializer implements IJsonSerializer<GuidePage> {

		@Override
		public GuidePage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			GuidePage r = new GuidePage();
			JsonObject jobj = json.getAsJsonObject();
			JsonElement pn = jobj.get("pagenumber");
			if (pn != null) {
				r.pageNumber = pn.getAsInt();
			}
			JsonElement mit = jobj.get("menuitems");
			if (mit != null) {
				r.menuItems = context.deserialize(mit, List.class);
			}
			JsonElement ad = jobj.get("address");
			if (ad != null) {
				r.address = ad.getAsString();
			}
			JsonElement ti = jobj.get("title");
			if (ti != null) {
				r.title = ti.getAsString();
			}
			JsonArray jarr = jobj.get("content").getAsJsonArray();
			r.content = new ArrayList<>();
			for (JsonElement je : jarr) {
				String type = je.getAsJsonObject().get("type").getAsString();
				Class c = GuideHandler.getTypes().get(type);
				if (c == null) {

				}
				r.content.add(context.deserialize(je, c));
			}
			JsonElement conditionFactory = jobj.get("condition");
			if (conditionFactory != null) {
				r.conditionFactory = conditionFactory.getAsString();
			}
			return r;
		}

		@Override
		public JsonElement serialize(GuidePage src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject jobj = new JsonObject();
			jobj.addProperty("address", src.address);
			jobj.addProperty("title", src.title);
			jobj.add("menuitems", context.serialize(src.menuItems, List.class));
			JsonArray jarr = new JsonArray();
			for (GuideObject go : src.content) {
				String type = go.getType();
				Class c = GuideHandler.getTypes().get(type);
				if (c == null) {

				}
				jarr.add(context.serialize(go, c));
			}
			jobj.addProperty("condition", src.conditionFactory);
			jobj.add("content", jarr);
			return jobj;
		}
	}
}
