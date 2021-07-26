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

package net.interstellar.lib.guide.client.element;

import net.interstellar.api.client.IGuiDraw;
import net.interstellar.lib.client.gui.element.SElement;
import net.interstellar.lib.guide.registry.TileData;
import net.interstellar.lib.utils.ColorUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;

public class TileDataElement extends SElement implements IGuiDraw {

	private TileData				data;
	protected SElement.XAlignment	xAlignment;
	protected SElement.YAlignment	yAlignment;
	protected String				styleCodes	= "";
	protected int					textColor;

	public TileDataElement(String elementID, int x, int y, TileData data) {
		super(elementID);
		this.xAlignment = SElement.XAlignment.LEFT;
		this.yAlignment = SElement.YAlignment.TOP;
		this.data = data;
		this.xPosOffset = x;
		this.yPosOffset = y;
		this.textColor = ColorUtil.MC_WHITE_A;
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		int tsw = this.gui.getFontRenderer().getStringWidth(this.data.getLocalizedData());
		int tsh = (this.gui.getFontRenderer()).FONT_HEIGHT;
		int xOffset = this.xAlignment.getOffset(tsw);
		int yOffset = this.yAlignment.getOffset(tsh);
		this.gui.getFontRenderer().drawString(this.styleCodes + this.data.getLocalizedData(), getXPosActual() + xOffset, getYPosActual() + yOffset, this.textColor);
	}

	public TileDataElement setColor(int color) {
		this.textColor = color;
		return this;
	}

	public TileDataElement setColor(int r, int g, int b) {
		this.textColor = ColorUtil.calcMCColor(r, g, b);
		return this;
	}

	public TileDataElement setStyleCodes(String codes) {
		this.styleCodes = codes;
		return this;
	}

	public TileDataElement setYAxisAlignment(YAlignment va) {
		this.yAlignment = va;
		return this;
	}

	public TileDataElement setXAxisAlignment(XAlignment ha) {
		this.xAlignment = ha;
		return this;
	}

	@Override
	public void drawForeground(int mx, int my, float partialTicks) {
	}

	@Override
	public int getXSize() {
		return this.gui.getFontRenderer().getStringWidth(this.data.getLocalizedData());
	}

	@Override
	public int getYSize() {
		return (this.gui.getFontRenderer()).FONT_HEIGHT;
	}

	@Override
	public void init() {
	}

	@Override
	public void read(NBTTagCompound nbt) {
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt) {
		return nbt;
	}
}
