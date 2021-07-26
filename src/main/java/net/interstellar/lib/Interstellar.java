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

package net.interstellar.lib;

import java.util.HashMap;
import java.util.Map;

import net.interstellar.lib.base.InterstellarMod;
import net.interstellar.lib.exception.InvalidFingerprintException;
import net.interstellar.lib.guide.example.InterstellarGuide;
import net.interstellar.lib.network.InterstellarNetwork;
import net.interstellar.lib.proxy.ServerProxy;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = LibInfo.ID, name = LibInfo.NAME, version = LibInfo.VERSION, acceptedMinecraftVersions = "[1.12,1.13)", certificateFingerprint = LibInfo.FINGERPRINT)
public class Interstellar extends InterstellarMod {

	@Instance(LibInfo.ID)
	public static Interstellar _instance;

	public static final Map<String, String> invalidCertificates = new HashMap<>();

	public static boolean				inObfEnv	= false;
	public static InterstellarNetwork	network;
	public static ASMDataTable			asmDataTable;

	@SidedProxy(clientSide = "net.interstellar.lib.proxy.ClientProxy", serverSide = "net.interstellar.lib.proxy.ServerProxy")
	public static ServerProxy proxy;

	public Interstellar() {
		super(Interstellar.class);
		_instance = this;
		network = new InterstellarNetwork("Interstellar");
		inObfEnv = !(boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
		InterstellarGuide.preInitGuideLoader();
	}

	@Mod.EventHandler
	@Override
	public void onPreInit(FMLPreInitializationEvent event) {

		// PacketRegistry.registerPackets(network);

		// REGISTRY.addRegistrationHandler(InterstellarItems::registerItems, Item.class);

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

		// NetworkRegistry.INSTANCE.registerGuiHandler(_instance, InterstellarGuiHandler.instance());

		proxy.postInit(registry, event);
		super.onPostInit(event);
	}

	@EventHandler
	public void onFingerprintViolation(FMLFingerprintViolationEvent event) {
		if ((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) {
			InterstellarLogger.info("Ignoring fingerprint signing since we are in a Development Environment");
			return;
		} else {
			throw new InvalidFingerprintException(event);
		}
	}
}