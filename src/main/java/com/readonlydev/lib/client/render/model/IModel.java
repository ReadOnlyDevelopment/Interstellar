package com.readonlydev.lib.client.render.model;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModel {

	String getType();

	@SideOnly(Side.CLIENT)
	void renderAll();

	@SideOnly(Side.CLIENT)
	void renderOnly(String... groupNames);

	@SideOnly(Side.CLIENT)
	void renderPart(String partName);

	@SideOnly(Side.CLIENT)
	void renderAllExcept(String... excludedGroupNames);
}
