package com.readonlydev.lib.celestial.data;

import java.awt.Color;

import com.readonlydev.lib.client.ColorRGB;

import micdoodle8.mods.galacticraft.api.vector.Vector3;

public enum StellarColorTable {

	O(new ColorArray(155, 176, 255), "#9bb0ff"),
	B(new ColorArray(170, 191, 255), "#aabfff"),
	A(new ColorArray(202, 215, 255), "#cad7ff"),
	F(new ColorArray(248, 247, 255), "#f8f7ff"),
	G(new ColorArray(255, 244, 234), "#fff4ea"),
	K(new ColorArray(255, 210, 161), "#ffd2a1"),
	M(new ColorArray(255, 204, 111), "#ffcc6f");

	ColorArray cArray;
	String hexColor;

	StellarColorTable(ColorArray cArray, String hexColor) {
		this.cArray = cArray;
		this.hexColor = hexColor;
	}

	public Vector3 toVector3() {
		return Vec.fromFloatArray(cArray.toColor().getRGBColorComponents(null)).toVector3();
	}

	public ColorRGB toColorRGB() {
		return ColorRGB.parse(hexColor);
	}

	static class ColorArray {
		int r, g, b;

		ColorArray(int r, int g, int b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}

		Color toColor() {
			return new Color(r, g, b);
		}
	}
}
