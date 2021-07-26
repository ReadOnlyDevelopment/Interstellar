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

package net.interstellar.lib.utils;

public class JavaUtil {

	@SuppressWarnings("unchecked")
	public static <T> T getClassInstance(String className, Class<T> expected) {
		try {
			Class<?> cls = Class.forName(className);
			if (!expected.isAssignableFrom(expected)) {
				throw new RuntimeException("Class '" + className + "' is nott a " + expected.getSimpleName());
			}
			return (T) cls.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could not find " + expected.getSimpleName() + ": " + className);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Could not instantiate " + expected.getSimpleName() + ": " + className);
		}
	}
}
