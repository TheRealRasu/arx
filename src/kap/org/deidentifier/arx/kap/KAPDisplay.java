/*
 * The main class which opens up several shells and gives the user the possibility 
 * to access all different forms of visualization.
 * 
 * @author Mario Antón
 */

package org.deidentifier.arx.kap;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.DataType;
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
	private Button next;

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

	private Chart chart;

	private StatisticsSummary<?> statSum;

	private String attribute;

	private DataType<?> attType;

	private boolean barSeriesClicked = false;
	private boolean boxPlotClicked = false;
	private boolean textClicked = false;
	private boolean string = false;
	private boolean orderedString = false;
	private boolean integer = false;
	private boolean decimal = false;
	private boolean date = false;

	private double[] barSeriesDouble;

	/*
	 * The method opening the shells and providing buttons to access different
	 * visualization methods.
	 * 
	 * @param dataHandle
	 */
	public void displayData(final DataHandle dataHandle) {

		// Establishing "String" as the default starting attribute.
		string = true;
		attribute = "String";
		statSum = dataHandle.getStatistics().getSummaryStatistics(true)
				.get(attribute);
		attType = dataHandle.getDefinition().getDataType(attribute);

		final Display display = new Display();

		final Shell mainShell = new Shell(display);
		final Shell textShell = new Shell(display);
		final Shell barSeriesShell = new Shell(display);
		chart = new Chart(barSeriesShell, SWT.NONE);
		chart.setLayout(new FillLayout());

		mainShell.setSize(400, 200);
		mainShell.setText("Displaying the data of attribute " + attribute);
		textShell.setSize(550, 315);
		textShell.setText("Text shell");
		barSeriesShell.setSize(800, 600);
		barSeriesShell.setText("Bar series shell");
		barSeriesShell.setLayout(new FillLayout());

		// creating all the needed text labels
		attLabel1 = new Label(textShell, SWT.LEFT);
		attLabel2 = new Label(textShell, SWT.LEFT);
		scaleLabel1 = new Label(textShell, SWT.LEFT);
		scaleLabel2 = new Label(textShell, SWT.LEFT);
		modeLabel1 = new Label(textShell, SWT.LEFT);
		modeLabel2 = new Label(textShell, SWT.LEFT);
		medianLabel1 = new Label(textShell, SWT.LEFT);
		medianLabel2 = new Label(textShell, SWT.LEFT);
		maxLabel1 = new Label(textShell, SWT.LEFT);
		maxLabel2 = new Label(textShell, SWT.LEFT);
		minLabel1 = new Label(textShell, SWT.LEFT);
		minLabel2 = new Label(textShell, SWT.LEFT);
		meanLabel1 = new Label(textShell, SWT.LEFT);
		meanLabel2 = new Label(textShell, SWT.LEFT);
		rangeLabel1 = new Label(textShell, SWT.LEFT);
		rangeLabel2 = new Label(textShell, SWT.LEFT);
		kurtosisLabel1 = new Label(textShell, SWT.LEFT);
		kurtosisLabel2 = new Label(textShell, SWT.LEFT);
		samVarLabel1 = new Label(textShell, SWT.LEFT);
		samVarLabel2 = new Label(textShell, SWT.LEFT);
		popVarLabel1 = new Label(textShell, SWT.LEFT);
		popVarLabel2 = new Label(textShell, SWT.LEFT);
		stdDevLabel1 = new Label(textShell, SWT.LEFT);
		stdDevLabel2 = new Label(textShell, SWT.LEFT);
		geoMeanLabel1 = new Label(textShell, SWT.LEFT);
		geoMeanLabel2 = new Label(textShell, SWT.LEFT);

		choice2 = new Button(mainShell, SWT.PUSH);

		choice2.setText("Display a Box-Plot");
		choice2.setBounds(160, 10, 120, 30);
		choice2.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				/*
				 * The booleans "boxPlotClicked", "barSeriesClicked" and
				 * "textClicked" exist to ensure that every visualization method
				 * can only be selected once per attribute. They are set to
				 * "false" by default, and switch to "true" once the button has
				 * been pushed.
				 * 
				 * After changing attributes with the "next" button, the
				 * booleans are set to "false" again.
				 */

				if (!boxPlotClicked) {
					final BoxPlotJFreeChart boxPlot = new BoxPlotJFreeChart();
					boxPlot.displayBoxPlot(dataHandle, attribute, attType,
							statSum);
					boxPlotClicked = true;
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		choice2.setVisible(false);

		choice3 = new Button(mainShell, SWT.PUSH);

		choice3.setText("Display values as Bar Series");
		choice3.setBounds(10, 40, 150, 30);
		choice3.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!barSeriesClicked) {
					chart.dispose();
					chart = barSeries(chart, barSeriesShell, statSum,
							attribute, attType);
					barSeriesShell.setLayout(new FillLayout());
					barSeriesClicked = true;
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		choice3.setVisible(false);

		choice1 = new Button(mainShell, SWT.PUSH);

		choice1.setText("Display values as text");
		choice1.setBounds(10, 10, 150, 30);
		choice1.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!textClicked) {
					attributeText(textShell, statSum, attribute, attType);
					textClicked = true;
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		next = new Button(mainShell, SWT.PUSH);

		next.setText("Next Attribute");
		next.setBounds(160, 40, 120, 30);
		next.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				/*
				 * upon pushing the "next" Button, the shell is given the next
				 * attribute in the sample database, and the booleans are set
				 * accordingly.
				 * 
				 * Moreover, the DataType of the attribute and the
				 * StatisticsSummary are updated.
				 */

				if (string) {
					attribute = "OrderedString";
					statSum = dataHandle.getStatistics()
							.getSummaryStatistics(true).get(attribute);
					attType = dataHandle.getDefinition().getDataType(attribute);
					mainShell.setText("Displaying the data of attribute "
							+ attribute);
					string = false;
					orderedString = true;
				} else if (orderedString) {
					attribute = "integer";
					statSum = dataHandle.getStatistics()
							.getSummaryStatistics(true).get(attribute);
					attType = dataHandle.getDefinition().getDataType(attribute);
					mainShell.setText("Displaying the data of attribute "
							+ attribute);
					orderedString = false;
					integer = true;
					choice2.setVisible(true);
					choice3.setVisible(true);

				} else if (integer) {
					attribute = "decimal";
					statSum = dataHandle.getStatistics()
							.getSummaryStatistics(true).get(attribute);
					attType = dataHandle.getDefinition().getDataType(attribute);
					mainShell.setText("Displaying the data of attribute "
							+ attribute);
					integer = false;
					decimal = true;
				} else if (decimal) {
					attribute = "date";
					statSum = dataHandle.getStatistics()
							.getSummaryStatistics(true).get(attribute);
					attType = dataHandle.getDefinition().getDataType(attribute);
					mainShell.setText("Displaying the data of attribute "
							+ attribute);
					decimal = false;
					date = true;
				} else if (date) {
					attribute = "String";
					statSum = dataHandle.getStatistics()
							.getSummaryStatistics(true).get(attribute);
					attType = dataHandle.getDefinition().getDataType(attribute);
					mainShell.setText("Displaying the data of attribute "
							+ attribute);
					date = false;
					string = true;
					choice2.setVisible(false);
					choice3.setVisible(false);
				}
				boxPlotClicked = false;
				barSeriesClicked = false;
				textClicked = false;
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

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

	/*
	 * Method to display values as text.
	 * 
	 * @param textShell
	 * 
	 * @param statSum
	 * 
	 * @param attribute
	 * 
	 * @param attType
	 */
	public void attributeText(Shell textShell, StatisticsSummary<?> statSum,
			String attribute, DataType<?> attType) {

		// All labels are set to invisible at the start of the method, as not
		// every attribute displays all labels.
		attLabel1.setVisible(false);
		attLabel2.setVisible(false);
		scaleLabel1.setVisible(false);
		scaleLabel2.setVisible(false);
		modeLabel1.setVisible(false);
		modeLabel2.setVisible(false);
		medianLabel1.setVisible(false);
		medianLabel2.setVisible(false);
		maxLabel1.setVisible(false);
		maxLabel2.setVisible(false);
		minLabel1.setVisible(false);
		minLabel2.setVisible(false);
		meanLabel1.setVisible(false);
		meanLabel2.setVisible(false);
		rangeLabel1.setVisible(false);
		rangeLabel2.setVisible(false);
		kurtosisLabel1.setVisible(false);
		kurtosisLabel2.setVisible(false);
		samVarLabel1.setVisible(false);
		samVarLabel2.setVisible(false);
		popVarLabel1.setVisible(false);
		popVarLabel2.setVisible(false);
		stdDevLabel1.setVisible(false);
		stdDevLabel2.setVisible(false);
		geoMeanLabel1.setVisible(false);
		geoMeanLabel2.setVisible(false);

		attLabel1.setBounds(10, 10, 160, 20);
		attLabel2.setBounds(170, 10, 160, 20);
		attLabel1.setText("Attribute:");
		attLabel2.setText(attribute);
		attLabel1.setVisible(true);
		attLabel2.setVisible(true);

		scaleLabel1.setBounds(10, 30, 160, 20);
		scaleLabel2.setBounds(170, 30, 160, 20);
		scaleLabel1.setText("Scale Of Measure");
		scaleLabel2.setText(statSum.getScale().toString());
		scaleLabel1.setVisible(true);
		scaleLabel2.setVisible(true);

		modeLabel1.setText("Mode:");
		modeLabel2.setText(statSum.getModeAsString());
		modeLabel1.setBounds(10, 50, 160, 20);
		modeLabel2.setBounds(170, 50, 160, 20);
		modeLabel1.setVisible(true);
		modeLabel2.setVisible(true);

		/*
		 * Depending on the Scale of the attribute, different amounts of labels
		 * will be displayed. This is why the Scale of measure is looked at in
		 * order to determine the needed labels.
		 */

		if (statSum.getScale() != ScaleOfMeasure.NOMINAL) {

			medianLabel1.setText("Median:");
			medianLabel2.setText(statSum.getMedianAsString());
			medianLabel1.setBounds(10, 70, 160, 20);
			medianLabel2.setBounds(170, 70, 160, 20);
			medianLabel1.setVisible(true);
			medianLabel2.setVisible(true);

			maxLabel1.setText("Maximum:");
			maxLabel2.setText(statSum.getMaxAsString());
			maxLabel1.setBounds(10, 90, 160, 20);
			maxLabel2.setBounds(170, 90, 160, 20);
			maxLabel1.setVisible(true);
			maxLabel2.setVisible(true);

			minLabel1.setText("Minimum:");
			minLabel2.setText(statSum.getMinAsString());
			minLabel1.setBounds(10, 110, 160, 20);
			minLabel2.setBounds(170, 110, 160, 20);
			minLabel1.setVisible(true);
			minLabel2.setVisible(true);

		}

		if (statSum.getScale() == ScaleOfMeasure.INTERVAL
				|| statSum.getScale() == ScaleOfMeasure.RATIO) {

			meanLabel1.setText("Arithmetic mean:");
			meanLabel2.setText(statSum.getArithmeticMeanAsString());
			meanLabel1.setBounds(10, 130, 160, 20);
			meanLabel2.setBounds(170, 130, 160, 20);
			meanLabel1.setVisible(true);
			meanLabel2.setVisible(true);

			rangeLabel1.setText("range:");
			rangeLabel2.setText(statSum.getRangeAsString());
			rangeLabel1.setBounds(10, 150, 160, 20);
			rangeLabel2.setBounds(170, 150, 160, 20);
			rangeLabel1.setVisible(true);
			rangeLabel2.setVisible(true);

			kurtosisLabel1.setText("kurtosis:");
			kurtosisLabel2.setText(statSum.getKurtosisAsString());
			kurtosisLabel1.setBounds(10, 170, 160, 20);
			kurtosisLabel2.setBounds(170, 170, 160, 20);
			kurtosisLabel1.setVisible(true);
			kurtosisLabel2.setVisible(true);

			samVarLabel1.setText("sample variance:");
			samVarLabel2.setText(statSum.getSampleVarianceAsString());
			samVarLabel1.setBounds(10, 190, 160, 20);
			samVarLabel2.setBounds(170, 190, 500, 20);
			samVarLabel1.setVisible(true);
			samVarLabel2.setVisible(true);

			popVarLabel1.setText("population variance:");
			popVarLabel2.setText(statSum.getPopulationVarianceAsString());
			popVarLabel1.setBounds(10, 210, 160, 20);
			popVarLabel2.setBounds(170, 210, 500, 20);
			popVarLabel1.setVisible(true);
			popVarLabel2.setVisible(true);

			stdDevLabel1.setText("standard deviance:");
			stdDevLabel2.setText(statSum.getStdDevAsString());
			stdDevLabel1.setBounds(10, 230, 160, 20);
			stdDevLabel2.setBounds(170, 230, 200, 20);
			stdDevLabel1.setVisible(true);
			stdDevLabel2.setVisible(true);

		}

		if (statSum.getScale() == ScaleOfMeasure.RATIO) {

			geoMeanLabel1.setText("geometric mean:");
			geoMeanLabel2.setText(statSum.getGeometricMeanAsString());
			geoMeanLabel1.setBounds(10, 250, 160, 20);
			geoMeanLabel2.setBounds(170, 250, 160, 20);
			geoMeanLabel1.setVisible(true);
			geoMeanLabel2.setVisible(true);

		}

	}

	/*
	 * Method to display the Mode, Median, Minimum and Maximum of an attribute
	 * as bar series.
	 * 
	 * @param barShell
	 * 
	 * @param statSum
	 * 
	 * @param attribute
	 * 
	 * @param dataType
	 * 
	 * @return
	 */
	public Chart barSeries(Chart chart, Shell barShell,
			StatisticsSummary<?> statSum, String attribute, DataType<?> dataType) {
		chart = new Chart(barShell, SWT.NONE);

		chart.getAxisSet().getXAxis(0).getTitle().setVisible(false);
		chart.getAxisSet().getYAxis(0).getTitle().setVisible(false);

		chart.getTitle().setText(
				"Displaying the Mode, Median, Minimum and Maximum of the attribute "
						+ attribute);

		// The method has to check for the DataType of the attribute, as "date"
		// needs different parsing commands.
		if (dataType != DataType.DATE) {

			barSeriesDouble = new double[] {
					Double.parseDouble(statSum.getModeAsString()),
					Double.parseDouble(statSum.getMedianAsString()),
					Double.parseDouble(statSum.getMinAsString()),
					Double.parseDouble(statSum.getMaxAsString()) };

		} else {
			barSeriesDouble = new double[] {
					stringToDate(statSum.getModeAsString()),
					stringToDate(statSum.getMedianAsString()),
					stringToDate(statSum.getMinAsString()),
					stringToDate(statSum.getMaxAsString()), };

			chart.getAxisSet().getYAxis(0).getTick().setVisible(false);
		}

		/*
		 * The following passage alters the definite zero of the DataType date and sets it
		 * to the 1st of January of the year 0 AD.
		 */
		if (dataType == DataType.DATE) {
			barSeriesDouble[0] = barSeriesDouble[0] + (1970 * 31536000000L);
			barSeriesDouble[1] = barSeriesDouble[1] + (1970 * 31536000000L);
			barSeriesDouble[2] = barSeriesDouble[2] + (1970 * 31536000000L);
			barSeriesDouble[3] = barSeriesDouble[3] + (1970 * 31536000000L);
		}

		IBarSeries barSeries = (IBarSeries) chart.getSeriesSet().createSeries(
				SeriesType.BAR, attribute);
		barSeries.setBarColor(new Color(Display.getDefault(), 80, 240, 180));
		barSeries.setYSeries(barSeriesDouble);

		IAxis xAxis = chart.getAxisSet().getXAxis(0);
		if (dataType == DataType.DATE) {
			xAxis.setCategorySeries(new String[] {
					"Mode: " + statSum.getModeAsString(),
					"Median: " + statSum.getMedianAsString(),
					"Minimum: " + statSum.getMinAsString(),
					"Maximum: " + statSum.getMaxAsString() });
		} else {
			xAxis.setCategorySeries(new String[] { "Mode", "Median", "Minimum",
					"Maximum" });
		}

		xAxis.enableCategory(true);

		ISeriesLabel valueLabel = barSeries.getLabel();
		valueLabel.setFormat("######.######");
		if (dataType == DataType.DATE) {
			valueLabel.setVisible(false);
		} else {
			valueLabel.setVisible(true);
		}

		chart.getAxisSet().adjustRange();
		return chart;

	}

	/*
	 * a method changing a String with the "DD.MM.YYYY"-format into a double
	 * value.
	 * 
	 * @param dateString
	 * 
	 * @return
	 */
	public Double stringToDate(String dateString) {
		final DateFormat format = new SimpleDateFormat("DD.MM.YYYY");
		Date d = null;
		try {
			d = format.parse(dateString);
		} catch (ParseException e) {
		}

		return ((double) d.getTime());
	}

}
