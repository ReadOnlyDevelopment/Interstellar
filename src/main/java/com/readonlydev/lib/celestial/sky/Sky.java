package com.readonlydev.lib.celestial.sky;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.readonlydev.lib.client.ColorRGB;
import com.readonlydev.lib.world.world.WorldProviderExoplanet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.client.FMLClientHandler;

public abstract class Sky extends IRenderHandler {

	protected final Minecraft CLIENT = FMLClientHandler.instance().getClient();

	protected void renderPlanet(ResourceLocation image, float x, float y, float z, float size) {
		renderPlanet(image, x, y, z, size, CLIENT.world.getStarBrightness(1.0F));
	}

	protected void renderPlanet(ResourceLocation image, float x, float y, float z, float size, float alpha) {
		renderPlanet(image, x, y, z, size, alpha, 0.0F);
	}

	protected void renderPlanet(ResourceLocation image, float x, float y, float z, float size, float alpha, float shadow) {
		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		GL11.glRotatef(x, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(y, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(z, 0.0F, 0.0F, 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
		CLIENT.renderEngine.bindTexture(image);
		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(-size, -100.0D, size).tex(0.0D, 1.0D).endVertex();
		buffer.pos(size, -100.0D, size).tex(1.0D, 1.0D).endVertex();
		buffer.pos(size, -100.0D, -size).tex(1.0D, 0.0D).endVertex();
		buffer.pos(-size, -100.0D, -size).tex(0.0D, 0.0D).endVertex();
		tessellator.draw();
		if (shadow > 0.0F) {
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			GL11.glColor4f(0.0F, 0.0F, 0.0F, shadow);
			buffer.begin(7, DefaultVertexFormats.POSITION);
			buffer.pos(-size, -100.0D, size).endVertex();
			buffer.pos(size, -100.0D, size).endVertex();
			buffer.pos(size, -100.0D, -size).endVertex();
			buffer.pos(-size, -100.0D, -size).endVertex();
			tessellator.draw();
			GL11.glDisable(3042);
		}
		GL11.glPopMatrix();
	}

	protected void renderStars(int starList) {
		GL11.glPushMatrix();
		GL11.glNewList(starList, GL11.GL_COMPILE);
		this.render();
		GL11.glEndList();
		GL11.glPopMatrix();
	}

	protected void renderSunAura(Tessellator tessellator, BufferBuilder buffer, float f10, ColorRGB colorRGB) {
		float r = colorRGB.getRed();
		float g = colorRGB.getGreen();
		float b = colorRGB.getBlue();
		float a = colorRGB.getAlpha();

		buffer.pos(-f10, 100.0D, -f10).color(r, g, b, a).endVertex();
		buffer.pos(0, 100.0D, (double) -f10 * 1.5F).color(r, g, b, a).endVertex();
		buffer.pos(f10, 100.0D, -f10).color(r, g, b, a).endVertex();
		buffer.pos((double) f10 * 1.5F, 100.0D, 0).color(r, g, b, a).endVertex();
		buffer.pos(f10, 100.0D, f10).color(r, g, b, a).endVertex();
		buffer.pos(0, 100.0D, (double) f10 * 1.5F).color(r, g, b, a).endVertex();
		buffer.pos(-f10, 100.0D, f10).color(r, g, b, a).endVertex();
		buffer.pos((double) -f10 * 1.5F, 100.0D, 0).color(r, g, b, a).endVertex();
		buffer.pos(-f10, 100.0D, -f10).color(r, g, b, a).endVertex();

		tessellator.draw();
	}

	protected void renderLargerSunAura(Tessellator tessellator, BufferBuilder buffer, float f10, ColorRGB colorRGB) {
		float r = colorRGB.getRed();
		float g = colorRGB.getGreen();
		float b = colorRGB.getBlue();
		float a = colorRGB.getAlpha();

		buffer.pos(-f10, 100.0D, -f10).color(r, g, b, a).endVertex();
		buffer.pos(0, 100.0D, (double) -f10 * 1.5F).color(r, g, b, a).endVertex();
		buffer.pos(f10, 100.0D, -f10).color(r, g, b, a).endVertex();
		buffer.pos((double) f10 * 1.5F, 100.0D, 0).color(r, g, b, a).endVertex();
		buffer.pos(f10, 100.0D, f10).color(r, g, b, a).endVertex();
		buffer.pos(0, 100.0D, (double) f10 * 1.5F).color(r, g, b, a).endVertex();
		buffer.pos(-f10, 100.0D, f10).color(r, g, b, a).endVertex();
		buffer.pos((double) -f10 * 1.5F, 100.0D, 0).color(r, g, b, a).endVertex();
		buffer.pos(-f10, 100.0D, -f10).color(r, g, b, a).endVertex();

		tessellator.draw();
	}

	private void render() {
		final Random rand = new Random(10842L);
		final Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		for (int starIndex = 0; starIndex < 35000; ++starIndex) {
			double var4 = rand.nextFloat() * 2.0F - 1.0F;
			double var6 = rand.nextFloat() * 2.0F - 1.0F;
			double var8 = rand.nextFloat() * 2.0F - 1.0F;
			final double var10 = 0.15F + rand.nextFloat() * 0.1F;
			double var12 = var4 * var4 + var6 * var6 + var8 * var8;
			if (var12 < 1.0D && var12 > 0.01D) {
				var12 = 1.0D / Math.sqrt(var12);
				var4 *= var12;
				var6 *= var12;
				var8 *= var12;
				final double x = var4 * rand.nextDouble() * 150D + 130D;
				final double y = var6 * rand.nextDouble() * 150D + 130D;
				final double z = var8 * rand.nextDouble() * 150D + 130D;
				final double var20 = Math.atan2(var4, var8);
				final double var22 = Math.sin(var20);
				final double var24 = Math.cos(var20);
				final double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
				final double var28 = Math.sin(var26);
				final double var30 = Math.cos(var26);
				final double var32 = rand.nextDouble() * Math.PI * 2.0D;
				final double var34 = Math.sin(var32);
				final double var36 = Math.cos(var32);
				for (int var38 = 0; var38 < 4; ++var38) {
					final double var39 = 0.0D;
					final double var41 = ((var38 & 2) - 1) * var10;
					final double var43 = ((var38 + 1 & 2) - 1) * var10;
					final double var47 = var41 * var36 - var43 * var34;
					final double var49 = var43 * var36 + var41 * var34;
					final double yY = var47 * var28 + var39 * var30;
					final double var55 = var39 * var28 - var47 * var30;
					final double xX = var55 * var22 - var49 * var24;
					final double zZ = var49 * var22 + var55 * var24;
					buffer.pos(x + xX, y + yY, z + zZ).endVertex();
				}
			}
		}
		tessellator.draw();
	}

	protected long getDayLength() {
		long daylength = 24000L;
		if (this.CLIENT.world.provider instanceof WorldProviderExoplanet) {
			WorldProviderExoplanet exoplanetProvider = (WorldProviderExoplanet) this.CLIENT.world.provider;
			daylength = exoplanetProvider.getDayLength();
		}
		return daylength;
	}
}
