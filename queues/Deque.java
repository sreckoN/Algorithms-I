/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int n;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {
        this.n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("you cannot add null to deque");
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
        } else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            oldfirst.previous = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("you cannot add null to deque");
        if (isEmpty()) {
            addFirst(item);
        } else {
            Node beforeLast = last;
            last = new Node();
            last.item = item;
            last.previous = beforeLast;
            beforeLast.next = last;
            n++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("deque is empty");
        Node deleteNode = first;
        if (size() != 1) {
            first = first.next;
            first.previous = null;
            deleteNode.next = null;
        } else {
            first = null;
            last = null;
        }
        n--;
        return deleteNode.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("deque is empty");
        Node oldLast = last;
        if (size() != 1) {
            last = oldLast.previous;
            oldLast.previous = null;
            last.next = null;
        } else {
            last = null;
            first = null;
        }
        n--;
        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No next elements");
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Removing is not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addLast("OK");
        deque.addLast("ME");
        deque.addLast("NEE");
        deque.addFirst("HEHEHE");

        deque.removeLast();
        deque.removeLast();
        deque.removeFirst();

        System.out.println("*****");
        for (String s : deque) {
            System.out.println(s + " !");
        }

        System.out.println(deque.isEmpty());
        System.out.println(deque.size());
    }
}