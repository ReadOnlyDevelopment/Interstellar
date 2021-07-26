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

package net.interstellar.lib.data;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class CachedData<T> implements ICachedData<T> {

	protected Supplier<T>		getter;
	protected BiPredicate<T, T>	predicate;
	protected T					lastData;
	protected T					currentData;

	public CachedData(Supplier<T> getter, BiPredicate<T, T> predicate) {
		this.getter = checkNotNull(getter);
		this.predicate = checkNotNull(predicate);
		currentData = getter.get();
		update();
	}

	public CachedData(Supplier<T> getter) {
		this(getter, (o1, o2) -> !Objects.equals(o1, o2));
	}

	/**
	 * Gets the current data.
	 *
	 * @return the t
	 */
	@Override
	public T get() {
		return currentData;
	}

	/**
	 * Updates the current data.
	 */
	@Override
	public void update() {
		lastData = currentData;
		currentData = getter.get();
	}

	/**
	 * Checks whether the data has changed since the last update.
	 *
	 * @return true, if data has changed
	 */
	@Override
	public boolean hasChanged() {
		return predicate.test(lastData, currentData);
	}
}
