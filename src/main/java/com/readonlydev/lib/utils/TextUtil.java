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

package com.readonlydev.lib.utils;

import static com.readonlydev.lib.utils.ColorUtil.Color.*;

import com.readonlydev.lib.utils.ColorUtil.ITextFormat;

public class TextUtil {

	public static final String SHIFTFORINFO = colorTextTranslate(AQUA, "info.shift") + " " + colorTextTranslate(WHITE, "info.forinfo");

	public static String colorText(ITextFormat format, String text) {
		return format.get() + " " + text;
	}

	public static String colorTextTranslate(ITextFormat format, String text) {
		return format.get() + " " + LangUtil.toLoc(text);
	}
}
