package com.readonlydev.lib.client.render;

import java.util.PriorityQueue;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomTessellator {

	public static boolean renderingWorldRenderer = false;
	public boolean defaultTexture = false;
	private int rawBufferSize = 0;
	public int textureID = 0;
	private int[] rawBuffer;
	private int vertexCount;
	private boolean hasColor;
	private boolean hasTexture;
	private boolean hasBrightness;
	private boolean hasNormals;
	private int rawBufferIndex;
	private double xOffset;
	private double yOffset;
	private double zOffset;
	public static final CustomTessellator instance = new CustomTessellator(2097152);

	private CustomTessellator(int p_i1250_1_) {
	}

	public CustomTessellator() {
	}

	static {
		instance.defaultTexture = true;
	}

	/**
	 * Draws the data set up in this tessellator and resets the state to prepare for new drawing.
	 */
	public int draw() {
		hasColor = false;
		hasNormals = false;
		this.xOffset = 0;
		this.yOffset = 0;
		this.zOffset = 0;
		Tessellator.getInstance().draw();
		return 1;
	}

	public TesselatorVertexState getVertexState(float x, float y, float z) {
		int[] aint = new int[this.rawBufferIndex];
		PriorityQueue<Integer> priorityqueue = new PriorityQueue<>(this.rawBufferIndex, new QuadComparator(this.rawBuffer, x + (float) this.xOffset, y + (float) this.yOffset, z + (float) this.zOffset));
		byte b0 = 32;
		int i;
		for (i = 0; i < this.rawBufferIndex; i += b0) {
			priorityqueue.add(i);
		}
		for (i = 0; !priorityqueue.isEmpty(); i += b0) {
			int j = (priorityqueue.remove());
			for (int k = 0; k < b0; ++k) {
				aint[i + k] = this.rawBuffer[j + k];
			}
		}
		System.arraycopy(aint, 0, this.rawBuffer, 0, aint.length);
		return new TesselatorVertexState(aint, this.rawBufferIndex, this.vertexCount, this.hasTexture, this.hasBrightness, this.hasNormals, this.hasColor);
	}

	public void setVertexState(TesselatorVertexState state) {
		while (state.getRawBuffer().length > rawBufferSize && rawBufferSize > 0) {
			rawBufferSize <<= 1;
		}
		if (rawBufferSize > rawBuffer.length) {
			rawBuffer = new int[rawBufferSize];
		}
		System.arraycopy(state.getRawBuffer(), 0, this.rawBuffer, 0, state.getRawBuffer().length);
		this.rawBufferIndex = state.getRawBufferIndex();
		this.vertexCount = state.getVertexCount();
		this.hasTexture = state.getHasTexture();
		this.hasBrightness = state.getHasBrightness();
		this.hasColor = state.getHasColor();
		this.hasNormals = state.getHasNormals();
	}

	/**
	 * Sets draw mode in the tessellator to draw quads.
	 */
	public void startDrawingQuads() {
		this.startDrawing(7);
	}

	/**
	 * Resets tessellator state and prepares for drawing (with the specified draw mode).
	 */
	public void startDrawing(int glMode) {
		Tessellator.getInstance().getBuffer().begin(glMode, DefaultVertexFormats.POSITION_TEX_NORMAL);
	}

	private int r, g, b, a;

	/**
	 * Adds a vertex specifying both x,y,z and the texture u,v for it.
	 */
	public void addVertexWithUV(double x, double y, double z, double u, double v) {
		BufferBuilder buf = Tessellator.getInstance().getBuffer();
		buf.pos(x + xOffset, y + yOffset, z + zOffset).tex(u, v);
		if (hasColor) {
			buf.color(r, g, b, a);
		}
		if (hasNormals) {
			buf.normal(normalTestX, normalTestY, normalTestZ);
		}
		buf.endVertex();
	}

	/**
	 * Adds a vertex with the specified x,y,z to the current draw call. It will trigger a draw() if the buffer gets full.
	 */
	public void addVertex(double x, double y, double z) {
		BufferBuilder buf = Tessellator.getInstance().getBuffer();
		buf.pos(x + xOffset, y + yOffset, z + zOffset);
		if (hasColor) {
			buf.color(r, g, b, a);
		}
		if (hasNormals) {
			buf.normal(normalTestX, normalTestY, normalTestZ);
		}
		buf.endVertex();
	}

	private float normalTestX, normalTestY, normalTestZ;

	/**
	 * Sets the normal for the current draw call.
	 */
	public void setNormal(float x, float y, float z) {
		this.hasNormals = true;
		// byte b0 = (byte)((int)(p_78375_1_ * 127.0F));
		// byte b1 = (byte)((int)(p_78375_2_ * 127.0F));
		// byte b2 = (byte)((int)(p_78375_3_ * 127.0F));
		// this.normal = b0 & 255 | (b1 & 255) << 8 | (b2 & 255) << 16;
		normalTestX = x;
		normalTestY = y;
		normalTestZ = z;
	}

	/**
	 * Sets the translation for all vertices in the current draw call.
	 */
	public void setTranslation(double p_78373_1_, double p_78373_3_, double p_78373_5_) {
		this.xOffset = p_78373_1_;
		this.yOffset = p_78373_3_;
		this.zOffset = p_78373_5_;
	}

	/**
	 * Offsets the translation for all vertices in the current draw call.
	 */
	public void addTranslation(float p_78372_1_, float p_78372_2_, float p_78372_3_) {
		this.xOffset += p_78372_1_;
		this.yOffset += p_78372_2_;
		this.zOffset += p_78372_3_;
	}
}
