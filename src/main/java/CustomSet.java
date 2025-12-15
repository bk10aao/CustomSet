import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * A custom implementation of the {@link Set} interface/>.
 * This set does not allow duplicate elements and permits null elements, like {@link HashSet}}.
 *
 * @param <E> the type of elements maintained by this set
 * @author Benjamin Kane
 * LinkedIn - <a href="https://www.linkedin.com/in/benjamin-kane-81149482/"/>
 * GitHub account bk10aao - <a href="https://github.com/bk10aao"/>
 * Repository - <a href="https://github.com/bk10aao/CustomSet"/>
 */
public class CustomSet<E> implements Set<E> {

    private double LOAD_FACTOR = 0.75;
    private int primesIndex = 0;
    private int size = 0;
    private int setSize = primes[primesIndex];

    private LinkedList<E>[] set;

    /**
     * Constructs an empty set with default initial capacity (17) and load factor (0.75).
     */
    @SuppressWarnings("unchecked")
    public CustomSet() {
        set = new LinkedList[setSize];
    }

    /**
     * Constructs a set containing the elements of the specified collection.
     *
     * @param c the collection whose elements are to be placed into this set
     * @throws NullPointerException if the specified collection is null
     */
    public CustomSet(final Collection<? extends E> c) {
        if(c == null)
            throw new NullPointerException();
        generateSet(Math.max((int) (c.size() / LOAD_FACTOR) + 1, 1));
        addAll(c);
    }

    /**
     * Constructs an empty set with the specified initial capacity and default load factor (0.75).
     *
     * @param initialCapacity the initial capacity
     * @throws IllegalArgumentException if the initial capacity is negative
     */
    public CustomSet(final int initialCapacity) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        generateSet(initialCapacity);
    }

    /**
     * Constructs an empty set with the specified initial capacity and load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     * @throws IllegalArgumentException if the initial capacity is negative or the load factor is non-positive or NaN
     */
    public CustomSet(final int initialCapacity, final double loadFactor) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        if (loadFactor <= 0 || Double.isNaN(loadFactor) || Double.isInfinite(loadFactor))
            throw new IllegalArgumentException();
        this.LOAD_FACTOR = loadFactor;
        generateSet(initialCapacity);
    }

    /**
     * Adds the specified element to this set if it is not already present.
     * If this set already contains the element, the call leaves the set unchanged
     * and returns {@code false}. This set permits null elements.
     *
     * @param item element to be added to this set
     * @return {@code true} if this set did not already contain the specified element
     */
    @Override
    public boolean add(final E item) {
        if(item == null)
            throw new NullPointerException();
        int index = Math.abs(item.hashCode()) % setSize;
        if (contains(item, index))
            return false;
        if (set[index] == null)
            set[index] = new LinkedList<>();
        set[index].add(item);
        size++;
        if ((double) size / (double) setSize > LOAD_FACTOR)
            expand();
        return true;
    }

    /**
     * Adds all the elements in the specified collection to this set if they're
     * not already present. If the specified collection is also a set, the
     * {@code addAll} operation effectively modifies this set so that its value
     * is the union of the two sets. Null elements are permitted.
     *
     * @param c collection containing elements to be added to this set
     * @return {@code true} if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean addAll(final Collection<? extends E> c) {
        if(c == null)
            throw new NullPointerException();
        int n = size;
        c.forEach(this::add);
        return n < size;
    }

    /**
     * Removes all the elements from this set.
     * The set will be empty after this call returns.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        primesIndex = 0;
        setSize = primes[primesIndex];
        size = 0;
        set = new LinkedList[setSize];
    }


    @SuppressWarnings("unchecked")
    @Override
    public CustomSet<E> clone() throws CloneNotSupportedException {
        CustomSet<E> clone = (CustomSet<E>) super.clone();
        clone.primesIndex = this.primesIndex;
        clone.setSize = this.setSize;
        clone.size = this.size;
        clone.set = new LinkedList[setSize];
        clone.LOAD_FACTOR = this.LOAD_FACTOR;
        for (int i = 0; i < set.length; i++)
            if (set[i] != null)
                clone.set[i] = new LinkedList<>(set[i]);
        return clone;
    }

    /**
     * Returns {@code true} if this set contains the specified element.
     * This set permits null elements.
     *
     * @param item element whose presence in this set is to be tested
     * @return {@code true} if this set contains the specified element
     */
    @Override
    public boolean contains(final Object item) {
        if(item == null)
            throw new NullPointerException();
        int index = Math.abs(item.hashCode()) % setSize;
        if (set[index] == null)
            return false;
        return set[index].contains(item);
    }

    /**
     * Returns {@code true} if this set contains all the elements of the
     * specified collection. Null elements are permitted in the specified collection.
     *
     * @param c collection to be checked for containment in this set
     * @return {@code true} if this set contains all the elements of the specified collection
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean containsAll(final Collection<?> c) {
        if(c == null)
            throw new NullPointerException();
        return c.stream().allMatch(this::contains);
    }

    /**
     * Compares this set with another Set for equality. Returns true if the other
     * set has the same size and contains all the same elements.
     *
     * @param o the object to compare with
     * @return true if the sets are equal
     */
    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Set<?> other) || other.size() != size())
            return false;
        return containsAll(other);
    }

    /**
     * Returns the hash code value for this set. The hash code of a set is
     * defined to be the sum of the hash codes of the elements in the set,
     * where the hash code of a {@code null} element is defined to be zero.
     *
     * @return the hash code value for this set
     */
    @Override
    public int hashCode() {
        return Arrays.stream(set)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .mapToInt(Object::hashCode)
                .sum();
    }

    /**
     * Returns {@code true} if this set contains no elements.
     *
     * @return {@code true} if this set contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns an iterator over the elements in this set. The elements are
     * returned in no particular order.
     *
     * @return an iterator over the elements in this set
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int bucketIndex = 0;
            private Iterator<E> currentIterator = null;
            private int elementsReturned = 0;

            @Override
            public boolean hasNext() {
                return elementsReturned < size;
            }

            @Override
            public E next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                while (currentIterator == null || !currentIterator.hasNext()) {
                    while (bucketIndex < set.length && set[bucketIndex] == null)
                        bucketIndex++;
                    if (bucketIndex >= set.length)
                        throw new NoSuchElementException();
                    currentIterator = set[bucketIndex++].iterator();
                }
                elementsReturned++;
                return currentIterator.next();
            }
        };
    }

    /**
     * Removes the specified element from this set if it is present.
     * Returns {@code true} if this set contained the element. This set
     * permits null elements.
     *
     * @param item object to be removed from this set, if present
     * @return {@code true} if this set contained the specified element
     */
    @Override
    public boolean remove(final Object item) {
        if(item == null)
            throw new NullPointerException();
        int index = Math.abs(item.hashCode()) % setSize;
        if (!contains(item, index))
            return false;
        set[index].remove(item);
        if(set[index].isEmpty())
            set[index] = null;
        size--;
        if(setSize > primes[0] && size <= setSize / 4)
            reduce();
        return true;
    }

    /**
     * Removes from this set all of its elements that are contained in the
     * specified collection. If the specified collection is also a set, this
     * operation effectively modifies this set so that its value is the
     * asymmetric set difference of the two sets. Null elements are permitted
     * in the specified collection.
     *
     * @param c collection containing elements to be removed from this set
     * @return {@code true} if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean removeAll(final Collection<?> c) {
        boolean changed = false;
        for(Object item : c)
            if (remove(item))
                changed = true;
        return changed;
    }

    /**
     * Retains only the elements in this set that are contained in the
     * specified collection. In other words, removes from this set all of
     * its elements that are not contained in the specified collection.
     * If the specified collection is also a set, this operation effectively
     * modifies this set so that its value is the intersection of the two sets.
     * Null elements are permitted in the specified collection.
     *
     * @param c collection containing elements to be retained in this set
     * @return {@code true} if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean retainAll(final Collection<?> c) {
        if (c == null || c.contains(null))
            throw new NullPointerException();
        boolean modified = false;
        for (int i = 0; i < set.length; i++)
            if (set[i] != null)
                modified = retain(c, set[i], modified, i);
        if (setSize > primes[0] && size <= setSize / 4)
            reduce();
        return modified;
    }

    /**
     * Returns the number of elements in this set (its cardinality).
     *
     * @return the number of elements in this set (its cardinality)
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * Returns an array containing all the elements in this set.
     * The returned array will be "safe" in that no references to it are
     * maintained by this set.
     *
     * @return an array containing all the elements in this set
     */
    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray() {
        E[] arr = (E[]) new Object[size];
        int[] idx = {0};
        Arrays.stream(set)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .forEach(item -> arr[idx[0]++] = item);
        return arr;
    }

    /**
     * Returns an array containing all the elements in this set; the
     * runtime type of the returned array is that of the specified array.
     * If the set fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this set.
     *
     * @param a the array into which the elements of this set are to be stored, if it is big enough;
     *          otherwise, a new array of the same runtime type is allocated
     * @return an array containing all the elements in this set
     * @throws ArrayStoreException if the runtime type of the specified array is not a supertype
     *         of the runtime type of every element in this set
     * @throws NullPointerException if the specified array is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        T[] arrayToFill;
        if (a.length < size)
            arrayToFill = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        else
            arrayToFill = a;
        int[] idx = {0};
        Arrays.stream(set)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .forEach(item -> arrayToFill[idx[0]++] = (T) item);
        if (a.length > size) {
            arrayToFill[size] = null;
        }
        return arrayToFill;
    }

    /**
     * Returns String representation of CustomSet
     *
     * @return String representation of CustomSet
     */
    @Override
    public String toString() {
        if (size == 0) return "{ }";
        StringBuilder sb = new StringBuilder("{");
        boolean[] first = {true};
        Arrays.stream(set)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .forEach(item -> {
                    if (!first[0]) sb.append(", ");
                    sb.append(item);
                    first[0] = false;
                });
        return sb.append('}').toString();
    }

    private boolean contains(final Object item, final int index) {
        return set[index] != null && set[index].contains(item);
    }

    @SuppressWarnings("unchecked")
    private void expand() {
        if(primesIndex + 1 >= primes.length)
            throw new IllegalStateException();
        setSize = primes[++primesIndex];
        LinkedList<E>[] newSet = new LinkedList[setSize];
        Arrays.stream(set)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .forEach(item -> {
                    int index = Math.abs(item.hashCode()) % setSize;
                    if (newSet[index] == null)
                        newSet[index] = new LinkedList<>();
                    newSet[index].add(item);
        });
        set = newSet;
    }

    @SuppressWarnings("unchecked")
    private void generateSet(final int initialCapacity) {
        setSize = primes[0];
        primesIndex = 0;
        if (initialCapacity > primes[primes.length - 1]) {
            setSize = primes[primes.length - 1];
            primesIndex = primes.length - 1;
        } else
            for (int i = 0; i < primes.length; i++) {
                if (primes[i] >= initialCapacity) {
                    setSize = primes[i];
                    primesIndex = i;
                    break;
                }
            }
        set = new LinkedList[setSize];
    }

    @SuppressWarnings("unchecked")
    private void reduce() {
        if(primesIndex == 0)
            return;
        primesIndex--;
        setSize = primes[primesIndex];
        LinkedList<E>[] newSet = new LinkedList[setSize];
        IntStream.range(0, set.length)
                .filter(i -> set[i] != null)
                .forEach(i -> set[i].forEach(item -> {
                    int index = Math.abs(item.hashCode()) % setSize;
                    if (newSet[index] == null)
                        newSet[index] = new LinkedList<>();
                    newSet[index].add(item);
                }));
        set = newSet;
    }

    private boolean retain(final Collection<?> c, final LinkedList<E> list, boolean modified, int index) {
        Iterator<E> iterator = list.iterator();
        while (iterator.hasNext())
            if (!c.contains(iterator.next())) {
                iterator.remove();
                size--;
                modified = true;
            }
        if(set[index].isEmpty())
            set[index] = null;
        return modified;
    }

    protected static final int[] primes = { 17, 23, 29, 37, 47, 59, 71, 89, 107, 131, 163, 197, 239, 293, 353, 431, 521, 631, 761, 919,
                                            1103, 1327, 1597, 1931, 2333, 2801, 3371, 4049, 4861, 5839, 7013, 8419, 10103, 12143, 14591,
                                            17519, 21023, 25229, 30293, 36353, 43627, 52361, 62851, 75431, 90523, 108631, 130363, 156437,
                                            187751, 225307, 270371, 324449, 389357, 467237, 560689, 672827, 807403, 968897, 1162687, 1395263,
                                            1674319, 2009191, 2411033, 2893249, 3471899, 4166287, 4999559, 5999471, 7199369, 8639231, 10367087, 12440509, 14928661, 17914393 };
}