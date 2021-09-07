package com.readonlydev.lib.base.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.readonlydev.lib.base.InterstellarMod;
import com.readonlydev.lib.base.config.impl.Category;
import com.readonlydev.lib.base.config.values.ConfigArrayDouble;
import com.readonlydev.lib.base.config.values.ConfigArrayInteger;
import com.readonlydev.lib.base.config.values.ConfigArrayString;
import com.readonlydev.lib.base.config.values.ConfigBoolean;
import com.readonlydev.lib.base.config.values.ConfigDouble;
import com.readonlydev.lib.base.config.values.ConfigInteger;
import com.readonlydev.lib.base.config.values.ConfigString;
import com.readonlydev.lib.base.config.values.ConfigValue;
import com.readonlydev.lib.version.Version;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class ReadOnlyConfig {

	private final String fileName;
	private File configFile;
	private ConfigVersion Configversion;
	private final InterstellarMod clazz;
	protected List<ConfigValue> properties = new ArrayList<>();
	protected List<String> propOrder = new ArrayList<>();
	protected List<Category> configCats = new ArrayList<>();

	private Configuration config;

	protected ReadOnlyConfig(InterstellarMod clazz, String fileName) {
		this.fileName = fileName;
		this.clazz = clazz;
		MinecraftForge.EVENT_BUS.register(this);
	}

	public File getConfigFile() {
		return configFile;
	}

	public ConfigVersion getConfigversion() {
		return Configversion;
	}

	public void setConfigFile() {
		this.configFile = new File(clazz.getConfigDirectory(), fileName + ".cfg");
	}

	protected void setConfigversion(ConfigVersion configversion) {
		Configversion = configversion;
	}

	protected void addCategory(Category category) {
		this.configCats.add(category);
	}

	protected void addCategories(Category... categories) {
		this.configCats.addAll(Lists.newArrayList(categories));
	}

	public boolean hasProperty(ConfigValue prop) {
		return this.properties.stream().anyMatch(property -> property.category().contentEquals(prop.category()) && property.key().contentEquals(prop.key()));
	}

	@SafeVarargs
	protected final <T extends ConfigValue> void addProperties(T... properties) {
		for (T prop : properties) {
			this.addPropToList(prop.key());
			this.addProp(prop);
		}
	}

	public void loadConfig() {
		config = initConfig();
		try {
			config.load();
			for (Category configCategory : configCats) {
				ConfigCategory category = getCategory(configCategory);
				category.setComment(configCategory.getCatgeoryComment());
				category.setRequiresMcRestart(configCategory.requiresMCRestart);
				category.setRequiresWorldRestart(configCategory.requiresWorldRestart);
				category.setLanguageKey(clazz.getModId() + configCategory.getLangKey());
			}
			for (ConfigValue prop : this.properties) {
				try {
					Property property;
					switch (prop.getType()) {
					case INTEGER:
						ConfigInteger propInt = (ConfigInteger) prop;
						if (propInt.hasRange()) {
							property = config.get(propInt.category(), propInt.key(), propInt.get(), propInt.comment(), propInt.min(), propInt.max()).setLanguageKey(propInt.langKey());
						} else {
							property = config.get(propInt.category(), propInt.key(), propInt.get(), propInt.comment()).setLanguageKey(propInt.langKey());
						}
						if (propInt.needsGameRestart()) {
							property.setRequiresMcRestart(true);
						}
						if (propInt.needsWorldRestart()) {
							property.setRequiresWorldRestart(true);
						}
						propInt.set(property.getInt());
						break;
					case INTEGER_ARRAY:
						ConfigArrayInteger propIntArray = (ConfigArrayInteger) prop;
						if (propIntArray.hasRange()) {
							property = config.get(propIntArray.category(), propIntArray.key(), propIntArray.get(), propIntArray.comment(), propIntArray.min(), propIntArray.max()).setLanguageKey(propIntArray.langKey());
						} else {
							property = config.get(propIntArray.category(), propIntArray.key(), propIntArray.get(), propIntArray.comment()).setLanguageKey(propIntArray.langKey());
						}
						if (propIntArray.needsGameRestart()) {
							property.setRequiresMcRestart(true);
						}
						if (propIntArray.needsWorldRestart()) {
							property.setRequiresWorldRestart(true);
						}
						propIntArray.set(property.getIntList());
						break;
					case DOUBLE:
						ConfigDouble propDouble = (ConfigDouble) prop;
						if (propDouble.hasRange()) {
							property = config.get(propDouble.category(), propDouble.key(), propDouble.get(), propDouble.comment(), propDouble.min(), propDouble.max()).setLanguageKey(propDouble.langKey());
						} else {
							property = config.get(propDouble.category(), propDouble.key(), propDouble.get(), propDouble.comment()).setLanguageKey(propDouble.langKey());
						}
						if (propDouble.needsGameRestart()) {
							property.setRequiresMcRestart(true);
						}
						if (propDouble.needsWorldRestart()) {
							property.setRequiresWorldRestart(true);
						}
						propDouble.set(property.getDouble());
						break;
					case DOUBLE_ARRAY:
						ConfigArrayDouble propDoubleArray = (ConfigArrayDouble) prop;
						if (propDoubleArray.hasRange()) {
							property = config.get(propDoubleArray.category(), propDoubleArray.key(), propDoubleArray.get(), propDoubleArray.comment(), propDoubleArray.min(), propDoubleArray.max()).setLanguageKey(propDoubleArray.langKey());
						} else {
							property = config.get(propDoubleArray.category(), propDoubleArray.key(), propDoubleArray.get(), propDoubleArray.comment()).setLanguageKey(propDoubleArray.langKey());
						}
						if (propDoubleArray.needsGameRestart()) {
							property.setRequiresMcRestart(true);
						}
						if (propDoubleArray.needsWorldRestart()) {
							property.setRequiresWorldRestart(true);
						}
						propDoubleArray.set(property.getDoubleList());
						break;
					case BOOLEAN:
						ConfigBoolean propBool = (ConfigBoolean) prop;
						property = config.get(propBool.category(), propBool.key(), propBool.get(), propBool.comment()).setLanguageKey(propBool.langKey());
						if (propBool.needsGameRestart()) {
							property.setRequiresMcRestart(true);
						}
						if (propBool.needsWorldRestart()) {
							property.setRequiresWorldRestart(true);
						}
						propBool.set(property.getBoolean());
						break;
					case STRING:
						ConfigString propString = (ConfigString) prop;
						if (propString.needsValidation()) {
							property = config.get(propString.category(), propString.key(), propString.get(), propString.comment(), propString.getValidValues(), propString.getValidValuesDisplay()).setLanguageKey(propString.langKey());
						} else {
							property = config.get(propString.category(), propString.key(), propString.get(), propString.comment()).setLanguageKey(propString.langKey());
						}
						if (propString.needsGameRestart()) {
							property.setRequiresMcRestart(true);
						}
						if (propString.needsWorldRestart()) {
							property.setRequiresWorldRestart(true);
						}
						propString.set(property.getString());
						break;
					case STRING_ARRAY:
						ConfigArrayString propStringArray = (ConfigArrayString) prop;
						property = config.get(propStringArray.category(), propStringArray.key(), propStringArray.get(), propStringArray.comment()).setLanguageKey(propStringArray.langKey());
						if (propStringArray.needsGameRestart()) {
							property.setRequiresMcRestart(true);
						}
						if (propStringArray.needsWorldRestart()) {
							property.setRequiresWorldRestart(true);
						}
						propStringArray.set(property.getStringList());
						break;
					}
				} catch (Exception e) {
					clazz.getLogger().error("Issue with Prop: {} of type {}", prop.key(), prop.getType().name());
					clazz.getLogger().catching(e);
				} finally {
					config.setCategoryPropertyOrder(prop.category(), this.propOrder);
				}
			}
		} catch (Exception ignored) {
			clazz.getLogger().error("Uh, we had a problem loading config: {}", config.getConfigFile());
		}
		saveConfig();
	}

	protected Configuration initConfig() {
		return new Configuration(this.configFile, this.Configversion.toString());
	}

	protected void saveConfig() {
		if (config.hasChanged()) {
			config.save();
		}
	}

	public Configuration get() {
		return config;
	}

	public ConfigCategory getCategory(Category category) {
		return config.getCategory(category.get());
	}

	protected List<IConfigElement> getElements() {
		List<IConfigElement> list = new ArrayList<>();
		list.addAll(getElementList());
		return list;
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equalsIgnoreCase(clazz.getModId())) {
			saveConfig();
			loadConfig();
		}
	}

	public static class ConfigVersion extends Version {
		public static final ConfigVersion NULL_VERSION = new ConfigVersion("0.0.0");

		public ConfigVersion(String version) {
			super(version);
		}

		public ConfigVersion(Version version) {
			super(version.toString());
		}

		public ConfigVersion(int major, int minor, int patch, int build) {
			super(of(major) + "." + of(minor) + "." + of(patch) + "." + of(build));
		}

		private static String of(int val) {
			return String.valueOf(val);
		}

		public static boolean isVersionLessOrEqual(ConfigVersion comparate1, ConfigVersion comparate2) {
			Version c1 = new Version(comparate1.toString());
			Version c2 = new Version(comparate2.toString());
			return c1.isEqualTo(c2);
		}
	}

	// PRIVATE UTILTITY METHODS //

	private List<ConfigElement> getElementList() {
		List<ConfigElement> elements = new ArrayList<>();
		for (Category category : configCats) {
			elements.add(new ConfigElement(getCategory(category)));
		}
		return elements;
	}

	private void addPropToList(String propName) {
		for (String name : this.propOrder) {
			if (name.contentEquals(propName)) {
				this.propOrder.remove(name);
				break;
			}
		}
		this.propOrder.add(propName);
	}

	private void addProp(ConfigValue property) {
		for (ConfigValue prop : this.properties) {
			if (prop.key().contentEquals(property.key())) {
				this.properties.remove(prop);
				break;
			}
		}
		this.properties.add(property);
	}
}
