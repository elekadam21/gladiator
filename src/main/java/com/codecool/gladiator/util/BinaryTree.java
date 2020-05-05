package com.codecool.gladiator.util;

import java.util.List;

/**
 * Custom implementation of the binary tree data structure
 *
 * @param <T> the type of data on the leaves
 */
public class BinaryTree<T> {

    private T value;
    private BinaryTree<T> leftBranch;
    private BinaryTree<T> rightBranch;
    private int size;

    /**
     * Default constructor
     */
    public BinaryTree() {
    }

    /**
     * Constructor with initial value
     *
     * @param value the initial value to be added to the tree
     */
    public BinaryTree(T value) {
        // Todo
    }

    /**
     * Constructor with initial list of values
     *
     * @param values the list of values to be added to the tree
     */
    public BinaryTree(List<T> values) {
        // Todo
    }

    /**
     * Getter for the value (must be null if there are further branches)
     *
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * Getter for the left branch
     *
     * @return the left branch
     */
    public BinaryTree<T> getLeftBranch() {
        return leftBranch;
    }

    /**
     * Getter for the right branch
     *
     * @return the right branch
     */
    public BinaryTree<T> getRightBranch() {
        return rightBranch;
    }

    /** Returns the number of values put in the tree
     *
     * @return the size of the tree
     */
    public int size() {
        return size;
    }

    /**
     * Adds a new value to the tree
     *
     * @param value the value to be added to the tree
     */
    public void add(T value) {
        // Todo
    }

    /**
     * Adds multiple values to the tree
     *
     * @param values the list of values to be added to the tree
     */
    public void addAll(List<T> values) {
        // Todo
    }

}
