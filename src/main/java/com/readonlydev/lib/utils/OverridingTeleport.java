package com.readonlydev.lib.utils;

import java.util.Random;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class OverridingTeleport implements ITeleportType {

	private final ITeleportType type;
	protected GCPlayerStats playerStats;

	public OverridingTeleport(ITeleportType teleportType) {
		this.type = teleportType;
	}

	@Override
	public Vector3 getEntitySpawnLocation(WorldServer world, Entity entity) {
		return type.getEntitySpawnLocation(world, entity);
	}

	@Override
	public Vector3 getParaChestSpawnLocation(WorldServer world, EntityPlayerMP player, Random rand) {
		return type.getParaChestSpawnLocation(world, player, rand);
	}

	@Override
	public Vector3 getPlayerSpawnLocation(WorldServer world, EntityPlayerMP player) {
		return type.getPlayerSpawnLocation(world, player);
	}

	@Override
	public void onSpaceDimensionChanged(World newWorld, EntityPlayerMP player, boolean ridingAutoRocket) {
		playerStats = GCPlayerStats.get(player);
		type.onSpaceDimensionChanged(newWorld, player, ridingAutoRocket);
	}

	@Override
	public void setupAdventureSpawn(EntityPlayerMP player) {
		type.setupAdventureSpawn(player);
	}

	@Override
	public boolean useParachute() {
		return type.useParachute();
	}

}
