package fr.autopdutop.ece.java.thread_safeBST.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import fr.autopdutop.ece.java.thread_safeBST.model.BSTAdder;
import fr.autopdutop.ece.java.thread_safeBST.model.BinarySearchTree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
		int i;
		pgBar.setProgress(0);
		// Get the value of text fields and convert strings to integer
		int nbThread = Integer.parseInt(this.nbThreads.getText());
		int nbWord = Integer.parseInt(this.nbVal.getText());
		// Print on output the values of text fields
		System.out.println(nbWord+' '+nbThread);		
		
		final Service<Void> service = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						for(int i=1;i<=nbThread;i++)
						{
							double avg = Benchmark.launch(i,nbWord);
							System.out.println(avg);
							series1.getData().add(new XYChart.Data<Number, Number>(i,avg));
							pgBar.setProgress(((double)i/(double)nbThread)*100);
						}
						return null;
					}
				};
			}
		};
		service.start();
		service.setOnSucceeded(workerStateEvent -> {
			service.reset();
		});
		
		
	}

}