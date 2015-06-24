/*
 * The test class for this project. 
 * 
 * @author Mario Antón
 */

package org.deidentifier.arx.kap;

import org.deidentifier.arx.Data;
import org.deidentifier.arx.Data.DefaultData;
import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.examples.Example;

public class Test extends Example {

	/*
	 * Main Method
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

		/*
		 * After creating a sample database, the method
		 * "displayData(DataHandle)" in the class KAPDisplay is executed.
		 */

		final DefaultData data = Data.create();

		data.add("date", "integer", "String", "OrderedString", "decimal");
		data.add("01.01.1982", "45", "female", "3", "0.152");
		data.add("07.04.1924", "45", "male", "2", "0.126");
		data.add("06.11.1956", "50", "male", "1", "-0.158");
		data.add("28.02.1984", "67", "female", "0", "0.126");
		data.add("30.06.1975", "30", "female", "2", "0.987");
		data.add("01.01.1982", "20", "male", "3", "0.346");
		data.add("05.10.1978", "69", "female", "0", "0.342");
		data.add("02.08.1992", "24", "male", "3", "0.532");

		data.getDefinition().setDataType("date", DataType.DATE);
		data.getDefinition().setDataType("integer", DataType.INTEGER);
		data.getDefinition().setDataType("String", DataType.STRING);
		data.getDefinition().setDataType("OrderedString",
				DataType.ORDERED_STRING);
		data.getDefinition().setDataType("decimal", DataType.DECIMAL);

		KAPDisplay barSeries = new KAPDisplay();
		DataHandle dataH = data.getHandle();

		barSeries.displayData(dataH);

	}

}