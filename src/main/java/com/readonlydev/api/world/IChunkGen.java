package com.readonlydev.api.world;

import javax.annotation.Nullable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;

public interface IChunkGen extends IChunkGenerator {

	@Override
	@Nullable
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position,
			boolean findUnexplored);

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z);

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos);

}
