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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.BiMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashBiMap;

public class DoubleKeyMap<K, V> implements Iterable<DoubleKeyMap.DoubleKeyEntry<K, V>> {
	public static class DoubleKeyEntry<K, V> {
		private int	index;
		private K	key;
		private V	value;

		public DoubleKeyEntry(int index, K key, V value) {
			this.index = index;
			this.key = key;
			this.value = value;
		}

		public int getIndex() {
			return index;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}
	}

	private List<DoubleKeyEntry<K, V>>	data	= new ArrayList<>();
	private BiMap<K, Integer>			keys	= HashBiMap.create();

	public int put(K key, V value) {
		if (keys.get(key) != null) {
			throw new IllegalArgumentException("Key already in map : " + key);
		}

		int i = data.size();
		keys.put(key, i);
		data.add(new DoubleKeyEntry<>(i, key, value));
		return i;
	}

	public DoubleKeyEntry<K, V> getEntry(int index) {
		return data.get(index);
	}

	public DoubleKeyEntry<K, V> getEntry(K key) {
		if (keys.get(key) == null) {
			return null;
		}
		return data.get(keys.get(key));
	}

	public K getKey(int index) {
		DoubleKeyEntry<K, V> entry = getEntry(index);
		return entry != null ? entry.getKey() : null;
	}

	public int getIndex(K key) {
		return keys.get(key);
	}

	public V get(int index) {
		DoubleKeyEntry<K, V> entry = getEntry(index);
		return entry != null ? entry.getValue() : null;
	}

	public V get(K key) {
		DoubleKeyEntry<K, V> entry = getEntry(key);
		return entry != null ? entry.getValue() : null;
	}

	@Override
	public Iterator<DoubleKeyEntry<K, V>> iterator() {
		return data.iterator();
	}

	public Collection<V> values() {
		return Collections2.transform(data, new Function<DoubleKeyEntry<K, V>, V>() {
			@Override
			public V apply(DoubleKeyEntry<K, V> entry) {
				return entry.getValue();
			}
		});
	}
}
