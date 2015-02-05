package fr.autopdutop.ece.java.thread_safeBST.tests;

public class SimpleThread {
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new Thread(// Runnable instanced using Lambda expresion
					() -> System.out.println(" I ␣ am ␣ "
							+ Thread.currentThread().getName())).start();
		}
	}
}