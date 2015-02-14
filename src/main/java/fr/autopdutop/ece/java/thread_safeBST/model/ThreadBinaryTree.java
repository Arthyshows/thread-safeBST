package fr.autopdutop.ece.java.thread_safeBST.model;

import java.util.concurrent.Callable;

import fr.autopdutop.ece.java.thread_safeBST.BinarySearchTree;

public class ThreadBinaryTree implements Callable<Boolean> {
	
	private BinarySearchTree<E> rbtree;
	private E e;	
	
	public ThreadBinaryTree(BinarySearchTree<E> rbtree, E e) {
		this.rbtree = rbtree;
		this.e = e;
	}

	@Override
	public void run() {
		System.out.println(e.toString());
		// TODO Auto-generated method stub
		try{
			rbtree.add(e);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public ThreadBinaryTree call() throws Exception {
		// TODO Auto-generated method stub
		rbtree.add(e);
		return null;
	}

}
