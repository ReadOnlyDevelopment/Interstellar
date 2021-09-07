package com.readonlydev.lib.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.readonlydev.lib.Interstellar;

import micdoodle8.mods.galacticraft.api.entity.IRocketType;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.IOrbitDimension;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.EntityCelestialFake;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.server.CommandTeleport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.server.permission.PermissionAPI;

public class TPCommand extends CommandTeleport {
	@Override
	public String getUsage(ICommandSender var1) {
		return "/" + this.getName() + " [<player>]";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getName() {
		return "planet";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP playerBase = null;

		if (args.length < 2) {
			try {
				if (args.length == 1) {
					playerBase = PlayerUtil.getPlayerBaseServerFromPlayerUsername(args[0], true);
				} else {
					playerBase = PlayerUtil.getPlayerBaseServerFromPlayerUsername(sender.getName(), true);
				}

				if (playerBase != null) {
					WorldServer worldserver = server.getWorld(GCCoreUtil.getDimensionID(sender.getEntityWorld()));
					if (worldserver == null) {
						Interstellar.log.info("WORLD-SERVER NULL");
					}
					BlockPos spawnPoint = worldserver.getSpawnPoint();
					GCPlayerStats stats = GCPlayerStats.get(playerBase);
					stats.setRocketStacks(NonNullList.withSize(2, ItemStack.EMPTY));
					stats.setRocketType(IRocketType.EnumRocketType.DEFAULT.ordinal());
					stats.setRocketItem(GCItems.rocketTier1);
					stats.setFuelLevel(1000);
					stats.setCoordsTeleportedFromX(spawnPoint.getX());
					stats.setCoordsTeleportedFromZ(spawnPoint.getZ());

					try {
						toCelestialSelection(playerBase, stats, Integer.MAX_VALUE);
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}

					CommandBase.notifyCommandListener(sender, this, "commands.dimensionteleport",
							String.valueOf(EnumColor.GREY + "[" + playerBase.getName()), "]");
				} else {
					throw new Exception("Could not find player with name: " + args[0]);
				}
			} catch (final Exception var6) {
				throw new CommandException(var6.getMessage(), new Object[0]);
			}
		} else {
			throw new WrongUsageException(
					GCCoreUtil.translateWithFormat("commands.dimensiontp.too_many", this.getUsage(sender)),
					new Object[0]);
		}
	}

	public static void toCelestialSelection(EntityPlayerMP player, GCPlayerStats stats, int tier) {
		player.dismountRidingEntity();
		stats.setSpaceshipTier(tier);

		HashMap<String, Integer> map = getArrayOfPossibleDimensions(tier, player);
		String dimensionList = "";
		int count = 0;
		for (Entry<String, Integer> entry : map.entrySet()) {
			Interstellar.log.info("ENTRY: " + entry);
			dimensionList = dimensionList.concat(entry.getKey() + (count < map.entrySet().size() - 1 ? "?" : ""));
			count++;
		}

		boolean canCreateStations = PermissionAPI.hasPermission(player, Constants.PERMISSION_CREATE_STATION);
		GalacticraftCore.packetPipeline.sendTo(
				new PacketSimple(EnumSimplePacket.C_UPDATE_DIMENSION_LIST, GCCoreUtil.getDimensionID(player.world),
						new Object[] { PlayerUtil.getName(player), dimensionList, canCreateStations }),
				player);
		stats.setUsingPlanetSelectionGui(true);
		stats.setSavedPlanetList(dimensionList);
		Entity fakeEntity = new EntityCelestialFake(player.world, player.posX, player.posY, player.posZ);
		player.world.spawnEntity(fakeEntity);
		player.startRiding(fakeEntity);
	}

	private static World getWorldForDimensionServer(int id) {
		World world = DimensionManager.getWorld(id);
		if (world == null) {
			Interstellar.log.noticableWarning("getWorldForDimensionServer returned NULL!");
		}
		return world;
	}

	public static CelestialBody getReachableCelestialBodiesForDimensionID(int id) {
		List<CelestialBody> celestialBodyList = Lists.newArrayList();
		celestialBodyList.addAll(GalaxyRegistry.getRegisteredMoons().values());
		celestialBodyList.addAll(GalaxyRegistry.getRegisteredPlanets().values());

		for (CelestialBody cBody : celestialBodyList) {
			if (cBody.getReachable()) {
				if (cBody.getDimensionID() == id) {
					return cBody;
				}
			}
		}

		return null;
	}

	public static HashMap<String, Integer> getArrayOfPossibleDimensions(int tier, EntityPlayerMP playerBase) {
		List<Integer> ids = getPossibleDimensionsForSpaceshipTier(tier, playerBase);
		final HashMap<String, Integer> map = new HashMap<>(ids.size(), 1F);

		for (Integer id : ids) {
			CelestialBody celestialBody = getReachableCelestialBodiesForDimensionID(id);

			// It's a space station
			if (celestialBody == GalacticraftCore.planetOverworld) {
				map.put(celestialBody.getName(), id);
			} else {
				WorldProvider provider = getWorldForDimensionServer(id).provider;
				if (celestialBody != null && provider != null) {
					if (provider instanceof IGalacticraftWorldProvider && !(provider instanceof IOrbitDimension)
							|| GCCoreUtil.getDimensionID(provider) == 0) {
						map.put(celestialBody.getName(), GCCoreUtil.getDimensionID(provider));
					}
				}
			}
		}
		ArrayList<CelestialBody> cBodyList = new ArrayList<>();
		cBodyList.addAll(GalaxyRegistry.getRegisteredPlanets().values());
		cBodyList.addAll(GalaxyRegistry.getRegisteredMoons().values());

		for (CelestialBody body : cBodyList) {
			if (!body.getReachable()) {
				map.put(body.getLocalizedName() + "*", body.getDimensionID());
			}
		}

		WorldUtil.celestialMapCache.put(playerBase, map);
		return map;
	}

	public static List<Integer> getPossibleDimensionsForSpaceshipTier(int tier, EntityPlayerMP playerBase) {
		List<Integer> temp = new ArrayList<>();

		if (!ConfigManagerCore.disableRocketsToOverworld) {
			temp.add(ConfigManagerCore.idDimensionOverworld);
		}

		for (Integer element : WorldUtil.registeredPlanets) {
			if (element == ConfigManagerCore.idDimensionOverworld) {
				continue;
			}
			WorldProvider provider = getWorldForDimensionServer(element).provider;

			if (provider != null) {
				if (provider instanceof IGalacticraftWorldProvider) {
					if (((IGalacticraftWorldProvider) provider).canSpaceshipTierPass(tier)) {
						temp.add(element);
					}
				} else {
					temp.add(element);
				}
			}
		}

		return temp;
	}
}
