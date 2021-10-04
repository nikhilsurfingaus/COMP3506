import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A binary tree, where each node contains at most two children
 * Each root node contains a value and references to its left and right children (if they exist)
 *
 * @param <E> the type of the tree's elements
 */
public class BinaryTree<E> implements Tree<E> {
    private E root; // the element sitting at this tree's root node
    private BinaryTree<E> left; // the left child (subtree)
    private BinaryTree<E> right; // the right child (subtree)

    /**
     * Constructs a new binary tree with a single node
     *
     * @param root the element to store at this tree's root
     */
    public BinaryTree(E root) {
        this.root = root;
        this.left = null;
        this.right = null;
    }

    @Override
    public int size() {
        return 1 + (left != null ? left.size() : 0) + (right != null ? right.size() : 0);
    }

    @Override
    public E getRoot() {
        return root;
    }

    @Override
    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public List<Tree<E>> getChildren() {
        List<Tree<E>> children = new ArrayList<>();
        if (left != null) {
            children.add(left);
        }
        if (right != null) {
            children.add(right);
        }
        return children;
    }

    @Override
    public boolean contains(E elem) {
        if (root.equals(elem)) {
            return true;
        }
        return (left != null && left.contains(elem)) || (right != null && right.contains(elem));
    }

    @Override
    public Iterator<E> iterator() {
        return new TreeIterator<>(this);
    }

    /**
     * Sets the left child of this tree's root to the given subtree
     * Any existing left child will be overridden
     *
     * @param left the new left child
     */
    public void setLeft(BinaryTree<E> left) {
        this.left = left;
    }

    /**
     * Sets the right child of this tree's root to the given subtree
     * Any existing right child will be overridden
     *
     * @param right the new right child
     */
    public void setRight(BinaryTree<E> right) {
        this.right = right;
    }

    /**
     * @return the left child subtree of this tree's root node
     */
    public BinaryTree<E> getLeft() {
        return left;
    }

    /**
     * @return the right child subtree of this tree's root node
     */
    public BinaryTree<E> getRight() {
        return right;
    }

    /**
     * Determines whether the parameter tree is a binary search tree or not
     * This is determined by the definition of a binary search tree provided in
     * the lectures
     *
     * Search all nodes and edges of the tree to ensure the properties of a
     * Binary search tree aren't violated.
     *
     * The run time complexity of the isBST method single call on this
     * recursive method in worst case with a BinaryTree of n nodes would have
     * 2 (n - 1) calls hence can be modelled as O(n) + T(n-1) + T(n-1) where
     * n > 1. Since each node calls the recursive method twice and keeps
     * splitting into 2 more subtrees we have an exponential relation of
     * 2^(n-1) where we need to check both sides of the binary search tree
     * twice. Hence the worst case time complexity can be modelled as
     * T(n) = 2^(n) which is exponential.
     *
     * @param tree the Binary tree to check whether its a valid BST
     * @return true if this tree is a BST, otherwise false
     *
     */
    public static <T extends Comparable<T>> boolean isBST(BinaryTree<T> tree) {
        //The node current parent node
        T top = tree.getRoot();
        //left and right child nodes subtrees
        BinaryTree<T> leftTree =  tree.getLeft();
        BinaryTree<T> rightTree =  tree.getRight();

        //If the nodes are empty this is a BST
        if (top == null || (leftTree == null && rightTree == null)) {
            return true;
        }
        //The left subtree must be nodes with keys less than the nodes key
        if (leftTree.getRoot() != null &&
                leftTree.getRoot().compareTo(top) != -1) {
            return false;
        }
        //The right subtree must be nodes with keys greater than the nodes key
        if (rightTree.getRoot() != null &&
                rightTree.getRoot().compareTo(top) != 1) {
            return false;
        }
        //recursively call this method so both the left and right hand side of
        // the Binary Tree are a valid BST.
        return isBST(leftTree) && isBST(rightTree);
    }
}
