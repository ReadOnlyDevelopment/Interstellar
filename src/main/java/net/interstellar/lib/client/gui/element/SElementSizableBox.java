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
import net.interstellar.lib.client.lib.SizableBox;
import net.interstellar.lib.client.lib.TexUtils;
import net.interstellar.lib.utils.ColorUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;

public abstract class SElementSizableBox extends SElement implements IGuiDraw {

	protected SizableBox	background;
	protected float			r;
	protected float			g;
	protected float			b;
	protected float			a;

	public SElementSizableBox(String elementID, SizableBox bg) {
		super(elementID);
		this.background = bg;
		this.r = ColorUtil.getRF(ColorUtil.MC_WHITE_A);
		this.g = ColorUtil.getGF(ColorUtil.MC_WHITE_A);
		this.b = ColorUtil.getBF(ColorUtil.MC_WHITE_A);
		this.a = ColorUtil.getAF(ColorUtil.MC_WHITE_A);
	}

	public void setColor(int mcColor) {
		this.r = ColorUtil.getRF(mcColor);
		this.g = ColorUtil.getGF(mcColor);
		this.b = ColorUtil.getBF(mcColor);
		this.a = ColorUtil.getAF(mcColor);
	}

	@Override
	public void init() {
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.color(this.r, this.g, this.b, this.a);
		TexUtils.renderSizableBox(this.background, this.gui, getXPosActual(), getYPosActual(), getRenderSizeX(), getRenderSizeY());
		GlStateManager.popMatrix();
	}

	@Override
	public void drawForeground(int mx, int my, float partialTicks) {
	}

	public void setSizableBox(SizableBox n) {
		this.background = n;
	}

	public abstract int getRenderSizeX();

	public abstract int getRenderSizeY();

	@Override
	public void read(NBTTagCompound nbt) {
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt) {
		return nbt;
	}
}
