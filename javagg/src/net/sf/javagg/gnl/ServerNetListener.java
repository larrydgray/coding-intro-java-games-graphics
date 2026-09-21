package net.sf.javagg.gnl;

/**
 * 
 * @author Larry
 *
 * @param <T>
 */
public interface ServerNetListener<T> {
	/**
	 * 
	 * @param id
	 * @param t
	 */
	void relay(int id, T t);
} // interface
