package day4;

import java.util.*;
import java.util.function.Consumer;

public class Range implements Iterable<Integer> {

    List<Integer> values = new ArrayList<>();

    public Range(int from, int to) {
        if (from <= to) {
            for (int i = from; i <= to; ++i) {
                values.add(i);
            }
        } else {
            for (int i = to; i <= from; ++i) {
                values.add(i);
            }
        }
    }

    public Integer start() {
        return values.get(0);
    }

    public Integer end() {
        return values.get(values.size() - 1);
    }

    public boolean contains(Integer value) {
        return values.contains(value);
    }

    public boolean contains(Set<Integer> set) {
        for (Integer value : set) {
            if (!values.contains(value)) {
                return false;
            }
        }

        return true;
    }

    public boolean contains(Range compare) {
        if (compare.ascending() && ascending()) {
            return compare.start() >= start() && end() >= compare.end();
        }

        if (!compare.ascending() && ascending()) {
            return end() >= compare.start() && start() <= compare.end();
        }

        if (!ascending() && compare.ascending()) {
            return start() >= compare.end() && end() <= compare.start();
        }

        return start() >= compare.start() && end() <= compare.end();
    }

    public Integer size() {
        return values.size();
    }

    public boolean ascending() {
        return start() <= end();
    }

    public boolean overlaps(Range compare) {
        if (compare.ascending() && ascending()) {
            return (start() <= compare.end() && end() >= compare.end())
                    || (compare.start() <= end() && compare.end() >= end());
        }

        if (!ascending() && !compare.ascending()) {
            return (start() >= compare.end() && compare.start() >= start())
                    || (compare.start() >= end() && start() >= compare.start());
        }

        return (start() <= compare.start() && start() >= compare.end())
                || (compare.start() <= start() && compare.start() >= end());
    }

    public List<Integer> values() {
        return values;
    }

    @Override
    public Iterator<Integer> iterator() {
        return values.iterator();
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        values.forEach(action);
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return values.spliterator();
    }
}
