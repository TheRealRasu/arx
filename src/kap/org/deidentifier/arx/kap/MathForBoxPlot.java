package org.deidentifier.arx.kap;

import java.util.ArrayList;
import java.util.Date;

import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.DataType.ARXOrderedString;
import org.deidentifier.arx.aggregates.StatisticsSummary.ScaleOfMeasure;

public class MathForBoxPlot {

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

		final DataType<?> type = dataHandle.getDataType(attribute);
		final Class<?> clazz = type.getDescription().getWrappedClass();

		ScaleOfMeasure scale = ScaleOfMeasure.NOMINAL;
		if (clazz == Long.class || clazz == Double.class) {
			scale = ScaleOfMeasure.RATIO;
		} else if (clazz == Date.class) {
			scale = ScaleOfMeasure.INTERVAL;
		} else if (type instanceof ARXOrderedString) {
			scale = ScaleOfMeasure.ORDINAL;
		}

		if (scale == ScaleOfMeasure.NOMINAL || scale == ScaleOfMeasure.ORDINAL) {
			throw new IllegalArgumentException(
					"This scale cannot be displayed with a Box-Plot");
		}

		String[] values = new String[rowAmount];
		for (int i = 0; i < rowAmount; i++) {
			values[i] = dataHandle.getValue(i, attLocation);
		}

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