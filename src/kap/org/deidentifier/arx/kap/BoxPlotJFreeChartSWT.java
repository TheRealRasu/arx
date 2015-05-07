package org.deidentifier.arx.kap;

import java.util.ArrayList;

import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.Data.DefaultData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
 
public class BoxPlotJFreeChartSWT extends Shell {
 
	private DefaultBoxAndWhiskerCategoryDataset boxPlotData;
	private JFreeChart boxChart;
	private ChartComposite composite;
 
	public BoxPlotJFreeChartSWT(Display display, DataHandle dataHandle, String attribute) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		composite = new ChartComposite(this, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
 
		boxPlotData = new DefaultBoxAndWhiskerCategoryDataset();
		MathForBoxPlot boxPlotMath = new MathForBoxPlot();
		ArrayList<Double> values = boxPlotMath.GetAttributeValuesList(
				dataHandle, attribute);

		for (int i = 0; i < values.size(); i++) {
			boxPlotData.add(values, "", "");
		}
		setSize(800, 700);
		
		boxChart = ChartFactory.createBoxAndWhiskerChart("Displaying a Box-Plot for the attribute "+attribute, "Median: "+boxPlotData.getMedianValue(0, 0)+
				"    Minimum: "+boxPlotData.getMinRegularValue(0, 0)+
				"    Maximum: "+boxPlotData.getMaxRegularValue(0, 0),"", boxPlotData, false);
	
	
		composite.setChart(boxChart);
	}
 
	public static void main(String args[]) {
		try {

			final DefaultData data = Data.create();
			data.add("age", "gender", "zipcode", "date");
			data.add("45", "female", "81675", "01.01.1982");
			data.add("34", "male", "81667", "11.05.1982");
			data.add("NULL", "male", "81925", "31.08.1982");
			data.add("60", "female", "81931", "02.07.1982");
			data.add("34", "female", null, "05.01.1982");
			data.add("70", "male", "81931", "24.03.1982");
			data.add("45", "male", "81931", "NULL");

			data.getDefinition().setDataType("age", DataType.INTEGER);
			data.getDefinition().setDataType("gender", DataType.STRING);
			data.getDefinition().setDataType("zipcode", DataType.DECIMAL);

			data.getDefinition().setDataType("date", DataType.DATE);

			DataHandle dataH = data.getHandle();
			
			
			Display display = Display.getDefault();
			BoxPlotJFreeChartSWT shell = new BoxPlotJFreeChartSWT(display, dataH, "age");
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
 
}