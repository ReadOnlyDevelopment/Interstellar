//package com.readonlydev.lib.world.onhold.deco;
//
//import java.util.Random;
//
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.ChunkPos;
//import net.minecraft.world.gen.feature.WorldGenerator;
//import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
//import net.minecraftforge.event.terraingen.TerrainGen;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//import com.readonlydev.lib.world.onhold.noise.chunk.IExoplanetBiome;
//
//class DecoWorldGen extends TerrainDecoration {
//
//	private WorldGenerator worldgen;
//	private Decorate.EventType eventtype;
//	private int loops;
//	private int chance;
//	private int minY;
//	private int maxY;
//
//	public DecoWorldGen(WorldGenerator worldgen) {
//
//		super();
//		this.setWorldGen(worldgen);
//		this.setEventType(Decorate.EventType.CUSTOM);
//		this.setLoops(1);
//		this.setChance(1);
//		this.setMinY(1);
//		this.setMaxY(255);
//	}
//
//	public DecoWorldGen(WorldGenerator worldgen, Decorate.EventType eventtype) {
//		this(worldgen);
//		this.setEventType(eventtype);
//	}
//
//	@Override
//	public void generate(final IExoplanetBiome biome, final ExoplanetWorld exoWorld, final Random rand,
//			final ChunkPos chunkPos, final float river, final boolean hasVillage) {
//
//		for (int i = 0; i <= loops; i++) {
//
//			final BlockPos pos = getOffsetPos(chunkPos).add(rand.nextInt(16), 0, rand.nextInt(16));
//			final int y = exoWorld.world().getHeight(pos).getY();
//
//			if (y >= this.minY && y <= this.maxY && rand.nextInt(this.chance) == 0) {
//				if (TerrainGen.decorate(exoWorld.world(), rand, chunkPos, eventtype)) {
//					this.worldgen.generate(exoWorld.world(), rand, pos.up(y));
//				}
//			}
//		}
//	}
//
//	public WorldGenerator getWorldGen() {
//		return worldgen;
//	}
//
//	public DecoWorldGen setWorldGen(WorldGenerator worldgen) {
//		this.worldgen = worldgen;
//		return this;
//	}
//
//	public Decorate.EventType getEventType() {
//		return eventtype;
//	}
//
//	public DecoWorldGen setEventType(Decorate.EventType eventtype) {
//		this.eventtype = eventtype;
//		return this;
//	}
//
//	public int getLoops() {
//		return loops;
//	}
//
//	public DecoWorldGen setLoops(int loops) {
//		this.loops = loops;
//		return this;
//	}
//
//	public int getChance() {
//		return chance;
//	}
//
//	public DecoWorldGen setChance(int chance) {
//		this.chance = chance;
//		return this;
//	}
//
//	public int getMinY() {
//		return minY;
//	}
//
//	public DecoWorldGen setMinY(int minY) {
//		this.minY = minY;
//		return this;
//	}
//
//	public int getMaxY() {
//		return maxY;
//	}
//
//	public DecoWorldGen setMaxY(int maxY) {
//		this.maxY = maxY;
//		return this;
//	}
//}
