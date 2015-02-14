package fr.autopdutop.ece.java.thread_safeBST.model;

import java.util.Iterator;
import java.util.Random;

public class RandomGenerator implements Iterable<Integer> {
	private final int bound ;
	private final Random rand = new Random ();
	
	public RandomGenerator ( int bound ) {
	this . bound = bound ;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator < Integer >() {
			
			private int counter = 0;
			@Override
			public boolean hasNext () {
			return counter < bound ? true : false ;
			}
			
			@Override
			public Integer next () {
			counter ++;
			return rand . nextInt ();
			}
		};
}
	

}
