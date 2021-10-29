/*
 * MIT License
 *
 * Copyright (c) 2021 Negative
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

package dev.negativekb.kitpvpframework.core.datastructure;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * BiMap is an extended version of {@link java.util.Map}, {@link java.util.HashMap},
 * or whatever. This data class can hold 2 values rather than 1 value similar to
 * {@link java.util.Map} or {@link java.util.HashMap} and any other data sets that
 * do not hold 2 values.
 *
 * @param <K>  Key (Object)
 * @param <V>  Value 1 (any Type)
 * @param <V1> Value 2 (any Type)
 * @author Negative
 * @since August 4th, 2021
 */
public class BiMap<K, V, V1> {

    /**
     * Stores all the data entries
     */
    private List<Entry<K, V, V1>> data = new ArrayList<>();

    /**
     * Clears the data map
     */
    public void clear() {
        this.data.clear();
    }

    /**
     * Puts an Entry in with 2 values
     *
     * @param key    Key
     * @param value1 Value 1
     * @param value2 Value 2
     */
    public void put(K key, V value1, V1 value2) {
        if (getEntry(key).isPresent())
            return;

        Entry<K, V, V1> entry = new Entry<>(key);
        entry.setValue1(value1);
        entry.setValue2(value2);

        this.data.add(entry);
    }

    /**
     * Replaces the values inside the entry with the valid key
     *
     * @param key    Key
     * @param value1 Value 1
     * @param value2 Value 2
     */
    public void replace(K key, V value1, V1 value2) {
        if (!getEntry(key).isPresent())
            return;

        Entry<K, V, V1> entry = getEntry(key).get();
        entry.setValue1(value1);
        entry.setValue2(value2);

        this.data.set(getDataIndex(key), entry);
    }

    /**
     * Creates a new Entry with only one value
     *
     * @param key    Key
     * @param value1 Value
     */
    public void putFirst(K key, V value1) {
        if (getEntry(key).isPresent())
            return;

        Entry<K, V, V1> entry = new Entry<>(key);
        entry.setValue1(value1);
        this.data.add(entry);
    }

    /**
     * Replaces the first value in an Entry with a valid key
     *
     * @param key   Key
     * @param value Value
     */
    public void replaceFirst(K key, V value) {
        if (!getEntry(key).isPresent())
            return;

        Entry<K, V, V1> entry = getEntry(key).get();
        entry.setValue1(value);

        this.data.set(getDataIndex(key), entry);
    }

    /**
     * Creates an Entry with only the second value made
     *
     * @param key    Key
     * @param value2 Value
     */
    public void putSecond(K key, V1 value2) {
        if (getEntry(key).isPresent())
            return;

        Entry<K, V, V1> entry = new Entry<>(key);
        entry.setValue2(value2);

        this.data.add(entry);
    }

    /**
     * Replaces the second value in the Entry
     *
     * @param key   Key
     * @param value Value
     */
    public void replaceSecond(K key, V1 value) {
        if (!getEntry(key).isPresent())
            return;

        Entry<K, V, V1> entry = getEntry(key).get();
        entry.setValue2(value);

        this.data.set(getDataIndex(key), entry);
    }

    /**
     * Gets the first value of an Entry with a valid key
     *
     * @param key Key
     * @return Value
     * @throws NullPointerException If the entry does not exist inside the entry data
     */
    public Optional<V> getFirst(K key) {
        if (!getEntry(key).isPresent())
            throw new NullPointerException("This entry with key of `" + key.toString() + "` is not in the BiMap");

        Entry<K, V, V1> entry = getEntry(key).get();
        return Optional.ofNullable(entry.getValue1());
    }

    /**
     * Gets the first value of an Entry with a valid index key
     *
     * @param index Index
     * @return Value
     * @throws ArrayIndexOutOfBoundsException if the Index value given is out of the index bounds
     */
    public Optional<V> getFirst(int index) {
        Entry<K, V, V1> entry = this.data.get(index);
        return Optional.ofNullable(entry.getValue1());
    }

    /**
     * Gets the second value of an Entry with a valid key
     *
     * @param key Key
     * @return Value
     * @throws NullPointerException If the entry does not exist inside the entry data
     */
    public Optional<V1> getSecond(K key) {
        if (!getEntry(key).isPresent())
            throw new NullPointerException("This entry with key of `" + key.toString() + "` is not in the BiMap");

        Entry<K, V, V1> entry = getEntry(key).get();
        return Optional.ofNullable(entry.getValue2());
    }

    /**
     * Gets the first value of an Entry with a valid index key
     *
     * @param index Index
     * @return Value
     * @throws ArrayIndexOutOfBoundsException if the Index value given is out of the index bounds
     */
    public Optional<V1> getSecond(int index) {
        Entry<K, V, V1> entry = this.data.get(index);
        return Optional.ofNullable(entry.getValue2());
    }

    /**
     * Get an Entry object with a valid key
     *
     * @param key Key
     * @return Entry
     * @throws NullPointerException If the Entry does not exist
     */
    public Optional<Entry<K, V, V1>> get(K key) {
        return getEntry(key);
    }

    /**
     * Removes an Entry from the entry set with a valid key
     *
     * @param key Key
     */
    public void remove(K key) {
        if (!getEntry(key).isPresent())
            return;

        this.data.remove(getEntry(key).get());
    }

    /**
     * Remove an Entry with a valid key if the function is true
     *
     * @param key      Key
     * @param function Function
     */
    public void removeIf(K key, Function<Entry<K, V, V1>, Boolean> function) {
        if (getEntry(key).isPresent())
            return;

        Entry<K, V, V1> entry = getEntry(key).get();
        Boolean apply = function.apply(entry);
        if (!apply)
            return;

        remove(key);
    }

    /**
     * Removes all the Entries matching the function
     *
     * @param function Function
     */
    public void removeAllIf(Function<Entry<K, V, V1>, Boolean> function) {
        List<Entry<K, V, V1>> toRemove = new ArrayList<>();
        getEntries().stream().filter(function::apply).forEach(toRemove::add);
        toRemove.forEach(e -> remove(e.getKey()));
    }

    /**
     * Checks if an Entry with a valid key matches the input
     *
     * @param key Key
     * @return true or false
     */
    public boolean containsKey(K key) {
        return getEntry(key).isPresent();
    }

    /**
     * Gets an Entry with a valid key
     *
     * @param key Key
     * @return Entry
     */
    private Optional<Entry<K, V, V1>> getEntry(K key) {
        return data.stream().filter(e -> e.getKey().equals(key)).findAny();
    }

    /**
     * Gets the index of an Entry with a valid key
     *
     * @param key Key
     * @return Index of the Entry provided
     */
    private int getDataIndex(K key) {
        if (!getEntry(key).isPresent())
            return -1;

        Entry<K, V, V1> entry = getEntry(key).get();
        return this.data.indexOf(entry);
    }

    /**
     * Returns the size of the BiMap
     *
     * @return Integer
     */
    public int size() {
        return this.data.size();
    }

    /**
     * Gets all the entries inside a List
     *
     * @return Entries
     */
    public List<Entry<K, V, V1>> getEntries() {
        return this.data;
    }

    /**
     * Set the entries of the entry set
     *
     * @param entries Entry set
     */
    public void setEntries(List<Entry<K, V, V1>> entries) {
        this.data = entries;
    }

    /**
     * Clones the BiMap
     *
     * @return BiMap
     */
    public BiMap<K, V, V1> cloneMap() {
        BiMap<K, V, V1> cloneMap = new BiMap<>();
        cloneMap.setEntries(getEntries());

        return cloneMap;
    }

    /**
     * Ability to stream the BiMap
     *
     * @return Stream
     */
    public Stream<Entry<K, V, V1>> stream() {
        return Stream.<Entry<K, V, V1>>builder().build();
    }

    /**
     * Returns whether the Map is empty
     *
     * @return true is the dataset is empty
     */
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    /**
     * Loops through everything in the entry-set
     *
     * @param function Function
     */
    public void forEach(TriConsumer<? super K, ? super V, ? super V1> function) {
        this.data.forEach(e -> function.accept(e.getKey(), e.getValue1(), e.getValue2()));
    }

    /**
     * Replaces all first values in Entries that matches the condition
     *
     * @param condition Condition
     * @param value     Value
     */
    public void replaceAllFirstValues(Function<Entry<K, V, V1>, Boolean> condition, V value) {
        List<Entry<K, V, V1>> passed = this.data.stream().filter(condition::apply).collect(Collectors.toList());
        passed.forEach(entry -> entry.setValue1(value));
    }

    /**
     * Replaces all secondary values in Entries that matches the condition
     *
     * @param condition Condition
     * @param value1    Value
     */
    public void replaceAllSecondaryValues(Function<Entry<K, V, V1>, Boolean> condition, V1 value1) {
        List<Entry<K, V, V1>> passed = this.data.stream().filter(condition::apply).collect(Collectors.toList());
        passed.forEach(entry -> entry.setValue2(value1));
    }

    /**
     * Replaces all values in Entries that matches the condition
     *
     * @param condition Condition
     * @param value1    Value
     * @param value2    Value
     */
    public void replaceAllValues(Function<Entry<K, V, V1>, Boolean> condition, V value1, V1 value2) {
        List<Entry<K, V, V1>> passed = this.data.stream().filter(condition::apply).collect(Collectors.toList());
        passed.forEach(entry -> {
            entry.setValue2(value2);
            entry.setValue1(value1);
        });
    }

    /**
     * Entry class
     * This is the data object which holds the Key and Values
     * of the Map
     *
     * @param <K>  Key
     * @param <V>  Value 1
     * @param <V1> Value 2
     */
    @Data
    public static class Entry<K, V, V1> {
        private final K key;
        private V value1;
        private V1 value2;

    }
}
