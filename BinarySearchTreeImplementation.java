package com.practice.treeNgraphs;

import java.util.ArrayList;
import java.util.List;

/*Tree:

				4
			  /   \
			 2		5
			/ \	     \
		   1   3       6
					    \
					 	  8
						 /
						7	

InOrder:     L  N  R
PreOrder:    N  L  R
Post Order:  L  R  N


*/

public class BinarySearchTreeImplementation 
{
	public static void main( String[] args )
	{
		BinarySeacrhTree bst = new BinarySeacrhTree();
		// {4, 5, 2, 6, 8, 7 }
		
		
		bst.insert(4);
		bst.insert(2);
		bst.insert(3);
		bst.insert(1);
		bst.insert(5);
		bst.insert(6);
		bst.insert(8);
		bst.insert(7);
		System.out.println("\n-------------Inorder Elements------------------");
		List< Integer > elements = bst.inOrderTraversal();
		elements.stream().forEach( System.out::println );
		System.out.println("\n-------------PreOrder Elements-----------------");
		elements = bst.preOrderTraversal();
		elements.stream().forEach( e -> {
			System.out.print( e + " ");
		});
		System.out.println("\n-------------PostOrder Elements-----------------");
		elements = bst.postOrderTraversal();
		elements.stream().forEach( e -> {
			System.out.print( e + " ");
		});
		
		System.out.println("\n-------------Search Elements 5-----------------");
		System.out.println( "Is 5 present in tree? : " + bst.search(5) );
		
		System.out.println("\n-------------Search Elements 10-----------------");
		System.out.println( "Is 10 present in tree? : " +  bst.search(10) );
		
		
		
	}
	

}

class BinarySeacrhTree
{
	Node root;
	
	private class Node
	{
		int data;
		Node left, right;
		
		Node( int data )
		{
			this.data = data;
			this.left = this.right = null;
		}
		
	}
	
	// add a note in BST
	public void insert( int data )
	{
		root = insertNode( root, data );
	}
	
	private Node insertNode( Node node, int data )
	{
		if( node == null )
		{
			node = new Node( data );
			return 	node;
		}
		
		if( data <= node.data )
		{
			node.left = insertNode(node.left, data);
		}
		else
		{
			node.right = insertNode(node.right, data);
		}
		return node;
	}
	
	public List< Integer > inOrderTraversal()
	{
		if( root == null )
		{
			return new ArrayList<>();
		}
		List< Integer > elements = new ArrayList<>();
		inOrderElements(root, elements);
		return elements;
	}
	
	public List< Integer > preOrderTraversal()
	{
		if( root == null )
		{
			return new ArrayList<>();
		}
		List< Integer > elements = new ArrayList<>();
		preOrderElements( root, elements );
		return elements;
	}
	
	public List< Integer > postOrderTraversal()
	{
		if( root == null )
		{
			return new ArrayList<>();
		}
		List< Integer > elements = new ArrayList<>();
		postOrderElements( root, elements );
		return elements;
	}
	
	public boolean search( int data )
	{
		if( root == null )
		{
			return false;
		}
		return searchElement(root, data);
	}
	
	private boolean searchElement( Node node, int data )
	{
		if( node ==  null )
		{
			return false;
		}
		if( node.data == data )
		{
			return true;
		}
		else
		{
			if( data < node.data )
			{
				return searchElement(node.left, data );
			}
			else
			{
				return searchElement(node.right, data );
			}
		}
		
	}
	
	private void postOrderElements( Node node, List< Integer > elements )
	{
		if( node == null )
		{
			return;
		}
		postOrderElements(node.left, elements);
		postOrderElements(node.right, elements);
		elements.add( node.data );
	}
	
	private void preOrderElements( Node node, List< Integer > elements )
	{
		if( node == null )
		{
			return;
		}
		elements.add( node.data );
		preOrderElements(node.left, elements );
		preOrderElements(node.right, elements );
	}
	
	private void inOrderElements( Node node, List< Integer > elements )
	{
		if( node == null )
		{
			return;
		}
		inOrderElements(node.left, elements);
		elements.add( node.data );
		inOrderElements(node.right, elements);
	}
}


