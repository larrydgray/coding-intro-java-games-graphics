package net.sf.javagg.gnl;

/**
 * 
 * @author Larry
 *
 * @param <T>
 */
public interface ClientNetListener<T> {
	/**
	 * 
	 * @param t
	 */
	void relay(T t);
} // interface ClientNetListener
