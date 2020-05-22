import solutions.BinaryTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.function.Consumer;

import java.util.List;

//variation of binary tree,we keep order sorted
public class BinarySearchTree<E extends Comparable<E>> {
    private Node<E> root;


    public BinarySearchTree(E element) {
        this.root = new Node<>(element);

    }

    public BinarySearchTree(Node<E> copiedRoot) {
        this.root = new Node<>(copiedRoot);
    }

    public BinarySearchTree() {

    }

    public static class Node<E> {
        private E value;
        private Node<E> leftChild;
        private Node<E> rightChild;
        private int count;

        public Node(E value) {
            this.value = value;
            this.count = 1;
        }

        public Node(Node<E> otherNode) {
            this.value = otherNode.value;
            this.count = otherNode.count;

            if (otherNode.getLeft() != null) {
                this.leftChild = new Node<>(otherNode.getLeft());
            }

            if (otherNode.getRight() != null) {
                this.rightChild = new Node<>(otherNode.getRight());
            }
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

        public int getCount() {
            return count;
        }
    }

    public void eachInOrder(Consumer<E> consumer) {
        nodeInOrder(this.root, consumer);
    }

    private void nodeInOrder(Node<E> node, Consumer<E> consumer) {
        if (node == null) {
            return;
        }

        this.nodeInOrder(node.getLeft(), consumer);
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

        node.count++;
    }

    public boolean contains(E element) {
        //reflective approach
        return containsNode(this.root, element);
    }

    private boolean containsNode(Node<E> node, E element) {
        if (node == null) {
            return false;
        }
        if (isEqual(element, node)) {
            return true;
        } else if (isGreater(element, node)) {
            return containsNode(node.getRight(), element);
        }

        return containsNode(node.getLeft(), element);
    }

    public BinarySearchTree<E> search(E element) {
        Node<E> found = searchedNode(this.root, element);
        return found == null ? null : new BinarySearchTree<>(found);
    }

    private Node<E> searchedNode(Node<E> node, E element) {
        if (node == null) {
            return null;
        }
        if (isEqual(element, node)) {
            return node;

        } else if (isGreater(element, node)) {

            return searchedNode(node.getRight(), element);
        }
        return searchedNode(node.getLeft(), element);
    }

    public List<E> range(E lower, E upper) {
        List<E> range = new ArrayList<>();
        Deque<Node<E>> queue = new ArrayDeque<>();

        queue.offer(this.root);

        while (!queue.isEmpty()) {
            Node<E> current = queue.poll();
            if (current.getLeft() != null) {
                queue.offer(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.offer(current.getRight());
            }

            if (isSmaller(lower, current) && isGreater(upper, current)) {
                range.add(current.getValue());
            } else if (isEqual(lower, current) || isEqual(upper, current)) {
                range.add(current.getValue());
            }
        }

        return range;
    }

    public void deleteMin() {
        ensureValidState();

        if (this.root.getLeft() == null) {
            this.root = this.root.getRight();
            return;
        }

        Node<E> currentNode = this.root;

        while (currentNode.getLeft().getLeft() != null) {
            currentNode.count--;
            currentNode = currentNode.getLeft();
        }
        currentNode.count--;
        currentNode.leftChild = currentNode.getLeft().getRight();
    }

    public void deleteMax() {
        ensureValidState();

        if (this.root.getRight() == null) {
            this.root = this.root.getLeft();
            return;
        }
        Node<E> current = this.root;
        while (current.getRight().getRight() != null) {
            current.count--;
            current = current.getRight();
        }

        current.count--;
        current.rightChild = current.getRight().getLeft();
    }

    public int count() {
        return this.root == null ? 0 : this.root.count;
    }

    public int rank(E element) {
        return nodeRank(this.root, element);
    }

    private int nodeRank(Node<E> node, E element) {
        if (node == null) {
            return 0;
        }

        if (isSmaller(element, node)) {
            return nodeRank(node.getLeft(), element);
        } else if (isEqual(element, node)) {
            return getNodeCount(node.getLeft());
        }

        return getNodeCount(node.getLeft()) + 1 + nodeRank(node.getRight(), element);
    }

    private int getNodeCount(Node<E> node) {
        return node == null ? 0 : node.getCount();
    }

    public E ceil(E element) {
        if (this.root == null) {
            return null;
        }
        Node<E> current = this.root;
        Node<E> nearestBigger = null;

        while (current != null) {
            if (isSmaller(element, current)) {
                nearestBigger = current;
                current = current.getLeft();
            } else if (isGreater(element, current)) {
                current = current.getRight();
            } else {
                Node<E> right = current.getRight();
                if (right != null && nearestBigger != null) {
                    nearestBigger = isSmaller(right.getValue(), nearestBigger) ? right : nearestBigger;
                } else if (nearestBigger == null) {
                    nearestBigger = right;
                }
                break;
            }
        }
        return nearestBigger == null ? null : nearestBigger.getValue();
    }

    public E floor(E element) {
        if (this.root == null) {
            return null;
        }
        Node<E> current = this.root;
        Node<E> nearestSmaller = null;

        while (current != null) {
            if (isGreater(element, current)) {
                nearestSmaller = current;
                current = current.getRight();
            } else if (isSmaller(element, current)) {
                current = current.getLeft();
            } else {
                Node<E> left = current.getLeft();
                if (left != null && nearestSmaller != null) {
                    nearestSmaller = isGreater(left.getValue(), nearestSmaller) ? left : nearestSmaller;
                } else if (nearestSmaller == null) {
                    nearestSmaller = left;
                }
                break;
            }
        }
        return nearestSmaller == null ? null : nearestSmaller.getValue();
    }

    //utility methods
    private boolean isEqual(E element, Node<E> node) {
        return element.compareTo(node.getValue()) == 0;
    }

    private boolean isGreater(E element, Node<E> node) {
        return element.compareTo(node.getValue()) > 0;
    }

    private boolean isSmaller(E element, Node<E> node) {
        return element.compareTo(node.getValue()) < 0;
    }

    private void ensureValidState() {
        if (this.root == null) {
            throw new IllegalStateException();
        }
    }
}
