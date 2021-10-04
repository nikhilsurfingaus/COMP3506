import javax.swing.tree.TreeNode;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * An iterator for the tree ADT that performs a preorder traversal
 */
public class TreeIterator<E> implements Iterator<E> {
    private Tree<E> currentNode;
    private  Stack<Tree<E>> stack = new Stack<Tree<E>>();
    /**
     * Constructs a new tree iterator from the root of the tree to iterate over
     *
     * You are welcome to modify this constructor but cannot change its signature
     * This method should have O(1) time complexity
     *
     * @param root A Tree<E> type to iterate over
     *
     */
    public TreeIterator(Tree<E> root) {
        this.currentNode = root;
        if (this.currentNode != null) {
            //intial push of root to stack
            this.stack.push(this.currentNode);
        }

    }
    /**
     * Returns true if the iteration has more elements returns true if next()
     *  would return an element rather than throwing an exception.
     *
     *  The worst case time complexity of calling this is O(1) time, since the
     *  isEmpty call to check if the stack is runs in linear time
     *
     * @return boolean true if the tree has children, will return false if this
     * condition is not met.
     *
     */
    @Override
    public boolean hasNext() {
        return !this.stack.isEmpty();
    }
    /**
     * Returns the next root for the current root
     * The stack if has two children for the current root must add
     * the right child before the left so when the stack is popped the left
     * child is traversed first the right hand side is popped second for any
     * tree.
     *
     * The worst case run time of the hasNext with a single call
     * incorporates having a sequence of operations occurs in worst case the
     * binary tree can also be represented as a graph. The single call pre order
     * traversal would be O(x + n), where n is the number of nodes in the
     * binary tree and x is the number (edges) from the root to the node.
     * Because both the number of root nodes and edges of any tree have a
     * time complexity for x and n that is linear, this worst case would be
     * having to traverse to a edge that exist and the root node that has
     * two children. hence an overall worst case time complexity of O(n).
     * However looking at the amortised time complexity each occurrence of
     * this method this only traverses to the child below the current root,
     * which is in O(1). Thus, the amortised time complexity would differ
     * than worst case when the next is below the root.
     *
     * @return next root for the current root if the stack is non empty,
     *
     */
    @Override
    public E next() {
        //only traverse through the tree if the stack is not empty
        //next time we pop we will get the left subtree
        if (hasNext()) {
            //The current node which to operate on, as well its number of
            // children to determine the pre-order process
            this.currentNode = this.stack.pop();
            int nodeChildSize = this.currentNode.getChildren().size();

            //cases for different types of trees which iterate over
            switch (nodeChildSize) {
                case 1 :
                    //Since a pre-order, no right child push the left first
                    stack.push(this.currentNode.getChildren().get(0));
                    break;
                case 2 :
                    //since no recursion need to push the right child first
                    this.stack.push(this.currentNode.getChildren().get(1));
                    break;
                default:
                     break;
            }
        }
        //return the next root for the current node
        return this.currentNode.getRoot();
    }
}
