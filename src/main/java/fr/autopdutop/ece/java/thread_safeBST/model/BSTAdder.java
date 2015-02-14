package fr.autopdutop.ece.java.thread_safeBST.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import java.time.Duration;

//Write a BSTAdder class which implements Callable of Duration. 
//The call method of this class adds a random word in the binary search tree and measures the duration of the operation (System.nanoTime()).

public class BSTAdder implements Callable<Duration> {

	private RandomWordGenerator gen;

	@Override
	public Duration call() throws Exception {
		Instant t1 = Instant.now();
		Thread.sleep(1000);
		// Generator execution here
		// this.gen.

		// Add node
		Instant t2 = Instant.now();
		// return the thread name executing this callable task
		// return Thread.currentThread().getName();
		return Duration.between(t1, t2);
	}

	public BSTAdder() {
		this.gen = new RandomWordGenerator(500);
	}

	public static void main(String args[]) {
		// Get ExecutorService from Executors utility class, thread pool size is
		// 10
		ExecutorService executor = Executors.newFixedThreadPool(10);
		// create a list to hold the Future object associated with Callable
		List<Future<Duration>> list = new ArrayList<Future<Duration>>();
		// Create MyCallable instance
		Callable<Duration> callable = new BSTAdder();
		for (int i = 0; i < 100; i++) {
			// submit Callable tasks to be executed by thread pool
			Future<Duration> future = executor.submit(callable);
			// add Future to the list, we can get return value using Future
			list.add(future);
		}
		for (Future<Duration> fut : list) {
			try {
				// print the return value of Future, notice the output delay in
				// console
				// because Future.get() waits for task to get completed
				System.out.println(new Date() + "::" + fut.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		// shut down the executor service now
		executor.shutdown();
	}

}
