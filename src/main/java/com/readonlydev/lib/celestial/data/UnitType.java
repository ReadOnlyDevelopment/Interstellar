package com.readonlydev.lib.celestial.data;

public enum UnitType {

	SOLAR("☉"), EARTH("⊕"), ABSOLUTE(null);

	private final String symbol;

	UnitType(String symbol) {
		this.symbol = symbol;
	}

	String getSymbol() {
		return symbol;
	}
}
