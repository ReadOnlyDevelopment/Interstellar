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

package net.interstellar.lib.celestial;

import net.interstellar.api.celestial.IHabitableZone;
import net.interstellar.lib.utils.data.ImmutableDataPair;

public final class HabitableZone implements IHabitableZone {

	private final double	start;
	private final double	end;

	public static HabitableZone of(final double start, final double end) {
		ImmutableDataPair<Double, Double> data = ImmutableDataPair.of(start, end);
		return new HabitableZone(data.getFirst(), data.getSecond());
	}

	private HabitableZone(double start, double end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public double getZoneStart() {
		return start;
	}

	@Override
	public double getZoneEnd() {
		return end;
	}
}