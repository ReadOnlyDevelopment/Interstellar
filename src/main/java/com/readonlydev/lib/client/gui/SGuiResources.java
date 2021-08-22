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

package com.readonlydev.lib.client.gui;

import com.readonlydev.lib.LibInfo;
import com.readonlydev.lib.client.lib.SizableBox;
import com.readonlydev.lib.client.lib.TexUtils;
import com.readonlydev.lib.client.lib.TextureLoc;

import net.minecraft.util.ResourceLocation;

public class SGuiResources {

	public static final ResourceLocation	S_ELEMENTS			= new ResourceLocation(LibInfo.ID, "textures/gui/s_elements.png");
	public static final ResourceLocation	LOGOS_CURSEFORGE	= new ResourceLocation(LibInfo.ID, "textures/gui/logos_curseforge.png");
	public static final ResourceLocation	LOGOS_DISCORD		= new ResourceLocation(LibInfo.ID, "textures/gui/logos_discord.png");
	public static final ResourceLocation	LOGOS_GITHUB		= new ResourceLocation(LibInfo.ID, "textures/gui/logos_github.png");
	public static final ResourceLocation	ICONS				= new ResourceLocation(LibInfo.ID, "textures/gui/icons.png");
	public static final ResourceLocation	GUI_PARTS			= new ResourceLocation(LibInfo.ID, "textures/gui/elements.png");

	public static SizableBox	S_A_WHITE;
	public static SizableBox	S_A_ORANGE;
	public static SizableBox	S_A_MAGENTA;
	public static SizableBox	S_A_LIGHT_BLUE;
	public static SizableBox	S_A_YELLOW;
	public static SizableBox	S_A_LIME;
	public static SizableBox	S_A_PINK;
	public static SizableBox	S_A_GRAY;
	public static SizableBox	S_A_LIGHT_GRAY;
	public static SizableBox	S_A_CYAN;
	public static SizableBox	S_A_PURPLE;
	public static SizableBox	S_A_BLUE;
	public static SizableBox	S_A_BROWN;
	public static SizableBox	S_A_GREEN;
	public static SizableBox	S_A_RED;
	public static SizableBox	S_A_BLACK;
	public static SizableBox	S_CURSEFORGE_GREEN;
	public static SizableBox	S_CURSEFORGE_ORANGE;
	public static SizableBox	S_DISCORD_BURPLE;
	public static SizableBox	S_DISCORD_GRAY;
	public static TextureLoc	LOGO_CURSEFORGE;
	public static TextureLoc	LOGO_DISCORD_BLUE;
	public static TextureLoc	LOGO_DISCORD_WHITE;
	public static TextureLoc	LOGO_DISCORD_BLACK;
	public static TextureLoc	LOGO_GITHUB_BLACK;
	public static TextureLoc	LOGO_GITHUB_WHITE;
	public static TextureLoc	S_PREV_WHITE;
	public static TextureLoc	S_NEXT_WHITE;
	public static TextureLoc	S_HOME_WHITE;
	public static TextureLoc	S_UP_WHITE;
	public static TextureLoc	S_DOWN_WHITE;
	public static TextureLoc	S_PREV_BLACK;
	public static TextureLoc	S_NEXT_BLACK;
	public static TextureLoc	S_HOME_BLACK;
	public static TextureLoc	S_UP_BLACK;
	public static TextureLoc	S_DOWN_BLACK;
	public static TextureLoc	SLOT_STYLE_1;
	public static TextureLoc	SLOT_STYLE_2;
	public static TextureLoc	SLOT_STYLE_3;
	public static SizableBox	SEARCH_STYLE_1;
	public static SizableBox	SEARCH_STYLE_2;
	public static SizableBox	GUI_STYLE_1;
	public static SizableBox	GUI_STYLE_2;
	public static SizableBox	GUI_STYLE_3;
	public static SizableBox	GUI_STYLE_4;
	public static SizableBox	GUI_STYLE_5;
	public static SizableBox	GUI_STYLE_6;
	public static SizableBox	GUI_BG_1;
	public static SizableBox	GUI_BG_2;
	public static SizableBox	BUTTON_STYLE_1;
	public static SizableBox	BUTTON_STYLE_2;
	public static SizableBox	BUTTON_STYLE_3;
	public static SizableBox	BUTTON_STYLE_4;
	public static SizableBox	BUTTON_STYLE_5;
	public static TextureLoc	ENERGY_OVERLAY_1;
	public static TextureLoc	FLUID_OVERLAY_1;
	public static TextureLoc	PROGRESS_OVERLAY_1;
	public static TextureLoc	PROGRESS_OVERLAY_2;

	private static int			boxSize				= 12;
	private static int			xp					= 0;
	private static int			yp					= 0;
	public static SizableBox	S_CC_BLACK			= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_DARK_BLUE		= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_DARK_GREEN		= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_DARK_AQUA		= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_DARK_RED		= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_DARK_PURPLE	= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_GOLD			= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_GRAY			= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_DARK_GRAY		= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_BLUE			= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_GREEN			= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_AQUA			= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_RED			= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_LIGHT_PURPLE	= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_YELLOW			= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
	public static SizableBox	S_CC_WHITE			= TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);

	static {

		xp = 0;
		yp = 1;
		S_A_WHITE = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_ORANGE = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_MAGENTA = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_LIGHT_BLUE = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_YELLOW = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_LIME = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_PINK = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_GRAY = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_LIGHT_GRAY = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_CYAN = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_PURPLE = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_BLUE = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_BROWN = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_GREEN = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_RED = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_A_BLACK = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);

		xp = 0;
		yp = 2;
		S_CURSEFORGE_GREEN = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_CURSEFORGE_ORANGE = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_DISCORD_BURPLE = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);
		S_DISCORD_GRAY = TexUtils.genSizableBox(S_ELEMENTS, getLoc(boxSize, xp++), getLoc(boxSize, yp), 4, 4);

		boxSize = 96;
		xp = 0;
		yp = 0;
		LOGO_CURSEFORGE = TexUtils.getTextureLoc(LOGOS_CURSEFORGE, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);

		xp = 0;
		yp = 0;
		LOGO_DISCORD_BLUE = TexUtils.getTextureLoc(LOGOS_DISCORD, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);
		LOGO_DISCORD_WHITE = TexUtils.getTextureLoc(LOGOS_DISCORD, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);

		xp = 0;
		yp = 1;
		LOGO_DISCORD_BLACK = TexUtils.getTextureLoc(LOGOS_DISCORD, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);

		xp = 0;
		yp = 0;
		LOGO_GITHUB_BLACK = TexUtils.getTextureLoc(LOGOS_GITHUB, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);
		LOGO_GITHUB_WHITE = TexUtils.getTextureLoc(LOGOS_GITHUB, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);

		boxSize = 48;
		xp = 0;
		yp = 0;
		S_PREV_WHITE = TexUtils.getTextureLoc(ICONS, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);
		S_NEXT_WHITE = TexUtils.getTextureLoc(ICONS, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);
		S_HOME_WHITE = TexUtils.getTextureLoc(ICONS, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);
		S_UP_WHITE = TexUtils.getTextureLoc(ICONS, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);
		S_DOWN_WHITE = TexUtils.getTextureLoc(ICONS, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);

		xp = 0;
		yp = 1;
		S_PREV_BLACK = TexUtils.getTextureLoc(ICONS, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);
		S_NEXT_BLACK = TexUtils.getTextureLoc(ICONS, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);
		S_HOME_BLACK = TexUtils.getTextureLoc(ICONS, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);
		S_UP_BLACK = TexUtils.getTextureLoc(ICONS, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);
		S_DOWN_BLACK = TexUtils.getTextureLoc(ICONS, getLoc(boxSize, xp++), getLoc(boxSize, yp), boxSize, boxSize);
		SLOT_STYLE_1 = TexUtils.getTextureLoc(GUI_PARTS, 0, 0, 18, 18);
		SLOT_STYLE_2 = TexUtils.getTextureLoc(GUI_PARTS, 18, 0, 18, 18);
		SLOT_STYLE_3 = TexUtils.getTextureLoc(GUI_PARTS, 36, 0, 18, 18);
		SEARCH_STYLE_1 = TexUtils.genSizableBox(GUI_PARTS, 0, 18, 2, 2);
		SEARCH_STYLE_2 = TexUtils.genSizableBox(GUI_PARTS, 6, 18, 2, 2);
		GUI_STYLE_1 = TexUtils.genSizableBox(GUI_PARTS, 0, 24, 4, 4);
		GUI_STYLE_2 = TexUtils.genSizableBox(GUI_PARTS, 12, 24, 4, 4);
		GUI_STYLE_3 = TexUtils.genSizableBox(GUI_PARTS, 24, 24, 4, 4);
		GUI_STYLE_4 = TexUtils.genSizableBox(GUI_PARTS, 36, 24, 4, 4);
		GUI_STYLE_5 = TexUtils.genSizableBox(GUI_PARTS, 48, 24, 4, 4);
		GUI_STYLE_6 = TexUtils.genSizableBox(GUI_PARTS, 60, 24, 4, 4);
		GUI_BG_1 = TexUtils.genSizableBox(GUI_PARTS, 0, 102, 4, 4);
		GUI_BG_2 = TexUtils.genSizableBox(GUI_PARTS, 12, 102, 4, 4);
		BUTTON_STYLE_1 = TexUtils.genSizableBox(GUI_PARTS, 0, 36, 4, 4);
		BUTTON_STYLE_2 = TexUtils.genSizableBox(GUI_PARTS, 12, 36, 4, 4);
		BUTTON_STYLE_3 = TexUtils.genSizableBox(GUI_PARTS, 24, 36, 4, 4);
		BUTTON_STYLE_4 = TexUtils.genSizableBox(GUI_PARTS, 36, 36, 4, 4);
		BUTTON_STYLE_5 = TexUtils.genSizableBox(GUI_PARTS, 48, 36, 4, 4);
		ENERGY_OVERLAY_1 = TexUtils.getTextureLoc(GUI_PARTS, 0, 48, 18, 54);
		FLUID_OVERLAY_1 = TexUtils.getTextureLoc(GUI_PARTS, 18, 48, 18, 54);
		PROGRESS_OVERLAY_1 = TexUtils.getTextureLoc(GUI_PARTS, 36, 48, 9, 54);
		PROGRESS_OVERLAY_2 = TexUtils.getTextureLoc(GUI_PARTS, 45, 48, 9, 54);
	}

	public static int getLoc(int size, int n) {
		return size * n;
	}
}
