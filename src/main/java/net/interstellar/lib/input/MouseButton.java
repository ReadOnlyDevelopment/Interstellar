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

package net.interstellar.lib.input;

public enum MouseButton {

	LEFT(0),
	RIGHT(1),
	MIDDLE(2),
	UNKNOWN(-1);

	public static MouseButton[] DEFAULT = { LEFT, RIGHT, MIDDLE };

	private int code;

	private MouseButton(int code) {
		this.code = code;
	}

	/**
	 * Gets the code for this {@link MouseButton}.
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Gets the {@link MouseButton} from the event code.
	 *
	 * @param id the id
	 * @return the button
	 */
	public static MouseButton getButton(int id) {
		if (id < DEFAULT.length && id >= 0) {
			return DEFAULT[id];
		}
		return UNKNOWN;
	}
}
