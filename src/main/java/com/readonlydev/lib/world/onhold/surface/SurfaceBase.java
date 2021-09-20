//package com.readonlydev.lib.world.onhold.surface;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.chunk.ChunkPrimer;
//
//public abstract class SurfaceBase {
//
//	public IBlockState shadowStoneBlock;
//	public IBlockState shadowDesertBlock;
//	protected IBlockState topBlock;
//	protected IBlockState fillerBlock;
//	protected IBlockState cliffStoneBlock;
//	protected IBlockState cliffCobbleBlock;
//
//	public SurfaceBase(Block top, Block fill) {
//
//		this(top.getDefaultState(), fill.getDefaultState());
//	}
//
//	public SurfaceBase(IBlockState top, IBlockState fill) {
//		topBlock = top;
//		fillerBlock = fill;
//	}
//
//	public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, ExoplanetWorld exoWorld,
//			float[] noise, float river, Biome[] base) {
//
//	}
//
//	protected IBlockState getShadowStoneBlock() {
//
//		return shadowStoneBlock;
//	}
//
//	protected IBlockState getShadowDesertBlock() {
//
//		return shadowDesertBlock;
//	}
//
//	protected IBlockState hcStone() {
//
//		return cliffStoneBlock;
//	}
//
//	protected IBlockState hcCobble() {
//
//		return cliffCobbleBlock;
//	}
//
//	public IBlockState getTopBlock() {
//
//		return this.topBlock;
//	}
//
//	public IBlockState getFillerBlock() {
//
//		return this.fillerBlock;
//	}
//}