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

package net.interstellar.lib.client.gui.element;

import net.interstellar.api.client.IGuiDraw;
import net.interstellar.lib.utils.ColorUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;

public class SElementText extends SElement implements IGuiDraw {

	protected SElement.XAlignment	xAlignment;
	protected SElement.YAlignment	yAlignment;
	protected SElement.Justified	justification;
	protected String				styleCodes	= "";
	protected String				text;
	protected int					textColor;

	public SElementText(String elementID, int x, int y, String text) {
		super(elementID);
		this.xAlignment = SElement.XAlignment.LEFT;
		this.yAlignment = SElement.YAlignment.TOP;
		this.xPosOffset = x;
		this.yPosOffset = y;
		this.text = text;
		this.textColor = ColorUtil.MC_WHITE_A;
	}

	public SElementText setText(String text) {
		this.text = text;
		return this;
	}

	public SElementText setStyleCodes(String codes) {
		this.styleCodes = codes;
		return this;
	}

	public SElementText setColor(int color) {
		this.textColor = color;
		return this;
	}

	public SElementText setColor(int r, int g, int b) {
		this.textColor = ColorUtil.calcMCColor(r, g, b);
		return this;
	}

	public SElementText setYAxisAlignment(SElement.YAlignment alignment) {
		this.yAlignment = alignment;
		return this;
	}

	public SElementText setXAxisAlignment(SElement.XAlignment alignment) {
		this.xAlignment = alignment;
		return this;
	}

	@Override
	public void init() {
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		int tsw = this.gui.getFontRenderer().getStringWidth(this.text);
		int tsh = (this.gui.getFontRenderer()).FONT_HEIGHT;
		int xOffset = this.xAlignment.getOffset(tsw);
		int yOffset = this.yAlignment.getOffset(tsh);
		this.gui.getFontRenderer().drawString(this.styleCodes + this.text, getXPosActual() + xOffset, getYPosActual() + yOffset, this.textColor);
	}

	@Override
	public void drawForeground(int mx, int my, float partialTicks) {
	}

	@Override
	public int getXSize() {
		return this.gui.getFontRenderer().getStringWidth(this.text);
	}

	@Override
	public int getYSize() {
		return (this.gui.getFontRenderer()).FONT_HEIGHT;
	}

	@Override
	public int getXPosActualLargest() {
		if (this.xAlignment == SElement.XAlignment.RIGHT) {
			return getXPosActual();
		}
		if (this.xAlignment == SElement.XAlignment.CENTER) {
			return getXPosActual() + getXSize() / 2;
		}
		return getXPosActual() + getXSize();
	}

	@Override
	public int getYPosActualLargest() {
		if (this.yAlignment == SElement.YAlignment.BOTTOM) {
			return getYPosActual();
		}
		if (this.yAlignment == SElement.YAlignment.CENTER) {
			return getYPosActual() + getYSize() / 2;
		}
		return getYPosActual() + getYSize();
	}

	@Override
	public void read(NBTTagCompound nbt) {
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt) {
		return nbt;
	}
}
