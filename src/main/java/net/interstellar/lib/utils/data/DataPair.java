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

package net.interstellar.lib.utils.data;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

public abstract class DataPair<FIRST, SECOND> implements Map.Entry<FIRST, SECOND>, Comparable<DataPair<FIRST, SECOND>>, Serializable {

	private static final long serialVersionUID = -3300802091873828456L;

	/**
	 * <p>
	 * Obtains an immutable pair of from two objects inferring the generic types.
	 * </p>
	 * 
	 * @param <FIRST>  the left element type
	 * @param <SECOND> the right element type
	 * @param left     the left element, may be null
	 * @param right    the right element, may be null
	 * @return a pair formed from the two parameters, not null
	 */
	public static <FIRST, SECOND> DataPair<FIRST, SECOND> of(final FIRST left, final SECOND right) {
		return new ImmutableDataPair<>(left, right);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Gets the left element from this pair.
	 * </p>
	 * <p>
	 * When treated as a key-value pair, this is the key.
	 * </p>
	 * 
	 * @return the left element, may be null
	 */
	public abstract FIRST getFirst();

	/**
	 * <p>
	 * Gets the right element from this pair.
	 * </p>
	 * <p>
	 * When treated as a key-value pair, this is the value.
	 * </p>
	 * 
	 * @return the right element, may be null
	 */
	public abstract SECOND getSecond();

	/**
	 * <p>
	 * Gets the key from this pair.
	 * </p>
	 * <p>
	 * This method implements the {@code Map.Entry} interface returning the left element as the key.
	 * </p>
	 * 
	 * @return the left element as the key, may be null
	 */
	@Override
	public final FIRST getKey() {
		return getFirst();
	}

	/**
	 * <p>
	 * Gets the value from this pair.
	 * </p>
	 * <p>
	 * This method implements the {@code Map.Entry} interface returning the right element as the value.
	 * </p>
	 * 
	 * @return the right element as the value, may be null
	 */
	@Override
	public SECOND getValue() {
		return getSecond();
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Compares the pair based on the left element followed by the right element. The types must be {@code Comparable}.
	 * </p>
	 * 
	 * @param other the other pair, not null
	 * @return negative if this is less, zero if equal, positive if greater
	 */
	@Override
	public int compareTo(final DataPair<FIRST, SECOND> other) {
		return new CompareToBuilder().append(getFirst(), other.getFirst()).append(getSecond(), other.getSecond()).toComparison();
	}

	/**
	 * <p>
	 * Compares this pair to another based on the two elements.
	 * </p>
	 * 
	 * @param obj the object to compare to, null returns false
	 * @return true if the elements of the pair are equal
	 */
	@SuppressWarnings("deprecation") // ObjectUtils.equals(Object, Object) has been deprecated in 3.2
	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Map.Entry<?, ?>) {
			final Map.Entry<?, ?> other = (Map.Entry<?, ?>) obj;
			return ObjectUtils.equals(getKey(), other.getKey()) && ObjectUtils.equals(getValue(), other.getValue());
		}
		return false;
	}

	/**
	 * <p>
	 * Returns a suitable hash code. The hash code follows the definition in {@code Map.Entry}.
	 * </p>
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		// see Map.Entry API specification
		return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
	}

	/**
	 * <p>
	 * Returns a String representation of this pair using the format {@code ($left,$right)}.
	 * </p>
	 * 
	 * @return a string describing this object, not null
	 */
	@Override
	public String toString() {
		return new StringBuilder().append('(').append(getFirst()).append(',').append(getSecond()).append(')').toString();
	}

	/**
	 * <p>
	 * Formats the receiver using the given format.
	 * </p>
	 * <p>
	 * This uses {@link java.util.Formattable} to perform the formatting. Two variables may be used to embed the left and right elements. Use {@code %1$s} for the left element (key) and {@code %2$s} for the right element (value). The default format used by {@code toString()} is {@code (%1$s,%2$s)}.
	 * </p>
	 * 
	 * @param format the format string, optionally containing {@code %1$s} and {@code %2$s}, not null
	 * @return the formatted string, not null
	 */
	public String toString(final String format) {
		return String.format(format, getFirst(), getSecond());
	}
}
