package com.readonlydev.lib.collection;

import java.security.InvalidParameterException;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class EntityMatchingList extends AbstractMatchingList<Entity> {

	@SafeVarargs
	public static <T> ItemMatchingList createWhitelist(boolean allowUserToChangeType, T... defaultValues) {
		T type = defaultValues[0];
		if (type instanceof String) {
			String[] stringArray = (String[]) ArrayUtils.clone(defaultValues);
			return new ItemMatchingList(true, allowUserToChangeType, stringArray);
		} else {
			throw new InvalidParameterException("defaultValues must be instanceof String");
		}
	}

	@SafeVarargs
	public static <T> ItemMatchingList createBlacklist(boolean allowUserToChangeType, T... defaultValues) {
		T type = defaultValues[0];
		if (type instanceof String) {
			String[] stringArray = (String[]) ArrayUtils.clone(defaultValues);
			return new ItemMatchingList(false, allowUserToChangeType, stringArray);
		} else {
			throw new InvalidParameterException("defaultValues must be instanceof String");
		}
	}

	EntityMatchingList(boolean whitelist, boolean allowUserToChangeType, String... defaultValues) {
		super(whitelist, allowUserToChangeType, defaultValues);
	}

	@Override
	protected boolean contains(Entity entity) {
		ResourceLocation resource = EntityList.getKey(entity);
		if (resource == null) {
			return false;
		}
		String id = resource.toString();
		String idOld = EntityList.getEntityString(entity);

		return getList().stream().anyMatch(entry -> keyMatches(entry, id) || entry.equalsIgnoreCase(idOld) || entry.equalsIgnoreCase("minecraft:" + id));
	}
}
