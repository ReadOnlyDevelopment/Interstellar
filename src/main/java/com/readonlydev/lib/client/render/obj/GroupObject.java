package com.readonlydev.lib.client.render.obj;

import java.util.ArrayList;

import com.readonlydev.lib.client.render.CustomTessellator;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GroupObject {
	public String name;
	public ArrayList<Face> faces = new ArrayList<>();
	public int glDrawingMode;

	public GroupObject() {
		this("");
	}

	public GroupObject(String name) {
		this(name, -1);
	}

	public GroupObject(String name, int glDrawingMode) {
		this.name = name;
		this.glDrawingMode = glDrawingMode;
	}

	@SideOnly(Side.CLIENT)
	public void render() {
		if (faces.size() > 0) {

			CustomTessellator tessellator = CustomTessellator.instance;
			tessellator.startDrawing(glDrawingMode);
			render(tessellator);
			tessellator.draw();
		}
	}

	@SideOnly(Side.CLIENT)
	public void render(CustomTessellator tessellator) {
		if (faces.size() > 0) {
			for (Face face : faces) {
				face.addFaceForRender(tessellator);
			}
		}
	}
}
