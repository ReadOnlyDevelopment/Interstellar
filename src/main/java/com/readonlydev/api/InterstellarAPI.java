/*
 * API License
 *
 * Copyright (c) 2021 ReadOnly Development
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.readonlydev.api;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;
import com.readonlydev.lib.Interstellar;
import com.readonlydev.lib.world.BiomeMap;
import com.readonlydev.lib.world.biome.IExoplanetBiome;

import lombok.experimental.UtilityClass;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerRiverMix;

@UtilityClass
public class InterstellarAPI {
	public static final String APIVERSION = "1.0.0";

	public static final BiomeMap INTERSTELLAR_BIOME_MAP = new BiomeMap();

	public static IExoplanetBiome getBiome(@Nonnull Biome biome) {
		return INTERSTELLAR_BIOME_MAP.get(biome);
	}

	public static IExoplanetBiome getBiome(int biomeId) {
		return INTERSTELLAR_BIOME_MAP.getValueAt(biomeId);
	}

	public static final class Debug {
		public static void dumpGenLayerStack(@Nonnull final GenLayer layersIn, final Level level) {
			final Collection<String> initialStack = Lists.newArrayList();
			final Collection<String> riverStack = Lists.newArrayList();
			final Collection<String> biomeStack = Lists.newArrayList();

			int count = 0;
			int biomecount;
			int rivercount;
			GenLayer layer = layersIn;
			initialStack.add(String.format("%s. %s", ++count, layer.getClass().getName()));
			while (layer.parent != null) {
				initialStack.add(String.format("%s. %s", ++count, layer.parent.getClass().getName()));
				layer = layer.parent;
			}
			if (layer instanceof GenLayerRiverMix) {
				biomecount = rivercount = count;
				GenLayer biomeLayer = ((GenLayerRiverMix) layer).biomePatternGeneratorChain;
				while (biomeLayer.parent != null) {
					biomeStack.add(String.format("%s. %s", ++biomecount, biomeLayer.parent.getClass().getName()));
					biomeLayer = biomeLayer.parent;
				}
				GenLayer riverLayer = ((GenLayerRiverMix) layer).riverPatternGeneratorChain;
				while (riverLayer.parent != null) {
					riverStack.add(String.format("%s. %s", ++rivercount, riverLayer.parent.getClass().getName()));
					riverLayer = riverLayer.parent;
				}
			}

			if (biomeStack.isEmpty() || riverStack.isEmpty()) {
				Interstellar.log.log(level, "\nGenLayer stack:\n{}", String.join("\n  ", initialStack));
			} else {
				Interstellar.log.log(level,
						"\nInitial GenLayer stack:\n  {}\nBiome GenLayer stack:\n  {}\nRiver GenLayer stack:\n  {}",
						String.join("\n  ", initialStack), String.join("\n  ", biomeStack),
						String.join("\n  ", riverStack));
			}
		}
	}
}
