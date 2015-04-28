package org.deidentifier.arx.kap;

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

	Button choice1;
	Button choice2;
	Button choice3;
	Label prompt;

	public void displayData(final StatisticsSummary statSum,
			final String attribute, final DataHandle dataHandle) {
		final Display display = new Display();

		Shell shell = new Shell(display);
		shell.setSize(300, 200);
		shell.setText("Choose the values of the attribute " + attribute);

		String displayText = ("Attribute:\t\t" + attribute);

		if (statSum.getScale() == ScaleOfMeasure.NOMINAL) {

			prompt = new Label(shell, SWT.LEFT);
			shell.setText("Displaying the data of attribute " + attribute);

			displayText = displayText
					.concat("\nScale Of Measure:\t\tNominal scale\nMode:\t\t\t"
							+ statSum.getModeAsString());
			prompt.setText(displayText);
			prompt.setBounds(0, 0, 1000, 1000);

		} else if (statSum.getScale() == ScaleOfMeasure.ORDINAL) {

			if (dataHandle.getDefinition().getDataType(attribute) == DataType.STRING
					|| dataHandle.getDefinition().getDataType(attribute) == DataType.ORDERED_STRING) {

				prompt = new Label(shell, SWT.LEFT);
				shell.setText("Displaying the data of attribute " + attribute);

				displayText = displayText
						.concat("\nScale Of Measure:\t\tOrdinal scale\nMode:\t\t\t"
								+ statSum.getModeAsString()
								+ "\nMedian:\t\t\t"
								+ statSum.getMedianAsString()
								+ "\nMaximum:\t\t"
								+ statSum.getMaxAsString()
								+ "\nMinimum:\t\t" + statSum.getMinAsString());
				prompt.setText(displayText);
				prompt.setBounds(0, 0, 1000, 100);

			} else {

				shell.setSize(800, 600);
				Chart medianMinMaxChart = new Chart(shell, SWT.NONE);
				shell.setLayout(new FillLayout());

				medianMinMaxChart.getAxisSet().getXAxis(0).getTitle()
						.setVisible(false);
				medianMinMaxChart.getAxisSet().getYAxis(0).getTitle()
						.setVisible(false);

				medianMinMaxChart.getTitle().setText(
						"Displaying the Median, Minimum and Maximum of the attribute "
								+ attribute);

				final double[] medMinMaxSeries = new double[] {
						Double.parseDouble(statSum.getModeAsString()),
						Double.parseDouble(statSum.getMedianAsString()),
						Double.parseDouble(statSum.getMinAsString()),
						Double.parseDouble(statSum.getMaxAsString()) };

				IBarSeries barSeries = (IBarSeries) medianMinMaxChart
						.getSeriesSet().createSeries(SeriesType.BAR, attribute);
				barSeries.setVisibleInLegend(false);
				barSeries.setBarColor(new Color(Display.getDefault(), 80, 240,
						180));
				barSeries.setYSeries(medMinMaxSeries);

				IAxis xAxis = medianMinMaxChart.getAxisSet().getXAxis(0);
				xAxis.setCategorySeries(new String[] { "Mode", "Median",
						"Minimum", "Maximum" });
				xAxis.enableCategory(true);

				ISeriesLabel valueLabel = barSeries.getLabel();
				valueLabel.setFormat("######,######");
				valueLabel.setVisible(true);

				medianMinMaxChart.getAxisSet().adjustRange();
				shell.open();

			}

		} else if (statSum.getScale() == ScaleOfMeasure.INTERVAL
				|| statSum.getScale() == ScaleOfMeasure.RATIO) {

			if (dataHandle.getDataType(attribute) != DataType.DATE) {

				choice1 = new Button(shell, SWT.PUSH);

				choice1.setText("Display values as text");
				choice1.setBounds(10, 10, 150, 30);
				choice1.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						intervalRatioText(display, statSum, attribute);
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);
					}
				});

				choice2 = new Button(shell, SWT.PUSH);

				choice2.setText("Display a Box-Plot");
				choice2.setBounds(160, 10, 120, 30);
				choice2.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						BoxPlot boxPlot = new BoxPlot();
						boxPlot.displayBoxPlot(dataHandle, attribute);
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);
					}
				});

			}

		}

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

	}

	public void intervalRatioText(Display display, StatisticsSummary statSum,
			String attribute) {

		final Shell modeShell = new Shell(display, SWT.CLOSE);
		modeShell
				.setText("Displaying the values of the attribute " + attribute);

		Label modeLabel = new Label(modeShell, SWT.NONE);
		String displayText = ("Attribute:\t\t" + attribute
				+ "\nScale Of Measure:\t\t" + "Ordinal scale\nMode:\t\t\t"
				+ statSum.getModeAsString() + "\nMedian:\t\t\t"
				+ statSum.getMedianAsString() + "\nMaximum:\t\t"
				+ statSum.getMaxAsString() + "\nMinimum:\t\t"
				+ statSum.getMinAsString() + "\nArithmetic Mean:\t\t"
				+ statSum.getArithmeticMeanAsString()
				+ "\nSample Variance:\t\t"
				+ statSum.getSampleVarianceAsString()
				+ "\nPopulation Variance:\t"
				+ statSum.getPopulationVarianceAsString()
				+ "\nStandard Deviance:\t" + statSum.getStdDevAsString()
				+ "\nKurtosis:\t\t\t" + statSum.getKurtosisAsString()
				+ "\nRange:\t\t\t" + statSum.getRangeAsString());
		if (statSum.isGeometricMeanAvailable()) {
			displayText = displayText.concat("\nGeometric Mean:\t\t"
					+ statSum.getGeometricMeanAsString());
		}
		modeLabel.setText(displayText);
		modeLabel.setBounds(10, 5, 1000, 1000);
		modeShell.setSize(350, 300);
		modeShell.open();

	}

	public void intervalRatioBarSeries(Display display,
			StatisticsSummary statSum, String attribute) {

		final Shell intervalRatioShell = new Shell(display, SWT.CLOSE);
		intervalRatioShell.setSize(800, 600);
		intervalRatioShell
				.setText("Displaying the Median, Minimum and Maximum of the attribute "
						+ attribute);
		intervalRatioShell.setLayout(new FillLayout());

		Chart intervalRatioChart = new Chart(intervalRatioShell, SWT.NONE);

		intervalRatioChart.getAxisSet().getXAxis(0).getTitle()
				.setVisible(false);
		intervalRatioChart.getAxisSet().getYAxis(0).getTitle()
				.setVisible(false);

		intervalRatioChart.getTitle().setText(
				"Displaying the Median, Minimum and Maximum of the attribute "
						+ attribute);

		final double[] intervalRatioSeries = new double[] {
				Double.parseDouble(statSum.getMedianAsString()),
				Double.parseDouble(statSum.getMinAsString()),
				Double.parseDouble(statSum.getMaxAsString()) };

		IBarSeries barSeries = (IBarSeries) intervalRatioChart.getSeriesSet()
				.createSeries(SeriesType.BAR, attribute);
		barSeries.setBarColor(new Color(Display.getDefault(), 80, 240, 180));
		barSeries.setYSeries(intervalRatioSeries);

		IAxis xAxis = intervalRatioChart.getAxisSet().getXAxis(0);
		xAxis.setCategorySeries(new String[] { "Median", "Minimum", "Maximum" });
		xAxis.enableCategory(true);

		ISeriesLabel valueLabel = barSeries.getLabel();
		valueLabel.setFormat("######,######");
		valueLabel.setVisible(true);

		intervalRatioChart.getAxisSet().adjustRange();
		intervalRatioShell.open();

	}

}
