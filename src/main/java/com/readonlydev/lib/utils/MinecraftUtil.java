package com.readonlydev.lib.utils;

import lombok.experimental.UtilityClass;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

@UtilityClass
public class MinecraftUtil {

	/**
	 * The length of one Minecraft day.
	 */
	public static final int MINECRAFT_DAY = 24000;

	/**
	 * The amount of steps there are in a vanilla comparator.
	 */
	public static final int COMPARATOR_MULTIPLIER = 15;

	/**
	 * The amount of ticks that go in one second.
	 */
	public static final int SECOND_IN_TICKS = 20;

	public static boolean isDevelopmentEnvironment() {
		return (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
	}

	/**
	 * Check if we are inside a modded minecraft environment.
	 *
	 * @return If in minecraft.
	 */
	public static boolean isModdedEnvironment() {
		return MinecraftUtil.class.getClassLoader() instanceof LaunchClassLoader;
	}

	/**
	 * Check if this code is ran on client side. Note that you should use
	 * {@link World#isRemote} rather than this method whenever a World object is
	 * available.
	 *
	 * @return If we are at client side.
	 */
	public static boolean isClientSide() {
		if (isModdedEnvironment()) {
			return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
		}
		return true;
	}
}
