package com.readonlydev.lib.celestial.sky;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.readonlydev.lib.client.ColorRGB;
import com.readonlydev.lib.world.world.WorldProviderExoplanet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ExoplanetSkyProvider extends Sky {

	private ResourceLocation sunTexture;

	private int starList;
	private int glSkyList;
	private int glSkyList2;

	private float sunSize;
	private float[] floatArray = new float[4];
	private ColorRGB rgb;

	public ExoplanetSkyProvider(WorldProviderExoplanet worldProvider) {
		float relativeSize = worldProvider.getPlanet().getParentSolarSystem().getMainStar().getRelativeSize();
		float distance = worldProvider.getPlanet().getRelativeDistanceFromCenter().unScaledDistance;
		this.sunTexture = worldProvider.getPlanet().getParentSolarSystem().getMainStar().getBodyIcon();
		this.sunSize = 1.0F / distance * relativeSize;
		int displayLists = GLAllocation.generateDisplayLists(3);
		this.starList = displayLists;
		this.glSkyList = displayLists + 1;
		this.glSkyList2 = displayLists + 2;

		this.renderStars(this.starList);

		final Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		GL11.glNewList(this.glSkyList, GL11.GL_COMPILE);
		final byte byte2 = 64;
		final int i = 256 / byte2 + 2;
		float f = 16F;
		for (int j = -byte2 * i; j <= byte2 * i; j += byte2) {
			for (int l = -byte2 * i; l <= byte2 * i; l += byte2) {
				buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
				buffer.pos(j, f, l).endVertex();
				buffer.pos(j + byte2, f, l).endVertex();
				buffer.pos(j + byte2, f, l + byte2).endVertex();
				buffer.pos(j, f, l + byte2).endVertex();
				tessellator.draw();
			}
		}
		GL11.glEndList();

		GL11.glNewList(this.glSkyList2, GL11.GL_COMPILE);
		f = -16F;
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		for (int k = -byte2 * i; k <= byte2 * i; k += byte2) {
			for (int i1 = -byte2 * i; i1 <= byte2 * i; i1 += byte2) {
				buffer.pos(k + byte2, f, i1).endVertex();
				buffer.pos(k, f, i1).endVertex();
				buffer.pos(k, f, i1 + byte2).endVertex();
				buffer.pos(k + byte2, f, i1 + byte2).endVertex();
			}
		}
		tessellator.draw();
		GL11.glEndList();
	}

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		Vec3d vec3 = world.getSkyColor(mc.getRenderViewEntity(), partialTicks);
		float f1 = (float) vec3.x;
		float f2 = (float) vec3.y;
		float f3 = (float) vec3.z;
		float f6;
		if (mc.gameSettings.anaglyph) {
			float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
			f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}
		GL11.glColor3f(f1, f2, f3);
		Tessellator tessellator1 = Tessellator.getInstance();
		BufferBuilder buffer = tessellator1.getBuffer();
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glColor3f(f1, f2, f3);
		GL11.glCallList(this.glSkyList);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		RenderHelper.disableStandardItemLighting();
		float f7;
		float f8;
		float f9;
		float f10;

		float f18 = world.getStarBrightness(partialTicks);
		if (f18 > 0.0F) {
			GL11.glPushMatrix();
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-19.0F, 0, 1.0F, 0);
			GL11.glColor4f(f18, f18, f18, f18);
			GL11.glCallList(this.starList);
			GL11.glPopMatrix();
		}
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glPushMatrix();
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
		floatArray[0] = 255 / 255.0F;
		floatArray[1] = 194 / 255.0F;
		floatArray[2] = 180 / 255.0F;
		floatArray[3] = 0.3F;
		f6 = floatArray[0];
		f7 = floatArray[1];
		f8 = floatArray[2];
		float f11;
		if (mc.gameSettings.anaglyph) {
			f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
			f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
			f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
			f6 = f9;
			f7 = f10;
			f8 = f11;
		}
		f18 = 1.0F - f18;

		buffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
		float r = f6 * f18;
		float g = f7 * f18;
		float b = f8 * f18;
		float a = floatArray[3] * 2 / f18;
		buffer.pos(0.0D, 100.0D, 0.0D).color(r, g, b, a).endVertex();
		r = floatArray[0] * f18;
		g = floatArray[1] * f18;
		b = floatArray[2] * f18;
		a = 0.0F;
		//
		{
			rgb = new ColorRGB(r, g, b, a);
			f10 = 20.0F;
			this.renderSunAura(tessellator1, buffer, f10, rgb);
		}
		//
		buffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
		r = f6 * f18;
		g = f7 * f18;
		b = f8 * f18;
		a = floatArray[3] * f18;
		buffer.pos(0.0D, 100.0D, 0.0D).color(r, g, b, a).endVertex();
		r = floatArray[0] * f18;
		g = floatArray[1] * f18;
		b = floatArray[2] * f18;
		a = 0.0F;
		//
		{
			rgb = new ColorRGB(r, g, b, a);
			f10 = 40.0F;
			this.renderLargerSunAura(tessellator1, buffer, f10, rgb);
		}
		//
		GL11.glPopMatrix();
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
		GL11.glPushMatrix();
		f7 = 0.0F;
		f8 = 0.0F;
		f9 = 0.0F;
		GL11.glTranslatef(f7, f8, f9);
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
		// Some blanking to conceal the stars
		f10 = this.sunSize / 3.5F;
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		buffer.pos(-f10, 99.9D, -f10).endVertex();
		buffer.pos(f10, 99.9D, -f10).endVertex();
		buffer.pos(f10, 99.9D, f10).endVertex();
		buffer.pos(-f10, 99.9D, f10).endVertex();
		tessellator1.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		f10 = this.sunSize;
		mc.renderEngine.bindTexture(this.sunTexture);
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(-f10, 100.0D, -f10).tex(0.0D, 0.0D).endVertex();
		buffer.pos(f10, 100.0D, -f10).tex(1.0D, 0.0D).endVertex();
		buffer.pos(f10, 100.0D, f10).tex(1.0D, 1.0D).endVertex();
		buffer.pos(-f10, 100.0D, f10).tex(0.0D, 1.0D).endVertex();
		tessellator1.draw();
	}
}
