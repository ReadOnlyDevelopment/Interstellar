package com.readonlydev.lib.client.render.obj;

import com.readonlydev.lib.client.render.model.IModel;
import com.readonlydev.lib.client.render.model.IModelLoader;
import com.readonlydev.lib.client.render.model.InvalidModelFormatException;

import net.minecraft.util.ResourceLocation;

public class ObjLoader implements IModelLoader {

	private static final String[] types = { "obj" };

	@Override
	public String getType() {
		return "OBJ model";
	}

	@Override
	public String[] getSuffixes() {
		return types;
	}

	@Override
	public IModel loadInstance(ResourceLocation resource) throws InvalidModelFormatException {

		return new WavefrontObj(resource);
	}

}
