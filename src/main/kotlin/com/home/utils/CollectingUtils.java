package com.home.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Утилита для разнообразных функций.
 */
public class CollectingUtils {

    public static <T> Set<T> set(final T t) {
        return Sets.newHashSet(t);
    }

    public static <T> Set<T> set(final T... t) {
        return Sets.newHashSet(t);
    }

    /**
     * @param keyExtractor функция, определяющая по значению какого поля элемента будет происходить сопоставление.
     * @param collection коллекция, к элементам которой сопоставляется их же значение определенного поля.
     * @param <Element> тип элемента.
     * @param <Key> тип значения поля сопоставления.
     * @return сопоставления - "значение поля элемента"-"элемент".
     */
    public static <Element, Key> Map<Key, Element> toMap(final Function<? super Element, Key> keyExtractor,
                                                         final Iterable<? extends Element> collection)
    {
        final HashMap<Key, Element> map = new LinkedHashMap<>();
        collection.forEach(element -> map.put(keyExtractor.apply(element), element));
        return map;
    }

    public static <Element, Key1, Key2, Key3> HashSet<Triple<Key1, Key2, Key3>> toMap(final Function<? super Element, Key1> key1Extractor,
                                                                                      final Function<? super Element, Key2> key2Extractor,
                                                                                      final Function<? super Element, Key3> key3Extractor,
                                                                                      final Iterable<? extends Element> collection)
    {
        final HashSet<Triple<Key1, Key2, Key3>> set = new HashSet<>();
        collection.forEach(element -> {
            final Key1 key1 = key1Extractor.apply(element);
            final Key2 key2 = key2Extractor.apply(element);
            final Key3 key3 = key3Extractor.apply(element);
            set.add(Triple.of(key1, key2, key3));
        });
        return set;
    }

    public static <Element, Key1, Key2> Map<Key1, Key2> toMap(final Function<? super Element, Key1> key1Extractor,
                                                              final Function<? super Element, Key2> key2Extractor,
                                                              final Iterable<? extends Element> collection)
    {
        final HashMap<Key1, Key2> set = new HashMap<>();
        collection.forEach(element -> {
            final Key1 key1 = key1Extractor.apply(element);
            final Key2 key2 = key2Extractor.apply(element);
            set.put(key1, key2);
        });
        return set;
    }


    public static <Element, Key> Collecting<Key> map(
        final Function<? super Element, Key> keyExtractor,
        final Iterable<? extends Element> collection)
    {

        final Set<Key> map = new LinkedHashSet<>();
        collection.forEach(element -> map.add(keyExtractor.apply(element)));
        return new Collecting<>(map);
    }

    public static <Key, Value> Map<Value, List<Key>> flip(final Map<Key, Value> map) {
        Map<Value, List<Key>> flipped = new HashMap<>();
        for (final Map.Entry<Key, Value> originalEntry : map.entrySet()) {
            Value flippedKey = originalEntry.getValue();
            List<Key> flippedValues = flipped.get(flippedKey);
            Key originalKey = originalEntry.getKey();
            if (flippedValues == null) {
                List<Key> flippedValue = Lists.<Key>newLinkedList();
                flippedValue.add(originalKey);
                flipped.put(flippedKey, flippedValue);
            } else {
                flippedValues.add(originalKey);
            }
        }
        return flipped;
    }

    public static <Key, Value> Collection<Pair<Key, Value>> entriesToPairs(final Map<Key, Value> toPersist) {
        return toPersist.entrySet()
            .stream()
            .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

    public static <Left, Right> Collector<Pair<Left, Right>, ?, Map<Left, Right>> pairToMap() {
        return Collectors.toMap(Pair::getLeft, Pair::getRight);
    }

    public static class Collecting<Element> {

        private Collection<? extends Element> source;
        private Map<Class<? extends Collection>, Function<Collection, ? extends Collection>> map = new HashMap<>();

        {
            final Function<Collection, HashSet> hashSetCollector = HashSet::new;
            final Function<Collection, LinkedHashSet> linkedHashSetCollector = LinkedHashSet::new;
            final Function<Collection, ArrayList> arrayListCollector = ArrayList::new;
            final Function<Collection, LinkedList> linkedListCollector = LinkedList::new;

            map.put(HashSet.class, hashSetCollector);
            map.put(LinkedHashSet.class, linkedHashSetCollector);
            map.put(ArrayList.class, arrayListCollector);
            map.put(LinkedList.class, linkedListCollector);
        }

        Collecting(final Collection<? extends Element> source) {
            this.source = source;
        }

        public ArrayList<Element> arrayList() {
            return (ArrayList<Element>) this.to(ArrayList.class);
        }
        public LinkedList<Element> linkedList() {
            return (LinkedList<Element>) this.to(LinkedList.class);
        }
        public LinkedHashSet<Element> linkedHashSet() {
            return (LinkedHashSet<Element>) this.to(LinkedHashSet.class);
        }

        public<Target extends Collection> Target to(Class<Target> collectionClass) {
            final Function<Collection, ? extends Collection> collector = map.get(collectionClass);
            return (Target) Optional.ofNullable(collector)
                .map(it -> it.apply(source))
                .orElseThrow(() -> new RuntimeException(String.format("Collector for class %s is not added", collectionClass)));
        }
    }

    public static <T> Collection<Pair<T, T>> zipToPairs(@NotNull final Collection<T> firsts,
                                                        @NotNull final Collection<T> seconds)
    {
        if (firsts.size() != seconds.size()) {
            throw new RuntimeException("collections can not be zipped to pairs due to sizes inequality");
        }
        final Set<Pair<T, T>> collect =
            StreamEx
                .zip(Lists.newArrayList(firsts),
                    Lists.newArrayList(seconds),
                    Pair::of)
                .collect(Collectors.toSet());
        return collect;
    }
}
