import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.IntStream;

public class CustomSet<E> implements SetInterface<E> {

    private static double LOAD_FACTOR = 0.75;
    private int primesIndex = 0;
    private int size = 0;
    private int setSize = primes[primesIndex];

    private LinkedList<E>[] set;

    @SuppressWarnings("unchecked")
    public CustomSet() {
        set = new LinkedList[setSize];
    }

    @SuppressWarnings("unchecked")
    public CustomSet(final Collection<E> c) {
        if(c == null)
            throw new NullPointerException();
        set = new LinkedList[primes[primesIndex]];
        addAll(c);
    }

    public CustomSet(final int initialCapacity) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        generateSet(initialCapacity);
    }

    public CustomSet(final int initialCapacity, final double loadFactor) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        if (loadFactor <= 0 || Double.isNaN(loadFactor) || Double.isInfinite(loadFactor))
            throw new IllegalArgumentException();
        generateSet(initialCapacity);
        LOAD_FACTOR = loadFactor;
    }

    public boolean add(final E item) {
        if(item == null)
            throw new NullPointerException();
        int hash = Math.abs(item.hashCode());
        int index = hash % setSize;
        if (contains(item, hash))
            return false;
        if (set[index] == null)
            set[index] = new LinkedList<>();
        set[index].add(item);
        size++;
        if ((double) size / (double) setSize > LOAD_FACTOR)
            expand();
        return true;
    }

    public boolean addAll(final Collection<? extends E> c) {
        if(c == null)
            throw new NullPointerException();
        int n = size;
        c.forEach(this::add);
        return n < size;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        primesIndex = 0;
        setSize = primes[primesIndex];
        size = 0;
        set = new LinkedList[setSize];
    }

    @SuppressWarnings("unchecked")
    @Override
    public CustomSet<E> clone() {
        CustomSet<E> clone = new CustomSet<>();
        clone.primesIndex = this.primesIndex;
        clone.setSize = this.setSize;
        clone.size = this.size;
        clone.set = new LinkedList[setSize];
        for (int i = 0; i < set.length; i++)
            if (set[i] != null)
                clone.set[i] = new LinkedList<>(set[i]);
        return clone;
    }

    public boolean contains(final E item) {
        if(item == null)
            throw new NullPointerException();
        int index = Math.abs(item.hashCode()) % setSize;
        if (set[index] == null)
            return false;
        return set[index].contains(item);
    }

    public boolean containsAll(final Collection<? extends E> c) {
        if(c == null)
            throw new NullPointerException();
        return c.stream().allMatch(this::contains);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SetInterface<?> other))
            return false;
        if (other.size() != size())
            return false;
        return containsAll((Collection<E>) other);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (LinkedList<E> list : set)
            if (list != null)
                for (E item : list)
                    hash += item.hashCode();
        return hash;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean remove(final E item) {
        if(item == null)
            throw new NullPointerException();
        if(!contains(item))
            return false;
        int index = Math.abs(item.hashCode()) % setSize;
        set[index].remove(item);
        if(set[index].isEmpty())
            set[index] = null;
        size--;
        if(setSize > primes[0] && size <= setSize / 4)
            reduce();
        return true;
    }

    public boolean removeAll(final Collection<? extends E> c) {
        boolean changed = false;
        for(E item : c) {
            if(item == null)
                throw new NullPointerException();
            if(remove(item))
                changed = true;
        }
        return changed;
    }

    public boolean retainAll(final Collection<? extends E> c) {
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

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public E[] toArray() {
        E[] arr = (E[]) new Object[size];
        int idx = 0;
        for(LinkedList<E> list : set) {
            if (list == null)
                continue;
            for (E item : list)
                arr[idx++] = item;
        }
        return arr;
    }

    @Override
    public String toString() {
        if(size == 0) return "{ }";
        StringBuilder stringBuilder = new StringBuilder("{ ");
        Arrays.stream(set)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .forEach(item -> stringBuilder.append(item)
                                                .append(", "));
        return stringBuilder.replace(stringBuilder.lastIndexOf(", "), stringBuilder.length(), " }").toString();
    }

    private boolean contains(final E item, int hash) {
        if(item == null)
            throw new NullPointerException();
        int index = hash % setSize;
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
        if(initialCapacity > primes[primes.length - 1])
            primesIndex = primes.length - 1;
        else
            for (int i = primesIndex + 1; i < primes.length; i++)
                if (primes[i] > initialCapacity) {
                    primesIndex = i;
                    break;
                }
        setSize = primes[primesIndex];
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

    private boolean retain(final Collection<? extends E> c, final LinkedList<E> list, boolean modified, int index) {
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