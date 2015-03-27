package math;


import org.apache.commons.math3.stat.descriptive.*;
import org.deidentifier.arx.DataDefinition;
import org.deidentifier.arx.DataHandle;

	/* 
	 * Mein erster Entwurf für die Berechnung des arithmetischen Mittels von Beispieldaten in ARX.
	 * Der Rest des Projektes ist in Englisch kommentiert.
	 * 
	 * 
	 */
public class DoingTheMath {
	
	public static DataHandle dataHandle;
	public static DataDefinition dataDef;
	public static int rowAmount;
	public static int columnAmount;
	public static String returnString;
	public static String[] header;
	public static int attLocation;
	public static DescriptiveStatistics stats;
	public static double[] numbers;
	public static double[] values;
	public static double[] nullReturn;
	
	/*
	 * In the Method GetAttributeValues(), The data is being transmitted as DataHandle, as well as a String carrying the name of the attribute which
	 * will be used for the mathematical operations.
	 * The Method GetAttributeValues() contains a lot of System.out.prinln()-commands which I used to test my methods.
	 *
	 */
	
	
	public static double[] GetAttributeValues(DataHandle dataHandle, String attribute){
		
	/*
	 * 
	 * First, the method defines the DataDefinition of dataHandle, a String array that contains all attribute names and
	 * two integers which contain the amount of rows and columns of the submitted DataHandle.
	 * 
	 * After that, the attribute names are compared to the String which has been transmitted to 
	 * ensure that the given attribute name even exists in the Data. If this is not the case, the 
	 * method will terminate and print out a notification.

	 * 
	 */
		dataDef=dataHandle.getDefinition();	
		
		rowAmount = dataHandle.getNumRows();
		columnAmount = dataHandle.getNumColumns();
		
		attLocation=dataHandle.getColumnIndexOf(attribute);
		if(attLocation==-1){
			
			throw new IllegalArgumentException("the given attribute is not present in the data set.");
				
			}
		
		/*
	 * If the attribute exists in the Data, the method prints out some information about the Data:
	 * - the number of rows (i.e. the amount of entries),
	 * - the number of columns (i.e. the amount of attributes)
	 * - the name of the attribute and its position among the attributes (i.e. the position of its column)
	 */

		

		System.out.println("The used dataset has "+columnAmount+" different attributes.");

		System.out.println("The used dataset has "+rowAmount+" entries.");
		
		
		
		returnString="The chosen attribut is "+attribute+" and in column "+(attLocation+1);
		System.out.println(returnString);
		

		
		
	
		/* 
		 * The following code tests whether the attribute is an integer, double, short or long in order to be able to be displayed.
 		 * In case of success, the values of the attribute are parsed as doubles and copied into a double array. The method then 
 		 * terminates by returning this double array.
		 * 
		 */

		
		String test=(dataHandle.getValue(0, attLocation));
		try{
			Integer.parseInt(test);
		} 
		catch(NumberFormatException e){
			System.out.println("The chosen attribute is not an integer. Trying to display the attribute as double...");
			
			try{
				Double.parseDouble(test);
			} catch(NumberFormatException e1){
				System.out.println("The chosen attribute is not a double. Trying to display the attribute as long...");
				try{
					Long.parseLong(test);
				} catch(NumberFormatException e2){
					System.out.println("The chosen attribute is not a long. Trying to display the attribute as short...");
					try{
						Short.parseShort(test);
					} catch(NumberFormatException e3){
						System.out.println("The chosen attribute is not a short, and as such, can't be displayed.");
						return nullReturn;
					}
				}
			}
		}
		
		
		System.out.println("The chosen attribute can be displayed.");
		
		numbers=new double[rowAmount];
		for (int i=0;i<rowAmount;i++){
		
			numbers[i]=Double.parseDouble(dataHandle.getValue(i, attLocation));
		}
		return numbers;
	}
	
	
	
	public static double ArithmeticMean (DataHandle data, String attribute){
		
		/*
		 * After letting the method GetAttributeValues() check whether the attribute can be displayed graphically and providing
		 * a double array with the attribute's values, this method calculates the arithmetic mean of this attribute's values.
		 * 
		 */
			
		
		numbers=GetAttributeValues(data, attribute);
		
		stats = new DescriptiveStatistics();
		for( int i = 0; i < rowAmount;i++) {
			stats.addValue(numbers[i]);
		}
			
		double mean=stats.getMean(); 		
		System.out.println("The attribute's Arithmetic Mean is " +mean);
		System.out.println();
		return mean;
		
		
		
	}
	
	public static double GeometricMean (DataHandle data, String attribute){
		
		/*
		 * After letting the method GetAttributeValues() check whether the attribute can be displayed graphically and providing
		 * a double array with the attribute's values, this method calculates the geometric mean of this attribute's values.
		 * 
		 */
			
		
		numbers=GetAttributeValues(data, attribute);
		
		stats = new DescriptiveStatistics();
		for( int i = 0; i < rowAmount;i++) {
			stats.addValue(numbers[i]);
		}
			
		double mean=stats.getGeometricMean(); 
		System.out.println("The attribute's Geometric Mean is " +mean);
		System.out.println();
		return mean;
		
	}
	
	public static double StandardDeviation (DataHandle data, String attribute){
		
	/*
	 * After letting the method GetAttributeValues() check whether the attribute can be displayed graphically and providing
	 * a double array with the attribute's values, this method calculates the standard deviation of this attribute's values.
	 * 
	 */
		
		
		
		numbers=GetAttributeValues(data, attribute);
		
		stats = new DescriptiveStatistics();
		for( int i = 0; i < rowAmount;i++) {
			stats.addValue(numbers[i]);
		}
			
		double mean=stats.getStandardDeviation();
		
		System.out.println("The attribute's Standard Deviation is " +mean);
		System.out.println();
		return mean;
		
	}
	
	public static double Kurtosis (DataHandle data, String attribute){
		
	/*
	 * After letting the method GetAttributeValues() check whether the attribute can be displayed graphically and providing
	 * a double array with the attribute's values, this method calculates the standard deviation of this attribute's values.
	 * 
	 */
		
		
		
		numbers=GetAttributeValues(data, attribute);
		
		stats = new DescriptiveStatistics();
		for( int i = 0; i < rowAmount;i++) {
			stats.addValue(numbers[i]);
		}
			
		double mean=stats.getKurtosis();
		
		System.out.println("The attribute's Standard Deviation is " +mean);
		System.out.println();
		return mean;
		
	}
	
	public static double Variance (DataHandle data, String attribute){
		
	/*
	 * After letting the method GetAttributeValues() check whether the attribute can be displayed graphically and providing
	 * a double array with the attribute's values, this method calculates the standard deviation of this attribute's values.
	 * 
	 */
		
		
		
		numbers=GetAttributeValues(data, attribute);
		
		stats = new DescriptiveStatistics();
		for( int i = 0; i < rowAmount;i++) {
			stats.addValue(numbers[i]);
		}
			
		double mean=stats.getVariance();
		
		System.out.println("The attribute's Standard Deviation is " +mean);
		System.out.println();
		return mean;
		
	}
	
	
	
}
	
	



