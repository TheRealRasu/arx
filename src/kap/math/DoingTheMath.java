package math;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.*;
import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.DataType.ARXOrderedString;
import org.deidentifier.arx.aggregates.StatisticsSummary.ScaleOfMeasure;

	/* 
	 * Mein erster Entwurf f�r die Berechnung des arithmetischen Mittels von Beispieldaten in ARX.
	 * Der Rest des Projektes ist in Englisch kommentiert.
	 * 
	 * 
	 */
public class DoingTheMath {
	
	public static DataHandle dataHandle;
	public static int rowAmount;
	public static int rowAmountWithoutNull;
	public static int columnAmount;
	public static String[] header;
	public static int attLocation;
	public static DescriptiveStatistics stats;
	public static ArrayList<String> valueList;
	public static ArrayList<Integer> modeAmountList;
	
	public static int[] valueCounter;
	public static String[] values;
	public static String[] valuesWithoutNull;
	public static Integer modeValue;
	public static boolean[] isNull;
	public static int j;
	
	
	/*
	 * In the Method GetAttributeValues(), The data is being transmitted as DataHandle, as well as a String carrying the name of the attribute which
	 * will be used for the mathematical operations.
	 * The Method GetAttributeValues() contains a lot of System.out.prinln()-commands which I used to test my methods.
	 *
	 */
	
	
	public static void GetAttributeValues(DataHandle dataHandle, String attribute){
		
	/*
	 * 
	 * First, the method defines the DataDefinition of dataHandle, a String array that contains all attribute names and
	 * two integers which contain the amount of rows and columns of the submitted DataHandle.
	 * 
	 * After that, the attribute names are compared to the String which has been transmitted to 
	 * ensure that the given attribute name even exists in the Data. If this is not the case, the 
	 * method will terminate and print out a notification.

	 * dataHandle
	 */
		
		
		rowAmount = dataHandle.getNumRows();
		rowAmountWithoutNull=rowAmount;
		isNull=new boolean[rowAmount];
		columnAmount = dataHandle.getNumColumns();
		
		attLocation=dataHandle.getColumnIndexOf(attribute);
		if(attLocation==-1){
			
			throw new IllegalArgumentException("the given attribute is not present in the data set.");
				
			}
		
		
		DataType<?> type = dataHandle.getDataType(attribute);
        Class<?> clazz = type.getDescription().getWrappedClass();
        
        // Scale
        ScaleOfMeasure scale = ScaleOfMeasure.NOMINAL;
        if (clazz == Long.class || clazz == Double.class) {
            scale = ScaleOfMeasure.RATIO;
        } else if (clazz == Date.class) {
            scale = ScaleOfMeasure.INTERVAL;
        } else if (type instanceof ARXOrderedString) {
            scale = ScaleOfMeasure.ORDINAL;
        }
		
		
		
		
		/*
	 * If the attribute exists in the Data, the method prints out some information about the Data:
	 * - the number of rows (i.e. the amount of entries),
	 * - the number of columns (i.e. the amount of attributes)
	 * - the name of the attribute and its position among the attributes (i.e. the position of its column)
	 */
	

		System.out.println("The used dataset has "+columnAmount+" different attributes.");

		System.out.println("The used dataset has "+rowAmount+" entries.");
		
		
		System.out.println("The chosen attribut is "+attribute+" and in column "+(attLocation+1));
		System.out.println("It has the "+ scale.toString()+" measure");
		
		
		for (int rA=0;rA<rowAmount;rA++){
			for (int cA=0;cA<columnAmount;cA++){
				if (DataType.isNull(dataHandle.getValue(rA, cA))){
					cA=columnAmount;
					isNull[rA]=true;
					rowAmountWithoutNull--;
				} else if (cA==columnAmount-1){
					isNull[rA]=false;
				}
			}
		}
		
	
	
	stats=new DescriptiveStatistics();
	valuesWithoutNull=new String[rowAmountWithoutNull];
	
	valueList = new ArrayList<String>();
	j=0;
	for (int i=0;i<rowAmount;i++){
		if(!isNull[i]){
			valuesWithoutNull[j]=dataHandle.getValue(i, attLocation);
			valueList.add(valuesWithoutNull[j]);
			if((!scale.equals(ScaleOfMeasure.NOMINAL)) && (!scale.equals(ScaleOfMeasure.NOMINAL) &&(!type.equals(DataType.DATE)))){
				stats.addValue(Double.parseDouble(valuesWithoutNull[j]));
				
			}
			j++;
			if(j==rowAmountWithoutNull){
				break;
			}
		}
	}
	
	valueCounter=new int[valuesWithoutNull.length];
	for (int i=0;i<valueCounter.length;i++){
		valueCounter[i]=1;
	}
	for (int i=0;i<valueCounter.length;i++){
		for(int j=(i+1);j<valueCounter.length;j++){
			if(valuesWithoutNull[i].equals(valuesWithoutNull[j])){
				valueCounter[i]++;
			}
		}
	}
	modeAmountList = new ArrayList<Integer>();
	for(int i=0;i<valueCounter.length;i++){
		modeAmountList.add(valueCounter[i]);
	}
	
	
	Collections.sort(modeAmountList);
	Collections.sort(valueList);
	
	if(scale.equals(ScaleOfMeasure.NOMINAL)){
		values=new String[1];
		values[0]=modeAmountList.get(modeAmountList.size()-1).toString();
		System.out.println("The attribute's mode is "+values[0]);
		
	} else if(scale.equals(ScaleOfMeasure.ORDINAL)){
		values=new String[4];
		values[0]=modeAmountList.get(modeAmountList.size()-1).toString();
		System.out.println("The attribute's mode is "+values[0]);
		values[1]=valueList.get(valueList.size() /2);
		values[2]=valueList.get(0);
		values[3]=valueList.get(valueList.size()-1);
		System.out.println("The attribute's median is "+values[1]);
		System.out.println("The attribute's minimum is "+values[2]);
		System.out.println("The attribute's maximum is "+values[3]);
	} else if (scale.equals(ScaleOfMeasure.INTERVAL)){
		
		values=new String[9];
		values[0]=modeAmountList.get(modeAmountList.size()-1).toString();
		System.out.println("The attribute's mode is "+values[0]);
		values[1]=valueList.get(valueList.size() /2);
		values[2]=valueList.get(0);
		values[3]=valueList.get(valueList.size()-1);
		System.out.println("The attribute's median is "+values[1]);
		System.out.println("The attribute's minimum is "+values[2]);
		System.out.println("The attribute's maximum is "+values[3]);
		values[4]=String.valueOf(stats.getMean());
		values[5]=String.valueOf(stats.getVariance());
		values[6]=String.valueOf(stats.getPopulationVariance());
		values[7]=String.valueOf(((stats.getMax())-(stats.getMin())));
		values[8]=String.valueOf(stats.getKurtosis());
		System.out.println("The attribute's mean is "+values[4]);
		System.out.println("The attribute's variance is "+values[5]);
		System.out.println("The attribute's population variance is "+values[6]);
		System.out.println("The attribute's range is "+values[7]);
		System.out.println("The attribute's kurtosis is "+values[8]);
		
	} else if (scale.equals(ScaleOfMeasure.RATIO)){
		
		values=new String[10];
		values[0]=modeAmountList.get(modeAmountList.size()-1).toString();
		System.out.println("The attribute's mode is "+values[0]);
		values[1]=valueList.get(valueList.size() /2);
		values[2]=valueList.get(0);
		values[3]=valueList.get(valueList.size()-1);
		System.out.println("The attribute's median is "+values[1]);
		System.out.println("The attribute's minimum is "+values[2]);
		System.out.println("The attribute's maximum is "+values[3]);
		values[4]=String.valueOf(stats.getMean());
		values[5]=String.valueOf(stats.getVariance());
		values[6]=String.valueOf(stats.getPopulationVariance());
		values[7]=String.valueOf(((stats.getMax())-(stats.getMin())));
		values[8]=String.valueOf(stats.getKurtosis());
		System.out.println("The attribute's mean is "+values[4]);
		System.out.println("The attribute's variance is "+values[5]);
		System.out.println("The attribute's population variance is "+values[6]);
		System.out.println("The attribute's range is "+values[7]);
		System.out.println("The attribute's kurtosis is "+values[8]);
		values[9]=String.valueOf(stats.getGeometricMean());
		System.out.println("The attribute's geometric mean is "+values[9]);
		
		
	}
	
	
	
	//TODO: count amounts of modes!
	
}
	
	
	
	
	}
	
	



