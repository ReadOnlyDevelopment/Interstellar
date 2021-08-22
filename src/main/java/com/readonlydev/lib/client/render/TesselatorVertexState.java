package com.readonlydev.lib.client.render;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TesselatorVertexState {

	private int[] rawBuffer;
	private int rawBufferIndex;
	private int vertexCount;
	private boolean hasTexture;
	private boolean hasBrightness;
	private boolean hasNormals;
	private boolean hasColor;

	public TesselatorVertexState(int[] rawBuffer, int rawBufferIndex, int vertexCount, boolean hasTexture,
			boolean hasBrightness, boolean hasNormals, boolean hasColor) {
		this.rawBuffer = rawBuffer;
		this.rawBufferIndex = rawBufferIndex;
		this.vertexCount = vertexCount;
		this.hasTexture = hasTexture;
		this.hasBrightness = hasBrightness;
		this.hasNormals = hasNormals;
		this.hasColor = hasColor;
	}

	public int[] getRawBuffer() {
		return this.rawBuffer;
	}

	public int getRawBufferIndex() {
		return this.rawBufferIndex;
	}

	public int getVertexCount() {
		return this.vertexCount;
	}

	public boolean getHasTexture() {
		return this.hasTexture;
	}

	public boolean getHasBrightness() {
		return this.hasBrightness;
	}

	public boolean getHasNormals() {
		return this.hasNormals;
	}

	public boolean getHasColor() {
		return this.hasColor;
	}
}
