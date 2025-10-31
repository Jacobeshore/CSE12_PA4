import java.util.AbstractList;
import java.util.NoSuchElementException;
import java.util.ListIterator;
import java.util.Iterator;

/*
Name: Jacob Shore
Email: jshore@ucsd.edu
PID: A19291655
Sources used: Textbook and lecture slides

Implements a simplified version of Java's doubly linked using generic types. 
It uses sentinel head and tail nodes, and supports various methods like adding, 
removing, retrieving, and updating elements. Null elements will not be allowed, 
and invalid operations will throw exceptions. This file will also include another 
class, MyListIterator, that implements the ListIterator interface.
*/

public class MyLinkedList<E> extends AbstractList<E> {

    int size;
    Node head;
    Node tail;

    /**
     * A Node class that holds data and references to previous and next Nodes.
     */
    protected class Node {
        E data;
        Node next;
        Node prev;

        /** 
         * Constructor to create singleton Node 
         * @param element Element to add, can be null	
         */
        public Node(E element) {
            // Initialize the instance variables
            this.data = element;
            this.next = null;
            this.prev = null;
        }

        /** 
         * Set the parameter prev as the previous node
         * @param prev new previous node
         */
        public void setPrev(Node prev) {
            this.prev = prev;		
        }

        /** 
         * Set the parameter next as the next node
         * @param next new next node
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /** 
         * Set the parameter element as the node's data
         * @param element new element 
         */
        public void setElement(E element) {
            this.data = element;
        }

        /** 
         * Accessor to get the next Node in the list 
         * @return the next node
         */
        public Node getNext() {
            return this.next;
        }
        /** 
         * Accessor to get the prev Node in the list
         * @return the previous node  
         */
        public Node getPrev() {
            return this.prev;
        } 
        /** 
         * Accessor to get the Nodes Element 
         * @return this node's data
         */
        public E getElement() {
            return this.data;
        } 
    }

    /**
     * Constructor that creates an empty doubly linked list and initializes all necessary variables. 
     * The list will use sentinel head and tail nodes, and will start from 0.
     */
    public MyLinkedList() {
        head = new Node(null); //dummy head node
        tail = new Node(null); //dummy tail node
        
        //this links the head and tail nodes together
        head.setNext(tail);
        tail.setPrev(head);
        
        size = 0;
    }

    /**
     * Finds and returns number of elements in the list.
     * @return the size of the list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Return the data/element at a certain index
     * @param index the index of the element
     * @return the data of the element at the given index
     * @throws IndexOutOfBoundsException if index is less than 0 or greater than or equal to size
     */
    @Override
    public E get(int index) {
    	if (index < 0 || index >= size) {
    		throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    	}
    	
    	return getNth(index).getElement();
    }

    /**
     * It will add an element at a specified index in the list, then elements are shifted.
     * @param index this is where the new element should be added
     * @param data the element being added
     * @throws NullPointerException if data is null
     * @throws IndexOutOfBoundsException if Index is less than 0 or greater than size
     */
    @Override
    public void add(int index, E data) {
        if (data == null) {
        	throw new NullPointerException("Data is null.");
        } else if (index < 0 || index > size){
        	throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        //Find the node at the given index
        Node nextNode;
        if (index == size) {
        	//identifies if the next node is the tail when inserting at the end
        	nextNode = tail; 
        } else {
        	//uses the helper method
        	nextNode = getNth(index);
        }
        
        
        Node prevNode = nextNode.getPrev();
        Node newNode = new Node(data);
        
        //Re-connect all the nodes after the addition
        prevNode.setNext(newNode);//connects the previous node to the new node
        newNode.setPrev(prevNode);//connects the new node back to the previous node
        newNode.setNext(nextNode);//connects new node forward to next node
        nextNode.setPrev(newNode);//connects next node back to previous
        
        //update the size of the list
        size++;
    }

    /**
     * It will add the chosen element to the end of the list.
     * @param data the element that needs to be added
     * @return true if the element was added successfully
     * @throws NullPointerException if data is null
     */
    @Override
    public boolean add(E data) {
        if (data == null) {
        	throw new NullPointerException("Data is null.");
        }
        
        Node newNode = new Node(data);
        
        //this gets the node before the sentinel tail node
        Node prevNode = tail.getPrev(); 
        
        
        prevNode.setNext(newNode);//connects previous node to new node
        newNode.setPrev(prevNode);//connects new node back to previous node
        newNode.setNext(tail); // connects new node forward to the tail
        tail.setPrev(newNode); //connects the tail back to the new node 
        
        size++; //update size
        return true;
        
    }

    /**
     * Sets the element for the node at a desire index, and returns old element
     * @param index the index of the node that the element will be set to
     * @param data the element that will be set to the node
     * @return the old element in the node
     * @throws NullPointerException if data is null
     * @throws IndexOutOfBounds if index is less than 0 or greater than / equal to the size 
     */
    @Override
    public E set(int index, E data) {
        if (data == null) {
        	throw new NullPointerException("Data is null.");
        } else if (index < 0 || index >= size) {
        	throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
        }
        
        Node node = getNth(index);//locate node at given index
        E oldData = node.getElement();//this will store old element
        node.setElement(data);//replaces old with new
        return oldData;
        
    }

    /**
     * Remove the node from a chosen index and return data from removed node
     * @param index the index of the node to be removed
     * @return the element in the removed node from the list
     * @throws IndexOutOfBoundsException if index is less than 0 or greater than/equal to the size of the list
     */
    @Override
    public E remove(int index) {
    	if (index < 0 || index >= size) {
    		throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
    	}
    	
        Node node = getNth(index);
        Node prevNode = node.getPrev();
        Node nextNode = node.getNext();
        
        //removes node by connecting the previous and next node
        prevNode.setNext(nextNode);
        nextNode.setPrev(prevNode);
	    // fully detach the removed node to avoid stale links
	    node.setNext(null);
	    node.setPrev(null);
	    
        //reduce size of the list
        size--;
        return node.getElement();
    }

    /**
     * Removes all elements from the list. 
     */
    @Override
    public void clear() {
        //head will point directly to tail
    	head.setNext(tail);
    	//tail will point directly to head
    	tail.setPrev(head);
    	//the  list is empty
    	size=0;
    }
    
    /**
     * checks if list is empty be returning true or false
     * @return true if size is 0
     */
    @Override
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * Returns the node at a specified index in the list
     * @param index the position, starting from 0, of the node to retrieve
     * @return current the node at the given index
     * @throws IndexOutOfBoundsException if index is less than 0 or greater or equal to the size of the array
     */
    protected Node getNth(int index) {
        if (index < 0 || index >= size) {
        	throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size); 
        }
        
        //this will start from the first element following the head node
        Node current = head.getNext();
        
        //this will move forward until desired index is reached
        for(int i = 0; i < index; i++) {
        	current = current.getNext();
        }
        
        return current;
        
    }

    /**
     * Checks to see if a list of indexes contains an element
     * @param data the element being checked
     * @param start the starting index
     * @param end the ending index
     * @return true if element is found and false if it is not found 
     * or if ending index is less than starting index
     * @throws IndexOutOfBoundsException if start is less than 0 
     * or if star is greater than or equal to size
     * or if end is less than 0 / end is greater than size
     */
    public boolean contains(E data, int start, int end) {
        if (data == null || end <= start) {
        	return false;
        } else if (start < 0 || start >= size || end < 0 || end > size) {
        	throw new IndexOutOfBoundsException();
        } 
        
        Node current = getNth(start);//starting point
        
        //traverses until the end of the list to find the element
        for (int i = start; i < end; i++) {
        	if (current.getElement().equals(data)) {
        		return true;//element is found
        	}
        	current = current.getNext(); //go to the next element
        }
        
        return false; //element was not found
        
    }
    /**
     * Returns the index of an elements first occurrence in the list
     * @param data the element to look for
     * @return Index of the the element or -1 if not found
     * @throws NullPointerException if data is null
     */
    public int indexOfElement(E data) {
        if (data == null) {
        	throw new NullPointerException("Data is null.");
        }
        
        Node current = head.getNext();//start from node after head
        
        //traverse through the list and checks the elements
        for (int i = 0; i < size; i++) {
        	if (current.getElement().equals(data)) {
        		
        		return i;//this means it found the element at index i
        	}
        	current = current.getNext(); //move on to next node
        }
        return -1;//element not found
    }
    
    /**
     * This inner class will use an iterator for MyLinkedList.
     * It can traverse in both directions, and can modify the list
     * using methods like add, remove, etc.
     */
    protected class MyListIterator implements ListIterator<E> {
    	
    	/** Instance variables */
    	Node left, right;
    	int idx;
    	boolean forward;
    	boolean canRemoveOrSet;
    
    	/**
    	 * Constructs a new Iterator starting before the first element. 
    	 */
    	public MyListIterator() {
    		left = head; 
            right = head.getNext();
            idx = 0;
            forward = true;
            canRemoveOrSet = false;

    	}
    
    	
        @Override
        public boolean hasNext() {
            return right != tail;
        }
	
        
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No such Element");
            }
            
            E data = right.data;// current node data
            left = right; //
            right = right.getNext();
            idx++;
            forward = true;
            canRemoveOrSet = true;
            return data;
        }
        
        
        @Override
        public boolean hasPrevious() {
            return left != head;
        }
        
        
        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            
            E data = left.getElement();
            right = left;
            left = left.getPrev();
            idx--;
            forward = false;
            canRemoveOrSet = true;
            return data;
        }
        
        @Override
        public int nextIndex() {
            return idx;
        }
        
        @Override
        public int previousIndex() {
            return idx -1 ;
        }
        
        @Override
        public void add(E element) {
            if (element == null) {
                throw new NullPointerException();
            }

            Node newNode = new Node(element);
            newNode.setPrev(left);
            newNode.setNext(right);
            left.setNext(newNode);
            right.setPrev(newNode);

            left = newNode;
            idx++;
            size++;
            canRemoveOrSet = false;
        }
        
        @Override
        public void set(E element) {
            if (!canRemoveOrSet) {
                throw new IllegalStateException();
            }
            if (element == null) {
                throw new NullPointerException();
            }

            if (forward) {
                left.setElement(element); // element  returned by next
            } else {
                right.setElement(element;//element returned by prev
            }
        }
        
        @Override
        public void remove() {
            if (!canRemoveOrSet) {
                throw new IllegalStateException();
            }

            Node toRemove;
            
            if (forward) {
                toRemove = left;
                left = toRemove.getPrev();//uses getter
                
                idx--;
            } else {
                toRemove = right;
                right = toRemove.getNext();
            }

            /** reconnect all the nodes */
            Node prevNode = toRemove.getPrev();
            Node nextNode = toRemove.getnext();
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);
            
            size--;
            canRemoveOrSet = false;
        }
	}
	    
	    @Override
	    public ListIterator<E> listIterator(){
	    	return new MyListIterator();
	    }
	    
	    @Override
	    public Iterator<E> iterator() {
	    	return new MyListIterator();
	    }
    
    
    
}
