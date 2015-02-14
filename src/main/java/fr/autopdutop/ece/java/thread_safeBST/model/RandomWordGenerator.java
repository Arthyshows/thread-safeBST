package fr.autopdutop.ece.java.thread_safeBST.model;

import java.util.Iterator;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Clément Aceituno 2) Write a RandomWordGenerator class which
 *         implements Iterable. The constructor of this class takes an integer
 *         parameter corresponding to the number of words to randomly generate.
 *
 */
public class RandomWordGenerator implements Iterable<String> {
	protected static final int MAX = 10;
	protected static final int MIN = 2;
	private final int bound;
	private final Random rand = new Random();

	public RandomWordGenerator(int bound) {
		this.bound = bound;
	}

	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {

			private int counter = 0;

			@Override
			public boolean hasNext() {
				return counter < bound ? true : false;
			}

			@Override
			public String next() {
				counter++;
				return rand.ints(rand.nextInt(MAX - MIN + 1) + MIN)
						.map(i -> Math.abs(i) % 26 + 'a')
						.mapToObj(i -> new String(Character.toChars(i)))
						.collect(Collectors.joining());
			}
		};
	}
}
