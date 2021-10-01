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

package com.readonlydev.lib.system;

import org.lwjgl.input.Keyboard;

public class Keys {

	public static boolean isKeyPressed(Key key) {
		return Keyboard.isKeyDown(key.code);
	}

	public enum Key {

		BACKSPACE(8),
		ENTER(10),
		SHIFT(16),
		CTRL(17),
		ALT(18),
		SPACE(32),
		LEFT(37),
		UP(38),
		RIGHT(39),
		DOWN(40),
		ZERO(48),
		ONE(49),
		TWO(50),
		THREE(51),
		FOUR(52),
		FIVE(53),
		SIX(54),
		SEVEN(55),
		EIGHT(56),
		NINE(57),
		A(65),
		B(66),
		C(67),
		D(68),
		E(69),
		F(70),
		G(71),
		H(72),
		I(73),
		J(74),
		K(75),
		L(76),
		M(77),
		N(78),
		O(79),
		P(80),
		Q(81),
		R(82),
		S(83),
		T(84),
		U(85),
		V(86),
		W(87),
		X(88),
		Y(89),
		Z(90),
		NP_ZERO(96),
		NP_ONE(97),
		NP_TWO(98),
		NP_THREE(99),
		NP_FOUR(100),
		NP_FIVE(101),
		NP_SIX(102),
		NP_SEVEN(103),
		NP_EIGHT(104),
		NP_NINE(105),
		NP_MULTIPLY(106),
		NP_ADD(107),
		NP_MINUS(109),
		NP_PERIOD(110),
		NP_DIVIDE(111);

		public int code;

		Key(int code) {
			this.code = code;
		}
	}
}
