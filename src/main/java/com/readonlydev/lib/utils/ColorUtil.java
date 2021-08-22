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

import net.minecraft.util.text.TextFormatting;

public class ColorUtil {

	public enum Color implements ITextFormat {

		BLACK(TextFormatting.BLACK + ""),
		DARK_BLUE(TextFormatting.DARK_BLUE + ""),
		DARK_GREEN(TextFormatting.DARK_GREEN + ""),
		DARK_AQUA(TextFormatting.DARK_AQUA + ""),
		DARK_RED(TextFormatting.DARK_RED + ""),
		DARK_PURPLE(TextFormatting.DARK_PURPLE + ""),
		GOLD(TextFormatting.GOLD + ""),
		GRAY(TextFormatting.GRAY + ""),
		DARK_GRAY(TextFormatting.DARK_GRAY + ""),
		BLUE(TextFormatting.BLUE + ""),
		GREEN(TextFormatting.GREEN + ""),
		AQUA(TextFormatting.AQUA + ""),
		RED(TextFormatting.RED + ""),
		LIGHT_PURPLE(TextFormatting.LIGHT_PURPLE + ""),
		YELLOW(TextFormatting.YELLOW + ""),
		WHITE(TextFormatting.WHITE + "");

		public String code;

		Color(String code) {
			this.code = code;
		}

		@Override
		public String get() {
			return code;
		}
	}

	public enum Modifier implements ITextFormat {

		OBFUSCATED(TextFormatting.OBFUSCATED + ""),
		BOLD(TextFormatting.BOLD + ""),
		STRIKETHROUGH(TextFormatting.STRIKETHROUGH + ""),
		UNDERLINE(TextFormatting.UNDERLINE + ""),
		ITALIC(TextFormatting.ITALIC + ""),
		RESET(TextFormatting.RESET + "");

		public String code;

		Modifier(String code) {
			this.code = code;
		}

		@Override
		public String get() {
			return code;
		}
	}

	public interface ITextFormat {
		String get();
	}

	public static int calcMCColor(int r, int g, int b) {
		return r << 16 | g << 8 | b;
	}

	public static int calcMCColor(int r, int g, int b, int a) {
		return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
	}

	public static int getR(int col) {
		return (col & 0xFF0000) >> 16;
	}

	public static int getG(int col) {
		return (col & 0xFF00) >> 8;
	}

	public static int getB(int col) {
		return col & 0xFF;
	}

	public static int getA(int col) {
		return (col & 0xFF000000) >>> 24;
	}

	public static float getRF(int col) {
		return getR(col) / 255.0F;
	}

	public static float getGF(int col) {
		return getG(col) / 255.0F;
	}

	public static float getBF(int col) {
		return getB(col) / 255.0F;
	}

	public static float getAF(int col) {
		return getA(col) / 255.0F;
	}

	public static int getR(float col) {
		return (int) (col * 255.0F);
	}

	public static int getG(float col) {
		return (int) (col * 255.0F);
	}

	public static int getB(float col) {
		return (int) (col * 255.0F);
	}

	public static int getA(float col) {
		return (int) (col * 255.0F);
	}

	public static int	MC_WHITE	= calcMCColor(255, 255, 255);
	public static int	MC_WHITE_A	= calcMCColor(255, 255, 255, 255);
	public static int	MC_BLACK	= calcMCColor(0, 0, 0);
	public static int	MC_BLACK_A	= calcMCColor(0, 0, 0, 255);
}
