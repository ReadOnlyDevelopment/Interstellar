package com.readonlydev.lib.world.deco.helper;

import java.util.Random;

import net.minecraft.util.math.ChunkPos;
import com.readonlydev.lib.world.ExoplanetWorld;
import com.readonlydev.lib.world.biome.IExoplanetBiome;
import com.readonlydev.lib.world.deco.TerrainDecoration;

/**
 * This deco helper takes an array of deco objects and an array of chances and
 * generates one accordingly.
 *
 * @author WhichOnesPink
 */
// TODO: [1.12] This class should use an implementation of net.minecraft.util.WeightedRandom$Item to simplify it,
//              and remove the chance of causing `ArrayOutOfBoundsException`s.
public class DecoHelperRandomSplit extends TerrainDecoration {

	public TerrainDecoration[] decos;
	public int[] chances;

	public DecoHelperRandomSplit() {

		super();

		this.decos = new TerrainDecoration[] {};
		this.chances = new int[] {};
	}

	@Override
	public void generate(final IExoplanetBiome biome, final ExoplanetWorld exoWorld, final Random rand,
			final ChunkPos chunkPos, final float river, final boolean hasVillage) {

		if (this.decos.length < 1 || this.chances.length < 1 || this.decos.length != this.chances.length) {
			throw new RuntimeException("DecoHelperRandomSplit is confused.");
		}

		int totalChances = 0;
		for (int i = 0; i < this.decos.length; i++) {
			totalChances += chances[i];
		}
		int chosen = rand.nextInt(totalChances);

		for (int i = 0; i < this.decos.length; i++) {

			if (chosen < this.chances[i]) {

				this.decos[i].generate(biome, exoWorld, rand, chunkPos, river, hasVillage);
			}
			// decrement chosen for the chances missed and continue;
			chosen -= chances[i];
		}
	}

	public TerrainDecoration[] getDecos() {

		return decos;
	}

	public DecoHelperRandomSplit setDecos(TerrainDecoration[] decos) {

		this.decos = decos;
		return this;
	}

	public int[] getChances() {

		return chances;
	}

	public DecoHelperRandomSplit setChances(int[] chances) {

		this.chances = chances;
		return this;
	}
}
