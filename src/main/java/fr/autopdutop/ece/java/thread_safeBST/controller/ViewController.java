package fr.autopdutop.ece.java.thread_safeBST.controller;

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
    LineChart.Series<Number, Number> series1 = new LineChart.Series<Number, Number>();
	
	public ViewController() {
	}
	
	 @FXML
	protected void initialize() {
		// Initialize the line chart points and displays
		/*series1.getData().add(new XYChart.Data<Number, Number>(0.0, 1.0));
		series1.getData().add(new XYChart.Data<Number, Number>(1.2, 1.4));
		series1.getData().add(new XYChart.Data<Number, Number>(2.2, 1.9));
		series1.getData().add(new XYChart.Data<Number, Number>(2.7, 2.3));
		series1.getData().add(new XYChart.Data<Number, Number>(2.9, 0.5));*/
		
		lineChartData.add(series1);
		chartGraph.setData(lineChartData);
		chartGraph.createSymbolsProperty();
	 }

	public void handleButtonLaunch(){
		int i;
		// Get the value of text fields and convert strings to integer
		int nbThread = Integer.parseInt(this.nbThreads.getText());
		int nbWord = Integer.parseInt(this.nbVal.getText());
		// Print on output the values of text fields
		System.out.println(nbWord+' '+nbThread);
		// Use the text fields values to set the next point on line chart
		series1.getData().add(new XYChart.Data<Number, Number>(nbThread, nbWord));		
		// Launch Benchmark
		for(i=1;i<=nbThread;i++)
		{
			launch(nbThread, nbWord);
		}
		
	}
	
	public static void launch(int nbThread, int nbWord) {
		BinarySearchTree<String> rbtree = new BinarySearchTree<>();

		ExecutorService executor = Executors.newFixedThreadPool(nbThread);
		List<Future<Duration>> list = new ArrayList<Future<Duration>>();
		Callable<Duration> callable = new BSTAdder(nbWord, rbtree);

		for (int i = 0; i < nbWord; i++) {
			Future<Duration> future = executor.submit(callable);
			list.add(future);
		}
		for (Future<Duration> fut : list) {
			try {
				System.out.println(new Date() + "::" + fut.get());
				//series1.getData().add(new XYChart.Data<Number, Number>(nbThread, future));
				
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		executor.shutdown();		
	}	

}