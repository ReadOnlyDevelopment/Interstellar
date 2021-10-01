package com.readonlydev.lib.math;

import com.readonlydev.lib.annotation.ReplaceWith;
import com.readonlydev.lib.celestial.data.Temperature;

/**
 * @deprecated Use {@link Temperature#Temperature(double value, Temperature.Unit unit)} instead
 */
@Deprecated
@ReplaceWith("new Temperature()")
public class TempConversion {

	//// Fahrenheit -> Celcius ////

	public static double toCelciusDouble(double fahrenheit) {
		return calculateFah2Cel(fahrenheit);
	}

	public static int toCelciusInt(double fahrenheit) {
		return calculateFah2Cel(fahrenheit);
	}

	//// Celcius -> Fahrenheit ////

	public static double toFahrenheit(double celcius) {
		return toFahrenheit((int) celcius);
	}

	public static double toFahrenheit(int celcius) {
		return calculateCel2Feh(celcius);
	}

	private static int calculateFah2Cel(double fahrenheit) {
		return (int) ((fahrenheit - 32) / 1.8000);
	}

	private static double calculateCel2Feh(int celcius) {
		return (celcius * 1.8000 + 32.00);
	}

}
