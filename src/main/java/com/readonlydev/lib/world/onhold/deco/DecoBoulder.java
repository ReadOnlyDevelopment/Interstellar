//package com.readonlydev.lib.world.onhold.deco;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Random;
//
//import com.readonlydev.lib.utils.BlockUtil;
//import com.readonlydev.lib.utils.BlockUtil.MatchType;
//import com.readonlydev.lib.world.gen.feature.WorldGenBlob;
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//import com.readonlydev.lib.world.onhold.noise.chunk.IExoplanetBiome;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.init.Blocks;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.ChunkPos;
//import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
//import net.minecraftforge.event.terraingen.TerrainGen;
//
//class DecoBoulder extends TerrainDecoration {
//
//	private IBlockState boulderBlock;
//	private float strengthFactor;
//	private int minY;
//	private int maxY;
//	private int chance;
//	private boolean water;
//	private List<Block> validGroundBlocks;
//
//	public DecoBoulder() {
//		super();
//		this.setBoulderBlock(Blocks.COBBLESTONE.getDefaultState());
//		this.setStrengthFactor(2f);
//		this.setMinY(60);
//		this.setMaxY(255);
//		this.setChance(10);
//		this.water = true;
//		this.validGroundBlocks = Arrays.asList(Blocks.GRASS, Blocks.DIRT, Blocks.STONE, Blocks.GRAVEL, Blocks.CLAY, Blocks.SAND);
//	}
//
//	@Override
//	public void generate(final IExoplanetBiome biome, final ExoplanetWorld exoWorld, final Random rand, final ChunkPos chunkPos, final float river, final boolean hasVillage) {
//		if (exoWorld.getGeneratorSettings().useBoulders && TerrainGen.decorate(exoWorld.world(), rand, chunkPos, Decorate.EventType.ROCK)) {
//			for (int i = 0; i < this.strengthFactor; ++i) {
//				final BlockPos pos = getOffsetPos(chunkPos).add(rand.nextInt(16), 0, rand.nextInt(16));
//				int y = exoWorld.world().getHeight(pos).getY();
//				if (y >= this.minY && y <= this.maxY && rand.nextInt(this.chance) == 0) {
//					if (!hasVillage || BlockUtil.checkAreaMaterials(MatchType.ALL_IGNORE_REPLACEABLE, exoWorld.world(), pos.up(y), 2)) {
//						new WorldGenBlob(boulderBlock, 0, validGroundBlocks, this.water).generate(exoWorld.world(), rand, pos.up(y));
//					}
//				}
//			}
//		}
//	}
//
//	public IBlockState getBoulderBlock() {
//		return boulderBlock;
//	}
//
//	public DecoBoulder setBoulderBlock(IBlockState boulderBlock) {
//		this.boulderBlock = boulderBlock;
//		return this;
//	}
//
//	public float getStrengthFactor() {
//		return strengthFactor;
//	}
//
//	public DecoBoulder setStrengthFactor(float strengthFactor) {
//		this.strengthFactor = strengthFactor;
//		return this;
//	}
//
//	public int getMinY() {
//		return minY;
//	}
//
//	public DecoBoulder setMinY(int minY) {
//		this.minY = minY;
//		return this;
//	}
//
//	public int getMaxY() {
//		return maxY;
//	}
//
//	public DecoBoulder setMaxY(int maxY) {
//		this.maxY = maxY;
//		return this;
//	}
//
//	public int getChance() {
//		return chance;
//	}
//
//	public DecoBoulder setChance(int chance) {
//		this.chance = chance;
//		return this;
//	}
//
//	public boolean isWater() {
//		return water;
//	}
//
//	public DecoBoulder setWater(boolean water) {
//		this.water = water;
//		return this;
//	}
//
//	public List<Block> getValidGroundBlocks() {
//		return Collections.unmodifiableList(this.validGroundBlocks);
//	}
//
//	public DecoBoulder setValidGroundBlocks(ArrayList<Block> validGroundBlocks) {
//		this.validGroundBlocks = Collections.unmodifiableList(validGroundBlocks);
//		return this;
//	}
//}
