import javax.swing.tree.TreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Ch9BinaryTree {
    public class BinaryTreeNode<T> {
        public T data;
        public BinaryTreeNode<T> left, right;

        public BinaryTreeNode(T data, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }


    public static void TreeTraversal(BinaryTreeNode<Integer> root) {
        if (root != null) {
            System.out.println("PreOrder: " + root.data);

            TreeTraversal(root.left);

            System.out.println("InOrder: " + root.data);

            TreeTraversal(root.right);

            System.out.println("PostOrder: " + root.data);
        }
    }

    public static class BalanceStatusWithHeight { // artificial tuple
        public boolean balanced;
        public int height;

        public BalanceStatusWithHeight(boolean balanced, int height) {
            this.balanced = balanced;
            this.height = height;
        }
    }

    public static boolean isBalanced(BinaryTreeNode<Integer> root) {
        return isBalancedHelper(root).balanced;
    }

    private static BalanceStatusWithHeight isBalancedHelper(BinaryTreeNode<Integer> root) {
        if (root == null) return new BalanceStatusWithHeight(true, -1);

        BalanceStatusWithHeight leftResult = isBalancedHelper(root.left);
        if (!leftResult.balanced) return leftResult;

        BalanceStatusWithHeight rightResult = isBalancedHelper(root.right);
        if (!rightResult.balanced) return rightResult;

        int height = Math.max(leftResult.height, rightResult.height) + 1;
        boolean isBalanced;
        if (Math.abs(leftResult.height - rightResult.height) > 1) isBalanced = false;
        else isBalanced = true;

        return new BalanceStatusWithHeight(isBalanced, height);
    }


    public static boolean isSymeetric(BinaryTreeNode<Integer> tree) {
        return tree == null || isSymeetricHelper(tree.left, tree.right);
    }

    public static boolean isSymeetricHelper(BinaryTreeNode<Integer> subtree1, BinaryTreeNode<Integer> subtree2) {
        if (subtree1 == null && subtree2 != null) return false;
        else if (subtree1 != null && subtree2 == null) return false;
        else if (subtree1 == null && subtree2 == null) return true;
        else {
            if (subtree1.data != subtree2.data) return false;
            return isSymeetricHelper(subtree1.left, subtree2.right) && isSymeetricHelper(subtree1.right, subtree2.left);
        }
    }

    private static class Status{
        public int numTargetNodes;
        public BinaryTreeNode<Integer> ancestor;

        public Status (int numTargetNodes, BinaryTreeNode<Integer> ancestor) {
            this.numTargetNodes = numTargetNodes;
            this.ancestor = ancestor;
        }
    }

    public static BinaryTreeNode<Integer> LCA (BinaryTreeNode<Integer> tree,
                                               BinaryTreeNode<Integer> node0,
                                               BinaryTreeNode<Integer> node1) {
        return LCAHelper(tree, node0, node1).ancestor;
    }

    // Node without parent node
    private static Status LCAHelper(BinaryTreeNode<Integer> tree,
                                    BinaryTreeNode<Integer> node0,
                                    BinaryTreeNode<Integer> node1) {
        if (tree == null) return new Status(0, null);

        Status leftResult = LCAHelper(tree.left, node0, node1);
        if (leftResult.numTargetNodes == 2) return leftResult;

        Status rightResult = LCAHelper(tree.right, node0, node1);
        if (rightResult.numTargetNodes == 2) return rightResult;

        int numTargetNodes = leftResult.numTargetNodes + rightResult.numTargetNodes + // and this remember the previous result
                (tree == node0 ? 1 : 0) + (tree == node1 ? 1 : 0); // the finding process is here

        return new Status(numTargetNodes, (numTargetNodes == 2 ? tree : null));
    }

    // tree with parent field
    private class BinaryTree<T> {
        int data;
        BinaryTree<T> parent;
        BinaryTree<T> left, right;

    }

    public static BinaryTree<Integer> LCAwParent(BinaryTree<Integer> node0, BinaryTree<Integer> node1) {
        int depth0  = getDepth(node0), depth1 = getDepth(node1);

        if (depth0 > depth1) { // let 1 be the node that has the deepest depth
            BinaryTree<Integer> tmp = node0;
            node0 = node1;
            node1 = tmp;
        }

        int depthDiff = Math.abs(depth0 - depth1);
        while(depthDiff-- > 0) node0 = node0.parent;

        while(node0 != node1) {
            node0 = node0.parent;
            node1 = node1.parent;
        }

        return node0;
    }

    public static int getDepth(BinaryTree<Integer> node) {
        int depth = 0;
        while(node.parent != null) {
            node = node.parent;
            depth++;
        }

        return depth;
    }

    public static int sumRootToLeaf(BinaryTreeNode<Integer> tree) {
        return sumRootToLeafHelper(tree, 0);
    }

    private static int sumRootToLeafHelper(BinaryTreeNode<Integer> tree, int sum) {
        if (tree == null) return 0;

        sum = sum * 2 + tree.data;

        if (tree.left == null && tree.right == null) // reduce unnecessary function call;
            return sum;

        return sumRootToLeafHelper(tree.left, sum) + sumRootToLeafHelper(tree.right, sum);
    }
    // should be simple, I overthinked
    private static int sumRootToLeafHelper2(BinaryTreeNode<Integer> tree, int sum) {
        if (tree == null) return 0;

        sum = sum * 2 + tree.data;

        return sumRootToLeafHelper2(tree.left, sum) + sumRootToLeafHelper2(tree.right, sum);
    }

    public static boolean hasPathSum(BinaryTreeNode<Integer> tree, int remainingWeight) {
        if (tree == null && remainingWeight == 0) return true;
        if (tree == null && remainingWeight != 0) return false;

        if (remainingWeight < 0) return false; // midway termination

        return hasPathSum(tree.left, remainingWeight - tree.data) ||
                hasPathSum(tree.right, remainingWeight - tree.data);
    }

    // the mystery of writing DFS with iteration
    public static List<Integer> BSTInSortedOrder(BinaryTreeNode<Integer> tree) {
        Deque<BinaryTreeNode<Integer>> s = new ArrayDeque<>();
        BinaryTreeNode<Integer> curr = tree;
        List<Integer> result = new ArrayList<>();

        while(!s.isEmpty() || curr != null) {
            if (curr != null) {
                s.addFirst(curr);
                // going left
                curr = curr.left;
            } else { // hit a null
                // going up
                curr = s.removeFirst();
                result.add(curr.data);
                // going right
                curr = curr.right;
            }
        }

        return result;
    }

    public static List<Integer> preOrderTraversal(BinaryTreeNode<Integer> tree) {
        Deque<BinaryTreeNode<Integer>> path = new ArrayDeque<>();

        if (tree != null) path.addFirst(tree);

        List<Integer> result = new ArrayList<>();

        while(!path.isEmpty()) {
            BinaryTreeNode<Integer> curr = path.removeFirst();
            result.add(curr.data);

            if (curr.right != null) path.addFirst(curr.right);
            if (curr.left != null) path.addFirst(curr.left); // in this case, left node is popped first
        }

        return result;
    }


    public static List<Integer> TraversalWithIteration(BinaryTreeNode<Integer> tree) {
        Deque<BinaryTreeNode<Integer>> path = new ArrayDeque<>();

        if (tree != null) path.addFirst(tree);

        List<Integer> inOrder = new ArrayList<>();
        List<Integer> preOrder = new ArrayList<>();
        List<Integer> postOrder = new ArrayList<>();


        while(!path.isEmpty()) {
            BinaryTreeNode<Integer> curr = path.removeFirst();
            if (curr.left == null) inOrder.add(curr.data);
            if (curr.right == null) postOrder.add(curr.data);
            preOrder.add(curr.data);

            if (curr.right != null) path.addFirst(curr.right);
            if (curr.left != null) path.addFirst(curr.left); // in this case, left node is popped first
        }

        return inOrder;
    }



}
