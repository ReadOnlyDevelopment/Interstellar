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

import com.readonlydev.lib.client.gui.SGuiResources;
import com.readonlydev.lib.client.gui.element.SElement;
import com.readonlydev.lib.client.gui.element.SElementText;
import com.readonlydev.lib.client.gui.element.button.SContainerButton;
import com.readonlydev.lib.client.gui.element.image.SElementImageScaledMaxSize;
import com.readonlydev.lib.client.gui.element.sizablebox.SElementGuiSizableBox;
import com.readonlydev.lib.client.lib.SizableBox;
import com.readonlydev.lib.client.lib.TextureLoc;

import net.minecraft.client.Minecraft;

public class ButtonSElement extends SContainerButton {

	private SElementGuiSizableBox		background;
	private SElementImageScaledMaxSize	icon;
	private SElementText				text;
	private SElementGuiSizableBox		bgMo;
	private SElementImageScaledMaxSize	iconMo;
	private SElementText				textMo;
	private SElementGuiSizableBox		bgDisabled;
	private SElementImageScaledMaxSize	iconDisabled;
	private SElementText				textDisabled;
	private TextureLoc					initLogo;

	public ButtonSElement(String elementID, int xPos, int yPos, TextureLoc icon, String text) {
		super(elementID, xPos, yPos, 120, 56);
		this.initLogo = icon;
		int cx = this.xSize / 2;
		int tY = this.ySize;
		SElement.YAlignment yAlignment = SElement.YAlignment.BOTTOM;
		SElement.XAlignment xAlignment = SElement.XAlignment.CENTER;
		int lY = this.ySize / 2 - 5;
		int lmaxSX = this.xSize - 8;
		int lmaxSY = this.ySize - (Minecraft.getMinecraft()).fontRenderer.FONT_HEIGHT - 2;
		addElement(this.background = new SElementGuiSizableBox("bg", SGuiResources.S_A_LIGHT_GRAY));
		addElement(this.icon = new SElementImageScaledMaxSize("logo", this.initLogo, cx, lY, lmaxSX, lmaxSY));
		this.icon.setXAxisAlignment(SElement.XAlignment.CENTER);
		this.icon.setYAxisAlignment(SElement.YAlignment.CENTER);
		addElement(this.text = new SElementText("text", cx, tY, text));
		this.text.setYAxisAlignment(yAlignment);
		this.text.setXAxisAlignment(xAlignment);
		addElementMouseOver(this.bgMo = new SElementGuiSizableBox("bg", SGuiResources.S_CC_GRAY));
		addElementMouseOver(this.iconMo = new SElementImageScaledMaxSize("logo", this.initLogo, cx, lY, lmaxSX, lmaxSY));
		this.iconMo.setXAxisAlignment(SElement.XAlignment.CENTER);
		this.iconMo.setYAxisAlignment(SElement.YAlignment.CENTER);
		addElementMouseOver(this.textMo = new SElementText("text", cx, tY, text));
		this.textMo.setYAxisAlignment(yAlignment);
		this.textMo.setXAxisAlignment(xAlignment);
		addElementDisable(this.bgDisabled = new SElementGuiSizableBox("bg", SGuiResources.S_A_GRAY));
		addElementDisable(this.iconDisabled = new SElementImageScaledMaxSize("logo", this.initLogo, cx, lY, lmaxSX, lmaxSY));
		this.iconDisabled.setXAxisAlignment(SElement.XAlignment.CENTER);
		this.iconDisabled.setYAxisAlignment(SElement.YAlignment.CENTER);
		addElementDisable(this.textDisabled = new SElementText("text", cx, tY, text));
		this.textDisabled.setYAxisAlignment(yAlignment);
		this.textDisabled.setXAxisAlignment(xAlignment);
	}

	@Override
	public void init() {
	}

	public ButtonSElement setBackground(SizableBox bg) {
		this.background.setSizableBox(bg);
		return this;
	}

	public ButtonSElement setBgMo(SizableBox bg) {
		this.bgMo.setSizableBox(bg);
		return this;
	}

	public ButtonSElement setBgDisabled(SizableBox bg) {
		this.bgDisabled.setSizableBox(bg);
		return this;
	}

	public ButtonSElement setIcon(TextureLoc n) {
		this.icon.setImage(n);
		return this;
	}

	public ButtonSElement setIconMo(TextureLoc n) {
		this.iconMo.setImage(n);
		return this;
	}

	public ButtonSElement setIconDisabled(TextureLoc n) {
		this.iconDisabled.setImage(n);
		return this;
	}

	public ButtonSElement setTextColor(int color) {
		this.text.setColor(color);
		return this;
	}

	public ButtonSElement setTextMoColor(int color) {
		this.textMo.setColor(color);
		return this;
	}

	public ButtonSElement setTextDisabledColor(int color) {
		this.textDisabled.setColor(color);
		return this;
	}

	public ButtonSElement setTextColor(String text) {
		this.text.setText(text);
		return this;
	}

	public ButtonSElement setTextMoColor(String text) {
		this.textMo.setText(text);
		return this;
	}

	public ButtonSElement setTextDisabledColor(String text) {
		this.textDisabled.setText(text);
		return this;
	}

	public void setPos(int x, int y) {
		this.xPosOffset = x;
		this.yPosOffset = y;
	}

}
