/*
 * Library License
 *
 * Copyright (c) 2021 ReadOnly Development
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.readonlydev.lib.base;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.readonlydev.api.mod.IEventListener;
import com.readonlydev.api.mod.IIDProvider;
import com.readonlydev.api.mod.phase.IInitPhase;
import com.readonlydev.api.mod.phase.IPostInitPhase;
import com.readonlydev.api.mod.phase.IPreInitPhase;
import com.readonlydev.api.mod.phase.Step;
import com.readonlydev.api.proxy.IProxy;
import com.readonlydev.lib.base.config.ReadOnlyConfig;
import com.readonlydev.lib.base.phase.PhaseSets;
import com.readonlydev.lib.celestial.misc.CelestialAsset;
import com.readonlydev.lib.registry.IEntryClass;
import com.readonlydev.lib.registry.InterstellarRegistry;
import com.readonlydev.lib.version.Version;

import lombok.Data;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Data
public abstract class InterstellarMod implements IIDProvider {

	public static final KeyReference<String> KEY_VERSION = KeyReference.of("version", String.class);
	public static final KeyReference<Boolean> KEY_RETROGEN = KeyReference.of("retrogen", Boolean.class);

	private final String modId, modName;
	private final Version version;
	private final Logger logger;

	private final Set<IEventListener> eventListeners = new HashSet<>();

	private final PhaseSets phaseSets = new PhaseSets();

	private final Set<ReadOnlyConfig> configFiles = new HashSet<>();
	private final Map<KeyReference<?>, Object> keyReference = new HashMap<>();
	private final CelestialAsset celestialAssetClass;
	private File configDirectory;

	protected final InterstellarRegistry registry;

	private IProxy proxy;
	private boolean checkForUpdates = false;

	public InterstellarMod(Class<? extends InterstellarMod> clazz) {
		Mod annotation = clazz.getAnnotation(Mod.class);
		this.modId = annotation.modid();
		this.modName = annotation.name();
		this.version = new Version(annotation.version());
		putKeyReference(KEY_VERSION, annotation.version());
		this.logger = LogManager.getLogger(annotation.name());
		this.registry = new InterstellarRegistry();
		this.celestialAssetClass = new CelestialAsset(this);
	}

	public InterstellarMod enableUpdateChecks() {
		this.checkForUpdates = true;
		return this;
	}

	/**
	 * Save a mod value.
	 *
	 * @param key The key.
	 * @param value The value.
	 * @param <T> The value type.
	 */
	public <T> void putKeyReference(KeyReference<T> key, T value) {
		keyReference.put(key, value);
	}

	public <T> T getKeyReference(KeyReference<T> key) {
		if (!keyReference.containsKey(key)) {
			throw new IllegalArgumentException("Could not find " + key + " as generic reference item.");
		}
		return key.getType().cast(keyReference.get(key));
	}

	public <T> void addPhaseListener(T listener) {
		if (listener instanceof IPreInitPhase) {
			phaseSets.addPreInitListener((IPreInitPhase) listener);
		}
		if (listener instanceof IInitPhase) {
			phaseSets.addInitListener((IInitPhase) listener);
		}
		if (listener instanceof IPostInitPhase) {
			phaseSets.addPostInitListener((IPostInitPhase) listener);
		}
	}

	/**
	 * Register a new init listener.
	 *
	 * @param initListener The init listener.
	 */
	public void addEventListeners(IEventListener listner) {
		synchronized (eventListeners) {
			eventListeners.add(listner);
		}
	}

	/**
	 * Get the init-listeners on a thread-safe way;
	 *
	 * @return A copy of the init listeners list.
	 */
	private Set<IEventListener> getSafeEventListeners() {
		Set<IEventListener> clonedInitListeners;
		synchronized (eventListeners) {
			clonedInitListeners = Sets.newHashSet(eventListeners);
		}
		return clonedInitListeners;
	}

	/**
	 * Call the init-listeners for the given step.
	 *
	 * @param step The step of initialization.
	 */
	protected void callEventStepListeners(Step step) {
		for (IEventListener listener : getSafeEventListeners()) {
			listener.on(step);
		}
	}

	/**
	 * Log a new info message for this mod.
	 *
	 * @param message The message to show.
	 */
	public void log(String message) {
		log(Level.INFO, message);
	}

	/**
	 * Log a new message of the given level for this mod.
	 *
	 * @param level The level in which the message must be shown.
	 * @param message The message to show.
	 */
	public void log(Level level, String message) {
		logger.log(level, message);
	}

	public void onPreInit(FMLPreInitializationEvent event) {
		log(Level.TRACE, "preInit()");
		configDirectory = new File(event.getModConfigurationDirectory(), modName);
		handleConfigSet();
		registry.setMod(getModObject());
		registry.getRecipeBuilder();

		// Now call PreInit Phase listeners
		phaseSets.callPreInitPhaseListeners(Step.PRE);

		phaseSets.callPreInitPhaseListeners(Step.INIT);

		phaseSets.callPreInitPhaseListeners(Step.POST);

		// call event listeners
		callEventStepListeners(Step.PRE);
	}

	public void onInit(FMLInitializationEvent event) {
		log(Level.TRACE, "init()");

		// Now call init Phase listeners
		phaseSets.callInitPhaseListeners(Step.PRE);

		phaseSets.callInitPhaseListeners(Step.INIT);

		phaseSets.callInitPhaseListeners(Step.POST);

		// call event listeners
		callEventStepListeners(Step.INIT);
	}

	public void onPostInit(FMLPostInitializationEvent event) {
		log(Level.TRACE, "postInit()");

		// Now call PreInit Phase listeners
		phaseSets.callPostInitPhaseListeners(Step.PRE);

		phaseSets.callPostInitPhaseListeners(Step.INIT);

		phaseSets.callPostInitPhaseListeners(Step.POST);

		// call event listeners
		callEventStepListeners(Step.POST);
	}

	public boolean hasAvailableUpdate() {
		Version currVersion = new Version(getKeyReference(KEY_VERSION));
		Version latestVersion = getVersion();
		if (latestVersion == null || "${VERSION}".equals(currVersion.toString())) {
			return false;
		}
		return currVersion.isLowerThan(latestVersion);
	}

	protected <T extends IForgeRegistryEntry<T>> void addContentClass(IEntryClass<T> registryClazz) {
		registry.addRegistrationHandler(registryClazz::register, registryClazz.getEntry());
	}

	protected void configFiles(ReadOnlyConfig... configs) {
		synchronized (configFiles) {
			configFiles.addAll(Lists.newArrayList(configs));
		}
	}

	private void handleConfigSet() {
		configFiles.forEach(ReadOnlyConfig::setConfigFile);
		configFiles.forEach(ReadOnlyConfig::loadConfig);
	}

	@Override
	public String toString() {
		return getModId();
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object object) {
		return object == this;
	}

	private Object getModObject() {
		ModContainer modContainer = Loader.instance().getIndexedModList().get(modId);
		return modContainer.getMod();
	}

	public static InterstellarMod get(String modId) {
		ModContainer modContainer = Loader.instance().getIndexedModList().get(modId);
		Object mod = modContainer.getMod();
		if (mod instanceof InterstellarMod) {
			return (InterstellarMod) mod;
		}
		return null;
	}
}
