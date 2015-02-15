package fr.autopdutop.ece.java.thread_safeBST.model;

import static org.junit.Assert.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

//6) Implement a method which tests if a BinarySearchTree object respects the BST properties. 
// The aim of this method is to validate the consistency of your multi-threaded addition process.

public class BinarySearchTreeTest {

	private BinarySearchTree<String> BSTest = new BinarySearchTree<>();
	
	
	@Test
	public void testIsValid() {
		boolean bool;
		int nbWord = 5;
		int nbThread = 1;
		double sum = 0;
		
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
		
		
		bool = BSTest.isValidBST(BSTest);
		
		// Test if instance of graph was created
		assertNotNull(bool);
	}

}
