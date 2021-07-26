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
import net.interstellar.lib.client.gui.element.image.SElementImageScaledMaxSize;
import net.interstellar.lib.client.gui.element.sizablebox.SElementGuiSizableBox;
import net.interstellar.lib.client.lib.SizableBox;
import net.interstellar.lib.client.lib.TextureLoc;

public class SButtonIcon extends SContainerButton {

	private SElementGuiSizableBox		bg;
	private SElementImageScaledMaxSize	icon;
	private SElementGuiSizableBox		bgMo;
	private SElementImageScaledMaxSize	iconMo;
	private SElementGuiSizableBox		bgDisabled;
	private SElementImageScaledMaxSize	iconDisabled;

	public SButtonIcon(String elementID, int xPos, int yPos, int xSize, int ySize, TextureLoc icon) {
		super(elementID, xPos, yPos, xSize, ySize);
		int ix = this.xSize / 2;
		int iy = this.ySize / 2;
		SElement.YAlignment yAlignment = SElement.YAlignment.CENTER;
		SElement.XAlignment xAlignment = SElement.XAlignment.CENTER;
		addElement(this.bg = new SElementGuiSizableBox("bg", SGuiResources.S_A_WHITE));
		addElement(this.icon = new SElementImageScaledMaxSize("logo", icon, ix, iy, this.xSize - 4, this.ySize - 4));
		this.icon.setXAxisAlignment(xAlignment);
		this.icon.setYAxisAlignment(yAlignment);
		addElementMouseOver(this.bgMo = new SElementGuiSizableBox("bg", SGuiResources.S_CC_GRAY));
		addElementMouseOver(this.iconMo = new SElementImageScaledMaxSize("logo", icon, ix, iy, this.xSize - 4, this.ySize - 4));
		this.iconMo.setXAxisAlignment(xAlignment);
		this.iconMo.setYAxisAlignment(yAlignment);
		addElementDisable(this.bgDisabled = new SElementGuiSizableBox("bg", SGuiResources.S_A_GRAY));
		addElementDisable(this.iconDisabled = new SElementImageScaledMaxSize("logo", icon, ix, iy, this.xSize - 4, this.ySize - 4));
		this.iconDisabled.setXAxisAlignment(xAlignment);
		this.iconDisabled.setYAxisAlignment(yAlignment);
	}

	public SButtonIcon setBg(SizableBox bg) {
		this.bg.setSizableBox(bg);
		return this;
	}

	public SButtonIcon setBgMo(SizableBox bg) {
		this.bgMo.setSizableBox(bg);
		return this;
	}

	public SButtonIcon setBgDisabled(SizableBox bg) {
		this.bgDisabled.setSizableBox(bg);
		return this;
	}

	public SButtonIcon setIcon(TextureLoc n) {
		this.icon.setImage(n);
		return this;
	}

	public SButtonIcon setIconMo(TextureLoc n) {
		this.iconMo.setImage(n);
		return this;
	}

	public SButtonIcon setIconDisabled(TextureLoc n) {
		this.iconDisabled.setImage(n);
		return this;
	}

	@Override
	public void init() {
	}
}
