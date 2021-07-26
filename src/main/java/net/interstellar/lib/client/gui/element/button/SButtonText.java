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

package net.interstellar.lib.client.gui.element.button;

import net.interstellar.lib.client.gui.SGuiResources;
import net.interstellar.lib.client.gui.element.SElement;
import net.interstellar.lib.client.gui.element.SElementText;
import net.interstellar.lib.client.gui.element.sizablebox.SElementGuiSizableBox;
import net.interstellar.lib.client.lib.SizableBox;

public class SButtonText extends SContainerButton {

	protected SElementText			text;
	protected SElementGuiSizableBox	bg;
	protected SElementText			textMo;
	protected SElementGuiSizableBox	bgMo;
	protected SElementText			textDisabled;
	protected SElementGuiSizableBox	bgDisabled;

	public SButtonText(String elementID, int xPos, int yPos, int xSize, int ySize, String text) {
		super(elementID, xPos, yPos, xSize, ySize);
		int tx = xSize / 2 + 0;
		int ty = ySize / 2 + 0;
		this.container.addElement(this.bg = new SElementGuiSizableBox("bg", SGuiResources.BUTTON_STYLE_1));
		this.container.addElement(this.text = new SElementText("text", tx, ty, text));
		this.text.setXAxisAlignment(SElement.XAlignment.CENTER);
		this.text.setYAxisAlignment(SElement.YAlignment.CENTER);
		this.containerMouseOver.addElement(this.bgMo = new SElementGuiSizableBox("bg", SGuiResources.BUTTON_STYLE_2));
		this.containerMouseOver.addElement(this.textMo = new SElementText("text", tx, ty, text));
		this.textMo.setXAxisAlignment(SElement.XAlignment.CENTER);
		this.textMo.setYAxisAlignment(SElement.YAlignment.CENTER);
		this.containerDisabled.addElement(this.bgDisabled = new SElementGuiSizableBox("bg", SGuiResources.BUTTON_STYLE_3));
		this.containerDisabled.addElement(this.textDisabled = new SElementText("text", tx, ty, text));
		this.textDisabled.setXAxisAlignment(SElement.XAlignment.CENTER);
		this.textDisabled.setYAxisAlignment(SElement.YAlignment.CENTER);
	}

	public void setText(String text) {
		this.text.setText(text);
	}

	public SButtonText setBg(SizableBox bg) {
		this.bg.setSizableBox(bg);
		return this;
	}

	public SButtonText setBgMo(SizableBox bg) {
		this.bgMo.setSizableBox(bg);
		return this;
	}

	public SButtonText setBgDisabled(SizableBox bg) {
		this.bgDisabled.setSizableBox(bg);
		return this;
	}

	public SElementText getText() {
		return this.text;
	}

	public SElementText getTextMo() {
		return this.textMo;
	}

	public SElementText getTextDisabled() {
		return this.textDisabled;
	}

	@Override
	public void init() {
	}
}
