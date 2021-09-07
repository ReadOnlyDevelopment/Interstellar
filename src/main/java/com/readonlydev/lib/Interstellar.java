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

package com.readonlydev.lib;

import java.util.HashMap;
import java.util.Map;

import com.readonlydev.api.InterstellarAPI;
import com.readonlydev.api.proxy.IProxy;
import com.readonlydev.lib.base.InterstellarMod;
import com.readonlydev.lib.command.TPCommand;
import com.readonlydev.lib.exception.InvalidFingerprintException;
import com.readonlydev.lib.guide.example.InterstellarGuide;
import com.readonlydev.lib.network.InterstellarNetwork;
import com.readonlydev.lib.utils.MinecraftUtil;
import com.readonlydev.lib.utils.system.Log;
import com.readonlydev.lib.world.biome.ExoplanetBiome;

import net.minecraft.world.DimensionType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = LibInfo.ID, name = LibInfo.NAME, version = LibInfo.VERSION, acceptedMinecraftVersions = "[1.12,1.13)", certificateFingerprint = LibInfo.FINGERPRINT)
public class Interstellar extends InterstellarMod {

	@Instance(LibInfo.ID)
	public static Interstellar _instance;

	public static final Map<String, String> invalidCertificates = new HashMap<>();

	public static InterstellarNetwork network;

	public static Log log;

	@SidedProxy(clientSide = "com.readonlydev.lib.proxy.ClientProxy", serverSide = "com.readonlydev.lib.proxy.DedicatedServerProxy")
	public static IProxy proxy;

	public Interstellar() {
		super(Interstellar.class);
		_instance = this;
		log = new Log(this, 11/* Integer.valueOf(LibInfo.BUILD) */);
		network = new InterstellarNetwork("Interstellar");
		InterstellarGuide.preInitGuideLoader();
	}

	@EventHandler
	public void onFingerprintViolation(FMLFingerprintViolationEvent event) {
		if (MinecraftUtil.isDevelopmentEnvironment()) {
			Interstellar.log.info("Ignoring fingerprint signing since we are in a Development Environment");
			return;
		} else {
			throw new InvalidFingerprintException(event);
		}
	}

	@Mod.EventHandler
	@Override
	public void onPreInit(FMLPreInitializationEvent event) {

		// PacketRegistry.registerPackets(network);

		// REGISTRY.addRegistrationHandler(InterstellarItems::registerItems,
		// Item.class);

		// InterstellarGuiHandler.instance().register(ItemSGuide.getGuiObjectReference());

		// MinecraftForge.EVENT_BUS.register(GuiGuide.class);

		proxy.preInit(registry, event);
		super.onPreInit(event);
	}

	@Override
	public void onInit(FMLInitializationEvent event) {
		proxy.init(registry, event);
		super.onInit(event);
	}

	@Override
	public void onPostInit(FMLPostInitializationEvent event) {

		// NetworkRegistry.INSTANCE.registerGuiHandler(_instance,
		// InterstellarGuiHandler.instance());
		InterstellarAPI.INTERSTELLAR_BIOME_MAP.addBiomes(registry.getBIOMES().toArray(new ExoplanetBiome[0]));
		proxy.postInit(registry, event);
		super.onPostInit(event);
	}

	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event) {
		registry.registerCommand(new TPCommand());
		for (DimensionType type : DimensionType.values()) {

			Interstellar.log.info("TYPE SUFFIX: " + type.getSuffix());
			Interstellar.log.info("TYPE NAME: " + type.getName());
			Interstellar.log.info("TYPE ID: " + type.getId());

		}
	}
}
