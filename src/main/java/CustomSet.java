import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.IntStream;

@SuppressWarnings("unchecked")
public class CustomSet<T> implements Cloneable, SetInterface<T> {

    private static double LOAD_FACTOR = 0.75;
    private int primesIndex = 0;
    private int size = 0;
    private int setSize = primes[primesIndex];

    private LinkedList<T>[] set;

    public CustomSet() {
        set = new LinkedList[setSize];
    }

    public CustomSet(final Collection<T> c) {
        if(c == null)
            throw new NullPointerException();
        set = new LinkedList[primes[primesIndex]];
        for(T item : c)
            add(item);
    }

    public CustomSet(final int initialCapacity) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        generateSet(initialCapacity);
    }

    public CustomSet(final int initialCapacity, final double loadFactor) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        if(loadFactor < 0 || loadFactor > 1)
            throw new IllegalArgumentException();
        generateSet(initialCapacity);
        LOAD_FACTOR = loadFactor;
    }

    public boolean add(final T item) {
        if(!contains(item)) {
            int index = Math.abs(item.hashCode()) % setSize;
            if (set[index] == null) {
                set[index] = new LinkedList<>();
                set[index].add(item);
            } else
                set[index].add(item);
            size++;
            if((double)size / (double)setSize > LOAD_FACTOR)
                expand();
            return true;
        }
        return false;
    }

    public boolean addAll(Collection<T> c) {
        int n = size;
        for(T t : c)
            add(t);
        return n < size;
    }

    public void clear() {
        primesIndex = 0;
        setSize = primes[primesIndex];
        size = 0;
        set = new LinkedList[setSize];
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean contains(final T item) {
        int index = Math.abs(item.hashCode()) % setSize;
        try {
            if (set[index] != null)
                return set[index].contains(item);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    public boolean containsAll(Collection<T> c) {
        for(T t : c)
            if (!contains(t))
                return false;
        return true;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean remove(final T item) {
        if(item == null)
            throw new NullPointerException();
        if(contains(item)) {
            int index = Math.abs(item.hashCode()) % setSize;
            set[index].remove(item);
            size--;
            if(setSize > 11 && size <= setSize / 4)
                reduce();
            return true;
        }
        return false;
    }

    public boolean removeAll(Collection<T> c) {
        boolean changed = false;
        for(T item : c) {
            if(item == null)
                throw new NullPointerException();
            if(remove(item))
                changed = true;
        }
        return changed;
    }
    public boolean retainAll(Collection<T> c) {
        CustomSet<T> temp = new CustomSet<>();
        for(T t : c) {
            if (t == null)
                throw new NullPointerException();
            if(contains(t))
                temp.add(t);
        }
        if(temp.size() > 0) {
            set = temp.set;
            size = temp.size;
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }


    public T[] toArray() {
        Object[] arr = new Object[size];
        int idx = 0;
        for(LinkedList<T> list : set)
            if (list != null && !list.isEmpty())
                for (Object item : list)
                    arr[idx++] = item;
        return (T[]) arr;
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

    private void expand() {
        setSize = primes[++primesIndex];
        LinkedList<T>[] newSet = new LinkedList[setSize];
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

    private void reduce() {
        primesIndex = (primesIndex / 2) + 1;
        setSize = primes[primesIndex];
        LinkedList<T>[] newSet = new LinkedList[setSize];
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

    private void generateSet(final int initialCapacity) {
        if(initialCapacity > primes[primesIndex])
            for (int i = primesIndex + 1; i < primes.length; i++)
                if (primes[i] > initialCapacity) {
                    setSize = primes[i];
                    primesIndex = i - 1;
                    break;
                }
        set = new LinkedList[setSize];
    }

    protected static final int[] primes = { 17, 23, 29, 37, 47, 59, 71, 89, 107, 131, 163, 197, 239, 293, 353, 431, 521, 631, 761, 919,
                                            1103, 1327, 1597, 1931, 2333, 2801, 3371, 4049, 4861, 5839, 7013, 8419, 10103, 12143, 14591,
                                            17519, 21023, 25229, 30293, 36353, 43627, 52361, 62851, 75431, 90523, 108631, 130363, 156437,
                                            187751, 225307, 270371, 324449, 389357, 467237, 560689, 672827, 807403, 968897, 1162687, 1395263,
                                            1674319, 2009191, 2411033, 2893249, 3471899, 4166287, 4999559, 5999471, 7199369 };
}