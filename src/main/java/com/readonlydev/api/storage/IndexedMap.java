package com.readonlydev.api.storage;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IndexedMap<K, V, I extends Comparable<I>> extends Map<K, V>, Serializable {

	boolean isStrict();

	boolean isMutable();

	boolean areEntriesMutable();

	Function<K, I> getIndexer();

	I getIndex(@Nonnull final K key);

	@Nullable
	V putAt(@Nonnull final K key, @Nonnull final V value, @Nullable final I index);

	@Nullable
	K getKeyAt(@Nonnull final I index);

	@Nullable
	V getValueAt(@Nonnull final I index);

	@Nullable
	V remove(@Nonnull final I index);

	interface LimitedIndexedMap<K, V, I extends Comparable<I>> extends IndexedMap<K, V, I> {

		int getCapacity();

		default boolean checkBounds(int index) {
			if (index < 0 || index >= getCapacity()) {
				if (isStrict()) {
					throw new IndexOutOfBoundsException("Index is out of bounds for map entry: " + index);
				}
				return true;
			}
			return false;
		}

		interface ByteLIMap<K, V> extends LimitedIndexedMap<K, V, Byte> {

		}

		interface ShortLIMap<K, V> extends LimitedIndexedMap<K, V, Short> {

		}

		interface IntegerLIMap<K, V> extends LimitedIndexedMap<K, V, Integer> {

		}

		interface LongLIMap<K, V> extends LimitedIndexedMap<K, V, Long> {

		}
	}

	interface IndexMapEntry<K, V, I extends Comparable<I>>
			extends Entry<K, V>, Comparable<IndexMapEntry<K, V, I>>, Serializable {

		I getIndex();

		// default comparison of only the index
		@Override
		default int compareTo(@Nonnull final IndexMapEntry<K, V, I> other) {
			return getIndex().compareTo(other.getIndex());
		}

		abstract class IndexEntryBase<K, V, I extends Comparable<I>> implements IndexMapEntry<K, V, I> {

			private static final long serialVersionUID = -5154097056428766455L;

			private final K key;
			private final I index;
			private V value;

			IndexEntryBase(@Nonnull final K key, @Nonnull final V value, @Nonnull final I index) {
				this.key = key;
				this.value = value;
				this.index = index;
			}

			@Override
			public K getKey() {
				return key;
			}

			@Override
			public V getValue() {
				return value;
			}

			@Override
			public I getIndex() {
				return index;
			}

			V putValue(@Nonnull final V newValue) {
				V ret = getValue();
				value = newValue;
				return ret;
			}

			@Override
			public boolean equals(final Object o) {
				if (this == o) {
					return true;
				}
				if (!(o instanceof IndexMapEntry.IndexEntryBase)) {
					return false;
				}
				IndexEntryBase<?, ?, ?> other = (IndexEntryBase<?, ?, ?>) o;
				return Objects.equals(getKey(), other.getKey()) && Objects.equals(getValue(), other.getValue())
						&& Objects.equals(getIndex(), other.getIndex());
			}

			@Override
			public int hashCode() {
				return getIndex().hashCode() ^ getKey().hashCode() ^ getValue().hashCode();
			}

			@Override
			public String toString() {
				return getClass().getSimpleName() + "[" + getIndex() + "[" + getKey() + "=" + getValue() + "]]";
			}

			public static class SimpleMutableIndexEntry<K, V, I extends Comparable<I>> extends IndexEntryBase<K, V, I> {

				private static final long serialVersionUID = -2225795238508933466L;

				public SimpleMutableIndexEntry(@Nonnull final K key, @Nonnull final V value, @Nonnull final I index) {
					super(key, value, index);
				}

				public SimpleMutableIndexEntry(@Nonnull final K key, @Nonnull final V value,
						@Nonnull final Function<K, I> indexer) {
					super(key, value, indexer.apply(key));
				}

				@Override
				public V setValue(@Nonnull final V newValue) {
					return putValue(newValue);
				}
			}

			public static class SimpleImmutableIndexEntry<K, V, I extends Comparable<I>>
					extends IndexEntryBase<K, V, I> {

				private static final long serialVersionUID = 2533506876382702762L;

				public SimpleImmutableIndexEntry(@Nonnull final K key, @Nonnull final V value, @Nonnull final I index) {
					super(key, value, index);
				}

				public SimpleImmutableIndexEntry(@Nonnull final K key, @Nonnull final V value,
						@Nonnull final Function<K, I> indexer) {
					super(key, value, indexer.apply(key));
				}

				@Override
				public V setValue(@Nonnull final V newValue) {
					throw new UnsupportedOperationException("Immutable data");
				}
			}
		}
	}
}
