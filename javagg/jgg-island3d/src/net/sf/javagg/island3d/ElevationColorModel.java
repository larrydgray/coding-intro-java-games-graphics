package net.sf.javagg.island3d;

import java.awt.*;

/**
 * A color model for a single elevation range.
 * 
 * @author Larry Gray
 * @version 1.1
 */
public class ElevationColorModel {
	/** The lowest elevation.*/
	private int bottomElevation;

	/** The color for the range of elevations.*/
	private Color color;

	/** The highest elevation. */
	private int topElevation;

	/**
	 * Builds an elevation color model.
	 * 
	 * @param bottomElevation lowest.
	 * @param topElevation highest.
	 * @param color a Color.
	 */
	public ElevationColorModel(int bottomElevation, int topElevation,
			Color color) {

		this.topElevation = topElevation;
		this.bottomElevation = bottomElevation;
		this.color = color;

	} // end constructor ElevationColorModel(int,int,color)

	/**
	 * Gets a Color if the given elevation falls within this
	 * models range. Else returns null.
	 * @param elevation an integer value.
	 * @return Color if in range or null if out of range.
	 */
	public Color getColor(int elevation) {
		if ((elevation >= this.bottomElevation)
				&& (elevation <= this.topElevation)) {
			return this.color;
		} else
			return null;
	} // end ethod getColor

	/**
	 * Gets the highest elevation for this range.
	 * @return int highest elevation.
	 */
	public int getHigh() {
		return this.topElevation;
	} // getHigh

	/**
	 * Gets the lowest elevation for this range.
	 * @return int lowest elevation.
	 */
	public int getLow() {
		return this.bottomElevation;
	} // getLow

	/**
	 *  Gets the color for this range.
	 * @return color a Color.
	 */
	public Color getRangeColor() {
		return this.color;
	} // getRangeColor

	/**
	 * Sets the color for this range.
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	} // setColor

	/**
	 * Sets the highest elevation for this range.
	 * @param topElevation the highest elevation.
	 */
	public void setHigh(int topElevation) {
		this.topElevation = topElevation;
	} // setHigh

	/**
	 * Sets the lowest elevation for this range.
	 * @param bottomElevation the lowest elevaiton.
	 */
	public void setLow(int bottomElevation) {
		this.bottomElevation = bottomElevation;
	} // setLow

	/**
	 * @return Returns the bottomElevation.
	 */
	public int getBottomElevation() {
		return bottomElevation;
	}

	/**
	 * @param bottomElevation The bottomElevation to set.
	 */
	public void setBottomElevation(int bottomElevation) {
		this.bottomElevation = bottomElevation;
	}

	/**
	 * @return Returns the topElevation.
	 */
	public int getTopElevation() {
		return topElevation;
	}

	/**
	 * @param topElevation The topElevation to set.
	 */
	public void setTopElevation(int topElevation) {
		this.topElevation = topElevation;
	}

	/**
	 * @return Returns the color.
	 */
	public Color getColor() {
		return color;
	}

} // end class ElevationColorModel
