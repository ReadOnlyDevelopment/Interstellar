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

package com.readonlydev.lib.system.data;

public final class ImmutableDataPair<FIRST, SECOND> extends DataPair<FIRST, SECOND> {

	private static final long serialVersionUID = -3300802091873828456L;

	public final FIRST	first;
	public final SECOND	second;

	public static <FIRST, SECOND> ImmutableDataPair<FIRST, SECOND> of(final FIRST first, final SECOND second) {
		return new ImmutableDataPair<>(first, second);
	}

	public ImmutableDataPair(final FIRST first, final SECOND second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public FIRST getFirst() {
		return first;
	}

	@Override
	public SECOND getSecond() {
		return second;
	}

	@Override
	public SECOND setValue(SECOND value) {
		throw new UnsupportedOperationException();
	}
}
