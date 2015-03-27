import org.deidentifier.arx.DataDefinition;
import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.DataType;


public class DataTypeTesting extends Test{
	
	
	
	
	public static void Zeug(DataHandle data, String att){
		
		DataDefinition dd= data.getDefinition();
		DataType a=dd.getDataType("age");

		System.out.println(dd.getDataType("age"));
		
	}
	

}
