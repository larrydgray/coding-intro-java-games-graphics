package net.sf.javagg.island3d;

/**
 *  A point on a map, or a grid intersection.
 * 
 *  @author Larry Gray
 *  @version 1.1
 */
public class MapPoint {
	/** Height of point. 	 */
	private int elevation;

	/** X value for grid intersetion. */
	private int x;

	/** Y value for grid intersection. */
	private int y;

	/**
	 * Makes a MapPoint with given values.
	 * 
	 * @param x grid intersection.
	 * @param y grid intersection.
	 * @param elevation height.
	 */
	public MapPoint(int x, int y, int elevation) {
		this.x = x;
		this.y = y;
		this.elevation = elevation;
	} // end constructor MapPoint(int,int,int)

	/**
	 *  Gets the height of a point.
	 */
	public int getElevation() {
		return elevation;
	} // getElevation

	/**
	 *  Gets the x for the point.
	 */
	public int getX() {
		return x;
	} // getX

	/**
	 *  Gets the y for the point.
	 */
	public int getY() {
		return y;
	} // getY

	/**
	 *  Sets the height of the point.
	 */
	public void setElevation(int elevation) {
		this.elevation = elevation;
	} // setElevation

} // class MapPoint
