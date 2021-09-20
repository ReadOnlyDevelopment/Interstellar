package com.readonlydev.lib.world.biome;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.readonlydev.api.storage.IndexedMap.LimitedIndexedMap.IntegerLIMap;
import com.readonlydev.api.world.biome.IBiomeExoplanet;

import net.minecraft.world.biome.Biome;

public final class BiomeMap implements IntegerLIMap<BiomeExoplanet, IBiomeExoplanet> {

	private static final long serialVersionUID = -5276712268351774212L;

	@SuppressWarnings("unchecked")
	transient private final IndexMapEntry<BiomeExoplanet, IBiomeExoplanet, Integer>[] entries = new IndexMapEntry[getCapacity()];
	private boolean locked = false;

	public BiomeMap() {
	}

	/* BiomeMap exclusive */

	public void addBiomes(IBiomeExoplanet... rbiomes) {
		Arrays.stream(rbiomes).forEach(biome -> put(biome.getBiome(), biome));
	}

	/* LimitedIndexedMap Overrides */

	@Override
	public int getCapacity() {
		return 256;
	}

	/* IndexedMap Overrides */

	@Override
	public boolean isStrict() {
		return false;
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public boolean areEntriesMutable() {
		return false;
	}

	@Override
	public Function<BiomeExoplanet, Integer> getIndexer() {
		return Biome::getIdForBiome;
	}

	@Override
	public Integer getIndex(@Nonnull final BiomeExoplanet key) {
		return getIndexer().apply(key);
	}

	@Override
	@Nullable
	public BiomeExoplanet getKeyAt(@Nonnull final Integer index) {
		if (checkBounds(index)) {
			return null;
		}
		return (entries[index] != null) ? entries[index].getKey() : null;
	}

	@Override
	@Nullable
	public IBiomeExoplanet getValueAt(@Nonnull final Integer index) {
		if (checkBounds(index)) {
			return null;
		}
		return (entries[index] != null) ? entries[index].getValue() : null;
	}

	@Override
	@Nullable
	public IBiomeExoplanet putAt(@Nonnull final BiomeExoplanet key, @Nonnull final IBiomeExoplanet value, final Integer index) {
		if (locked) {
			return null;
		}
		if (checkBounds(index)) {
			return null;
		}
		if (isMutable()) {
			Objects.requireNonNull(key, "Key cannot be null");
			Objects.requireNonNull(value, "Value cannot be null");
			return insert(getNewEntry(key, value), index);
		}
		if (isStrict()) {
			throw new IllegalArgumentException("Immutable data");
		}
		return null;
	}

	@Override
	@Nullable
	public IBiomeExoplanet remove(@Nonnull final Integer index) {
		if (locked) {
			return null;
		}
		IBiomeExoplanet oldValue = getValueAt(index);
		entries[index] = null;
		return oldValue;
	}

	/* IndexedMap Utility */

	@Nullable
	private IBiomeExoplanet insert(@Nonnull final IndexMapEntry<BiomeExoplanet, IBiomeExoplanet, Integer> newEntry, final int index) {
		Entry<BiomeExoplanet, IBiomeExoplanet> oldValue = entries[index];
		entries[index] = newEntry;
		return (oldValue != null) ? oldValue.getValue() : null;
	}

	/* java.util.Map Overrides */

	@Override
	public int size() {
		return (int) Arrays.stream(entries).filter(Objects::nonNull).count();
	}

	@Override
	public boolean isEmpty() {
		return Arrays.stream(entries).noneMatch(Objects::nonNull);
	}

	@Override
	public boolean containsKey(@Nonnull final Object key) {
		return Arrays.stream(entries).anyMatch(e -> e != null && e.getKey().equals(key));
	}

	@Override
	public boolean containsValue(@Nonnull final Object value) {
		return Arrays.stream(entries).anyMatch(e -> e != null && e.getValue().equals(value));
	}

	@Override
	@Nullable
	public IBiomeExoplanet get(@Nonnull final Object key) {
		return Arrays.stream(entries).filter(e -> Objects.nonNull(e) && e.getKey().equals(key)).findFirst().map(Entry::getValue).orElse(null);
	}

	@Override
	@Nullable
	public IBiomeExoplanet put(@Nonnull final BiomeExoplanet key, @Nonnull final IBiomeExoplanet value) {
		if (locked) {
			return null;
		}
		return putAt(key, value, getIndexer().apply(key));
	}

	@Override
	@Nullable
	public IBiomeExoplanet remove(@Nonnull final Object key) {
		if (locked) {
			return null;
		}
		for (int i = 0; i < entries.length; i++) {
			if (Objects.equals(getKeyAt(i), key)) {
				return remove(i);
			}
		}
		return null;
	}

	@Override
	public void putAll(@Nonnull final Map<? extends BiomeExoplanet, ? extends IBiomeExoplanet> m) {
		if (locked) {
			return;
		}
		m.forEach(this::put);
	}

	@Override
	public void clear() {
		if (locked) {
			return;
		}
		Arrays.fill(entries, null);
	}

	@Override
	@Nonnull
	public Set<BiomeExoplanet> keySet() {
		return Arrays.stream(entries).filter(Objects::nonNull).map(Entry::getKey).collect(Collectors.toCollection(LinkedHashSet::new));
	}

	@Override
	@Nonnull
	public Collection<IBiomeExoplanet> values() {
		return Arrays.stream(entries).filter(Objects::nonNull).map(Entry::getValue).collect(Collectors.toList());
	}

	@Override
	@Nonnull
	public Set<Entry<BiomeExoplanet, IBiomeExoplanet>> entrySet() {
		return Arrays.stream(entries).filter(Objects::nonNull).collect(Collectors.toCollection(LinkedHashSet::new));
	}

	/* java.lang.Object Overrides */

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BiomeMap map = (BiomeMap) o;
		return getCapacity() == map.getCapacity() && Arrays.equals(entries, map.entries);
	}

	@Override
	public int hashCode() {
		return 31 * getCapacity() + Arrays.hashCode(entries);
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder(getClass().getSimpleName()).append("[");
		boolean first = true;
		for (Entry<BiomeExoplanet, IBiomeExoplanet> e : entrySet()) {
			if (first) {
				ret.append("{");
			} else {
				ret.append(",{");
			}
			ret.append(e.toString()).append("}");
			first = false;
		}
		return ret.append("]").toString();
	}

	/* Added convenience methods */

	@SuppressWarnings("unused")
	public Stream<Entry<BiomeExoplanet, IBiomeExoplanet>> stream() {
		return StreamSupport.stream(Spliterators.spliterator(entrySet(), 0), false);
	}

	private IndexMapEntry<BiomeExoplanet, IBiomeExoplanet, Integer> getNewEntry(@Nonnull BiomeExoplanet key, @Nonnull IBiomeExoplanet value) {
		if (areEntriesMutable()) {
			return new IndexMapEntry.IndexEntryBase.SimpleMutableIndexEntry<>(key, value, getIndexer().apply(key));
		} else {
			return new IndexMapEntry.IndexEntryBase.SimpleImmutableIndexEntry<>(key, value, getIndexer().apply(key));
		}
	}

	public void setLocked() {
		locked = true;
	}
}
