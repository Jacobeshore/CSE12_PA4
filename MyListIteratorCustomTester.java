import static org.junit.Assert.*;
import org.junit.*;

/*
Name: Jacob Shore
Email: jshore@ucsd.edu
PID: A19291655
Sources used: Textbook, lecture slides

This file contains custom JUnit test cases that I created for MyLinkedList's 
MyListIterator. .
*/
public class MyListIteratorCustomTester {

    /**
     * This sets up the test fixture. JUnit invokes this method before
     * every testXXX method. The @Before tag tells JUnit to run this method
     * before each test.
     */
    @Before
    public void setUp() throws Exception {
    	
    
    
    }

    /**
     * Aims to test the next() method when iterator is at end of the list 
     */
    @Test
    public void testNextEnd() {
    	MyLinkedList<String> list = new MyLinkedList<>();
        list.add("Yo");
        MyLinkedList<String>.MyListIterator iterator = list.new MyListIterator();

        assertEquals("Yo", iterator.next());

        try {
        	iterator.next();
            fail("Calling next() at the end should throw NoSuchElementException");
        } catch (java.util.NoSuchElementException e) {
            // expected
        }
    }

    /**
     * Aims to test the previous() method when iterator is at the start of the 
     * list 
     */
    @Test
    public void testPreviousStart() {
    	MyLinkedList<String> list = new MyLinkedList<>();
        list.add("Hi");
        list.add("Hello");
        MyLinkedList<String>.MyListIterator iterator = list.new MyListIterator();

        try {
        	iterator.previous();
            fail("Calling previous() at the start should throw NoSuchElementException");
        } catch (java.util.NoSuchElementException e) {
            // expected
        }
    }

    /**
     * Aims to test the add(E e) method when an invalid element is added
     */
    @Test
    public void testAddInvalid() {
    	MyLinkedList<String> list = new MyLinkedList<>();
        list.add("Hi");
        MyLinkedList<String>.MyListIterator iterator = list.new MyListIterator();

        try {
        	iterator.add(null);
            fail("add(null) should throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }

    }

    /**
     * Aims to test the set(E e) method when canRemoveOrSet is false
     */
    @Test
    public void testCantSet() {
    	MyLinkedList<String> list = new MyLinkedList<>();
        list.add("Yo");
        MyLinkedList<String>.MyListIterator iterator = list.new MyListIterator();

        try {
        	iterator.set("Whatsup");
            fail("set() before next/previous should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expected
        }

        iterator.add("Bye");
        try {
        	iterator.set("Dog");
            fail("set() right after add() should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expected
        }

    }

    /**
     * Aims to test the remove() method when canRemoveOrSet is false
     */
    @Test
    public void testCantRemove() {
    	MyLinkedList<String> list = new MyLinkedList<>();
        list.add("Hi");
        list.add("Hello");
        MyLinkedList<String>.MyListIterator iterator = list.new MyListIterator();

        try {
        	iterator.remove();
            fail("remove() before next/previous should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expected
        }

        iterator.add("Cat");
        try {
        	iterator.remove();
            fail("remove() right after add() should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // expected
        }

    }

    /**
     * Aims to tests the hasNext() method at the end of a list
     */
    @Test
    public void testHasNextEnd() {
    	MyLinkedList<String> list = new MyLinkedList<>();
        list.add("hi");
        list.add("Hello");
        MyLinkedList<String>.MyListIterator iterator = list.new MyListIterator();

        assertEquals("hi", iterator.next());
        assertEquals("Hello", iterator.next());

        assertFalse(iterator.hasNext());
    }

    /**
     * Aims to test the hasPrevious() method at the start of a list
     */
    @Test
    public void testHasPreviousStart() {
    	MyLinkedList<String> list = new MyLinkedList<>();
        list.add("Yo");
        MyLinkedList<String>.MyListIterator iterator = list.new MyListIterator();
        assertFalse(iterator.hasPrevious());

        MyLinkedList<String> empty = new MyLinkedList<>();
        MyLinkedList<String>.MyListIterator itEmpty = empty.new MyListIterator();
        assertFalse(itEmpty.hasPrevious());

    }

    /**
     * Aims to test the previousIndex() method at the start of a list
     */
    @Test
    public void testPreviousIndexStart() {
    	MyLinkedList<String> list = new MyLinkedList<>();
        list.add("Hi");
        list.add("Hellp");
        MyLinkedList<String>.MyListIterator iterator = list.new MyListIterator();
        assertEquals(-1, iterator.previousIndex());

        MyLinkedList<String> list1 = new MyLinkedList<>();
        list1.add("Yo");
        MyLinkedList<String>.MyListIterator iterator1 = list1.new MyListIterator();
        assertEquals(-1, iterator1.previousIndex());

        MyLinkedList<String> empty = new MyLinkedList<>();
        MyLinkedList<String>.MyListIterator itEmpty = empty.new MyListIterator();
        assertEquals(-1, itEmpty.previousIndex());
    }

    /**
     * Aims to test the nextIndex() method at the end of a list
     */
    @Test
    public void testNextIndexEnd() {
    	MyLinkedList<String> list1 = new MyLinkedList<>();
        list1.add("Yo");
        MyLinkedList<String>.MyListIterator iterator1 = list1.new MyListIterator();
        assertEquals("Yo", iterator1.next());
        assertEquals(1, iterator1.nextIndex());

        MyLinkedList<String> list2 = new MyLinkedList<>();
        list2.add("Hi");
        list2.add("Hellp");
        MyLinkedList<String>.MyListIterator iterator2 = list2.new MyListIterator();
        assertEquals("Hi", iterator2.next());
        assertEquals("Hellp", iterator2.next());
        assertEquals(2, iterator2.nextIndex());

        MyLinkedList<String> empty = new MyLinkedList<>();
        MyLinkedList<String>.MyListIterator itEmpty = empty.new MyListIterator();
        assertEquals(0, itEmpty.nextIndex());
    }
}
