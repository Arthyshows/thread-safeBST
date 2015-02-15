package fr.autopdutop.ece.java.thread_safeBST.model;

import java.util.concurrent.Callable;

/**
 * @author Arthur Mauvezin 3) Write a BSTAdder class which implements Callable
 *         of Duration. The call method of this class adds a random word in the
 *         binary search tree and measures the duration of the operation
 *         (System.nanoTime()).
 */
public class BSTAdder implements Callable<Long> {

	private RandomWordGenerator gen;
	private BinarySearchTree<String> rbtree;

	@Override
	public Long call() throws Exception {

		if (this.gen.iterator().hasNext()) {
			long startTime = System.nanoTime();
			rbtree.add(this.gen.iterator().next());
			long stopTime = System.nanoTime();
			
			return stopTime - startTime;
		} else {
			throw new Exception("No next word !!");
		}
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
