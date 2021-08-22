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

package com.readonlydev.lib.utils.factory;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;
import com.readonlydev.lib.utils.JavaUtil;

import io.netty.util.internal.StringUtil;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class ConditionalFactory {

	public static class GenericTrueFactory implements IConditionFactory {
		@Override
		public BooleanSupplier parse(JsonContext context, JsonObject json) {
			return new BooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return true;
				}
			};
		}
	}

	public static class GenericFalseFactory implements IConditionFactory {
		@Override
		public BooleanSupplier parse(JsonContext context, JsonObject json) {
			return new BooleanSupplier() {
				@Override
				public boolean getAsBoolean() {
					return false;
				}
			};
		}
	}

	public static IConditionFactory getCondition(String address, Boolean fallback) {
		if (!StringUtil.isNullOrEmpty(address)) {
			IConditionFactory cf = JavaUtil.getClassInstance(address, IConditionFactory.class);
			return cf;
		} else if (StringUtil.isNullOrEmpty(address) && fallback == true) {
			return new GenericTrueFactory();
		} else {
			return new GenericFalseFactory();
		}
	}
}
