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

	private CategoryAxis xAxis;
	private NumberAxis yAxis;

	@SuppressWarnings("deprecation")
	public void displayBoxPlot(DataHandle dataHandle, String attribute,
			DataType<?> dataType, StatisticsSummary<?> statSum) {

		final DefaultBoxAndWhiskerCategoryDataset boxPlotData = new DefaultBoxAndWhiskerCategoryDataset();

		MathForBoxPlot boxPlotMath = new MathForBoxPlot();
		ArrayList<Double> values = boxPlotMath.GetAttributeValuesList(
				dataHandle, attribute);

		for (int i = 0; i < values.size(); i++) {
			boxPlotData.add(values, "", "");
		}

		yAxis = new NumberAxis("");
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

		renderer.setFillBox(true);
		renderer.setMeanVisible(false);
		renderer.setMedianVisible(true);
		renderer.setPaint(new Color(80, 240, 180));

		chart.setBackgroundPaint(Color.white);
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.white);
		final double max = Double.parseDouble(boxPlotData.getMaxRegularValue(0,
				0).toString());
		final double min = Double.parseDouble(boxPlotData.getMinRegularValue(0,
				0).toString());
		plot.getRangeAxis().setRange(min - (max / 10), max + (max / 10));

		// min, then max
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 600));
		chartPanel.getChart().removeLegend();

		JFrame frame = new JFrame();
		JScrollPane scrollPane = new JScrollPane(chartPanel);
		scrollPane.setPreferredSize(new Dimension(800, 700));
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		frame.add(scrollPane);
		frame.pack();
		frame.setVisible(true);

	}
}