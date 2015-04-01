import math.DoingTheMath;








import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.deidentifier.arx.AttributeType.Hierarchy;
import org.deidentifier.arx.AttributeType.Hierarchy.DefaultHierarchy;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.Data.DefaultData;
import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.examples.*;
import org.eclipse.swt.widgets.Display;

import display.BarSeries;

public class Test extends Example{
	public static int[] mathValues;
	
	public static void main (String[] args){
		final DefaultData data = Data.create();
        data.add("age", "gender", "zipcode");
        data.add("34", "male", "81667");
        data.add("45", "female", "81675");
        data.add("null", "male", "83524");
        data.add("45", "male", "81925");
        data.add("70", "male", "81930");
        data.add("70", "NULL", "81931");
        data.add("34", "female", "81931");
        data.add("70", "male", "81931");
        data.add("70", "female", null);
        data.add("45", "male", "81931");
        final DefaultHierarchy age = Hierarchy.create();
        age.add("34", "<50", "*");
        age.add("45", "<50", "*");
        age.add("66", ">=50", "*");
        age.add("70", ">=50", "*");

        final DefaultHierarchy gender = Hierarchy.create();
        gender.add("male", "*");
        gender.add("female", "*");

        // Only excerpts for readability
        final DefaultHierarchy zipcode = Hierarchy.create();
        zipcode.add("81667", "8166*", "816**", "81***", "8****", "*****");
        zipcode.add("81675", "8167*", "816**", "81***", "8****", "*****");
        zipcode.add("81925", "8192*", "819**", "81***", "8****", "*****");
        zipcode.add("81931", "8193*", "819**", "81***", "8****", "*****");


        data.getDefinition().setDataType("age", DataType.INTEGER);
        data.getDefinition().setDataType("gender", DataType.STRING);
        data.getDefinition().setDataType("zipcode", DataType.DECIMAL);

        DataHandle dataH=data.getHandle();
        //System.out.println
        
       
        DoingTheMath.GetAttributeValues(dataH, "age");
        DoingTheMath.GetAttributeValues(dataH, "gender");
        DoingTheMath.GetAttributeValues(dataH, "zipcode");
        
        
        //DataTypeTesting.Zeug(dataH, "age");
	
	
        /*double[] dA=new double[8];
        dA[7]=dA[6]=dA[5]=dA[4]=dA[3]=dA[2]=dA[1]=dA[0]=0.0;
        Display display = new Display();
        BarSeries.Choices(display,dA);
        display.dispose();
        
        */
	}
	
	
}