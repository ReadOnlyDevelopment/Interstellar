/*
 * API License
 *
 * Copyright (c) 2021 ReadOnly Development
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.readonlydev.api.block;

import java.util.List;

import com.readonlydev.api.block.element.IBlockElement;
import com.readonlydev.api.block.element.IElement;

public interface IElementProvider {
	/**
	 * Gets the {@link IBlockElement} for this {@link IElementProvider}.
	 *
	 * @return the components
	 */
	public List<IElement> getElements();

	/**
	 * Adds the {@link IBlockElement} to this {@link IElementProvider}.
	 *
	 * @param component the component
	 */
	public void addElement(IElement component);

	/**
	 * Gets the {@link IBlockElement} of the specified type from this {@link IElementProvider}.
	 *
	 * @param <T>  the generic type
	 * @param type the type
	 * @return the component
	 */
	public default <T> T getElement(Class<T> type) {
		return getElements().stream().filter(type::isInstance).map(type::cast).findFirst().orElse(null);
	}
}
