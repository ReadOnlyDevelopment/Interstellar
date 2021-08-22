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

package com.readonlydev.lib.asm;

import static com.readonlydev.lib.utils.parsing.token.Token.*;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

import com.readonlydev.lib.utils.parsing.Parser;
import com.readonlydev.lib.utils.parsing.token.Token;

public class MethodDescriptor {
	private Class<?>[]	params;
	private Class<?>	returnType;

	public MethodDescriptor(Class<?> returnType, Class<?>... params) {
		this.params = params;
		this.returnType = returnType;
	}

	public MethodDescriptor(String descriptor) {
		new MethodSignatureParser(this, descriptor).parse();
	}

	public Class<?>[] getParams() {
		return params;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	@Override
	public String toString() {
		String str = "(";
		if (params != null) {
			for (Class<?> c : params) {
				str += c.getName();
			}
		}
		str += ")";
		if (returnType != null) {
			str += returnType.getName();
		}
		return str;
	}

	private static class MethodSignatureParser extends Parser<MethodDescriptor> {
		MethodDescriptor desc;

		public MethodSignatureParser(MethodDescriptor desc, String s) {
			super(s);
			this.desc = desc;
			withTokens(TypeToken.token, Colon, OpenCar, OpenPar, ClosePar);
		}

		@Override
		public MethodDescriptor parse() {
			boolean isParam = true;
			Mutable<Class<?>> clazz = new MutableObject<>();
			while (!isEnd()) {
				if (match(OpenPar)) {
					isParam = true;
				} else if (match(ClosePar)) {
					if (!isParam) {
						error(OpenPar);
					}
					isParam = false;
				} else if (match(TypeToken.token, clazz)) {
					if (isParam) {
						desc.params = ArrayUtils.add(desc.params, clazz.getValue());
					} else {
						desc.returnType = clazz.getValue();
					}
				} else {
					readToken();
				}
			}

			return desc;
		}
	}

	private static class TypeToken extends Token<Class<?>> {
		private static TypeToken token = (TypeToken) new TypeToken().name("TypeToken");

		private int size;

		@Override
		public boolean matches(String s, int index) {
			value = null;
			size = 1;
			char c = s.charAt(index);
			switch (c) {
				case 'Z':
					value = boolean.class;
					break;
				case 'B':
					value = byte.class;
					break;
				case 'C':
					value = char.class;
					break;
				case 'S':
					value = short.class;
					break;
				case 'I':
					value = int.class;
					break;
				case 'J':
					value = long.class;
					break;
				case 'F':
					value = float.class;
					break;
				case 'D':
					value = double.class;
					break;
				case 'V':
					value = void.class;
					break;
				case '[':
				case 'L':
					value = getClass(s, index);
					break;
			}

			return value != null;
		}

		private Class<?> getClass(String s, int index) {
			boolean isArray = s.charAt(index) == '[';
			int e = s.indexOf(';', index);
			if (e == -1) {
				return null;
			}
			try {
				String name = s.substring(isArray ? index : index + 1, isArray ? e + 1 : e).replace('/', '.');
				size = name.length() + (isArray ? 0 : 2);
				return Class.forName(name);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				return null;
			}
		}

		@Override
		public int size() {
			return size;
		}

	}
}
