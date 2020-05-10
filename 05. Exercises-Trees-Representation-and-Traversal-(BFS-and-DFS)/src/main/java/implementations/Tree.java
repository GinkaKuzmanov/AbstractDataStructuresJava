package implementations;

import interfaces.AbstractTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {

    private final E value;
    private Tree<E> parent;
    private final List<Tree<E>> children;

    @SafeVarargs
    public Tree(E key, Tree<E>... children) {
        this.value = key;
        this.children = new ArrayList<>();

        for (Tree<E> child : children) {
            child.setParent(this);
            this.children.add(child);
        }

    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.value;
    }

    @Override
    public String getAsString() {
        StringBuilder builder = new StringBuilder();

        traverseTreeRecursively(builder, 0, this);

        return builder.toString().trim();
    }

    private void traverseTreeRecursively(StringBuilder builder, int indent, Tree<E> tree) {
        builder
                .append(this.getPadding(indent))
                .append(tree.getKey())
                .append(System.lineSeparator());

        for (Tree<E> child : tree.children) {
            traverseTreeRecursively(builder, indent + 2, child);
        }
    }

    private String getPadding(int size) {
        return " ".repeat(Math.max(0, size));
    }

    @Override
    public List<E> getLeafKeys() {
        return traverseBFS().stream()
                .filter(eTree -> eTree.children.size() == 0)
                .map(Tree::getKey)
                .collect(Collectors.toList());
    }

    private List<Tree<E>> traverseBFS() {
        Deque<Tree<E>> treeDeque = new ArrayDeque<>();
        treeDeque.offer(this);
        List<Tree<E>> allNodes = new ArrayList<>();

        while (!treeDeque.isEmpty()) {
            Tree<E> currentTree = treeDeque.poll();
            allNodes.add(currentTree);
            for (Tree<E> child : currentTree.children) {
                treeDeque.offer(child);
            }
        }
        return allNodes;
    }

    @Override
    public List<E> getMiddleKeys() {
        List<Tree<E>> allNodes = new ArrayList<>();
        traverseDFS(allNodes, this);

        return allNodes.stream()
                .filter(tree -> tree.parent != null && tree.children.size() > 0)
                .map(Tree::getKey)
                .collect(Collectors.toList());
    }

    private void traverseDFS(List<Tree<E>> collection, Tree<E> tree) {
        collection.add(tree);
        for (Tree<E> child : tree.children) {
            traverseDFS(collection, child);
        }
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        List<Tree<E>> trees = this.traverseBFS();

        int maxPath = 0;

        Tree<E> leftMostNode = null;

        for (Tree<E> tree : trees) {
            if (tree.isLeaf()) {
                int currentPath = this.getStepsFromLeafToRoot(tree);
                if (currentPath > maxPath) {
                    maxPath = currentPath;
                    leftMostNode = tree;
                }

            }
        }
        return leftMostNode;
    }

    private int getStepsFromLeafToRoot(Tree<E> tree) {
        int counter = 0;
        Tree<E> current = tree;
        while (current.parent != null) {
            counter++;
            current = current.parent;
        }
        return counter;
    }

    private boolean isLeaf() {
        return this.parent != null && this.children.size() == 0;
    }

    @Override
    public List<E> getLongestPath() {
        List<Tree<E>> allTrees = this.traverseBFS();
        int maxPath = -1;

        List<E> toReturn = new ArrayList<>();
        for (Tree<E> tree : allTrees) {
            int currentPath = this.getStepsFromLeafToRoot(tree);
            if (currentPath > maxPath) {
                toReturn.add(tree.getKey());
                maxPath = currentPath;
            }
        }

        return toReturn;
    }


    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        int s = 0;

        List<List<E>> results = new ArrayList<>();

        List<Tree<E>> treesWithSum = this.traverseBFS();

        List<E> temp = new ArrayList<>();

        for (Tree<E> current : treesWithSum) {
            s += (int) current.getKey();
            if (s < sum) {
                temp.add(current.value);
            }

            if (s == sum) {
                results.add(temp);
                temp.clear();
            }
        }

        return results;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        int s = 0;
        List<Tree<E>> allTrees = this.traverseBFS();
        List<Tree<E>> subTrees = new ArrayList<>();
        for (Tree<E> tree : allTrees) {
            subTrees.add(tree);
            s += (int) tree.value;
            for(Tree<E> children : tree.children){
                s += (int)children.value;
                if(s<=sum){
                    subTrees.add(children);
                }
            }
        }
        return subTrees;
    }
}



