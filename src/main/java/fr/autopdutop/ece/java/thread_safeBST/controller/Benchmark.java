package fr.autopdutop.ece.java.thread_safeBST.controller;

import java.io.IOException;
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

	public static long launch(int nbThread, int nbWord) throws IOException {
		long sum = 0;

		BinarySearchTree<String> rbtree = new BinarySearchTree<>();

		ExecutorService executor = Executors.newFixedThreadPool(nbThread);
		Callable<Long> callable = new BSTAdder(nbWord, rbtree);

		for (int i = 0; i < nbWord; i++) {
			try {
				Future<Long> future = executor.submit(callable);
				sum += future.get();
				//System.out.println(future.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		executor.shutdown();

		return sum / (long)nbThread;
	}
}
