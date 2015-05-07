package org.deidentifier.arx.kap;

import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.aggregates.StatisticsSummary;
import org.deidentifier.arx.aggregates.StatisticsSummary.ScaleOfMeasure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.swtchart.Chart;
import org.swtchart.IAxis;
import org.swtchart.IBarSeries;
import org.swtchart.ISeries.SeriesType;
import org.swtchart.ISeriesLabel;

public class KAPDisplay {

	private Button choice1;
	private Button choice2;
	private Button choice3;
	
	private Label attLabel1;
	private Label attLabel2;
	private Label scaleLabel1;
	private Label scaleLabel2;
	private Label modeLabel1;
	private Label modeLabel2;
	private Label medianLabel1;
	private Label medianLabel2;
	private Label maxLabel1;
	private Label maxLabel2;
	private Label minLabel1;
	private Label minLabel2;
	private Label meanLabel1;
	private Label meanLabel2;
	private Label rangeLabel1;
	private Label rangeLabel2;
	private Label kurtosisLabel1;
	private Label kurtosisLabel2;
	private Label samVarLabel1;
	private Label samVarLabel2;
	private Label popVarLabel1;
	private Label popVarLabel2;
	private Label stdDevLabel1;
	private Label stdDevLabel2;
	private Label geoMeanLabel1;
	private Label geoMeanLabel2;
	
	
	private double[] barSeriesDouble;

	public void displayData(final StatisticsSummary<?> statSum,
			final String attribute, final DataHandle dataHandle) {
		final Display display = new Display();

		final Shell mainShell = new Shell(display);
		final Shell textShell =new Shell(display);
		final Shell barSeriesShell=new Shell(display);
		mainShell.setSize(400, 200);
		textShell.setSize(400, 400);
		barSeriesShell.setSize(640, 480);
		mainShell.setText("Displaying the data of attribute " + attribute);
		textShell.setText("Text shell");
		barSeriesShell.setText("Bar series shell");
		

		if (statSum.getScale() == ScaleOfMeasure.NOMINAL|| statSum.getScale()==ScaleOfMeasure.ORDINAL) {
			
			choice1 = new Button(mainShell, SWT.PUSH);

			choice1.setText("Display values as text");
			choice1.setBounds(10, 10, 150, 30);
			choice1.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					attributeText(textShell, statSum, attribute);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});
			
		

			

		} else if (statSum.getScale() == ScaleOfMeasure.INTERVAL
				|| statSum.getScale() == ScaleOfMeasure.RATIO) {

				choice1 = new Button(mainShell, SWT.PUSH);

				choice1.setText("Display values as text");
				choice1.setBounds(10, 10, 150, 30);
				choice1.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						attributeText(textShell, statSum, attribute);
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);
					}
				});

				choice2 = new Button(mainShell, SWT.PUSH);

				choice2.setText("Display a Box-Plot");
				choice2.setBounds(160, 10, 120, 30);
				choice2.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						final BoxPlotJFreeChart boxPlot=new BoxPlotJFreeChart();
						boxPlot.displayBoxPlot(dataHandle, attribute);
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);
					}
				});
				
				choice3 = new Button(mainShell, SWT.PUSH);

				choice3.setText("Display values as Bar Series");
				choice3.setBounds(10, 40, 150, 30);
				choice3.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						barSeries(barSeriesShell, statSum, attribute);
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);
					}
				});

			}

		

		mainShell.open();
		textShell.open();
		barSeriesShell.open();
		while (!mainShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

	}

	public void attributeText(Shell textShell, StatisticsSummary<?> statSum,
			String attribute) {

		attLabel1 = new Label(textShell, SWT.LEFT);
		attLabel2 = new Label(textShell, SWT.LEFT);
		attLabel1.setBounds(10, 10, 160, 20);
		attLabel2.setBounds(170,10,160,20);
		attLabel1.setText("Attribute:");
		attLabel2.setText(attribute);
		
		
		scaleLabel1 = new Label(textShell, SWT.LEFT);
		scaleLabel2 = new Label(textShell, SWT.LEFT);
		scaleLabel1.setBounds(10, 30, 160, 20);
		scaleLabel2.setBounds(170,30,160,20);
		scaleLabel1.setText("Scale Of Measure");
		scaleLabel2.setText(statSum.getScale().toString());
		
	
		modeLabel1 = new Label(textShell, SWT.LEFT);
		modeLabel2 = new Label(textShell, SWT.LEFT);
		modeLabel1.setText("Mode:");
		modeLabel2.setText(statSum.getModeAsString());
		modeLabel1.setBounds(10, 50, 160, 20);
		modeLabel2.setBounds(170,50,160,20);
		

		if(statSum.getScale()!=ScaleOfMeasure.NOMINAL){
			
			medianLabel1 = new Label(textShell, SWT.LEFT);
			medianLabel2 = new Label(textShell, SWT.LEFT);
			medianLabel1.setText("Median:");
			medianLabel2.setText(statSum.getMedianAsString());
			medianLabel1.setBounds(10, 70, 160, 20);
			medianLabel2.setBounds(170, 70, 160, 20);
			
			maxLabel1 = new Label(textShell, SWT.LEFT);
			maxLabel2 = new Label(textShell, SWT.LEFT);
			maxLabel1.setText("Maximum:");
			maxLabel2.setText(statSum.getMaxAsString());
			maxLabel1.setBounds(10, 90, 160, 20);
			maxLabel2.setBounds(170, 90, 160, 20);
			
			minLabel1 = new Label(textShell, SWT.LEFT);
			minLabel2 = new Label(textShell, SWT.LEFT);
			minLabel1.setText("Minimum:");
			minLabel2.setText(statSum.getMinAsString());
			minLabel1.setBounds(10, 110, 160, 20);
			minLabel2.setBounds(170, 110, 160, 20);

		}
		
		if(statSum.getScale()==ScaleOfMeasure.INTERVAL || statSum.getScale()==ScaleOfMeasure.RATIO){
			
			meanLabel1 = new Label(textShell, SWT.LEFT);
			meanLabel2 = new Label(textShell, SWT.LEFT);
			meanLabel1.setText("Arithmetic mean:");
			meanLabel2.setText(statSum.getArithmeticMeanAsString());
			meanLabel1.setBounds(10, 130, 160, 20);
			meanLabel2.setBounds(170, 130, 160, 20);
			
			rangeLabel1 = new Label(textShell, SWT.LEFT);
			rangeLabel2 = new Label(textShell, SWT.LEFT);
			rangeLabel1.setText("range:");
			rangeLabel2.setText(statSum.getRangeAsString());
			rangeLabel1.setBounds(10, 150, 160, 20);
			rangeLabel2.setBounds(170, 150, 160, 20);
			
			kurtosisLabel1 = new Label(textShell, SWT.LEFT);
			kurtosisLabel2 = new Label(textShell, SWT.LEFT);
			kurtosisLabel1.setText("kurtosis:");
			kurtosisLabel2.setText(statSum.getKurtosisAsString());
			kurtosisLabel1.setBounds(10, 170, 160, 20);
			kurtosisLabel2.setBounds(170, 170, 160, 20);
			
			samVarLabel1 = new Label(textShell, SWT.LEFT);
			samVarLabel2 = new Label(textShell, SWT.LEFT);
			samVarLabel1.setText("sample variance:");
			samVarLabel2.setText(statSum.getSampleVarianceAsString());
			samVarLabel1.setBounds(10, 190, 160, 20);
			samVarLabel2.setBounds(170, 190, 160, 20);
			
			popVarLabel1 = new Label(textShell, SWT.LEFT);
			popVarLabel2 = new Label(textShell, SWT.LEFT);
			popVarLabel1.setText("population variance:");
			popVarLabel2.setText(statSum.getPopulationVarianceAsString());
			popVarLabel1.setBounds(10, 210, 160, 20);
			popVarLabel2.setBounds(170, 210, 160, 20);
			
			stdDevLabel1 = new Label(textShell, SWT.LEFT);
			stdDevLabel2 = new Label(textShell, SWT.LEFT);
			stdDevLabel1.setText("standard deviance:");
			stdDevLabel2.setText(statSum.getStdDevAsString());
			stdDevLabel1.setBounds(10, 230, 160, 20);
			stdDevLabel2.setBounds(170, 230, 160, 20);
			
			
		}
		
		if(statSum.getScale()==ScaleOfMeasure.RATIO){
			
			
			geoMeanLabel1 = new Label(textShell, SWT.LEFT);
			geoMeanLabel2 = new Label(textShell, SWT.LEFT);
			geoMeanLabel1.setText("geometric mean:");
			geoMeanLabel2.setText(statSum.getGeometricMeanAsString());
			geoMeanLabel1.setBounds(10, 250, 160, 20);
			geoMeanLabel2.setBounds(170, 250, 160, 20);
			
		}
		
		
		
		
		
		
		

	}

	
	
	public void barSeries(Shell barShell,
			StatisticsSummary<?> statSum, String attribute) {

		
		barShell.setLayout(new FillLayout());

		Chart barChart = new Chart(barShell, SWT.NONE);

		barChart.getAxisSet().getXAxis(0).getTitle()
				.setVisible(false);
		barChart.getAxisSet().getYAxis(0).getTitle()
				.setVisible(false);

		barChart.getTitle().setText(
				"Displaying the Mode, Median, Minimum and Maximum of the attribute "
						+ attribute);
		
		if(statSum.getScale()!=ScaleOfMeasure.INTERVAL){
			
			barSeriesDouble = new double[] {
				Double.parseDouble(statSum.getModeAsString()),
				Double.parseDouble(statSum.getMedianAsString()),
				Double.parseDouble(statSum.getMinAsString()),
				Double.parseDouble(statSum.getMaxAsString()) };
		} else {
			
			barSeriesDouble=new double[]{
					
			};
			
		}
			IBarSeries barSeries = (IBarSeries) barChart.getSeriesSet()
				.createSeries(SeriesType.BAR, attribute);
			barSeries.setBarColor(new Color(Display.getDefault(), 80, 240, 180));
			barSeries.setYSeries(barSeriesDouble);

			IAxis xAxis = barChart.getAxisSet().getXAxis(0);
			xAxis.setCategorySeries(new String[] { "Mode", "Median", "Minimum", "Maximum" });
			xAxis.enableCategory(true);

			ISeriesLabel valueLabel = barSeries.getLabel();
			valueLabel.setFormat("######.######");
			valueLabel.setVisible(true);

			barChart.getAxisSet().adjustRange();
			

	}

}
