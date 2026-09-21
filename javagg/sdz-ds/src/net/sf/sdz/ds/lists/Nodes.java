package net.sf.sdz.ds.lists;

/**
 * Base interface for Node types in linked, tree or graph structures.
 *
 * @param <E>
 */
public interface Nodes<E> {

    /**
     * Gets the element that this node contains.
     * @return the element
     */
    E getElement();

    /**
     * Gets the next Node in the list.
     *
     * @return next Node
     */
    Node getNext();

    /**
     *
     * Sets the element that this Node contains.
     *
     * @param element
     */
    void setElement(E element);

    /**
     * Sets the next Node in the list.
     *
     * @param next
     */
    void setNext(Node next);

} // Nodes interface
