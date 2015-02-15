package fr.autopdutop.ece.java.thread_safeBST.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;


public class ViewController {
	
	@FXML 
	private Button launchBtn;
	@FXML 
	private ProgressBar pgBar;
	@FXML 
	private TextField nbThreads;
	@FXML 
	private TextField nbVal;
	@FXML 
	private LineChart<Number, Number> chartGraph;
	ObservableList<XYChart.Series<Number, Number>> lineChartData = FXCollections.observableArrayList();
    LineChart.Series<Number, Number> series1 = new LineChart.Series<Number, Number>();
	
	public ViewController() {
	}
	
	 @FXML
	protected void initialize() {
		// Initialize the line chart points and displays
		series1.getData().add(new XYChart.Data<Number, Number>(0.0, 1.0));
		series1.getData().add(new XYChart.Data<Number, Number>(1.2, 1.4));
		series1.getData().add(new XYChart.Data<Number, Number>(2.2, 1.9));
		series1.getData().add(new XYChart.Data<Number, Number>(2.7, 2.3));
		series1.getData().add(new XYChart.Data<Number, Number>(2.9, 0.5));
		
		lineChartData.add(series1);
		chartGraph.setData(lineChartData);
		chartGraph.createSymbolsProperty();
	 }

	public void handleButtonLaunch(){
		// Get the value of text fields and convert strings to integer
		int nbThread = Integer.parseInt(this.nbThreads.getText());
		int nbWord = Integer.parseInt(this.nbVal.getText());
		// Print on output the values of text fields
		System.out.println(nbWord+' '+nbThread);
		// Use the text fields values to set the next point on line chart
		series1.getData().add(new XYChart.Data<Number, Number>(nbThread, nbWord));		
		// Launch Benchmark
		Benchmark.launch(nbThread, nbWord);
	}
}