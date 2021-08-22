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

/**
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

package com.readonlydev.lib.client.lib;

import org.lwjgl.opengl.GL11;

import com.readonlydev.lib.client.gui.InterstellarGui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class TexUtils {

	public static final ResourceLocation BLOCKS_TEX = TextureMap.LOCATION_BLOCKS_TEXTURE;

	public static SizableBox generateSlotStyle(ResourceLocation loc, int x, int y) {
		return genSizableBox(loc, x, y, 2, 2);
	}

	public static SizableBox generateSearchBoxStyle(ResourceLocation loc, int x, int y) {
		return genSizableBox(loc, x, y, 2, 2);
	}

	public static SizableBox generateGuiBGStyle(ResourceLocation loc, int x, int y) {
		return genSizableBox(loc, x, y, 4, 4);
	}

	public static SizableBox genSizableBox(ResourceLocation loc, int x, int y, int w, int h) {
		SizableBox ss = new SizableBox(loc, w, h);
		int xs0 = x + w * 0;
		int xs1 = x + w * 1;
		int xs2 = x + w * 2;
		int xe0 = x + w * 1;
		int xe1 = x + w * 2;
		int xe2 = x + w * 3;
		int ys0 = y + h * 0;
		int ys1 = y + h * 1;
		int ys2 = y + h * 2;
		int ye0 = y + h * 1;
		int ye1 = y + h * 2;
		int ye2 = y + h * 3;
		ss.setTextureLoc(SizableBox.BoxLocation.TOP_LEFT, new TextureLoc(loc, xs0, ys0, xe0, ye0));
		ss.setTextureLoc(SizableBox.BoxLocation.TOP_MID, new TextureLoc(loc, xs1, ys0, xe1, ye0));
		ss.setTextureLoc(SizableBox.BoxLocation.TOP_RIGHT, new TextureLoc(loc, xs2, ys0, xe2, ye0));
		ss.setTextureLoc(SizableBox.BoxLocation.MID_LEFT, new TextureLoc(loc, xs0, ys1, xe0, ye1));
		ss.setTextureLoc(SizableBox.BoxLocation.MID, new TextureLoc(loc, xs1, ys1, xe1, ye1));
		ss.setTextureLoc(SizableBox.BoxLocation.MID_RIGHT, new TextureLoc(loc, xs2, ys1, xe2, ye1));
		ss.setTextureLoc(SizableBox.BoxLocation.BOT_LEFT, new TextureLoc(loc, xs0, ys2, xe0, ye2));
		ss.setTextureLoc(SizableBox.BoxLocation.BOT_MID, new TextureLoc(loc, xs1, ys2, xe1, ye2));
		ss.setTextureLoc(SizableBox.BoxLocation.BOT_RIGHT, new TextureLoc(loc, xs2, ys2, xe2, ye2));
		return ss;
	}

	public static void setColor(int mcColor) {
		float f3 = (mcColor >> 24 & 0xFF) / 255.0F;
		float f = (mcColor >> 16 & 0xFF) / 255.0F;
		float f1 = (mcColor >> 8 & 0xFF) / 255.0F;
		float f2 = (mcColor & 0xFF) / 255.0F;
		GlStateManager.color(f, f1, f2, f3);
	}

	public static TextureLoc getTextureLoc(ResourceLocation loc, int x, int y, int w, int h) {
		return new TextureLoc(loc, x, y, x + w, y + h);
	}

	public static void renderTexLocation(TextureLoc tex, InterstellarGui gui, int x, int y) {
		bindTexture(tex.texture);
		gui.getGui().drawTexturedModalRect(x, y, tex.startX, tex.startY, tex.width, tex.height);
	}

	public static void renderTexLocationScaled(TextureLoc tex, InterstellarGui gui, int x, int y, int w, int h) {
		bindTexture(tex.texture);
		gui.getGui();
		Gui.drawScaledCustomSizeModalRect(x, y, tex.startX, tex.startY, tex.width, tex.height, w, h, 256.0F, 256.0F);
	}

	public static void renderSizableBox(SizableBox sb, InterstellarGui gui, int x, int y, int w, int h) {
		bindTexture(sb.getTexture());
		if (sb.getMiddleRepeatable()) {
			renderSizableBoxRepeatableMiddle(sb, gui, x, y, w, h);
		} else {
			renderSizableBoxStretchableMiddle(sb, gui, x, y, w, h);
		}
	}

	private static void bindTexture(ResourceLocation res) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(res);
	}

	private static void renderSizableBoxStretchableMiddle(SizableBox sb, InterstellarGui gui, int x, int y, int w, int h) {
		int middleW = w - 2 * sb.getUnitX();
		int middleH = h - 2 * sb.getUnitY();
		int tsy = y;
		int msy = y + sb.getUnitY();
		int bsy = y + h - 1 * sb.getUnitY();
		int lsx = x;
		int msx = x + sb.getUnitX();
		int rsx = x + w - 1 * sb.getUnitX();
		// Gui guic = gui.getGui();
		TextureLoc topl = sb.getTextureLoc(SizableBox.BoxLocation.TOP_LEFT);
		Gui.drawModalRectWithCustomSizedTexture(lsx, tsy, topl.startX, topl.startY, topl.width, topl.height, topl.getTexW(), topl.getTexH());
		TextureLoc topm = sb.getTextureLoc(SizableBox.BoxLocation.TOP_MID);
		Gui.drawScaledCustomSizeModalRect(msx, tsy, topm.startX, topm.startY, topm.width, topm.height, middleW, sb.getUnitY(), topm.getTexW(), topm.getTexH());
		TextureLoc topr = sb.getTextureLoc(SizableBox.BoxLocation.TOP_RIGHT);
		Gui.drawModalRectWithCustomSizedTexture(rsx, tsy, topr.startX, topr.startY, topr.width, topr.height, topr.getTexW(), topr.getTexH());
		TextureLoc midl = sb.getTextureLoc(SizableBox.BoxLocation.MID_LEFT);
		Gui.drawScaledCustomSizeModalRect(lsx, msy, midl.startX, midl.startY, midl.width, midl.height, sb.getUnitX(), middleH, midl.getTexW(), midl.getTexH());
		TextureLoc midm = sb.getTextureLoc(SizableBox.BoxLocation.MID);
		Gui.drawScaledCustomSizeModalRect(msx, msy, midm.startX, midm.startY, midm.width, midm.height, middleW, middleH, midm.getTexW(), midm.getTexH());
		TextureLoc midr = sb.getTextureLoc(SizableBox.BoxLocation.MID_RIGHT);
		Gui.drawScaledCustomSizeModalRect(rsx, msy, midr.startX, midr.startY, midr.width, midr.height, sb.getUnitX(), middleH, midr.getTexW(), midr.getTexH());
		TextureLoc botl = sb.getTextureLoc(SizableBox.BoxLocation.BOT_LEFT);
		Gui.drawModalRectWithCustomSizedTexture(lsx, bsy, botl.startX, botl.startY, botl.width, botl.height, botl.getTexW(), botl.getTexH());
		TextureLoc botm = sb.getTextureLoc(SizableBox.BoxLocation.BOT_MID);
		Gui.drawScaledCustomSizeModalRect(msx, bsy, botm.startX, botm.startY, botm.width, botm.height, middleW, sb.getUnitY(), botm.getTexW(), botm.getTexH());
		TextureLoc botr = sb.getTextureLoc(SizableBox.BoxLocation.BOT_RIGHT);
		Gui.drawModalRectWithCustomSizedTexture(rsx, bsy, botr.startX, botr.startY, botr.width, botr.height, botr.getTexW(), botr.getTexH());
	}

	private static void renderSizableBoxRepeatableMiddle(SizableBox sb, InterstellarGui gui, int x, int y, int w, int h) {
	}

	public static void renderGuiFluid(FluidStack fluid, float scale, int x, int y, int wt, int maxHt) {
		if (fluid == null || fluid.getFluid() == null || fluid.amount <= 0) {
			return;
		}
		TextureAtlasSprite sprite = getStillTexture(fluid);
		if (sprite == null) {
			return;
		}
		int rendHeight = (int) Math.max(Math.min(maxHt, maxHt * scale), 1.0F);
		int yPos = y + maxHt - rendHeight;
		bindTexture(BLOCKS_TEX);
		int fluidColor = fluid.getFluid().getColor(fluid);
		GL11.glColor3ub((byte) (fluidColor >> 16 & 0xFF), (byte) (fluidColor >> 8 & 0xFF), (byte) (fluidColor & 0xFF));
		GlStateManager.enableBlend();
		for (int i = 0; i < wt; i += 16) {
			for (int j = 0; j < rendHeight; j += 16) {
				int dwt = Math.min(wt - i, 16);
				int dht = Math.min(rendHeight - j, 16);
				int dx = x + i;
				int dy = yPos + j;
				double minU = sprite.getMinU();
				double maxU = sprite.getMaxU();
				double minV = sprite.getMinV();
				double maxV = sprite.getMaxV();
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder tes = tessellator.getBuffer();
				tes.begin(7, DefaultVertexFormats.POSITION_TEX);
				tes.pos(dx, (dy + dht), 0.0D).tex(minU, minV + (maxV - minV) * dht / 16.0D).endVertex();
				tes.pos((dx + dwt), (dy + dht), 0.0D).tex(minU + (maxU - minU) * dwt / 16.0D, minV + (maxV - minV) * dht / 16.0D).endVertex();
				tes.pos((dx + dwt), dy, 0.0D).tex(minU + (maxU - minU) * dwt / 16.0D, minV).endVertex();
				tes.pos(dx, dy, 0.0D).tex(minU, minV).endVertex();
				tessellator.draw();
			}
		}
		GlStateManager.disableBlend();
	}

	public static TextureAtlasSprite getStillTexture(FluidStack fluid) {
		if (fluid == null || fluid.getFluid() == null) {
			return null;
		}
		return getStillTexture(fluid.getFluid());
	}

	public static TextureAtlasSprite getStillTexture(Fluid fluid) {
		ResourceLocation tex = fluid.getStill();
		if (tex == null) {
			return null;
		}
		return Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(tex.toString());
	}
}
