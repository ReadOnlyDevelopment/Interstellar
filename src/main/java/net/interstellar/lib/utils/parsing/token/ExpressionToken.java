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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionToken extends Token<String> {
	private Pattern pattern;

	public ExpressionToken(String regex) {
		this.pattern = Pattern.compile(regex);
	}

	@Override
	public boolean matches(String s, int index) {
		value = null;
		Matcher m = pattern.matcher(s.substring(index));
		if (!m.find()) {
			return false;
		}

		value = m.group(0);
		return true;
	}

	@Override
	public int size() {
		return value != null ? value.length() : 0;
	}

}
