import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INIT_CAPACITY = 8;
    private Item[] a;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = a[i];
        }
        a = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("you cannot add null");
        if (n == a.length) resize(a.length * 2);
        a[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size() == 0) throw new NoSuchElementException("queue is empty");
        int randomIndex = StdRandom.uniform(n);
        Item temp = a[randomIndex];
        a[randomIndex] = a[n-1];
        a[n-1] = null;
        n--;
        if (n > 0 && n == a.length / 4) resize(a.length / 2);
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size() == 0) throw new NoSuchElementException("queue is empty");
        int randomIndex = StdRandom.uniform(n);
        Item temp = a[randomIndex];
        if (n > 0 && n == a.length / 4) resize(a.length / 2);
        return temp;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int i;
        private final Item[] b;

        public RandomizedQueueIterator() {
            this.b = (Item[]) new Object[n];
            this.i = n-1;
            int counter = 0;
            for (int j = 0; j < a.length; j++) {
                if (a[j] != null) {
                    b[counter++] = a[j];
                }
            }
            StdRandom.shuffle(b);
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return b[i--];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue("Jumbo");
        randomizedQueue.enqueue("Jumbo Bwana");
        randomizedQueue.enqueue("Habari Gwani");
        randomizedQueue.enqueue("Nzuri Sana");

        for (String s : randomizedQueue) {
            System.out.println(s);
        }
        System.out.println("***");
        for (String s : randomizedQueue) {
            System.out.println(s);
        }
        System.out.println("***");
        for (String s : randomizedQueue) {
            System.out.println(s);
        }
    }
}