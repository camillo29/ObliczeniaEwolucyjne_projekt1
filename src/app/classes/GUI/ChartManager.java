package app.classes.GUI;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ChartManager {
    private LineChart<Number, Number> chart;
    private XYChart.Series series;

    public ChartManager(String name){
        series = new XYChart.Series();
        series.setName(name);
    }

    public void addDataToSeries(int generation, double value){
        series.getData().add(new XYChart.Data(generation, value));
    }

    public void exportChart(String xLabel, String yLabel, String title, String filePath){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
        chart = new LineChart<Number, Number>(xAxis, yAxis);
        chart.setTitle(title);
        chart.setCreateSymbols(false);

        Scene scene = new Scene(chart, 1200, 400);
        chart.setAnimated(false);

        chart.getData().add(series);
        saveAsPng(scene, filePath + ".png");
    }

    public void saveAsPng(Scene scene, String path){
        WritableImage image = scene.snapshot(null);
        File file = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
