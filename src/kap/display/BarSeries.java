package display;



import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.swtchart.Chart;
import org.swtchart.IAxis;
import org.swtchart.IAxisSet;
import org.swtchart.IAxisTick;
import org.swtchart.IBarSeries;
import org.swtchart.ISeries.SeriesType;
import org.swtchart.ISeriesLabel;
import org.swtchart.Range;



public class BarSeries {
	
	public static Shell shell;
	public static Shell shell2;
	public static Button checkButton1;
	public static Button checkButton2;
	public static Button checkButton3;
	public static Button checkButton4;
	public static Button checkButton5;
	public static Button checkButton6;
	public static Button checkButton7;
	public static Button checkButton8;
	public static Button checkButton9;
	public static Button checkButton10;
	public static Button printButton;
	public static double[] ySeries;
	public static int printLength=0;
	public static int valuesTransmitted=0;
	public static Composite composite;
	
	
	public static void BarSeriesDisplay (String[] modes, String[] mathValues, String attribute){
		
		//will be implemented in this class if tests are completed. The main() class will be removed 
		//after completing tests.
		
		Display display=new Display();
		Shell shell=new Shell(display);
		
		
		shell.setSize(1000,700);
		shell.setLayout(new FillLayout());
		

		Chart chart=new Chart(shell, SWT.NONE);
		IAxisSet aS= chart.getAxisSet();
		IAxis xAxis = aS.getXAxis(0);
		chart.getTitle().setText("Displaying the values of the attribute "+attribute);
		shell.setText("Klinisches Anwendungsprojekt: Bar series");
		Composite composite=chart.getPlotArea();
		composite.setBounds(100, 100, 100, 100);

		IAxisTick xTick=aS.getXAxis(0).getTick();
		IBarSeries BarSeries = (IBarSeries) chart.getSeriesSet()
				.createSeries(SeriesType.BAR, attribute);
		
		ISeriesLabel barLabel=BarSeries.getLabel();
		barLabel.setFormat("#####.###");
		barLabel.setVisible(true);
		chart.getAxisSet().getXAxis(0).getTitle().setVisible(false);
		chart.getAxisSet().getYAxis(0).getTitle().setVisible(false);
		
		
		if (mathValues.length==1){
			
		xAxis.setCategorySeries(new String[]{"Mode"});
		xAxis.enableCategory(true);
		xTick.setVisible(false);
		chart.getAxisSet().getXAxis(0).getTitle().setText("Mode");
		chart.getAxisSet().getXAxis(0).getTitle().setVisible(true);

		BarSeries.setBarPadding(50);
		BarSeries.setYSeries(new double[]{Double.parseDouble(mathValues[0])});

		
		
		}
		if (mathValues.length==4){
			
			xAxis.setCategorySeries(new String[] {"Mode","Median", "Min","Max"});
			xAxis.enableCategory(true);
			xAxis.setRange(new Range(0,4));

			BarSeries.setBarPadding(10);
			BarSeries.setYSeries(new double[]{Double.parseDouble(mathValues[0]),
			Double.parseDouble(mathValues[1]), Double.parseDouble(mathValues[2]), 
			Double.parseDouble(mathValues[3])});
		} 
		if (mathValues.length==9){
			xAxis.setCategorySeries(new String[] {"Mode","Median", "Min","Max",
			"Mean","Variance", "Pop. Variance","range", "kurtosis"});
			xAxis.enableCategory(true);
			xAxis.setRange(new Range(0,9));

			BarSeries.setBarPadding(10);
			BarSeries.setYSeries(new double[]{Double.parseDouble(mathValues[0]),
			Double.parseDouble(mathValues[1]), Double.parseDouble(mathValues[2]), 
			Double.parseDouble(mathValues[3]),Double.parseDouble(mathValues[4]),
			Double.parseDouble(mathValues[5]),Double.parseDouble(mathValues[6]),
			Double.parseDouble(mathValues[7]),Double.parseDouble(mathValues[8])});	
				
			}
		if(mathValues.length==10){
			xAxis.setCategorySeries(new String[] {"Mode","Median", "Min","Max",
			"Mean","Variance", "Pop. Variance","range", "kurtosis", "Geometric Mean"});
			xAxis.enableCategory(true);
			xAxis.setRange(new Range(0,9));

			BarSeries.setBarPadding(10);
			BarSeries.setYSeries(new double[]{Double.parseDouble(mathValues[0]),
			Double.parseDouble(mathValues[1]), Double.parseDouble(mathValues[2]), 
			Double.parseDouble(mathValues[3]),Double.parseDouble(mathValues[4]),
			Double.parseDouble(mathValues[5]),Double.parseDouble(mathValues[6]),
			Double.parseDouble(mathValues[7]),Double.parseDouble(mathValues[8]),
			Double.parseDouble(mathValues[9])});	
		}
		
		aS.adjustRange();
		shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
			
		display.dispose();	
		//ShowChoices(display, mathValues);
			
		/*
		
			//only display mode(s)
		}
		if(mathValues.length==4){
			//display mode(s), median, minimum and maximum
		}
		if(mathValues.length==9){
			//display mode(s), median, minimum, maximum, arithmetic mean, varianca, 
			//population variance, range and kurtosis
		}
		if(mathValues.length==10){
			//display mode(s), median, minimum, maximum, arithmetic mean, varianca, 
			//population variance, range, kurtosis and geometric mean
		}
		
		*/
		
	
		
		
		
		
		}
	
	
	public static void main(String[]args){
		
		//BarSeriesDisplay ( new String [] {"male"}, new String[] {"2"}, "gender");
		//BarSeriesDisplay ( new String [] {"45"}, new String[] {"2", "4", "56", "10"}, "age");
		//BarSeriesDisplay ( new String [] {"45"}, new String[] {"2", "81931", "81667", "81931", "81801.0",
		//"22544.0", "16908.0", "264.0", "-5.985810572911061" }, "zipcode");
		BarSeriesDisplay ( new String [] {"45"}, new String[] {"2", "81931", "81667", "81931", "81801.0",
		"22544.0", "16908.0", "264.0", "-5.985810572911061", "81800.89665140922" }, "zipcode");
		
	
		}
}

	
	
	
	
	
	
	
	
	
	
	