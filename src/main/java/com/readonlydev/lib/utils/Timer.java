/*
 * Library License
 *
 * Copyright (c) 2021 ReadOnly Development
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.readonlydev.lib.utils;

public class Timer {
	private long start;

	public Timer() {
		start();
	}

	public Timer(long start) {
		setStart(start);
	}

	public void start() {
		setStart(System.currentTimeMillis());
	}

	public void setStart(long start) {
		if (start < 0) {
			setRelativeStart(start);
		} else {
			this.start = start;
		}
	}

	public void setRelativeStart(long start) {
		setStart(System.currentTimeMillis() + start);
	}

	public long getStart() {
		return start;
	}

	public long elapsedTime() {
		return System.currentTimeMillis() - start;
	}

	public long elapsedTick() {
		return timeToTick(System.currentTimeMillis() - start);
	}

	public static long timeToTick(long time) {
		return time * 20 / 1000;
	}

	public static long tickToTime(long ticks) {
		return ticks * 1000 / 20;
	}

	@Override
	public String toString() {
		int elapsed = (int) (elapsedTime() / 1000);
		int minutes = (elapsed % 3600) / 60;
		int seconds = elapsed % 60;

		return String.format("%02d:%02d ago", minutes, seconds);
	}
}
