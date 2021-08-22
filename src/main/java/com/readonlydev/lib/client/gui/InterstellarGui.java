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

import java.util.List;

import javax.annotation.Nonnull;

import com.readonlydev.lib.client.gui.element.SElement;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public interface InterstellarGui {

	Gui getGui();

	int getGuiLeft();

	int getGuiTop();

	int getGuiSizeX();

	int getGuiSizeY();

	FontRenderer getFontRenderer();

	void drawHoverText(List<String> paramList, int paramInt1, int paramInt2);

	boolean isInGUI(int paramInt1, int paramInt2);

	default boolean isInBox(int xIn, int yIn, int xBox, int yBox, int boxWidth, int boxHeight) {
		if (xIn >= xBox && xIn < xBox + boxWidth && yIn >= yBox && yIn < yBox + boxHeight) {
			return true;
		}
		return false;
	}

	default boolean isInBoxAndGUI(int xIn, int yIn, int xBox, int yBox, int boxWidth, int boxHeight) {
		if (isInGUI(xIn, yIn)) {
			return isInBox(xIn, yIn, xBox, yBox, boxWidth, boxHeight);
		}
		return false;
	}

	void onElementResize(@Nonnull SElement paramSElement);
}
