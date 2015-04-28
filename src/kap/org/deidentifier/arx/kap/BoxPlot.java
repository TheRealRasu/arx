package org.deidentifier.arx.kap;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.deidentifier.arx.Data;
import org.deidentifier.arx.Data.DefaultData;
import org.deidentifier.arx.DataHandle;
import org.deidentifier.arx.DataType;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

public class BoxPlot {

	@SuppressWarnings("deprecation")
	public void displayBoxPlot(DataHandle dataHandle, String attribute) {

		final DefaultBoxAndWhiskerCategoryDataset boxPlotData = new DefaultBoxAndWhiskerCategoryDataset();

		MathForBoxPlot boxPlotMath = new MathForBoxPlot();
		ArrayList<Double> values = boxPlotMath.GetAttributeValuesList(
				dataHandle, attribute);

		for (int i = 0; i < values.size(); i++) {
			boxPlotData.add(values, "1", "2");
		}

		CategoryAxis xAxis = new CategoryAxis("");
		NumberAxis yAxis = new NumberAxis("");

		BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
		CategoryPlot plot = new CategoryPlot(boxPlotData, xAxis, yAxis,
				renderer);
		JFreeChart chart = new JFreeChart("Test", plot);

		renderer.setFillBox(true);
		renderer.setMeanVisible(false);
		renderer.setMedianVisible(true);
		renderer.setPaint(new Color(80, 240, 180));

		chart.setBackgroundPaint(Color.white);
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.white);
		plot.getRangeAxis().setRange(20, 80);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 600));

		JFrame frame = new JFrame();
		JScrollPane scrollPane = new JScrollPane(chartPanel);
		scrollPane.setPreferredSize(new Dimension(800, 700));
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		frame.add(scrollPane);
		frame.pack();
		frame.setVisible(true);

	}

	public static void main(String[] args) {

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

		BoxPlot example = new BoxPlot();
		example.displayBoxPlot(dataH, "age");

	}
}