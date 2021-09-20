package com.readonlydev.lib.collection;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * Can be used as a blacklist/whitelist for Items. Matches to the Item's registry name.
 */
public class ItemMatchingList extends AbstractMatchingList<Item> {

	@SafeVarargs
	public static <T> ItemMatchingList createWhitelist(boolean allowUserToChangeType, T... defaultValues) {
		T type = defaultValues[0];
		if (type instanceof String) {
			String[] stringArray = (String[]) ArrayUtils.clone(defaultValues);
			return new ItemMatchingList(true, allowUserToChangeType, stringArray);
		} else if (type instanceof Item) {
			Item[] itemArray = (Item[]) ArrayUtils.clone(defaultValues);
			return new ItemMatchingList(true, allowUserToChangeType, itemArray);
		} else {
			throw new InvalidParameterException("defaultValues must be instanceof String or Item");
		}
	}

	@SafeVarargs
	public static <T> ItemMatchingList createBlacklist(boolean allowUserToChangeType, T... defaultValues) {
		T type = defaultValues[0];
		if (type instanceof String) {
			String[] stringArray = (String[]) ArrayUtils.clone(defaultValues);
			return new ItemMatchingList(false, allowUserToChangeType, stringArray);
		} else if (type instanceof Item) {
			Item[] itemArray = (Item[]) ArrayUtils.clone(defaultValues);
			return new ItemMatchingList(false, allowUserToChangeType, itemArray);
		} else {
			throw new InvalidParameterException("defaultValues must be instanceof String or Item");
		}
	}

	ItemMatchingList(boolean whitelist, boolean allowUserToChangeType, String... defaultValues) {
		super(whitelist, allowUserToChangeType, defaultValues);
	}

	ItemMatchingList(boolean whitelist, boolean allowUserToChangeType, Item... defaultValues) {
		super(whitelist, allowUserToChangeType, Arrays.stream(defaultValues).map(Item::getRegistryName).filter(Objects::nonNull).map(ResourceLocation::toString).toArray(String[]::new));
	}

	@Override
	protected boolean contains(Item item) {
		ResourceLocation name = item.getRegistryName();
		return name != null && containsKey(name.toString());
	}
}
