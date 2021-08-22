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

package com.readonlydev.lib.client.gui.element.text;

import java.util.List;

import com.readonlydev.lib.client.gui.InterstellarGui;
import com.readonlydev.lib.client.gui.element.SElementText;

import net.minecraft.client.renderer.GlStateManager;

public class STextParagraph extends SElementText {

	protected int	paraHeight;
	protected int	paraLargestWidth;
	protected int	maxWidth	= 20;

	public STextParagraph(String elementID, int x, int y, String text, int maxWidth) {
		super(elementID, x, y, text);
		this.maxWidth = maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.paraLargestWidth = maxWidth;
		recalcDimensions();
	}

	@Override
	public void setGui(InterstellarGui gui) {
		this.gui = gui;
		recalcDimensions();
	}

	@Override
	public STextParagraph setText(String text) {
		this.text = text;
		recalcDimensions();
		return this;
	}

	protected void recalcDimensions() {
		this.paraHeight = this.gui.getFontRenderer().getWordWrappedHeight(this.text, this.maxWidth);
		List<String> test = this.gui.getFontRenderer().listFormattedStringToWidth(this.text, this.maxWidth);
		for (String string : test) {
			int t = this.gui.getFontRenderer().getStringWidth(string);
			if (t > this.paraLargestWidth) {
				this.paraLargestWidth = t;
			}
		}
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		int xOffset = this.xAlignment.getOffset(this.paraLargestWidth);
		int yOffset = this.yAlignment.getOffset(this.paraHeight);
		this.gui.getFontRenderer().drawSplitString(this.styleCodes + this.text, this.gui.getGuiLeft() + this.xPosOffset + xOffset, this.gui.getGuiTop() + this.yPosOffset + yOffset, this.maxWidth, this.textColor);
	}

	@Override
	public int getXSize() {
		return this.paraLargestWidth;
	}

	@Override
	public int getYSize() {
		return this.paraHeight;
	}
}
