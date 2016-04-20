package com.goatdev;

import com.goatdev.util.HashQueue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for HashQueue.
 * Created by ran on 4/20/16.
 */
public class HashQueueTest {

    HashQueue<Integer, String> queue;

    @Before
    public void init() {
        queue = new HashQueue<>();
    }

    @Test
    public void sizeTest() {
        queue.enqueue(0, "Hello");
        assertEquals(queue.getSize(), 1);
        assertEquals(queue.dequeue(), "Hello");
        assertEquals(queue.getSize(), 0);
    }

    @Test
    public void fifoTest() {
        queue.enqueue(1, "asdf");
        queue.enqueue(2, "bsdf");
        queue.enqueue(3, "csdf");

        assertEquals(queue.dequeue(), "asdf");
        assertEquals(queue.dequeue(), "bsdf");
        assertEquals(queue.dequeue(), "csdf");
    }

    @Test
    public void arbitraryRemovalTest() {
        queue.enqueue(1, "asdf");
        queue.enqueue(2, "bsdf");
        queue.enqueue(3, "csdf");
        assertEquals(queue.remove(2), "bsdf");
        assertEquals(queue.dequeue(), "asdf");
        assertEquals(queue.dequeue(), "csdf");

        queue.enqueue(1, "asdf");
        queue.enqueue(2, "bsdf");
        queue.enqueue(3, "csdf");
        assertEquals(queue.remove(1), "asdf");
        assertEquals(queue.dequeue(), "bsdf");
        assertEquals(queue.dequeue(), "csdf");

        queue.enqueue(1, "asdf");
        queue.enqueue(2, "bsdf");
        queue.enqueue(3, "csdf");
        assertEquals(queue.remove(3), "csdf");
        assertEquals(queue.dequeue(), "asdf");
        assertEquals(queue.dequeue(), "bsdf");

        assertEquals(queue.getSize(), 0);

    }
}
