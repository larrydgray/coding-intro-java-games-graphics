package net.sf.sdz.ds.lists;

/**
 * Position interface. Operation on current Node. For a later data structure
 * called Sequence.
 *
 * @param <E> element type
 */
public interface Position<E> {

    /**
     * Gets the element at the current node.
     * @return E element
     */
    public E getElement();
} // interface Position
