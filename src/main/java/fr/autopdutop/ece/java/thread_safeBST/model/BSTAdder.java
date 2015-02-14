package fr.autopdutop.ece.java.thread_safeBST.model;

import java.time.Instant;
import java.util.concurrent.Callable;
import java.time.Duration;

/**
 * @author Arthur Mauvezin 3) Write a BSTAdder class which implements Callable
 *         of Duration. The call method of this class adds a random word in the
 *         binary search tree and measures the duration of the operation
 *         (System.nanoTime()).
 */
public class BSTAdder implements Callable<Duration> {

	private RandomWordGenerator gen;
	private BinarySearchTree<String> rbtree;

	@Override
	public Duration call() throws Exception {
		Instant t1 = Instant.now();

		if (this.gen.iterator().hasNext()) {
			// Add node
			rbtree.add(this.gen.iterator().next());
		} else {
			throw new Exception("No next word !!");
		}

		Instant t2 = Instant.now();
		return Duration.between(t1, t2);
	}

	/**
	 * @param n
	 *            Number of word the generator will create.
	 * @param rbtree
	 *            The binary search tree to add node to.
	 */
	public BSTAdder(int n, BinarySearchTree<String> rbtree) {
		this.gen = new RandomWordGenerator(n);
		this.rbtree = rbtree;
	}
}
