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

public interface ICachedData<T> {

	/**
	 * Gets the current value of the data
	 *
	 * @return the t
	 */
	public T get();

	/**
	 * Checks if data has changed.
	 *
	 * @return true, if successful
	 */
	public boolean hasChanged();

	/**
	 * Updates the current data.
	 */
	public void update();
}
