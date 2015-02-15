package fr.autopdutop.ece.java.thread_safeBST.controller;

import java.io.IOException;
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

/**
 * @author Arthur Mauvezin Allow to launch different benchmark
 *
 */
public class Benchmark {

	public static double launch(int nbThread, int nbWord) throws IOException {
		double sum = 0;

		BinarySearchTree<String> rbtree = new BinarySearchTree<>();

		ExecutorService executor = Executors.newFixedThreadPool(nbThread);
		List<Future<Duration>> list = new ArrayList<Future<Duration>>();
		Callable<Duration> callable = new BSTAdder(nbWord, rbtree);

		for (int i = 0; i < nbWord; i++) {
			try {
				Future<Duration> future = executor.submit(callable);
				sum += future.get().toNanos();
				// System.out.println(future.get().toNanos());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Future<Duration> fut : list) {
			try {
				System.out.println(new Date() + "::" + fut.get());

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		executor.shutdown();

		return sum / nbThread;
	}
}
