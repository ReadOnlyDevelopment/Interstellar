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

package com.readonlydev.lib.client.gui.element.level;

import com.readonlydev.api.client.IGuiData;
import com.readonlydev.lib.client.gui.element.SElementLevelVert;
import com.readonlydev.lib.client.lib.TexUtils;
import com.readonlydev.lib.client.lib.TextureLoc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fluids.IFluidTank;

public class SLevelFluid extends SElementLevelVert implements IGuiData {

	protected IFluidTank tank;

	public SLevelFluid(String elementID, int x, int y, TextureLoc levelOverlay, IFluidTank fluidTank) {
		super(elementID, x, y, levelOverlay);
		this.tank = fluidTank;
	}

	@Override
	public void updateLevel(float level) {
		if (level < 0.0F) {
			this.level_scaled = 0.0F;
		} else if (level > 1.0F) {
			this.level_scaled = 1.0F;
		} else {
			this.level_scaled = level;
		}
	}

	public void update() {
		if (this.tank == null) {
			this.level_scaled = 0.0F;
			System.out.println(this.level_scaled);
			return;
		}
		updateLevel(calcMultiplier(this.tank.getCapacity(), this.tank.getFluidAmount()));
	}

	@Override
	public void drawBackground(int mx, int my, float partialTicks) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		TexUtils.renderGuiFluid(this.tank.getFluid(), this.level_scaled, getXPosActual(), getYPosActual(), this.levelOverlay.width, this.levelOverlay.height);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.levelOverlay.texture);
		this.gui.getGui().drawTexturedModalRect(this.gui.getGuiLeft() + this.xPosOffset, this.gui.getGuiTop() + this.yPosOffset, this.levelOverlay.startX, this.levelOverlay.startY, this.levelOverlay.width, this.levelOverlay.height);
	}
}
