package implementations;

import interfaces.AbstractQueue;

import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {
    //begin
    private Node<E> head;
    //end
    private Node<E> tail;
    //size
    private int size;

    private static class Node<E> {
        private E element;
        private Node<E> next;

        private Node(E element) {
            this.element = element;
        }
    }

    public Queue() {
    }

    @Override
    public void offer(E element) {
        //fifo// first in, first out
        //head -> tail:newNode1 = tail.next -> newNode2 = tail->tail.next = newNode3=tail ->
        Node<E> nodeToOffer = new Node<>(element);
        if (this.head == null) {
            this.head = this.tail = nodeToOffer;
        } else {
            //problem with complexity
            //head ne e prazno, ima node
            this.tail.next = nodeToOffer;
            this.tail = nodeToOffer;
        }
        this.size++;
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        E element = this.head.element;
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            Node<E> next = this.head.next;
            this.head.next = null;
            this.head = next;
        }
        this.size--;
        return element;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.head.element;
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
                return this.current != tail;
            }

            @Override
            public E next() {
                E element = this.current.element;
                this.current = this.current.next;
                return element;
            }
        };
    }

    private void ensureNonEmpty() {
        if (this.size == 0) {
            throw new IllegalStateException("Illegal operation on empty stack");
        }
    }
}
