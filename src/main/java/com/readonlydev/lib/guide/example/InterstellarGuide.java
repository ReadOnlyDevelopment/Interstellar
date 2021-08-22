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

package com.readonlydev.lib.guide.example;

import java.util.List;

import com.readonlydev.lib.Interstellar;
import com.readonlydev.lib.client.gui.SGuiResources;
import com.readonlydev.lib.client.gui.element.image.SElementImage;
import com.readonlydev.lib.guide.client.element.ButtonSElement;
import com.readonlydev.lib.guide.client.element.GuiGuidePage;
import com.readonlydev.lib.guide.client.element.Guide;
import com.readonlydev.lib.guide.json.GuideHandler;
import com.readonlydev.lib.utils.ColorUtil;

public class InterstellarGuide extends Guide {

	public static GuideHandler loader = new GuideHandler(Interstellar.class) {

		@Override
		public String getModId() {
			return "interstellar";
		}
	};

	SGuiResources	guiResources;
	ButtonSElement	button;

	public static void preInitGuideLoader() {
		loader.reload();
	}

	public InterstellarGuide() {
		super("interstellar");
	}

	@Override
	public SElementImage getMainMenuLogo() {
		return null;
		// return new SElementImage("interstellarlogo", ETGuiResources.LOGO_190X16_WHITE, 0, 0);
	}

	@Override
	public ButtonSElement getButton() {
		if (this.button == null) {
			this.button = new ButtonSElement(this.elementID, 4, 4, SGuiResources.LOGO_CURSEFORGE, "Interstellar");
			this.button.setTextColor(ColorUtil.calcMCColor(255, 0, 0));
			this.button.add("Interstellar Guide");
		}
		return this.button;
	}

	@Override
	public void addElements() {
		List<GuiGuidePage> pages = loader.addGuiPages(getMainMenu(), this.xSize);
		for (GuiGuidePage guiGuidePage : pages) {
			guiGuidePage.setXPosOffset(4);
			guiGuidePage.setYPosOffset(4);
		}
	}
}
