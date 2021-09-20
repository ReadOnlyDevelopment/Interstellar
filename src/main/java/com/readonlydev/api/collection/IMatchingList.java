package com.readonlydev.api.collection;

import net.minecraftforge.common.config.Configuration;

public interface IMatchingList<T> {
	/**
	 * Check whether the object "matches" the list.<br>
	 * This is not the same as being "contained" in the list, as this depends on whether we are a whitelist or a blacklist.
	 *
	 * @param t The object being checked
	 * @return Returns true if the list is a whitelist and contains the object,<br>
	 *         or if the list is a blacklist and does not contain the object, false otherwise.
	 */
	boolean matches(T t);

	/**
	 * Load properties of the list from the config file.
	 *
	 * @param config   The mod's configuration object
	 * @param name     The base name of the config options. Implementations of loadConfig will likely append text to this.
	 * @param category The config category
	 * @param comment  A comment for the config
	 */
	void loadConfig(Configuration config, String name, String category, String comment);
}
