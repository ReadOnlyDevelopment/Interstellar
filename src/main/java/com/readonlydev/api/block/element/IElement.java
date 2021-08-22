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

package com.readonlydev.api.block.element;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.readonlydev.api.block.IElementProvider;

public interface IElement {
	/**
	 * Checks if this element should only be used client side.
	 *
	 * @return true, if is client element
	 */
	public default boolean isClientElement() {
		return false;
	}

	/**
	 * Gets the additional elements that this {@link IElement} depends on.
	 *
	 * @return the dependencies
	 */
	public default List<IElement> getDependencies() {
		return ImmutableList.of();
	}

	/**
	 * Called when this {@link IElement} is added to the {@link IElementProvider}.
	 *
	 * @param provider the provider
	 */
	public default void onElementAdded(IElementProvider provider) {

	}

	/**
	 * Gets the element of the specify <code>type</code> for the {@link Object}.<br>
	 * The returned object may <b>not</b> be a element but the block itself if it implements an interface used for a {@link IElement}.
	 *
	 * @param <T>    the generic type
	 * @param type   the type
	 * @param object the block
	 * @return the component
	 */
	public static <T> T getElement(Class<T> type, Object object) {
		if (object == null) {
			return null;
		}

		if (type.isAssignableFrom(object.getClass())) {
			return type.cast(object);
		}

		if (!(object instanceof IElementProvider)) {
			return null;
		}

		return ((IElementProvider) object).getElement(type);
	}
}
