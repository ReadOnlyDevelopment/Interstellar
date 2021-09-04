package com.readonlydev.lib.world.deco.collection;

import java.util.ArrayList;

import com.readonlydev.lib.world.deco.TerrainDecoration;

public class TerrainDecorations {

	public ArrayList<TerrainDecoration> decorations;

	public TerrainDecorations() {
		this.decorations = new ArrayList<>();
	}

	public TerrainDecorations addTerrainDecoration(TerrainDecoration decorations) {
		this.decorations.add(decorations);
		return this;
	}
}
