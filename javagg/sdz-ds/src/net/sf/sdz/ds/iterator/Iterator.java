package net.sf.sdz.ds.iterator;

/**
 * This is the interface for the Iterator design pattern. A custom interface
 * rather than java.util.Iterator, kept in its own package so callers that
 * want the JDK's Iterator can still use it unambiguously.
 *
 * @param <E> Can be of any Element E
 */
public interface Iterator<E> {

    /**
     * Do we have more elements in list or are we done.
     * @return true if end of list.
     */
    boolean hasNext();

    /**
     * Moves to and returns next element.
     * @return next element in list.
     *
     */
    E next();
} // interface Iterator
