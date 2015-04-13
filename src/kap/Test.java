import java.util.List;

import math.MathForBoxPlot;









import org.deidentifier.arx.AttributeType.Hierarchy;
import org.deidentifier.arx.AttributeType.Hierarchy.DefaultHierarchy;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.Data.DefaultData;
import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.examples.*;


public class Test extends Example{
	public static int[] mathValues;
	
	public static void main (String[] args){
		final DefaultData data = Data.create();
		data.add("age", "gender", "zipcode", "date");
        data.add("45", "female", "81675", "01.01.1982");
        data.add("34", "male", "81667", "11.05.1982");
        data.add("NULL", "male", "81925", "31.08.1982");
        data.add("70", "female", "81931", "02.07.1982");
        data.add("34", "female", null, "05.01.1982");
        data.add("70", "male", "81931", "24.03.1982");
        data.add("45", "male", "81931", "NULL");
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

        data.getDefinition().setDataType("date", DataType.DATE);

        DataHandle dataH=data.getHandle();
        //System.out.println
        
       
        List list=MathForBoxPlot.GetAttributeValuesList(dataH, "date");
        System.out.println(list);
        
        
        //DataTypeTesting.Zeug(dataH, "age");
	
	
        /*double[] dA=new double[8];
        dA[7]=dA[6]=dA[5]=dA[4]=dA[3]=dA[2]=dA[1]=dA[0]=0.0;
        Display display = new Display();
        BarSeries.Choices(display,dA);
        display.dispose();
        
        */
	}
	
	
}