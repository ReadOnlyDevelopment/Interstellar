//package com.readonlydev.lib.world.onhold.noise.terrain;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//
//class VariableRuggednessEffect extends HeightEffect {
//
//	public static float STANDARD_RUGGEDNESS_WAVELENGTH = 200f;
//	public static int STANDARD_RUGGEDNESS_OCTAVE = 1;
//
//	public HeightEffect smoothTerrain;
//	public HeightEffect ruggedTerrain;
//	public float startTransition = Integer.MAX_VALUE;
//	public float transitionWidth = 0;
//	public int octave = 1;
//	public float wavelength;
//
//	public VariableRuggednessEffect() {
//	}
//
//	public VariableRuggednessEffect(HeightEffect smoothTerrain, HeightEffect ruggedTerrain, float startTransition, float transitionWidth, float wavelength) {
//		this.smoothTerrain = smoothTerrain;
//		this.ruggedTerrain = ruggedTerrain;
//		this.startTransition = startTransition;
//		this.transitionWidth = transitionWidth;
//		this.wavelength = wavelength;
//	}
//
//	public VariableRuggednessEffect(HeightEffect smoothTerrain, HeightEffect ruggedTerrain, float startTransition, float transitionWidth) {
//		this(smoothTerrain, ruggedTerrain, startTransition, transitionWidth, STANDARD_RUGGEDNESS_WAVELENGTH);
//	}
//
//	@Override
//	public final float added(ExoplanetWorld exoWorld, float x, float y) {
//		float choice = exoWorld.simplexInstance(octave).noise2f(x / wavelength, y / wavelength);
//		if (choice <= startTransition) {
//			return smoothTerrain.added(exoWorld, x, y);
//		}
//		if (choice >= startTransition + transitionWidth) {
//			return ruggedTerrain.added(exoWorld, x, y);
//		}
//		float smooth = smoothTerrain.added(exoWorld, x, y);
//		float rugged = ruggedTerrain.added(exoWorld, x, y);
//		return ((choice - startTransition) * rugged + (startTransition + transitionWidth - choice) * smooth) / transitionWidth;
//	}
//
//}