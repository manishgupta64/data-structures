public class LinkedListPractice {

	public static void main(String[] args) {
		LinkedList ll = new LinkedList();
		
		// 1) Insert at the begining of the linked list
		ll.insert(2);
		ll.insert(5);
		ll.insert(3);
		ll.insert(1);
		
		System.out.println("\nAfter addign elements at the begining");
		ll.print();
		
		// 2) Insert at the end of the linked lsit
		ll.insertAtLast(6);
		ll.insertAtLast(9);
		System.out.println("\nAfter inserting 6 and then 9 at the end of linked list");
		ll.print();
		
		// 3) Insert element after given element; For ex: insert 4 after 3, if 3 is not in the list, it will return false and will not insert 4
		ll.insertAfter(4, 3);
		System.out.println("\nLinked list when 4 is adding after 3");
		ll.print();

		ll.insertAfter(13, 12);
		System.out.println("\nLinked list when 13 is adding after 12");
		ll.print();
		
		// 4) Remove the given element from the linked list
		ll.remove(5);
		System.out.println("\nAfter removing element 5");
		ll.print();

		// 5) Remove the midlle element from the linked list
		ll.removeMiddleElement();
		System.out.println("\nAfter remove middle element");
		ll.print();
		
		// 6) Get and print the size of the linked list
		System.out.println("\nSize  of linked lis is " + ll.size() );		
		
		// 7) Reverse the linked list
		ll.reverse();
		System.out.println("\nReversed linked list");
		ll.print();
		
		// 8) Reset/delete the linked list
		ll.reset();
		System.out.println("\nAfter linked list is deleted");
		ll.print();
	}
}

class LinkedList
{
	Node head;
	Node tail;	// for storing the element at the end
	int count; // for storing the number of elements of the linked list
	
	private static class Node
	{
		int data;
		Node next;
	
		Node( int data )
		{
			this.data = data;
			this.next = null;
		}
	}
	
	public boolean insert( int data )
	{
		Node newNode = new Node( data );
		if( this.head != null )
		{
			if( ( this.head.next == null && this.tail == null ) )
			{
				this.tail = this.head;				
			}
		}
		
		newNode.next = this.head;
		this.head = newNode;
		if( this.head != null && this.head.next == null )
		{
			this.tail = this.head;
		}
		++this.count;
		return true;
	}
	
	public boolean insertAtLast( int data )
	{
		boolean elemAdded = false;
		if( this.head == null )
		{
			insert( data );
			elemAdded = true;
		}
		else
		{
			Node newNode = new Node( data );
			this.tail.next = newNode;
			this.tail = newNode;
			++this.count;
			elemAdded = true;
		}
		return elemAdded;
	}
	
	public boolean insertAfter( int newData, int afterElement )
	{
		boolean elemAdded = false;
		if( this.head == null )
		{
			return elemAdded;
		}
		Node temp = this.head;
		while( temp != null )
		{
			if( temp.data == afterElement )
			{
				Node newNode = new Node( newData );
				newNode.next = temp.next;
				temp.next = newNode;
				++this.count;
				elemAdded = true;
				break;
			}
			temp = temp.next;
		}
		
		return elemAdded;
	}
	
	public boolean remove( int data )
	{
		Node temp = this.head;
		boolean elemRemoved = false;
		while( temp != null )
		{
			if( temp.data == data )
			{
				if( temp == this.head )
				{
					this.head = temp.next;
					temp.next = null;
					elemRemoved = true;
					break;
				}
			}
			else if( temp.next != null && temp.next.data == data )
			{
				temp.next = temp.next.next;
				elemRemoved = true;
				
				break;
			}
			else
			{
				temp = temp.next;
			}
		}
		if( elemRemoved )
		{
			--this.count;
		}
		return elemRemoved;
	}
	
	public boolean removeMiddleElement()
	{
		boolean elemRemoved = false;
		if( this.head == null || this.head.next == null )
		{
			return elemRemoved;
		}
		Node slowPtr = this.head;
		Node fastPtr = this.head;
		while( fastPtr != null )
		{
			if( fastPtr.next != null )
			{
				fastPtr = fastPtr.next.next;
				if( fastPtr == null || fastPtr.next == null )
				{
					slowPtr.next = slowPtr.next.next;
					elemRemoved = true;
					break;
				}
				else
				{
					slowPtr = slowPtr.next;
				}
			}
		}
		if( elemRemoved )
		{
			--this.count;
		}
		return elemRemoved;
	}
	
	public void print()
	{
		Node temp = this.head;
		while( temp != null )
		{
			System.out.print( temp.data + "->" );
			temp = temp.next;
		}
		System.out.println(" NULL");
	}
	
	public int size()
	{
		return this.count;
	}
	
	public void reset()
	{
		this.head = null;
	}
	
	public void reverse()
	{
		Node current = this.head, prev = null, next = null;
		while( current != null )
		{
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		if( prev != null )
		{
			this.head = prev;			
		}
	}

}
