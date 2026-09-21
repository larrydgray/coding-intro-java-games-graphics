package net.sf.javagg.island3d;

import java.awt.Color;

/**
 * This is a color model for a given map which sets up the colors for given
 * elevations. The default is around 10 elevation ranges with blue for water,
 * green for island white, red and yellow for peaks.
 * 
 * @author Larry Gray
 * @version 1.1
 */
public class MapColorModel {
	/** A default set of elevation color models.*/
	static private ElevationColorModel[] colorModel = null; // end array

	// definition
	// colorModel
	/** No args constructor. */
	public MapColorModel() {
	} // MapColorModel no agrs constructor

	/**
	 * Makes a map with a given elevation color model.
	 * 
	 * @param elevationColorModel
	 */
	public MapColorModel(ElevationColorModel[] elevationColorModel) {
		this.colorModel = elevationColorModel;
	} // MapColorModel(elevationColorModel) constructor

	/**
	 * Gets the ElevationColorModel for this MapColorModel.
	 * 
	 * @param elevationRangeNumber
	 * @return
	 */
	public ElevationColorModel getElevationColorModel(int elevationRangeNumber) {
		return this.colorModel[elevationRangeNumber];
	}// getElvationColorModel

	/**
	 * Gets a color for a given elevation range.
	 * 
	 * @param elevationRangeNumber
	 *            number in array.
	 * @return Color for an elevation range.
	 */
	public Color getElevationRangeColor(int elevationRangeNumber) {
		ElevationColorModel tempElevationColorModel = colorModel[elevationRangeNumber];
		return tempElevationColorModel.getRangeColor();
	} // getElvationRangeColor

	/**
	 * Gets the Elevation Range High value.
	 * 
	 * @param elevationRangeNumber for the set of ranges.
	 * @return int high value for elevation range.
	 */
	public int getElevationRangeHigh(int elevationRangeNumber) {
		ElevationColorModel tempElevationColorModel = colorModel[elevationRangeNumber];
		return tempElevationColorModel.getHigh();
	} // getElevationRangeHigh

	/**
	 * Gets the Elevation Range Low value.
	 * @param elevationRangeNumber for the set of ranges.
	 * @return int low value for elevation range.
	 */
	public int getElevationRangeLow(int elevationRangeNumber) {
		ElevationColorModel tempElevationColorModel = colorModel[elevationRangeNumber];
		return tempElevationColorModel.getLow();
	} // getElevationRangeLow

	/**
	 * Gets number of Elevations in the model.
	 * 
	 * @return int number of elevations.
	 */
	public int getNumberElevations() {
		return this.colorModel.length;
	} // getNumberElevations

	/**
	 * Sets the elevation color model set for this MapColorModel.
	 * 
	 * @param elevationColorModel a set.
	 */
	public static void setColorModel(ElevationColorModel[] elevationColorModel) {

		colorModel = elevationColorModel;

	} // setColorModel

	/**
	 * Sets the elevation color model for a single range.
	 * @param elevationColorModel the model.
	 * @param elevationRangeNumber the range.
	 */
	public void setElevationColorModel(ElevationColorModel elevationColorModel,
			int elevationRangeNumber) {
		this.colorModel[elevationRangeNumber] = elevationColorModel;
	} // setElevationColorModel

	/**
	 * Sets the Color for a given elevation range.
	 * @param elevationRangeNumber the range.
	 * @param color a Color.
	 */
	public void setElevationRangeColor(int elevationRangeNumber, Color color) {
		ElevationColorModel tempElevationColorModel = colorModel[elevationRangeNumber];
		tempElevationColorModel.setColor(color);
	}// setElevationRangeColor

	/**
	 * Sets the high value for a given elevation range.
	 * @param elevationRangeNumber the range.
	 * @param high the top.
	 */
	public void setElevationRangeHigh(int elevationRangeNumber, int high) {
		ElevationColorModel tempElevationColorModel = colorModel[elevationRangeNumber];
		tempElevationColorModel.setHigh(high);
	} // setElevatgionRangeHigh

	/**
	 * Sets low value for a given elevation range.
	 * @param elevationRangeNumber the range.
	 * @param low the bottom.
	 */
	public void setElevationRangeLow(int elevationRangeNumber, int low) {
		ElevationColorModel tempElevationColorModel = colorModel[elevationRangeNumber];
		tempElevationColorModel.setLow(low);

	} // setElevationRangeLow

} // MapColorModel class
