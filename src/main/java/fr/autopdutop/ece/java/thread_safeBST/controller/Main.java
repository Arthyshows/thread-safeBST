package fr.autopdutop.ece.java.thread_safeBST.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.autopdutop.ece.java.thread_safeBST.BinarySearchTree;
import fr.autopdutop.ece.java.thread_safeBST.model.ThreadBinaryTree;

public class Main {
	public static final void main(String[] args) throws IOException {
		String name = "rbtree";
		BinarySearchTree<Integer> rbtree = new BinarySearchTree<>();
		
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
		
		/*
		rbtree.add(10);
		rbtree.add(15);
		rbtree.add(8);
		rbtree.add(12);
		rbtree.add(4);
		rbtree.add(9);
		*/
		
		PrintWriter writer = new PrintWriter(name + ".dot");
		writer.println(rbtree.toDOT(name));
		writer.close();
		ProcessBuilder builder = new ProcessBuilder("dot", "-Tpdf", "-o", name
				+ ".pdf", name + ".dot");
		builder.start();
	}
}
