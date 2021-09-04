package com.readonlydev.lib.world.surface;

import java.util.Random;

import com.readonlydev.lib.world.ExoplanetWorld;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class SurfaceGeneric extends SurfaceBase {

	public SurfaceGeneric(IBlockState top, IBlockState filler) {

		super(top, filler);
	}

	@Override
	public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int z, int depth, ExoplanetWorld exoWorld,
			float[] noise, float river, Biome[] base) {

		Random rand = exoWorld.rand();

		for (int k = 255; k > -1; k--) {
			Block b = primer.getBlockState(x, k, z).getBlock();

			if (b == Blocks.AIR) {
				depth = -1;
			} else if (b == Blocks.STONE) {
				depth++;

				if (depth == 0 && k > 61) {
					primer.setBlockState(x, k, z, topBlock);
				} else if (depth < 4) {
					primer.setBlockState(x, k, z, fillerBlock);
				}
			}
		}
	}
}
