package com.readonlydev.lib.client.render.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.readonlydev.lib.Interstellar;
import com.readonlydev.lib.client.render.obj.ObjLoader;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelLoader {
	private static Map<String, IModelLoader> instances = new HashMap<>();

	/**
	 * Register a new model handler
	 *
	 * @param modelHandler The model handler to register
	 */
	public static void registerModelHandler(IModelLoader modelHandler) {
		for (String suffix : modelHandler.getSuffixes()) {
			instances.put(suffix, modelHandler);
		}
	}

	/**
	 * Load the model from the supplied classpath resolvable resource name
	 *
	 * @param resource The resource name
	 * @return A model
	 * @throws IllegalArgumentException if the resource name cannot be understood
	 * @throws ModelFormatException     if the underlying model handler cannot parse
	 *                                  the model format
	 */
	public static IModel loadModel(ResourceLocation resource)
			throws IllegalArgumentException, InvalidModelFormatException {
		String name = resource.getResourcePath();
		int i = name.lastIndexOf('.');
		if (i == -1) {
			Interstellar.log.error("The resource name %s is not valid", resource);
			throw new IllegalArgumentException("The resource name is not valid");
		}
		String suffix = name.substring(i + 1);
		IModelLoader loader = instances.get(suffix);
		if (loader == null) {
			Interstellar.log.error("The resource name %s is not supported", resource);
			throw new IllegalArgumentException("The resource name is not supported");
		}

		return loader.loadInstance(resource);
	}

	public static Collection<String> getSupportedSuffixes() {
		return instances.keySet();
	}

	static {
		registerModelHandler(new ObjLoader());
	}
}
