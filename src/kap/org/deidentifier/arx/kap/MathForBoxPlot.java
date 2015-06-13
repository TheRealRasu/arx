/*
 * The class which copies all values from the database into an ArrayList of the type Double. 
 * 
 * @author Mario Antón
 */

package org.deidentifier.arx.kap;

import java.util.ArrayList;
import java.util.Date;

import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.DataType;

public class MathForBoxPlot {
	/*
	 * This method returns an ArrayList of the type double
	 * 
	 * @param dataHandle
	 * 
	 * @param attribute
	 * 
	 * @return
	 */
	public ArrayList<Double> GetAttributeValuesList(DataHandle dataHandle,
			String attribute) {

		final int rowAmount = dataHandle.getNumRows();
		final boolean[] isNull = new boolean[rowAmount];
		final int columnAmount = dataHandle.getNumColumns();

		final int attLocation = dataHandle.getColumnIndexOf(attribute);
		if (attLocation == -1) {

			throw new IllegalArgumentException(
					"the given attribute is not present in the data set.");

		}
		// The DataType is saved in order to determine the correct parsing
		final DataType<?> type = dataHandle.getDataType(attribute);

		String[] values = new String[rowAmount];
		for (int i = 0; i < rowAmount; i++) {
			values[i] = dataHandle.getValue(i, attLocation);
		}
		// The database is checked for NULL values
		for (int rA = 0; rA < rowAmount; rA++) {
			for (int cA = 0; cA < columnAmount; cA++) {
				if (DataType.isNull(dataHandle.getValue(rA, cA))) {
					cA = columnAmount;
					isNull[rA] = true;
				} else if (cA == columnAmount - 1) {
					isNull[rA] = false;
				}
			}
		}

		final ArrayList<Double> valueList = new ArrayList<Double>();

		// If the database contains NULL values, they are skipped and not added
		// to the ArrayList
		for (int i = 0; i < rowAmount; i++) {
			if (!isNull[i]) {

				if (!type.equals(DataType.DATE)) {
					valueList.add(Double.parseDouble(values[i]));

				} else if (type.equals(DataType.DATE)) {
					final Long l = ((Date) type.parse(values[i])).getTime();

					valueList.add(l.doubleValue());
				}

			} else {

			}
		}

		return valueList;

	}
}