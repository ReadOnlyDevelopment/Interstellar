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

package com.readonlydev.lib.utils.data;

public class MutableDataPair<FIRST, SECOND> extends DataPair<FIRST, SECOND> {

	private static final long serialVersionUID = -3300802091873828456L;

	public FIRST	first;
	public SECOND	second;

	public static <FIRST, SECOND> MutableDataPair<FIRST, SECOND> of(final FIRST first, final SECOND second) {
		return new MutableDataPair<>(first, second);
	}

	public MutableDataPair() {
		super();
	}

	public MutableDataPair(FIRST first, SECOND second) {
		super();
		this.first = first;
		this.second = second;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FIRST getFirst() {
		return first;
	}

	/**
	 * Sets the first element of the pair.
	 * 
	 * @param first the new value of the first element, may be null
	 */
	public void setFirst(final FIRST first) {
		this.first = first;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SECOND getSecond() {
		return second;
	}

	/**
	 * Sets the second element of the pair.
	 * 
	 * @param second the new value of the second element, may be null
	 */
	public void setSecond(final SECOND second) {
		this.second = second;
	}

	/**
	 * Sets the {@code Map.Entry} value. This sets the right element of the pair.
	 * 
	 * @param value the second value to set, not null
	 * @return the old value for the second element
	 */
	@Override
	public SECOND setValue(final SECOND value) {
		final SECOND result = getSecond();
		setSecond(value);
		return result;
	}
}
