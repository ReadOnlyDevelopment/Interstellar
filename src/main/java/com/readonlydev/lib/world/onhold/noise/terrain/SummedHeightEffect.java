//package com.readonlydev.lib.world.onhold.noise.terrain;
//
//import com.readonlydev.lib.world.onhold.noise.ExoplanetWorld;
//
//class SummedHeightEffect extends HeightEffect {
//
//	private final HeightEffect one;
//	private final HeightEffect two;
//
//	public SummedHeightEffect(HeightEffect one, HeightEffect two) {
//		this.one = one;
//		this.two = two;
//	}
//
//	@Override
//	public float added(ExoplanetWorld exoWorld, float x, float y) {
//		return one.added(exoWorld, x, y) + two.added(exoWorld, x, y);
//	}
//}
