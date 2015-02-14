package fr.autopdutop.ece.java.thread_safeBST.controller;

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

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import fr.autopdutop.ece.java.thread_safeBST.model.*;

/*
 1) Implement a BinarySearchTree of String.
 2) Write a RandomWordGenerator class which implements Iterable. The constructor of this class takes an integer parameter corresponding to the number of words to randomly generate.
 3) Write a BSTAdder class which implements Callable of Duration. The call method of this class adds a random word in the binary search tree and measures the duration of the operation (System.nanoTime()).
 4) Implement the synchronization mechanisms in the BinarySearchTree (see Lesson 3) to ensure the consistency of the tree during adding. (A voir pour un plus petit sync)
 5) Implement an ExecutorService (CompletionService ?) allowing to submit BSTAdder for each randomly generated word.
 6) Implement a method which tests if a BinarySearchTree object respects the BST properties. The aim of this method is to validate the consistency of your multi-threaded addition process.
 7) Final step: write a (very) small JavaFX GUI with a TextField to enter the number of value to add, a TextField to enter a maximum number of thread, a button to launch the benchmark, a progress bar to see progression of the process and line chart to display the result. The process of evaluation is as follows: for a maximum number of 4 threads and 1000 words added in the BST, 1000 words are added with 1 thread. Then 1000 words are added with 2 threads, 3 threads and so on. The line chart represents the average addition time according to the number of thread. Don't write any logic to compute the average, Int/LongStream provides average method of reduction.
 */

/**
 * @author Arthur Mauvezin 1) Implement a BinarySearchTree of String. 5)
 *         Implement an ExecutorService (CompletionService ?) allowing to submit
 *         BSTAdder for each randomly generated word.
 *
 */
public class Main {
	
	private Stage primaryStage;

	public void start(Stage primaryStage) {
        try {
            StackPane page = (StackPane) FXMLLoader.load(Main.class.getResource("view.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Java Advanced");
            primaryStage.show();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	public static final void main(String[] args) throws IOException {

		BinarySearchTree<String> rbtree = new BinarySearchTree<>();

		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<Future<Duration>> list = new ArrayList<Future<Duration>>();
		Callable<Duration> callable = new BSTAdder(50, rbtree);

		for (int i = 0; i < 60; i++) {
			Future<Duration> future = executor.submit(callable);
			list.add(future);
		}
		for (Future<Duration> fut : list) {
			try {
				System.out.println(new Date() + "::" + fut.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		executor.shutdown();

		String name = "rbtree";
		PrintWriter writer = new PrintWriter(name + ".dot");
		writer.println(rbtree.toDOT(name));
		writer.close();
		ProcessBuilder builder = new ProcessBuilder("dot", "-Tpdf", "-o", name
				+ ".pdf", name + ".dot");
		builder.start();
	}
}
