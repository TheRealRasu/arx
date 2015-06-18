/*
 * The class whose function it is to display a Box-Plot through JFreeChart
 *
 * @author Mario Antón
 */
package org.deidentifier.arx.kap;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.aggregates.StatisticsSummary;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

public class BoxPlotJFreeChart {

	// X- and Y-Axis of the Box-Plot
	private CategoryAxis xAxis;
	private NumberAxis yAxis;

	@SuppressWarnings("deprecation")
	/*
	 * The only method in this class. Its function is to display a Box-Plot.
	 * 
	 * @param dataHandle
	 * 
	 * @param attribute
	 * 
	 * @param dataType
	 * 
	 * @param statSum
	 */
	public void displayBoxPlot(DataHandle dataHandle, String attribute,
			DataType<?> dataType, StatisticsSummary<?> statSum) {

		final DefaultBoxAndWhiskerCategoryDataset boxPlotData = new DefaultBoxAndWhiskerCategoryDataset();

		MathForBoxPlot boxPlotMath = new MathForBoxPlot();
		ArrayList<Double> values = boxPlotMath.GetAttributeValuesList(
				dataHandle, attribute);
		/*
		 * JFreeChart's DefaultBoxAndWhiskerCategoryDataset usually displays
		 * numerous Box-Plots at once, who are categorized into different
		 * "series" and "types" (think of it as a 2-dimensional Box-Plot-array).
		 * The following for-loop adds every value into one single series and
		 * type, therefore ensuring only one Box-Plot will be returned.
		 */
		for (int i = 0; i < values.size(); i++) {
			boxPlotData.add(values, "", "");
		}

		yAxis = new NumberAxis("");

		// Setting the text of the X-Axis to display the values of Median,
		// Minimum and Maximum

		if (dataHandle.getDefinition().getDataType(attribute) == DataType.DATE) {
			xAxis = new CategoryAxis("Median: " + statSum.getMedianAsString()
					+ "    Minimum: " + statSum.getMinAsString()
					+ "    Maximum: " + statSum.getMaxAsString());
			yAxis.setVisible(false);

		} else {
			xAxis = new CategoryAxis("Median: "
					+ boxPlotData.getMedianValue(0, 0) + "    Minimum: "
					+ boxPlotData.getMinRegularValue(0, 0) + "    Maximum: "
					+ boxPlotData.getMaxRegularValue(0, 0));

		}

		BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
		CategoryPlot plot = new CategoryPlot(boxPlotData, xAxis, yAxis,
				renderer);
		JFreeChart chart = new JFreeChart(
				"Displaying a Box-Plot for the attribute " + attribute, plot);

		// hiding the arithmetic mean, as its visibility led to visualization
		// issues.
		renderer.setFillBox(true);
		renderer.setMeanVisible(false);
		renderer.setMedianVisible(true);
		renderer.setPaint(new Color(80, 240, 180));

		chart.setBackgroundPaint(Color.white);
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.white);

		// Adjusting the range of the window to ensure that the whole Box-Plot
		// is visible.
		final double max = Double.parseDouble(boxPlotData.getMaxRegularValue(0,
				0).toString());
		final double min = Double.parseDouble(boxPlotData.getMinRegularValue(0,
				0).toString());
		plot.getRangeAxis().setRange(min - (max / 10), max + (max / 10));

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 600));
		chartPanel.getChart().removeLegend();

		JFrame frame = new JFrame();
		JScrollPane scrollPane = new JScrollPane(chartPanel);
		scrollPane.setPreferredSize(new Dimension(600, 700));
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		frame.add(scrollPane);
		frame.pack();
		frame.setVisible(true);

	}
}