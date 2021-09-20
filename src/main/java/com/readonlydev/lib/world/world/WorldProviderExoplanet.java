package com.readonlydev.lib.world.world;

import com.readonlydev.lib.celestial.objects.Exoplanet;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.core.event.EventHandlerGC;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class WorldProviderExoplanet extends WorldProviderSpace {

	@Override
	protected void init() {
		this.hasSkyLight = true;
	}

	@Override
	public Class<? extends IChunkGenerator> getChunkProviderClass() {
		return null;
	}

	@Override
	public BiomeProvider getBiomeProvider() {
		return this.biomeProvider;
	}

	@Override
	public boolean canRespawnHere() {
		if (EventHandlerGC.bedActivated) {
			EventHandlerGC.bedActivated = false;
			return true;
		}
		return false;
	}

	@Override
	public CelestialBody getCelestialBody() {
		return this.getPlanet();
	}

	public Exoplanet getPlanet() {
		return (Exoplanet) this.getCelestialBody();
	}

	@Override
	public long getDayLength() {
		return this.getPlanet().getDayLength();
	}

	@Override
	public String getSaveFolder() {
		return "Exoplanets/" + this.getPlanet().getName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final float getStarBrightness(float par1) {
		final float var2 = this.world.getCelestialAngle(par1);
		float var3 = 1.0F - ((MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F) + 0.25F);
		if (var3 < 0.0F) {
			var3 = 0.0F;
		}
		if (var3 > 1.0F) {
			var3 = 1.0F;
		}
		return (var3 * var3 * 0.5F) + 0.3F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final float getSunBrightness(float par1) {
		float f1 = this.world.getCelestialAngle(1.0F);
		float f2 = 1.25F - ((MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F) + 0.2F);
		if (f2 < 0.0F) {
			f2 = 0.0F;
		}
		if (f2 > 1.0F) {
			f2 = 1.0F;
		}
		f2 = 1.0F - f2;
		return (f2 * 0.8F) + 0.2F;
	}

	@Override
	public boolean shouldForceRespawn() {
		return !ConfigManagerCore.forceOverworldRespawn;
	}

	@Override
	public boolean canSpaceshipTierPass(int tier) {
		return tier >= getPlanet().getTierRequirement();
	}

	@Override
	public boolean hasSunset() {
		return false;
	}

	@Override
	public abstract IChunkGenerator createChunkGenerator();
}
