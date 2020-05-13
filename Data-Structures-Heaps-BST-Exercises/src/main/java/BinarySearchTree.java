import solutions.BinaryTree;

import java.util.function.Consumer;

import java.util.List;

//variation of binary tree,we keep order sorted
public class BinarySearchTree<E extends Comparable<E>> {
    private Node<E> root;

    public BinarySearchTree(E element) {
        this.root = new Node<>(element);
    }

    public static class Node<E> {
        private E value;

        private Node<E> leftChild;

        private Node<E> rightChild;

        public Node(E value) {
            this.value = value;
        }

        public Node<E> getLeft() {
            return this.leftChild;
        }

        public Node<E> getRight() {
            return this.rightChild;
        }

        public E getValue() {
            return this.value;
        }
    }

    public void eachInOrder(Consumer<E> consumer) {
        nodeInOrder(this.root, consumer);
    }

    private void nodeInOrder(Node<E> node, Consumer<E> consumer) {
        if(node == null) { return;}

        this.nodeInOrder(node.getLeft(),consumer);
        consumer.accept(node.getValue());
        this.nodeInOrder(node.getRight(), consumer);
    }

    public Node<E> getRoot() {
        return this.root;
    }

    public void insert(E element) {
        insertInto(this.root, element);
    }

    private void insertInto(Node<E> node, E element) {
        if (isGreater(element, node)) {
            if (node.getRight() == null) {
                node.rightChild = new Node<>(element);
            } else {
                insertInto(node.getRight(), element);
            }
        } else if (isSmaller(element, node)) {
            if (node.getLeft() == null) {
                node.leftChild = new Node<>(element);
            } else {
                insertInto(node.getLeft(), element);
            }
        }

    }

    public boolean contains(E element) {
        return false;
    }

    public BinarySearchTree<E> search(E element) {
        return null;
    }

    public List<E> range(E first, E second) {
        return null;
    }

    public void deleteMin() {

    }

    public void deleteMax() {

    }

    public int count() {
        return 0;
    }

    public int rank(E element) {
        return 0;
    }

    public E ceil(E element) {
        return null;
    }

    public E floor(E element) {
        return null;
    }

    private boolean isEqual(E element, Node<E> node) {
        return element.compareTo(node.getValue()) == 0;
    }

    private boolean isGreater(E element, Node<E> node) {
        return element.compareTo(node.getValue()) > 0;
    }

    private boolean isSmaller(E element, Node<E> node){
        return element.compareTo(node.getValue()) < 0;
    }
}
