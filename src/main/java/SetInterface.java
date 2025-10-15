import java.util.Collection;
import java.util.Iterator;

public interface SetInterface<E> {

    /**
     * Add item to Set.
     * @param item - item to be added to List.
     * @throws NullPointerException - on null value
     * @return boolean true if successful, else false.
     */
    boolean add(final E item);

    /**
     * Add all items in collection to Set.
     * @param c - Collection of items to be added to Set.
     * @throws NullPointerException - on null element in collection.
     * @return boolean true if successful, else false.
     */
    boolean addAll(final Collection<? extends E> c);
    /**
     * Clear Set to new Empty Set
     */
    void clear();

    /**
     * Check if Set contains item
     * @param item - item to be searched for in List.
     * @throws NullPointerException - on null item.
     * @return boolean true if contains, else false.
     */
    boolean contains(final E item);

    /**
     * Check if Set contains all items in collection
     * @param c  - collection of items to be checked to contain all.
     * @return boolean true if contains, else false.
     */
    boolean containsAll(final Collection<? extends E> c);

    /**
     * Check if Set is Empty.
     * @return True if empty.
     */
    boolean isEmpty();

    Iterator<E> iterator();

    /**
     * Remove object from Set.
     * @param item to be removed
     * @return true if successful.
     */
    boolean remove(final E item);

    /**
     * Remove all items from set that does not exist in collection
     * @param c - collection of items to be checked to retain.
     * @return boolean true if set is modified, else false.
     */
    boolean removeAll(final Collection<? extends E> c);

    /**
     * retail all items from set that does not exist in collection
     * @param c - collection of items to be checked to retain.
     * @return boolean true if contains, else false.
     */
    boolean retainAll(final Collection<? extends E> c);

    /**
     * Get size of set
     * @return size as Integer
     */
    int size();

    /**
     * Return set values
     * @return T[] set values
     */
    E[] toArray();

    <T> T[] toArray(T[] a);

    /**
     * Get Set object as String
     * @return String representation of Set
     */
    String toString();
}