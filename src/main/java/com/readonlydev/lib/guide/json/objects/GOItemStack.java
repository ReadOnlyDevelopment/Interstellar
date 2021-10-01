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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.readonlydev.api.json.IJsonSerializer;
import com.readonlydev.lib.client.gui.element.SElementItemStackList;
import com.readonlydev.lib.client.gui.element.container.SElementContainerBase;
import com.readonlydev.lib.guide.json.JsonUtil;

import net.minecraft.item.ItemStack;

public class GOItemStack extends GuideObject {
	public static final GOItemStack BLANK = new GOItemStack();

	@Override
	public String getType() {
		return "itemstack";
	}

	public List<String> items = new ArrayList<>();

	public boolean drawToolTip = false;

	public int ticksPerItem = 20;

	@Override
	public String toString() {
		String str = "{" + this.items + "}";
		return str;
	}

	public List<ItemStack> getStacks() {
		List<ItemStack> stacks = new ArrayList<>();
		for (String is : this.items) {
			ItemStack stack = JsonUtil.getFromString(is);
			if (stack != ItemStack.EMPTY) {
				stacks.add(stack);
			}
		}
		return stacks;
	}

	@Override
	public void initElement(String baseAddress, SElementContainerBase cont) {
		SElementItemStackList isl = new SElementItemStackList(baseAddress + "." + cont.getTotalElements() + getType(), 0, 0, getStacks());
		cont.addElement(isl);
	}

	public static class GOItemStackSerializer implements IJsonSerializer<GOItemStack> {
		@Override
		public GOItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			GOItemStack r = new GOItemStack();
			JsonObject jobj = json.getAsJsonObject();
			JsonElement id = jobj.get("items");
			if (id != null) {
				r.items = context.deserialize(id, List.class);
			}
			JsonElement tt = jobj.get("tooltip");
			if (tt != null) {
				r.drawToolTip = tt.getAsBoolean();
			}
			JsonElement tk = jobj.get("ticks");
			if (tk != null) {
				r.ticksPerItem = tk.getAsInt();
			}
			return r;
		}

		@Override
		public JsonElement serialize(GOItemStack src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject r = new JsonObject();
			r.addProperty("type", src.getType());
			r.add("items", context.serialize(src.items, List.class));
			r.addProperty("tooltip", src.drawToolTip);
			r.addProperty("ticks", Integer.valueOf(src.ticksPerItem));
			return r;
		}
	}
}
