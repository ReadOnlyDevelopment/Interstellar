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

package net.interstellar.lib.utils.parsing.token;

public class KeywordToken extends Token<String> {
	public KeywordToken(String keyword) {
		value = keyword;
	}

	@Override
	public boolean matches(String s, int index) {
		if (value == null) {
			return false;
		}
		int e = index + value.length();
		if (e > s.length()) {
			return false;
		}
		if (s.substring(index, index + value.length()).toLowerCase().equals(value.toLowerCase())) {
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return value.length();
	}

}
