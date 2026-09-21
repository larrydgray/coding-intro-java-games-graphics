package net.sf.sdz.ds.lists;

import net.sf.sdz.ds.iterator.Iterator;

/**
 * This is an implementation of the Single Linked List data structure. It
 * also is an implementation of the Iterator design pattern. The advantage
 * of the linked data structure of course is that it can grow in size until
 * you run out of memory. And certain operations are more efficient or in
 * some cases less efficient. Insert and delete are very efficient. Finding
 * the nth value is less efficient than with arrays. Extending this class
 * makes very good stacks and queues.
 *
 * @param <E> of any Element.
 */
public class SingleLinkedList<E> implements LinkedList<E>, Iterator<E> {
    /** Head, front, start or top of list */
    private Node<E> head;
    /** Tail, back, end or bottom of list */
    private Node<E> tail;
    /** Temporary node storage*/
    private Node<E> temp;
    /** Position of pointer in list when traversing list. reset() moves
     * this pointer back to head.
     */
    private Node<E> position;
    /**
     * Size of list which is kept current by modifications to list that add or
     * remove list items.
     */
    private int size;

    /**
     * Inserts element E head, front, start or top of list.
     *
     * @param element E
     */
    @Override
    public void insertHead(E element) {
        if (head == null) {
            head = new Node(element, null);
            tail = head;
            position = head;
        } else {
            temp = new Node(element, head);
            head = temp;
            position = head;
        } // else head not null
        size++;
    } // insertHead(E)

    /**
     * Inserts element E at tail, back, end or bottom of list.
     *
     * @param element
     */
    @Override
    public void insertTail(E element) {
        if (tail == null) {
            tail = new Node(element, null);
            head = tail;
            position = head;
        } else {
            temp = new Node(element, null);
            tail.setNext(temp);
            tail = temp;
        } // else tail not null
        size++;
    } //insertTail(E)

    /**
     * Removes head from head, front or top of list. Deletes the element that
     * was removed.
     *
     * @return head element E
     */
    @Override
    public E removeHead() {
        if (empty()) return null;
        temp = head;
        head = head.getNext();
        if (size() == 1) {
            head = null;
            position = null;
            tail = null;
        }
        if (head != null) position = head;
        size--;
        return temp.getElement();
    } // E removeHead()

    /**
     * Removes element E from tail, end or bottom of list. Deletes the element
     * that was removed.
     *
     * @return element E
     */
    @Override
    public E removeTail() {
        if (empty()) return null;
        temp = head;
        if (size() == 1) {
            head = null;
            position = null;
            tail = null;
            size = 0;
            return temp.getElement();
        }
        // get us to the end of list, if getNext() is = tail we
        // are at the node previous to tail.
        while (temp.getNext() != tail) {
            temp = temp.getNext();
        } // while
        Node beforeTail = temp;
        temp = tail;
        beforeTail.setNext(null);
        tail = beforeTail;
        position = tail;
        size--;
        return temp.getElement();
    } // E removeTail()

    /**
     * Sets the navigational pointer back to head of list.
     */
    @Override
    public void reset() {
        position = head;

    } // reset()

    /**
     * Moves pointer to next position and returns the Element.
     *
     * @return E element at next position. A return value of null does not necessarily
     * mean end of list. You must use hasNext() with next()
     */
    @Override
    public E next() {
        nextElement();
        return currentElement;
    } // next()

    /**
     * Inserts new element after element at current pointer position.
     * @param element the new element to be inserted.
     */
    @Override
    public void insertAfter(E element) {
        if (position == tail) {
            insertTail(element);
        } else {
            temp = new Node(element, position.getNext());
            temp.setNext(position.getNext());
            position.setNext(temp);
        } // else position not tail
        size++;
    } // insertAfter(E)

    /**
     * Inserts new element before element at current position.
     *
     * @param element the new element to be inserted.
     */
    @Override
    public void insertBefore(E element) {
        if (position == head) {
            insertHead(element);
        } else {
            temp = head;
            while (temp.getNext() != position) {
                temp = temp.getNext();
            } // else position not head
            Node previous = temp;
            Node newNode = new Node(element, position.getNext());
            previous.setNext(newNode);
        }
        size++;
    } // insertBefore(E)

    /**
     * Deletes the element at the current position.
     */
    @Override
    public void delete() {
        if (empty()) return;
        if (position == head && head == tail) {
            position = null;
            head = null;
            tail = null;
        } else if (position == head) {
            removeHead();
            return;
        } else if (position == tail) {
            removeTail();
            return;
        } else {
            temp = head;
            while (temp.getNext() != position) {
                temp = temp.getNext();
            } // while
            temp.setNext(position.getNext());
        } // else position is not head or tail.
        if (position != tail) position = position.getNext();
        size--;
    } // delete()
    /**
     * Deletes and returns the element at the current position.
     * @return element to be removed.
     */
    @Override
    public E remove() {
        if (empty()) return null;
        Node<E> removed = position;
        if (position == head && head == tail) {
            position = null;
            head = null;
            tail = null;
        } else if (position == head) {
            return removeHead();
        } else if (position == tail) {
            return removeTail();
        } else {
            temp = head;
            while (temp.getNext() != position) {
                temp = temp.getNext();
            } //while
            temp.setNext(position.getNext());
        } // else position is not head or tail
        if (position != tail) position = position.getNext();
        size--;
        return removed.getElement();
    } // E remove()
    /** Stores element at current position. */
    private E currentElement;

    /**
     * Iterator method that returns false at end of list.
     * @return true if more elements in list.
     */
    @Override
    public boolean hasNext() {
        if (position != null) {
            currentElement = position.getElement();
        } else {
            currentElement = null;
            return false;
        }
        return true;
    } // hasNext()

    /**
     * Returns the current size of the list or number of elements in the list. This is
     * kept current by insert and remove or delete methods.
     * @return number of elements in the list.
     */
    @Override
    public int size() {
        return size;
    } // size()

    /**
     * Is the list empty? If there is no head element then yes it is.
     * @return true if list contains no elements.
     */
    @Override
    public boolean empty() {
        if (head == null) {
            return true;
        } // if
        return false;
    } // empty()

    /**
     * Retrieves the element at the head. Does not remove it.
     *
     * @return head element.
     */
    @Override
    public E head() {
        if (empty()) return null;
        return head.getElement();
    } // head()

    /**
     * Retrieves the element at the tail. Does not remove it.
     *
     * @return tail element.
     */
    @Override
    public E tail() {
        if (empty()) return null;
        return tail.getElement();
    } // E tail()

    /**
     * Gets the element at the current position.
     * @return element at current position.
     */
    @Override
    public E get() {
        if (position == null) {
            return null;
        } // if
        return position.getElement();
    } // E get()

    /**
     * Sets and replaces the element at the current position.
     *
     * @param e new element
     */
    @Override
    public void set(E e) {
        if (position == null) {
            insertHead(e);
        } // if
        position.setElement(e);

    } // set(E)

    /**
     * Moves pointer position to next element if there is one.
     * @return returns false if position is null (at end of list)
     */
    @Override
    public boolean nextElement() {
        if (position == null) return false;
        if (position.getNext() == null) {
            position = null;
            return false;
        }
        position = position.getNext();
        return true;
    } // nextElement()
} // SingleLinkedList
