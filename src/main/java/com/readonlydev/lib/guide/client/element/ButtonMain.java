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

package com.readonlydev.lib.guide.client.element;

import java.util.List;

import com.readonlydev.lib.client.gui.SGuiResources;
import com.readonlydev.lib.client.gui.element.SElement;
import com.readonlydev.lib.client.gui.element.SElementItemStackList;
import com.readonlydev.lib.client.gui.element.SElementText;
import com.readonlydev.lib.client.gui.element.button.SContainerButton;
import com.readonlydev.lib.client.gui.element.sizablebox.SElementGuiSizableBox;
import com.readonlydev.lib.client.lib.SizableBox;

import net.minecraft.item.ItemStack;

public class ButtonMain extends SContainerButton {

	private SElementItemStackList	item;
	private SElementGuiSizableBox	bg;
	private SElementText			text;
	private SElementGuiSizableBox	bgMo;
	private SElementText			textMo;
	private SElementGuiSizableBox	bgDisabled;
	private SElementText			textDisabled;

	public ButtonMain(String elementID, int xPos, int yPos, int xSize, int ySize, List<ItemStack> stacks, String text) {
		super(elementID, xPos, yPos, xSize, ySize);
		int tY = ySize - 1;
		int il = ySize / 2;
		int tx = il * 2;
		this.item = new SElementItemStackList("item", il - 8, il - 8, stacks);
		this.item.setDrawToolTip(false);
		SElement.YAlignment yAlignment = SElement.YAlignment.BOTTOM;
		SElement.XAlignment xAlignment = SElement.XAlignment.LEFT;
		addElement(this.item);
		addElementDisable(this.item);
		addElementMouseOver(this.item);
		addElement(this.bg = new SElementGuiSizableBox("bg", SGuiResources.S_A_LIGHT_GRAY));
		addElement(this.text = new SElementText("text", tx, tY, text));
		this.text.setYAxisAlignment(yAlignment);
		this.text.setXAxisAlignment(xAlignment);
		addElementMouseOver(this.bgMo = new SElementGuiSizableBox("bg", SGuiResources.S_CC_GRAY));
		addElementMouseOver(this.textMo = new SElementText("text", tx, tY, text));
		this.textMo.setYAxisAlignment(yAlignment);
		this.textMo.setXAxisAlignment(xAlignment);
		addElementDisable(this.bgDisabled = new SElementGuiSizableBox("bg", SGuiResources.S_A_GRAY));
		addElementDisable(this.textDisabled = new SElementText("text", tx, tY, text));
		this.textDisabled.setYAxisAlignment(yAlignment);
		this.textDisabled.setXAxisAlignment(xAlignment);
	}

	public ButtonMain setBg(SizableBox bg) {
		this.bg.setSizableBox(bg);
		return this;
	}

	public ButtonMain setBgMo(SizableBox bg) {
		this.bgMo.setSizableBox(bg);
		return this;
	}

	public ButtonMain setBgDisabled(SizableBox bg) {
		this.bgDisabled.setSizableBox(bg);
		return this;
	}

	public ButtonMain setTextColor(int color) {
		this.text.setColor(color);
		return this;
	}

	public ButtonMain setTextMoColor(int color) {
		this.textMo.setColor(color);
		return this;
	}

	public ButtonMain setTextDisabledColor(int color) {
		this.textDisabled.setColor(color);
		return this;
	}

	public ButtonMain setTextColor(String text) {
		this.text.setText(text);
		return this;
	}

	public ButtonMain setTextMoColor(String text) {
		this.textMo.setText(text);
		return this;
	}

	public ButtonMain setTextDisabledColor(String text) {
		this.textDisabled.setText(text);
		return this;
	}

	@Override
	public void init() {
	}
}
