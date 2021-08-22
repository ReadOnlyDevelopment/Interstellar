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

package com.readonlydev.lib.client.gui.element.button;

import java.util.List;

import com.readonlydev.lib.client.gui.InterstellarGui;
import com.readonlydev.lib.client.gui.element.SElement;
import com.readonlydev.lib.client.gui.element.SElementButton;
import com.readonlydev.lib.client.gui.element.container.SContainerFixedSize;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public abstract class SContainerButton extends SElementButton implements InterstellarGui {

	protected SContainerFixedSize	container;
	protected SContainerFixedSize	containerMouseOver;
	protected SContainerFixedSize	containerDisabled;

	public SContainerButton(String elementID, int xPos, int yPos, int xSize, int ySize) {
		super(elementID, xPos, yPos, xSize, ySize);
		this.container = new SContainerFixedSize("button.container", xSize, ySize) {
			@Override
			public void addElements() {
			}
		};
		this.container.setGui(this);
		this.containerMouseOver = new SContainerFixedSize("button.container.mouseover", xSize, ySize) {
			@Override
			public void addElements() {
			}
		};
		this.containerMouseOver.setGui(this);
		this.containerDisabled = new SContainerFixedSize("button.container.disable", xSize, ySize) {
			@Override
			public void addElements() {
			}
		};
		this.containerDisabled.setGui(this);
	}

	public void addElement(SElement ele) {
		ele.setGui(this);
		this.container.addElement(ele);
	}

	public void addElementMouseOver(SElement ele) {
		ele.setGui(this);
		this.containerMouseOver.addElement(ele);
	}

	public void addElementDisable(SElement ele) {
		ele.setGui(this);
		this.containerDisabled.addElement(ele);
	}

	public void drawBackground(int mx, int my, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.enabled) {
			if (this.gui.isInBoxAndGUI(mx, my, getXPosActual(), getYPosActual(), this.xSize, this.ySize)) {
				this.containerMouseOver.drawBackground(mx, my, partialTicks);
			} else {
				this.container.drawBackground(mx, my, partialTicks);
			}
		} else {
			this.containerDisabled.drawBackground(mx, my, partialTicks);
		}
		GlStateManager.popMatrix();
	}

	public void drawForeground(int mx, int my, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.enabled) {
			if (this.gui.isInBoxAndGUI(mx, my, getXPosActual(), getYPosActual(), this.xSize, this.ySize)) {
				this.containerMouseOver.drawForeground(mx, my, partialTicks);
			} else {
				this.container.drawForeground(mx, my, partialTicks);
			}
		} else {
			this.containerDisabled.drawForeground(mx, my, partialTicks);
		}
		GlStateManager.popMatrix();
	}

	public Gui getGui() {
		return this.gui.getGui();
	}

	public int getGuiLeft() {
		return this.gui.getGuiLeft() + this.xPosOffset;
	}

	public int getGuiTop() {
		return this.gui.getGuiTop() + this.yPosOffset;
	}

	public int getGuiSizeX() {
		return this.xSize;
	}

	public int getGuiSizeY() {
		return this.ySize;
	}

	public FontRenderer getFontRenderer() {
		return this.gui.getFontRenderer();
	}

	public void drawHoverText(List<String> list, int x, int y) {
		this.gui.drawHoverText(list, x, y);
	}

	public boolean isInGUI(int x, int y) {
		return isInBox(x, y, this.xPosOffset, this.yPosOffset, this.xSize, this.ySize);
	}

	public void onElementResize(SElement ele) {
	}
}
