package implementations;

import interfaces.LinkedList;

import java.util.Iterator;

public class DoublyLinkedList<E> implements LinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    private static class Node<E> {
        private E element;
        private Node<E> previous;
        private Node<E> next;

        public Node(E value) {
            this.element = value;
        }
    }

    public DoublyLinkedList() {
    }

    @Override
    public void addFirst(E element) {
        // new head:newNode -><- ordinaryNode!=head <- -> null
        Node<E> nodeToAdd = new Node<>(element);
        if (this.head != null) {
            nodeToAdd.next = this.head;
            this.head.previous = nodeToAdd;
            this.head = nodeToAdd;
        }
        this.head = this.tail = nodeToAdd;

        this.size++;
    }

    @Override
    public void addLast(E element) {
        if (this.size() == 0) {
            this.addFirst(element);
        } else {
            Node<E> nodeToAdd = new Node<>(element);
            this.tail.next = nodeToAdd;
            nodeToAdd.previous = this.tail;
            this.tail = nodeToAdd;
            this.size++;
        }
    }

    @Override
    public E removeFirst() {
        ensureNotEmpty();
        E element = this.head.element;
        if (this.size == 1) {
            this.head = this.tail = null;
        } else {
            Node<E> newHead = this.head.next;
            newHead.previous = null;
            this.head.next = null;
            this.head = newHead;
        }
        this.size--;
        return element;
    }

    private void ensureNotEmpty() {
        if (this.size == 0) {
            throw new IllegalStateException("Illegal remove for empty LinkedList");
        }
    }

    @Override
    public E removeLast() {
        ensureNotEmpty();
        if (this.size == 1) {
            return removeFirst();
        }
        E element = this.tail.element;

        Node<E> currentTail = this.tail;

        this.tail = currentTail.previous;
        this.tail.next = null;


        this.size--;

        return element;
    }

    @Override
    public E getFirst() {
        ensureNotEmpty();
        return this.head.element;
    }

    @Override
    public E getLast() {
        ensureNotEmpty();
        return this.tail.element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != tail;
            }

            @Override
            public E next() {
                E element = current.element;
                current = current.next;
                return element;
            }
        };
    }
}
