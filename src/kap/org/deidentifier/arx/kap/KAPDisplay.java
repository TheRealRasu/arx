package org.deidentifier.arx.kap;



import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.aggregates.StatisticsSummary;
import org.deidentifier.arx.aggregates.StatisticsSummary.ScaleOfMeasure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
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




public class KAPDisplay {
	
	public void displayData (StatisticsSummary statSum, String attribute, DataHandle dataHandle){
		Display display=new Display();
		
		Shell shell=new Shell(display);
		shell.setSize(500, 500);
		shell.setText("Displaying the value "+attribute);
		String displayText=("Attribute:\t\t"+attribute);
		
		
		if(statSum.getScale()==ScaleOfMeasure.NOMINAL){
			
			displayText=displayText.concat("\nScale Of Measure:\tNominal scale\nMode:\t\t"
			+statSum.getModeAsString());
		
		
		} else if (statSum.getScale()==ScaleOfMeasure.ORDINAL){
			
			if (dataHandle.getDataType(attribute)==DataType.STRING || 
					dataHandle.getDataType(attribute)==DataType.ORDERED_STRING){
				displayText=displayText.concat("\nScale Of Measure:\tOrdinal scale\nMode:\t\t"
			+statSum.getModeAsString()+"\nMedian:\t\t"+statSum.getModeAsString()+"\nMaximum:\t\t"
			+statSum.getMaxAsString()+"\nMinimum:\t\t"+statSum.getMinAsString());
				
			} else {
				Chart chart=new Chart(shell,SWT.NONE);
				IAxisSet aX=chart.getAxisSet();
				chart.getTitle().setText("Displaying the value of "+attribute);
				aX.getXAxis(0).getTitle().setVisible(false);
				aX.getYAxis(0).getTitle().setVisible(false);
				
				IBarSeries barSeries =(IBarSeries) chart.getSeriesSet().
						createSeries(SeriesType.BAR, attribute);
				barSeries.setYSeries(new double[] 
						{Double.parseDouble(statSum.getMedianAsString()), 
						Double.parseDouble(statSum.getMinAsString()), 
						Double.parseDouble(statSum.getMaxAsString())});
				aX.adjustRange();
				
			}
			
			
		} else if (statSum.getScale()==ScaleOfMeasure.INTERVAL){
			
			//TODO: INTERVAL scale values!	
			//TODO: RATIO scale values!
		}
		shell.open();

        while (!shell.isDisposed()) {
          if (!display.readAndDispatch()) {
            display.sleep();
          }
        }
        display.dispose();
		
		
	}
	
	
	
}
		
	
	
	
	
	
	