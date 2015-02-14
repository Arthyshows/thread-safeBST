package fr.autopdutop.ece.java.thread_safeBST.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import fr.autopdutop.ece.java.thread_safeBST.model.*;

/*
1) Implement a BinarySearchTree of String.
2) Write a RandomWordGenerator class which implements Iterable. The constructor of this class takes an integer parameter corresponding to the number of words to randomly generate.
3) Write a BSTAdder class which implements Callable of Duration. The call method of this class adds a random word in the binary search tree and measures the duration of the operation (System.nanoTime()).
4) Implement the synchronization mechanisms in the BinarySearchTree (see Lesson 3) to ensure the consistency of the tree during adding. (A voir pour un plus petit sync)
5) Implement an ExecutorService (CompletionService ?) allowing to submit BSTAdder for each randomly generated word.
6) Implement a method which tests if a BinarySearchTree object respects the BST properties. The aim of this method is to validate the consistency of your multi-threaded addition process.
7) Final step: write a (very) small JavaFX GUI with a TextField to enter the number of value to add, a TextField to enter a maximum number of thread, a button to launch the benchmark, a progress bar to see progression of the process and line chart to display the result. The process of evaluation is as follows: for a maximum number of 4 threads and 1000 words added in the BST, 1000 words are added with 1 thread. Then 1000 words are added with 2 threads, 3 threads and so on. The line chart represents the average addition time according to the number of thread. Don't write any logic to compute the average, Int/LongStream provides average method of reduction.
*/
public class Main {
	public static final void main(String[] args) throws IOException {
		
		BinarySearchTree<String> rbtree = new BinarySearchTree<>();
		
		RandomWordGenerator gen = new RandomWordGenerator (10);
	    
	    for (String randWord : gen ) {
	    	System.out.println(randWord);
	    	rbtree.add(randWord);
	    }
		
		//5) Implement an ExecutorService (CompletionService ?) allowing to submit BSTAdder for each randomly generated word.
		
		//Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(10);
        //create a list to hold the Future object associated with Callable
        List<Future<Duration>> list = new ArrayList<Future<Duration>>();
        //Create MyCallable instance
        Callable<Duration> callable = new BSTAdder();
        for(int i=0; i< 100; i++){
            //submit Callable tasks to be executed by thread pool
            Future<Duration> future = executor.submit(callable);
            //add Future to the list, we can get return value using Future
            list.add(future);
        }
        for(Future<Duration> fut : list){
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                System.out.println(new Date()+ "::"+fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service now
        executor.shutdown();
        
        
		
		/*
		ExecutorService executorService = Executors.newFixedThreadPool(10);		
		int[] table = new int[6];
		table[0]=10;
		table[1]=15;
		table[2]=8;
		table[3]=12;
		table[4]=4;
		table[5]=9;		
		for (int i=0; i<6; i++){
			//System.out.println(table[i]);
			executorService.execute(new ThreadBinaryTree<Integer>(rbtree, table[i]));
			//new Thread(new ThreadBinaryTree<Integer>(rbtree, table[i]));
		}	
		executorService.shutdown();
		*/
		
		/*
		rbtree.add(10);
		rbtree.add(15);
		rbtree.add(8);
		rbtree.add(12);
		rbtree.add(4);
		rbtree.add(9);
		*/
		/*
		 * String name = "rbtree";
		PrintWriter writer = new PrintWriter(name + ".dot");
		writer.println(rbtree.toDOT(name));
		writer.close();
		ProcessBuilder builder = new ProcessBuilder("dot", "-Tpdf", "-o", name
				+ ".pdf", name + ".dot");
		builder.start();
		*/
	}
}
