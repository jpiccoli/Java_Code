package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		if( head == null )
		{
			LLNode<E> new_node = new LLNode<E>(element);
			head = new_node;
			size++;
			return true;
		}
		
		LLNode<E> iter = head;
		while( iter.next != null )
		{
			iter = iter.next;
		}
		
		// iter is now existing tail
		LLNode<E> new_node = new LLNode<E>(element);
		iter.next = new_node;
		new_node.prev = iter;
		tail = new_node;
		size++;
		
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		if( index + 1 > size || index < 0 )
		{
			throw new IndexOutOfBoundsException();
		}
		
		// TODO: Implement this method.
		int idx = 0;
		LLNode<E> iter = head;
		while( iter.next != null && idx != index )
		{
			iter = iter.next;
			idx++;
		}
		
		return iter.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		LLNode<E> new_node = new LLNode<E>( element );

//		if( index == 0 )
//		{
//			// Add new head
//			if( head == null )
//			{
//				// Empty list
//				head = new_node;
//				size++;
//				return;
//			}
//			else
//			{
//				new_node.next = head;
//				head.prev = new_node;
//				head = new_node;
//				size++;
//				return;
//			}
//		}
		
		if( head == null )
		{
			// Empty list
			head = new_node;
		}
		else
		{
			int idx = 0;
			LLNode<E> iter = head;
			while( iter.next != null && idx != index )
			{
				iter = iter.next;
				idx++;
			}
		
			LLNode<E> current_node = iter;
			new_node.next = current_node;
			new_node.prev = current_node.prev;
			current_node.prev.next = new_node;
			current_node.prev = new_node;
		
			if( iter == head )
			{
				head = new_node;
			}
		}
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if( index + 1 > size || index < 0 )
		{
			throw new IndexOutOfBoundsException();
		}
	
		if( index == 0 )
		{
			if( size == 1 )
			{
				// Removing the head
				E element = head.data;
				head = null;
				size--;
				return element;
			}
			
			LLNode<E> new_head = head.next;
			new_head.prev = null;
			E element = head.data;
			head = new_head;
			size--;
			return element;
		}
		
		
		int idx = 0;
		LLNode<E> iter = head;
		while( iter.next != null && idx != index )
		{
			iter = iter.next;
			idx++;
		}
		
		LLNode<E> prev = iter.prev;
		LLNode<E> next = iter.next;
		prev.next = iter.next;
		next.prev = iter.prev;
		size--;
		
		return null;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if( index + 1 > size )
		{
			throw new IndexOutOfBoundsException();
		}
		
		int idx = 0;
		LLNode<E> iter = head;
		while( iter.next != null && idx != index )
		{
			iter = iter.next;
			idx++;
		}

		iter.data = element;
	
		return null;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
