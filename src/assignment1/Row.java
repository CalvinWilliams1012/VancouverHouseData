package assignment1;
/** Row class for reading csv. Represents one row*/
public class Row {
	/**String to hold street name */
	private String streetName;
	/**String to hold postal code */
	private String propertyPostalCode;
	/**String to hold house type */
	private String houseType;
	/**double to hold current land value */
	private double currentLandValue;
	/**double to hold previous land value */
	private double previousLandValue;
	/**double to hold year built */
	private double yearBuilt;

	/**Constructor that sets variables
	 * @param houseType String to hold house type
	 * @param streetName String to hold street name
	 * @param propertyPostalCode String to hold postal code
	 * @param currentLandValue double to hold current land value
	 * @param previousLandValue double to hold previous land value
	 * @param yearBuilt double to hold year built
	 */
	public Row(String houseType, String streetName, String propertyPostalCode, double currentLandValue, double previousLandValue,
			double yearBuilt) {
		this.houseType = houseType;
		this.streetName = streetName;
		this.propertyPostalCode = propertyPostalCode;
		this.currentLandValue = currentLandValue;
		this.previousLandValue = previousLandValue;
		this.yearBuilt = yearBuilt;
	}

	/**House type getter
	 * @return houseType house type
	 */
	public String getHouseType(){
		return houseType;
	}
	
	/**Street name getter
	 * @return streetName street name
	 */
	public String getStreetName() {
		return streetName;
	}

	/**Postal code getter
	 * @return propertyPostalCode postal code */
	public String getPropertyPostalCode() {
		return propertyPostalCode;
	}

	/** Current Value getter
	 * @return currentLandValue current value*/
	public double getCurrentLandValue() {
		return currentLandValue;
	}

	/** Previous Value getter
	 * @return previousLandValue previous value*/
	public double getPreviousLandValue() {
		return previousLandValue;
	}

	/**Year Built getter
	 * @return yearBuilt year built*/
	public double getYearBuilt() {
		return yearBuilt;
	}

}
