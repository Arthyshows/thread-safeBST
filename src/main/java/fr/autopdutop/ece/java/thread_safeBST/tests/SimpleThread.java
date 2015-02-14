package fr.autopdutop.ece.java.thread_safeBST.tests;

public class SimpleThread {
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new Thread(// Runnable instanced using Lambda expresion
					() -> System.out.println(" I ␣ am ␣ "
							+ Thread.currentThread().getName())).start();
		}
		
		//Creation of a deamon
		/*
		Thread thread = new Thread ();
		thead . setDaemon ( true );
		 */
		
		
		//Exemploe of wait of the end of a thread
		/*
		public class JoiningRunnable implements Runnable {
			@Override
			public void run () {
			// Do a lot of very important work
			}
			public static final void main ( String [] args ) {
			Thread thread = new Thread (new JoinableRunnable );
			thread . join ();
			// Do something after the thread finish
			}
			}
*/
	}
}