package net.sf.sdz.ds.lists;

/**
 * LinkedList data structure interface. Some methods are based on position of
 * pointer in list while navigating list, such as insertAfter() and
 * insertBefore(). reset() resets the pointer to the head of the list.
 * nextElement() moves the pointer along. If used along with the Iterator
 * interface, next() also moves the pointer along while returning the
 * current Element.
 *
 * @param <E> element
 */
public interface LinkedList<E> {

    /**
     * Deletes node.
     */
    void delete();

    /**
     * Is the list empty?
     * @return true if empty.
     */
    boolean empty();

    /**
     * Gets element at current pointer.
     * @return element.
     */
    E get();

    /**
     * Sets and replaces element at current pointer.
     * @param e new element
     */
    void set(E e);

    /**
     * Gets the head of the list, no delete.
     * @return head element
     */
    E head();

    /**
     * Inserts after the current pointer.
     * @param element new element
     */
    void insertAfter(E element);

    /**
     * Inserts before the current pointer.
     * @param element new element.
     */
    void insertBefore(E element);

    /**
     * Inserts at the head of the list.
     * @param element new element.
     */
    void insertHead(E element);

    /**
     * Inserts at the tail of the list.
     * @param element new element.
     */
    void insertTail(E element);

    /**
     * Removes and returns element at current pointer.
     * @return element
     */
    E remove();

    /**
     * Removes and returns element at head of list.
     * @return element
     */
    E removeHead();

    /**
     * Removes and returns element at tail of list.
     * @return element
     */
    E removeTail();

    /**
     * Resets pointer to head element.
     */
    void reset();

    /**
     * Returns number of elements in list
     * @return number of elements
     */
    int size();

    /**
     * Returns tail element of list, no delete.
     * @return
     */
    E tail();

    /**
     * Moves pointer to next element in list.
     * @return false if no more elements
     */
    boolean nextElement();

} // interface LinkedList
