package fr.autopdutop.ece.java.thread_safeBST.controller;

import java.io.IOException;
import javafx.application.Platform;
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
	int nbThread = 0;
	@FXML 
	private TextField nbVal;
	int nbWord = 0;
	@FXML 
	private LineChart<Number, Number> chartGraph;
	ObservableList<XYChart.Series<Number, Number>> lineChartData = FXCollections.observableArrayList();
    static LineChart.Series<Number, Number> series1 = new LineChart.Series<Number, Number>();
    
    
	public ViewController() {
	}
	
	 @FXML
	protected void initialize() {
		lineChartData.add(series1);
		chartGraph.setData(lineChartData);
		chartGraph.createSymbolsProperty();
		chartGraph.setLegendVisible(true);
	 }

	public void handleButtonLaunch(){
		pgBar.setProgress(0);
		lineChartData.remove(series1);
		series1 = new LineChart.Series<Number, Number>();
		lineChartData.add(series1);
		// Get the value of text fields and convert strings to integer
		int nbThread = Integer.parseInt(this.nbThreads.getText());
		int nbWord = Integer.parseInt(this.nbVal.getText());
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for(int i=1;i<=nbThread;i++)
				{
					long avg;
					try {
						avg = Benchmark.launch(i,nbWord);
						series1.getData().add(new XYChart.Data<Number, Number>(i,avg));
						pgBar.setProgress(((double)i/(double)nbThread)*100);
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}
			}
		});		
	}

}
