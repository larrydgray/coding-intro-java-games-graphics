package net.sf.sdz.ds.lists;

/**
 * Is a node for Single Linked List.
 *
 * @param <E>
 */
public class Node<E> implements Nodes<E> {
    /** The element that this node holds */
    private E element;
    /** The next node in the list */
    private Node next;

    /**
     * Constructs an empty node.
     */
    public Node() {
        this(null, null);
    } // Node()

    /**
     * Constructs a node given the element to store and the next node in the list.
     *
     * @param element
     * @param next Node
     */
    public Node(E element, Node next) {
        this.element = element;
        this.next = next;
    } // Node(E,Node)

    /**
     * Gets the element that this Node contains.
     *
     * @return element
     */
    @Override
    public E getElement() {
        return element;
    } // getElement()

    /**
     * Gets the next Node in the list.
     *
     * @return next Node
     */
    @Override
    public Node getNext() {
        return next;
    } // getNext()

    /**
     * Sets the element this Node contains.
     *
     * @param element
     */
    @Override
    public void setElement(E element) {
        this.element = element;
    } // setElement()

    /**
     * Sets the next Node in the list.
     * @param next
     */
    @Override
    public void setNext(Node next) {
        this.next = next;
    } // setNext
} // Node class
