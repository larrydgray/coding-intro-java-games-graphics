package net.sf.sdz.ds.lists;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SingleLinkedListNGTest {

    SingleLinkedList testList1 = new SingleLinkedList();

    public SingleLinkedListNGTest() {

    }

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() {
        testList1 = new SingleLinkedList();
        testList1.insertHead(3);
        testList1.insertHead(2);
        testList1.insertHead(1);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of insertHead method, of class SingleLinkedList.
     */
    @Test
    public void testInsertHead() {
        System.out.println("insertHead");
        testList1 = new SingleLinkedList();
        // new list insert
        testList1.insertHead(3);
        assertEquals(testList1.size(), 1);
        assertEquals(testList1.get(), 3);
        assertEquals(testList1.head(), 3);
        assertEquals(testList1.tail(), 3);
        // 2nd item list insert
        testList1.insertHead(2);
        assertEquals(testList1.size(), 2);
        assertEquals(testList1.get(), 2);
        assertEquals(testList1.head(), 2);
        assertEquals(testList1.tail(), 3);
        // 3rd item list insert
        testList1.insertHead(1);
        assertEquals(testList1.size(), 3);
        assertEquals(testList1.get(), 1);
        assertEquals(testList1.head(), 1);
        assertEquals(testList1.tail(), 3);
    } // testInsertHead

    /**
     * Test of insertTail method, of class SingleLinkedList.
     */
    @Test
    public void testInsertTail() {
        System.out.println("insertTail");
        testList1 = new SingleLinkedList();
        // new list insert
        testList1.insertTail(1);
        assertEquals(testList1.size(), 1);
        assertEquals(testList1.get(), 1);
        assertEquals(testList1.head(), 1);
        assertEquals(testList1.tail(), 1);
        // 2nd item list insert
        testList1.insertTail(2);
        assertEquals(testList1.size(), 2);
        assertEquals(testList1.get(), 1);
        assertEquals(testList1.head(), 1);
        assertEquals(testList1.tail(), 2);
        // 3rd item list insert
        testList1.insertTail(3);
        assertEquals(testList1.size(), 3);
        assertEquals(testList1.get(), 1);
        assertEquals(testList1.head(), 1);
        assertEquals(testList1.tail(), 3);

    } // testInsertTail

    /**
     * Test of removeHead method, of class SingleLinkedList.
     */
    @Test
    public void testRemoveHead() {
        System.out.println("removeHead");
        setUpMethod();
        // test with more than 2 in list
        Integer theInt;
        theInt = (Integer) testList1.removeHead();
        assertEquals(theInt.intValue(), 1);
        assertEquals(((Integer) testList1.head()).intValue(), 2);
        // test with 2 in list
        theInt = (Integer) testList1.removeHead();
        assertEquals(theInt.intValue(), 2);
        assertEquals(((Integer) testList1.head()).intValue(), 3);
        assertEquals(((Integer) testList1.tail()).intValue(), 3);
        assertEquals(((Integer) testList1.get()).intValue(), 3);
        // test with 1 in list
        theInt = (Integer) testList1.removeHead();
        assertEquals(theInt.intValue(), 3);
        assertNull(testList1.head());
        assertNull(testList1.tail());
        assertNull(testList1.get());
        assertEquals(testList1.size(), 0);
        // empty list

        theInt = (Integer) testList1.removeHead();
        assertNull(theInt);
    } // testRemoveHead

    /**
     * Test of removeTail method, of class SingleLinkedList.
     */
    @Test
    public void testRemoveTail() {
        System.out.println("removeTail");
        setUpMethod();
        // test with more than 2 in list
        Integer theInt;
        theInt = (Integer) testList1.removeTail();
        assertEquals(theInt.intValue(), 3);
        assertEquals(((Integer) testList1.tail()).intValue(), 2);
        // test with 2 in list
        theInt = (Integer) testList1.removeTail();
        assertEquals(theInt.intValue(), 2);
        assertEquals(((Integer) testList1.head()).intValue(), 1);
        assertEquals(((Integer) testList1.tail()).intValue(), 1);
        assertEquals(((Integer) testList1.get()).intValue(), 1);
        // test with 1 in list
        theInt = (Integer) testList1.removeHead();
        assertEquals(theInt.intValue(), 1);
        assertNull(testList1.head());
        assertNull(testList1.tail());
        assertNull(testList1.get());
        assertEquals(testList1.size(), 0);
        // empty list
        theInt = (Integer) testList1.removeHead();
        assertNull(theInt);
    }

    /**
     * Test of reset method, of class SingleLinkedList.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        SingleLinkedList testList1 = new SingleLinkedList();
        // test empty
        testList1.reset();
        // insert 3
        testList1.insertHead(3);
        testList1.insertHead(2);
        testList1.insertHead(1);

        testList1.next();
        testList1.next();

        testList1.reset();
        assertEquals(((Integer) testList1.head()).intValue(), 1);
        assertNotNull(testList1.head());
        assertNotNull(testList1.tail());
    } // testReset

    /**
     * Test of next method, of class SingleLinkedList.
     */
    @Test
    public void testNext() {
        System.out.println("next");
        SingleLinkedList testList1 = new SingleLinkedList();
        // empty
        testList1.next();
        // 1 item in list
        testList1.insertHead(3);
        testList1.reset();
        testList1.next();
        assertEquals(testList1.get(), null);
        // 2 items in list
        testList1.insertHead(2);
        testList1.insertHead(1);
        boolean exception = false;

        assertNotNull(testList1.head());
        testList1.next();
        assertEquals(((Integer) testList1.get()).intValue(), 2);
        testList1.next();
        assertEquals(((Integer) testList1.get()).intValue(), 3);
        testList1.next();
        assertNull(testList1.get());

    } // testNext

    /**
     * Test nextElementMethod() for front of list, mid list and end list.
     */
    @Test
    public void testNextElement() {
        System.out.println("nextElement");
        SingleLinkedList testList1 = new SingleLinkedList();
        boolean notEOL = false;
        // empty
        notEOL = testList1.nextElement();
        assertFalse(notEOL);
        // 1 item
        notEOL = false;
        testList1.insertHead(3);
        testList1.reset();
        notEOL = testList1.nextElement();
        assertFalse(notEOL);

        // 2 items
        testList1.insertHead(2);
        testList1.insertHead(1);
        testList1.reset();
        testList1.nextElement();
        // 3 items
        notEOL = false;
        testList1.reset();
        assertNotNull(testList1.head());
        notEOL = testList1.nextElement();
        assertTrue(notEOL);
        assertEquals(((Integer) testList1.get()).intValue(), 2);
        notEOL = testList1.nextElement();
        assertTrue(notEOL);
        assertEquals(((Integer) testList1.get()).intValue(), 3);
        notEOL = testList1.nextElement();
        assertFalse(notEOL);
        assertNull(testList1.get());
    } // testNextElement

    /**
     * Test of insertAfter method, of class SingleLinkedList.
     */
    @Test
    public void testInsertAfter() {
        System.out.println("insertAfter");
        SingleLinkedList testList1 = new SingleLinkedList();
        // empty list
        testList1.insertAfter(4);
        assertEquals(((Integer) testList1.get()).intValue(), 4);
        assertEquals(((Integer) testList1.head()).intValue(), 4);
        assertEquals(((Integer) testList1.tail()).intValue(), 4);
        //
        testList1.remove();
        // one item list
        testList1.insertHead(3);
        testList1.insertAfter(4);
        assertEquals(testList1.tail(), 4);
        testList1.removeTail();
        // two item list
        testList1.insertHead(2);
        testList1.insertAfter(4);
        assertEquals(testList1.tail(), 3);
        testList1.next();
        assertEquals(testList1.get(), 4);
        // three item list
        testList1.insertHead(1);
        // insert after 2 the 2nd item in list
        assertEquals(testList1.head(), 1);
        testList1.next();
        testList1.insertAfter(4);
        testList1.next();
        assertEquals(((Integer) testList1.get()).intValue(), 4);

    } // testInsertAfter

    /**
     * Test of insertBefore method, of class SingleLinkedList.
     */
    @Test
    public void testInsertBefore() {
        System.out.println("insertBefore");
        SingleLinkedList testList1 = new SingleLinkedList();
        // empty list
        testList1.insertBefore(4);
        assertEquals(((Integer) testList1.get()).intValue(), 4);
        assertEquals(((Integer) testList1.head()).intValue(), 4);
        assertEquals(((Integer) testList1.tail()).intValue(), 4);
        testList1.remove();
        // one item list
        testList1.insertHead(3);
        testList1.insertBefore(4);
        assertEquals(testList1.head(), 4);
        testList1.removeHead();
        // two item list
        testList1.reset();
        testList1.insertHead(2);
        testList1.next();
        testList1.insertBefore(4);
        assertEquals(testList1.tail(), 3);
        testList1.reset();
        testList1.next();
        assertEquals(testList1.get(), 4);
        // three item list
        testList1.insertHead(1);
        assertNotNull(testList1.head());
        testList1.reset();
        assertEquals(testList1.head(), 1);

        testList1.next();
        org.testng.Assert.assertNotNull(testList1.head());
        testList1.insertBefore(4);
        //org.testng.Assert.assertNull(testList1.head());
        testList1.reset();
        testList1.next();
        assertEquals(((Integer) testList1.get()).intValue(), 4);

    } // testInsertBefore

    /**
     * Test of delete method, of class SingleLinkedList.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        SingleLinkedList testList1 = new SingleLinkedList();
        // empty
        testList1.delete();
        // with 3 items
        testList1.insertHead(3);
        testList1.insertHead(2);
        testList1.insertHead(1);

        // delete from middle
        testList1.next();
        testList1.delete();
        assertEquals(((Integer) testList1.get()).intValue(), 3);
        // delete from head
        testList1.reset();
        testList1.delete();
        assertEquals(testList1.size(), 1);
        assertEquals(((Integer) testList1.head()).intValue(), 3);
        // delete last one
        testList1.delete();
        assertNull(testList1.head());
        assertNull(testList1.tail());

        // regression: delete from head of a 3+ element list must land position on
        // the new head, not skip past it (only reproduces when new head != tail,
        // which the earlier 2-element head-delete case above doesn't cover)
        testList1 = new SingleLinkedList();
        testList1.insertHead(3);
        testList1.insertHead(2);
        testList1.insertHead(1);
        testList1.reset();
        testList1.delete();
        assertEquals(((Integer) testList1.head()).intValue(), 2);
        assertEquals(((Integer) testList1.get()).intValue(), 2);
        assertEquals(testList1.size(), 2);

        // test deleting tail
        testList1 = new SingleLinkedList();
        testList1.insertHead(3);
        testList1.insertHead(2);
        testList1.insertHead(1);

        testList1.reset();
        testList1.next();
        testList1.next();
        testList1.delete();
        assertEquals(((Integer) testList1.tail()).intValue(), 2);
        assertEquals(testList1.size(), 2);
    } // testDelete

    /**
     * Test of remove method, of class SingleLinkedList.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        // empty list
        testList1 = new SingleLinkedList();
        testList1.remove();
        assertNull(testList1.head());
        assertNull(testList1.tail());
        assertNull(testList1.get());
        setUpMethod();
        // remove from middle
        testList1.next();
        Integer i2 = (Integer) testList1.remove();
        assertEquals(i2.intValue(), 2);
        assertEquals(((Integer) testList1.get()).intValue(), 3);
        // remove from front
        setUpMethod();
        i2 = (Integer) testList1.remove();
        assertEquals(i2.intValue(), 1);
        assertEquals(testList1.size(), 2);
        assertEquals(((Integer) testList1.get()).intValue(), 2);
        // remove from end
        setUpMethod();
        testList1.next();
        testList1.next();
        i2 = (Integer) testList1.remove();
        assertEquals(i2.intValue(), 3);
        assertEquals(((Integer) testList1.get()).intValue(), 2);

        // remove last remaining
        setUpMethod();
        testList1.next();
        testList1.next();
        i2 = (Integer) testList1.remove();
        assertEquals(i2.intValue(), 3);
        i2 = (Integer) testList1.remove();
        assertEquals(i2.intValue(), 2);
        i2 = (Integer) testList1.remove();
        assertEquals(i2.intValue(), 1);
        i2 = (Integer) testList1.remove();
        assertNull(i2);

    }  // testRemove

    /**
     * Test of hasNext method, of class SingleLinkedList.
     */
    @Test
    public void testHasNext() {
        System.out.println("hasNext");
        SingleLinkedList testList1 = new SingleLinkedList();
        // empty list
        assertFalse(testList1.hasNext());
        // one item list
        testList1.reset();
        testList1.insertHead(3);
        assertTrue(testList1.hasNext());
        testList1.next();
        assertFalse(testList1.hasNext());
        // two item list
        testList1.insertHead(2);
        testList1.reset();
        assertTrue(testList1.hasNext());
        testList1.next();
        assertTrue(testList1.hasNext());
        testList1.next();
        assertFalse(testList1.hasNext());
        //  three in list
        testList1.insertHead(1);
        int total = 0;
        while (testList1.hasNext()) {
            total += ((Integer) testList1.next());
            System.out.println("hasNext total:" + total);
        } // while
        assertEquals(total, 6);
    } //testHasNext

    /**
     * Test of size method, of class SingleLinkedList.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        SingleLinkedList testList1 = new SingleLinkedList();
        assertEquals(testList1.size(), 0);
        testList1.insertHead(3);
        assertEquals(testList1.size(), 1);
        testList1.insertHead(2);
        assertEquals(testList1.size(), 2);
        testList1.insertHead(1);
        assertEquals(testList1.size(), 3);
    } // testSize

    /**
     * Test of empty method, of class SingleLinkedList.
     */
    @Test
    public void testEmpty() {
        System.out.println("empty");
        // test genearl case with remove()
        setUpMethod();
        testList1.remove();
        assertEquals(testList1.size(), 2);
        testList1.remove();
        assertEquals(testList1.size(), 1);
        testList1.remove();
        assertEquals(testList1.size(), 0);
        testList1.insertHead(new Integer(3));
        assertEquals(testList1.empty(), false);
        testList1.remove();
        assertTrue(testList1.empty());
        assertEquals(testList1.size(), 0);
        // test delte();
        setUpMethod();
        testList1.delete();
        assertEquals(testList1.size(), 2);
        testList1.delete();
        assertEquals(testList1.size(), 1);
        testList1.delete();
        assertEquals(testList1.size(), 0);
        testList1.insertHead(new Integer(3));
        assertEquals(testList1.empty(), false);
        testList1.delete();
        assertTrue(testList1.empty());
        assertEquals(testList1.size(), 0);
        // test removeHead();
        testList1 = new SingleLinkedList();
        testList1.insertHead(1);
        assertEquals(testList1.size(), 1);
        assertEquals(testList1.get(), 1);
        testList1.removeHead();
        assertEquals(testList1.size(), 0);
        assertNull(testList1.get());
        assertTrue(testList1.empty());

        // test removeTail();
        testList1 = new SingleLinkedList();
        testList1.insertTail(1);
        assertEquals(testList1.size(), 1);
        assertEquals(testList1.get(), 1);
        testList1.removeTail();
        assertEquals(testList1.size(), 0);
        assertNull(testList1.get());
        assertTrue(testList1.empty());
    } // testEmpty

    /**
     * Test of head method, of class SingleLinkedList.
     */
    @Test
    public void testHead() {
        System.out.println("head");
        SingleLinkedList testList1 = new SingleLinkedList();
        testList1.insertHead(3);
        testList1.insertHead(2);
        testList1.insertHead(1);
        assertEquals(testList1.head(), 1);
        // empty list
        testList1.remove();
        testList1.remove();
        testList1.remove();
        assertNull(testList1.head());
    } // testHead

    /**
     * Test of tail method, of class SingleLinkedList.
     */
    @Test
    public void testTail() {
        System.out.println("tail");
        SingleLinkedList testList1 = new SingleLinkedList();
        testList1.insertHead(3);
        testList1.insertHead(2);
        testList1.insertHead(1);
        assertEquals(testList1.tail(), 3);
        // empty list
        testList1.remove();
        testList1.remove();
        testList1.remove();
        assertNull(testList1.tail());
    } // testTail

    /**
     * Test of get method, of class SingleLinkedList.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        SingleLinkedList testList1 = new SingleLinkedList();
        // empty
        assertNull(testList1.get());
        // one item
        testList1.insertHead(3);
        assertEquals(testList1.get(), 3);
        // more than one item
        testList1.insertHead(2);
        testList1.insertHead(1);
        testList1.next();
        assertEquals(testList1.get(), 2);

    } // testGet

    /**
     * Test of set method, of class SingleLinkedList.
     */
    @Test
    public void testSet() {
        System.out.println("set");
        SingleLinkedList testList1 = new SingleLinkedList();
        // empty
        testList1.set(1);
        assertEquals(testList1.head(), 1);
        assertEquals(testList1.size(), 1);
        testList1.delete();
        // one element
        testList1.insertHead(3);
        testList1.set(5);
        assertEquals(testList1.head(), 5);
        assertEquals(testList1.size(), 1);
        // more than one
        testList1.insertHead(2);
        testList1.insertHead(1);

        assertEquals(testList1.get(), 1);
        testList1.next();
        assertEquals(testList1.get(), 2);
        testList1.set(4);
        assertEquals(testList1.get(), 4);

    } // testSet

} // SingleLinkedListNGTest
