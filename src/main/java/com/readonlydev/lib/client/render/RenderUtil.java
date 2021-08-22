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

package com.readonlydev.lib.client.render;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderUtil {

	public static void guiScissorStart(Minecraft mc, int x, int y, int w, int h) {
		GL11.glEnable(3089);
		ScaledResolution r = new ScaledResolution(mc);
		int f = r.getScaleFactor();
		FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
		GL11.glGetFloat(2982, floatBuffer);
		floatBuffer.rewind();
		Matrix4f matrix = new Matrix4f();
		matrix.load(floatBuffer);
		int nx = (int) ((x * f) * matrix.m00 + matrix.m30 * f);
		int ny = (r.getScaledHeight() - (int) ((y + h) * matrix.m11 + matrix.m31)) * f;
		int nw = (int) ((w * f) * matrix.m00);
		int nh = (int) ((h * f) * matrix.m11);
		GL11.glScissor(nx, ny, nw, nh);
	}

	public static void guiScissorEnd(Minecraft mc, int x, int y, int w, int h) {
		GL11.glDisable(3089);
	}

	public static void renderCube(Tessellator tessellator, BufferBuilder buffer, double x1, double y1, double z1, double x2, double y2, double z2, int r, int g, int b) {
		GlStateManager.glLineWidth(2.0F);
		buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		buffer.pos(x1, y1, z1).color(r, r, r, 0.0F).endVertex();
		buffer.pos(x1, y1, z1).color(r, r, r, b).endVertex();
		buffer.pos(x2, y1, z1).color(r, g, g, b).endVertex();
		buffer.pos(x2, y1, z2).color(r, r, r, b).endVertex();
		buffer.pos(x1, y1, z2).color(r, r, r, b).endVertex();
		buffer.pos(x1, y1, z1).color(g, g, r, b).endVertex();
		buffer.pos(x1, y2, z1).color(g, r, g, b).endVertex();
		buffer.pos(x2, y2, z1).color(r, r, r, b).endVertex();
		buffer.pos(x2, y2, z2).color(r, r, r, b).endVertex();
		buffer.pos(x1, y2, z2).color(r, r, r, b).endVertex();
		buffer.pos(x1, y2, z1).color(r, r, r, b).endVertex();
		buffer.pos(x1, y2, z2).color(r, r, r, b).endVertex();
		buffer.pos(x1, y1, z2).color(r, r, r, b).endVertex();
		buffer.pos(x2, y1, z2).color(r, r, r, b).endVertex();
		buffer.pos(x2, y2, z2).color(r, r, r, b).endVertex();
		buffer.pos(x2, y2, z1).color(r, r, r, b).endVertex();
		buffer.pos(x2, y1, z1).color(r, r, r, b).endVertex();
		buffer.pos(x2, y1, z1).color(r, r, r, 0.0F).endVertex();
		tessellator.draw();
		GlStateManager.glLineWidth(1.0F);
	}
}
